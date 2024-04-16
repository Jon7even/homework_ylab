package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutUpdateDto;
import com.github.jon7even.application.services.GroupPermissionsService;
import com.github.jon7even.application.services.TypeWorkoutService;
import com.github.jon7even.core.domain.v1.dao.TypeWorkoutDao;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.NotUpdatedException;
import com.github.jon7even.core.domain.v1.mappers.TypeWorkoutMapper;
import com.github.jon7even.core.domain.v1.mappers.TypeWorkoutMapperImpl;
import com.github.jon7even.infrastructure.dataproviders.inmemory.TypeWorkoutRepository;
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;

import java.util.List;

import static com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions.UPDATE;
import static com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions.WRITE;
import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.SERVICE_TYPE_WORKOUT;

/**
 * Реализация сервиса взаимодействия с типами тренировок
 *
 * @author Jon7even
 * @version 1.0
 */
public class TypeWorkoutServiceImpl implements TypeWorkoutService {
    private static TypeWorkoutServiceImpl instance;
    private final TypeWorkoutMapper typeWorkoutMapper;
    private final TypeWorkoutDao typeWorkoutRepository;
    private final GroupPermissionsService groupPermissionsService;
    private final UserDao userRepository;

    public static TypeWorkoutServiceImpl getInstance() {
        if (instance == null) {
            instance = new TypeWorkoutServiceImpl();
        }
        return instance;
    }

    private TypeWorkoutServiceImpl() {
        typeWorkoutRepository = TypeWorkoutRepository.getInstance();
        typeWorkoutMapper = new TypeWorkoutMapperImpl();
        groupPermissionsService = GroupPermissionsServiceImpl.getInstance();
        userRepository = UserRepository.getInstance();
    }

    @Override
    public TypeWorkoutResponseDto createTypeWorkout(TypeWorkoutCreateDto typeWorkoutCreateDto) {
        System.out.println("К нам пришел на создание новый тип тренировки: " + typeWorkoutCreateDto);

        Long requesterId = typeWorkoutCreateDto.getRequesterId();
        System.out.println("Пользователь с requesterId="
                + requesterId + "запрашивает разрешение на создание нового типа тренировки");

        if (groupPermissionsService.getPermissionsForService(getGroupPermissionsId(requesterId),
                SERVICE_TYPE_WORKOUT.getId(), WRITE)) {
            TypeWorkoutEntity typeWorkoutEntityForSaveInRepository = typeWorkoutMapper.toTypeWorkoutEntityFromDtoCreate(
                    typeWorkoutCreateDto, SERVICE_TYPE_WORKOUT.getId()
            );
            System.out.println("Новый тип тренировки для сохранения собран: " + typeWorkoutEntityForSaveInRepository);

            TypeWorkoutEntity createdTypeWorkoutEntity =
                    typeWorkoutRepository.createTypeWorkout(typeWorkoutEntityForSaveInRepository)
                            .orElseThrow(() -> new NotCreatedException("New TypeWorkout"));

            System.out.println("Новый тип тренировки успешно сохранен");
            return typeWorkoutMapper.toTypeWorkoutResponseDtoFromEntity(createdTypeWorkoutEntity);
        } else {
            System.out.println("У пользователя нет доступа для создания нового типа тренировки");
            throw new AccessDeniedException(String.format("For [requesterId=%d]", requesterId));
        }
    }

    @Override
    public TypeWorkoutResponseDto findTypeWorkoutByTypeWorkoutId(Long typeWorkoutId) {
        TypeWorkoutEntity typeWorkoutEntityFromRepository = getTypeWorkoutEntityByTypeWorkoutId(typeWorkoutId);
        System.out.println("Тип тренировки получен, начинаю маппить и отправлять");
        return typeWorkoutMapper.toTypeWorkoutResponseDtoFromEntity(typeWorkoutEntityFromRepository);
    }

    @Override
    public TypeWorkoutResponseDto updateTypeWorkout(TypeWorkoutUpdateDto typeWorkoutUpdateDto) {
        System.out.println("Начинаем обновлять тип тренировки, вот что нужно обновить: " + typeWorkoutUpdateDto);

        Long requesterId = typeWorkoutUpdateDto.getRequesterId();
        Long typeWorkoutId = typeWorkoutUpdateDto.getTypeWorkoutId();
        System.out.println("Пользователь с requesterId="
                + requesterId + "запрашивает разрешение на обновление типа тренировки");

        if (groupPermissionsService.getPermissionsForService(getGroupPermissionsId(requesterId),
                SERVICE_TYPE_WORKOUT.getId(), UPDATE)) {
            TypeWorkoutEntity typeWorkoutEntityForUpdate = getTypeWorkoutEntityByTypeWorkoutId(typeWorkoutId);

            System.out.println("Объединяем данные в сущности");
            typeWorkoutMapper.updateTypeWorkoutEntityFromDtoUpdate(typeWorkoutEntityForUpdate, typeWorkoutUpdateDto);

            System.out.println("Сохраняю получившийся тип тренировки: " + typeWorkoutEntityForUpdate);
            TypeWorkoutEntity updatedDiaryFromRepository =
                    typeWorkoutRepository.updateTypeWorkout(typeWorkoutEntityForUpdate)
                            .orElseThrow(() -> new NotUpdatedException(typeWorkoutUpdateDto.toString()));

            System.out.println("Тип тренировки обновлен в БД: " + updatedDiaryFromRepository
                    + " начинаю маппить и отправлять");
            return typeWorkoutMapper.toTypeWorkoutResponseDtoFromEntity(updatedDiaryFromRepository);

        } else {
            System.out.println("У вас нет доступа для обновления нового типа тренировки");
            throw new AccessDeniedException(String.format("For [requesterId=%d]", requesterId));
        }
    }


    @Override
    public boolean isExistByTypeWorkoutId(Long typeWorkoutId) {
        System.out.println("Проверяю существует ли тип тренировки с typeWorkoutId=" + typeWorkoutId);
        return typeWorkoutRepository.findByTypeWorkoutId(typeWorkoutId).isPresent();
    }

    @Override
    public List<TypeWorkoutShortDto> findAllTypeWorkoutsNoSort() {
        System.out.println("Начинаем получать все типы тренировок");
        List<TypeWorkoutEntity> listTypeWorkoutEntityNoSort = typeWorkoutRepository.findAllTypeWorkoutsNoSort();
        System.out.println("Получен список из типов тренировок в количестве=" + listTypeWorkoutEntityNoSort.size());
        System.out.println("Начинаем мапппить и отправлять");
        return typeWorkoutMapper.toListTypeWorkoutDtoFromEntity(listTypeWorkoutEntityNoSort);
    }

    private TypeWorkoutEntity getTypeWorkoutEntityByTypeWorkoutId(Long typeWorkoutId) {
        System.out.println("Начинаю получать тип тренировки по typeWorkoutId=" + typeWorkoutId);
        return typeWorkoutRepository.findByTypeWorkoutId(typeWorkoutId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("TypeWorkout by [typeWorkoutId=%s]", typeWorkoutId)
                ));
    }

    private Integer getGroupPermissionsId(Long requesterId) {
        System.out.println("Проверяю существование запрашиваемого пользователя requesterId=" + requesterId);
        return userRepository.findByUserId(requesterId)
                .orElseThrow(() -> new NotFoundException(String.format("User with [requesterId=%s]", requesterId)))
                .getIdGroupPermissions();
    }
}
