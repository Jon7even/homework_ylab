package com.github.jon7even.services.impl;

import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.dao.WorkoutDao;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutUpdateDto;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import com.github.jon7even.core.domain.v1.exception.*;
import com.github.jon7even.core.domain.v1.mappers.WorkoutMapper;
import com.github.jon7even.core.domain.v1.mappers.WorkoutMapperImpl;
import com.github.jon7even.dataproviders.configuration.ConfigLoader;
import com.github.jon7even.dataproviders.inmemory.WorkoutRepository;
import com.github.jon7even.dataproviders.jdbc.UserJdbcRepository;
import com.github.jon7even.services.DiaryService;
import com.github.jon7even.services.GroupPermissionsService;
import com.github.jon7even.services.TypeWorkoutService;
import com.github.jon7even.services.WorkoutService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions.READ;

/**
 * Реализация сервиса взаимодействия с тренировками
 *
 * @author Jon7even
 * @version 1.0
 */
public class WorkoutServiceImpl implements WorkoutService {
    private static WorkoutServiceImpl instance;
    private static final Integer SERVICE_WORKOUT_ID = 3;
    private final UserDao userRepository;
    private final TypeWorkoutService typeWorkoutService;
    private final WorkoutDao workoutRepository;
    private final DiaryService diaryService;
    private final WorkoutMapper workoutMapper;
    private final GroupPermissionsService groupPermissionsService;

    public static WorkoutServiceImpl getInstance() {
        if (instance == null) {
            instance = new WorkoutServiceImpl();
        }
        return instance;
    }

    private WorkoutServiceImpl() {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        this.userRepository = new UserJdbcRepository(configLoader.getConfig());
        this.workoutRepository = WorkoutRepository.getInstance();
        this.diaryService = DiaryServiceImpl.getInstance();
        this.typeWorkoutService = TypeWorkoutServiceImpl.getInstance();
        this.workoutMapper = new WorkoutMapperImpl();
        this.groupPermissionsService = GroupPermissionsServiceImpl.getInstance();
    }

    @Override
    public WorkoutFullResponseDto saveWorkout(WorkoutCreateDto workoutCreateDto) {
        System.out.println("К нам пришла на сохранение новая тренировка: " + workoutCreateDto);

        validateWorkoutByTypeToday(
                workoutCreateDto.getIdTypeWorkout(), workoutCreateDto.getTimeStartOn().toLocalDate()
        );
        WorkoutEntity workoutEntityForSaveInRepository = workoutMapper.toWorkoutEntityFromDtoCreate(
                workoutCreateDto
        );
        System.out.println("Тренировка для сохранения собрана: " + workoutEntityForSaveInRepository);

        WorkoutEntity createdWorkout = workoutRepository.createWorkout(workoutEntityForSaveInRepository)
                .orElseThrow(() -> new NotCreatedException("New Workout"));
        System.out.println("Новая тренировка сохранена: " + createdWorkout);

        TypeWorkoutResponseDto typeWorkoutResponseDto =
                typeWorkoutService.findTypeWorkoutByTypeWorkoutId(createdWorkout.getIdTypeWorkout());
        return workoutMapper.toWorkoutFullResponseDtoFromEntity(createdWorkout, typeWorkoutResponseDto);
    }

    @Override
    public WorkoutFullResponseDto getWorkoutById(Long workoutId) {
        System.out.println("Начинаю получать тренировку по workoutId=" + workoutId);
        WorkoutEntity foundWorkoutEntity = getWorkoutEntityById(workoutId);
        TypeWorkoutResponseDto typeWorkoutResponseDto =
                typeWorkoutService.findTypeWorkoutByTypeWorkoutId(foundWorkoutEntity.getIdTypeWorkout());
        return workoutMapper.toWorkoutFullResponseDtoFromEntity(foundWorkoutEntity, typeWorkoutResponseDto);
    }

    @Override
    public void deleteWorkoutByWorkoutIdAndOwnerId(Long workoutId, Long requesterId) {
        System.out.println("Пользователь requesterId=" + requesterId
                + "шлет запрос на удаление своей тренировки по workoutId=" + workoutId);
        isOwnerWorkout(getWorkoutEntityById(workoutId), requesterId);
        workoutRepository.deleteWorkoutByWorkoutId(workoutId);
    }

    @Override
    public boolean isExistWorkoutByWorkoutId(Long workoutId) {
        System.out.println("Проверяю существует ли тренировка с workoutId=" + workoutId);
        return workoutRepository.findByWorkoutByWorkoutId(workoutId).isPresent();
    }

    @Override
    public WorkoutFullResponseDto updateWorkout(WorkoutUpdateDto workoutUpdateDto) {
        System.out.println("Начинаем обновлять тренировку, вот что нужно обновить: " + workoutUpdateDto);
        WorkoutEntity workoutEntityForUpdate = getWorkoutEntityById(workoutUpdateDto.getId());
        LocalDate localDateOldWorkout = workoutEntityForUpdate.getTimeStartOn().toLocalDate();
        workoutMapper.updateWorkoutEntityFromDtoUpdate(workoutEntityForUpdate, workoutUpdateDto);

        if (!localDateOldWorkout.equals(workoutEntityForUpdate.getTimeStartOn().toLocalDate())) {
            validateWorkoutByTypeToday(
                    workoutUpdateDto.getIdTypeWorkout(), workoutEntityForUpdate.getTimeStartOn().toLocalDate()
            );
        }

        System.out.println("Сохраняю получившуюся тренировку: " + workoutUpdateDto);
        WorkoutEntity updatedWorkoutEntity = workoutRepository.updateWorkout(workoutEntityForUpdate)
                .orElseThrow(() -> new NotUpdatedException(workoutUpdateDto.toString()));
        System.out.println("Тренировка обновлена: " + updatedWorkoutEntity);

        TypeWorkoutResponseDto typeWorkoutResponseDto =
                typeWorkoutService.findTypeWorkoutByTypeWorkoutId(updatedWorkoutEntity.getIdTypeWorkout());
        return workoutMapper.toWorkoutFullResponseDtoFromEntity(updatedWorkoutEntity, typeWorkoutResponseDto);
    }

    @Override
    public List<WorkoutShortResponseDto> findAllWorkoutByOwnerDiaryBySortByDeskDate(Long idDiary, Long requesterId) {
        isExistUserOrThrowException(requesterId);
        List<WorkoutEntity> listFoundWorkouts = getListWorkoutEntityNotSort(idDiary);
        if (!listFoundWorkouts.isEmpty()) {
            isOwnerWorkout(listFoundWorkouts.stream().findFirst()
                            .orElseThrow(() -> new NotFoundException(String.format("Workouts [idDiary=%s]", idDiary))),
                    requesterId);
        }
        List<WorkoutEntity> sortedListWorkouts = sortListWorkoutsByDataDesk(listFoundWorkouts);
        System.out.println("Начинаю маппить и возвращать");
        return workoutMapper.toListWorkoutShortResponseDtoFromEntity(sortedListWorkouts);
    }

    @Override
    public List<WorkoutShortResponseDto> findAllWorkoutByAdminDiaryBySortByDeskDate(Long userId, Long requesterId) {
        System.out.println("requesterId=" + requesterId
                + "хочет получить список тренировок пользователя с userId=" + userId);
        isExistUserOrThrowException(userId);
        validationOfPermissions(requesterId, READ);
        List<WorkoutEntity> listFoundWorkouts = getListWorkoutEntityNotSort(getDiaryByUserId(userId));
        List<WorkoutEntity> sortedListWorkouts = sortListWorkoutsByDataDesk(listFoundWorkouts);
        System.out.println("Начинаю маппить и возвращать");
        return workoutMapper.toListWorkoutShortResponseDtoFromEntity(sortedListWorkouts);
    }

    private WorkoutEntity getWorkoutEntityById(Long workoutId) {
        System.out.println("Начинаю получать тренировку по workoutId=" + workoutId);
        WorkoutEntity foundWorkoutEntity = workoutRepository.findByWorkoutByWorkoutId(workoutId)
                .orElseThrow(() -> new NotFoundException(String.format("Workout by [workoutId=%s]", workoutId)));
        System.out.println("Тренировка существует с workoutId=" + workoutId);
        return foundWorkoutEntity;
    }

    private void isOwnerWorkout(WorkoutEntity foundWorkoutEntity, Long requesterId) {
        System.out.println("Начинаем проверку является ли пользователь владельцем тренировки");
        Long diaryId = diaryService.getIdDiaryByUserId(requesterId);
        if (foundWorkoutEntity.getIdDiary().equals(diaryId)) {
            System.out.println("Проверка завершена, запрос высылает владелец");
        } else {
            System.out.println("Пользователь не является владельцем тренировки");
            throw new AccessDeniedException(String.format("For [requesterId=%d]", requesterId));
        }
    }

    private void validateWorkoutByTypeToday(Long idTypeWorkout, LocalDate dayOfWorkout) {
        System.out.println("Проверяю не загружена ли тренировка с с таким типом в эту дату");
        if (workoutRepository.findByWorkoutByWorkoutIdAndDate(idTypeWorkout, dayOfWorkout).isPresent()) {
            System.out.println("С таким типом тренировка уже есть в этот день");
            throw new AlreadyExistException(String.format("Workout witch [idTypeWorkout=%d]", idTypeWorkout));
        } else {
            System.out.println("Тренировки с таким типом в этот день не загружено, все в порядке");
        }
    }

    private List<WorkoutEntity> sortListWorkoutsByDataDesk(List<WorkoutEntity> listNotSort) {
        System.out.println("Сортируем список тренировок");
        List<WorkoutEntity> sortedList = listNotSort.stream()
                .sorted(Comparator.comparing(WorkoutEntity::getTimeStartOn)
                        .reversed())
                .collect(Collectors.toList());
        System.out.println("Список отсортирован: " + sortedList);
        return sortedList;
    }

    private Long getDiaryByUserId(Long userId) {
        System.out.println("ищу дневник пользователя по userId=" + userId);
        return diaryService.getIdDiaryByUserId(userId);
    }

    private void isExistUserOrThrowException(Long userId) {
        if (!isExistUserById(userId)) {
            throw new NotFoundException(String.format("User with [userId=%s]", userId));
        }
    }

    private boolean isExistUserById(Long userId) {
        System.out.println("Проверяю существование запрашиваемого пользователя");
        return userRepository.findByUserId(userId).isPresent();
    }

    private void validationOfPermissions(Long requesterId, FlagPermissions flagPermissions) {
        System.out.println("Пользователь с requesterId="
                + requesterId + "запрашивает разрешение на операцию: " + flagPermissions);
        if (groupPermissionsService.getPermissionsForService(getGroupPermissionsId(requesterId),
                SERVICE_WORKOUT_ID, flagPermissions)) {
            System.out.println("Разрешение на эту операцию получено.");
        } else {
            System.out.println("У пользователя нет доступа на эту операцию");
            throw new AccessDeniedException(String.format("For [requesterId=%d]", requesterId));
        }
    }

    private Integer getGroupPermissionsId(Long requesterId) {
        System.out.println("Проверяю существование запрашиваемого пользователя requesterId=" + requesterId);
        return userRepository.findByUserId(requesterId)
                .orElseThrow(() -> new NotFoundException(String.format("User with [requesterId=%s]", requesterId)))
                .getIdGroupPermissions();
    }

    private List<WorkoutEntity> getListWorkoutEntityNotSort(Long idDiary) {
        List<WorkoutEntity> listFoundWorkouts = workoutRepository.findAllWorkoutByDiaryId(idDiary);
        System.out.println("Получен список из тренировок в количестве=" + listFoundWorkouts.size());
        return listFoundWorkouts;
    }
}
