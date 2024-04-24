package com.github.jon7even.services.impl;

import com.github.jon7even.core.domain.v1.dao.TypeWorkoutDao;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.dto.typeworkout.*;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.NotUpdatedException;
import com.github.jon7even.core.domain.v1.mappers.TypeWorkoutMapper;
import com.github.jon7even.core.domain.v1.mappers.TypeWorkoutMapperImpl;
import com.github.jon7even.dataproviders.configuration.ConfigLoader;
import com.github.jon7even.dataproviders.inmemory.TypeWorkoutRepository;
import com.github.jon7even.dataproviders.jdbc.UserJdbcRepository;
import com.github.jon7even.services.GroupPermissionsService;
import com.github.jon7even.services.TypeWorkoutService;

import java.util.List;

import static com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions.UPDATE;
import static com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions.WRITE;

/**
 * Реализация сервиса взаимодействия с типами тренировок
 *
 * @author Jon7even
 * @version 1.0
 */
public class TypeWorkoutServiceImpl implements TypeWorkoutService {
    private static TypeWorkoutServiceImpl instance;
    private static final Integer SERVICE_TYPE_WORKOUT_ID = 4;
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
        ConfigLoader configLoader = ConfigLoader.getInstance();
        this.userRepository = new UserJdbcRepository(configLoader.getConfig());
    }

    @Override
    public TypeWorkoutResponseDto createTypeWorkout(TypeWorkoutCreateDto typeWorkoutCreateDto) {
        System.out.println("К нам пришел на создание новый тип тренировки: " + typeWorkoutCreateDto);
        validationOfPermissions(typeWorkoutCreateDto.getRequesterId(), WRITE);

        DetailOfTypeWorkoutEntity detailOfTypeWorkoutEntity = typeWorkoutMapper.toEntityDetailOfTypeFromDtoResponse(
                findDetailOfTypeByDetailOfTypeId(typeWorkoutCreateDto.getDetailOfTypeId())
        );

        TypeWorkoutEntity typeWorkoutEntityForSaveInRepository = typeWorkoutMapper.toTypeWorkoutEntityFromDtoCreate(
                typeWorkoutCreateDto, detailOfTypeWorkoutEntity
        );
        System.out.println("Новый тип тренировки для сохранения собран: " + typeWorkoutEntityForSaveInRepository);

        TypeWorkoutEntity createdTypeWorkoutEntity =
                typeWorkoutRepository.createTypeWorkout(typeWorkoutEntityForSaveInRepository)
                        .orElseThrow(() -> new NotCreatedException("New TypeWorkout"));

        System.out.println("Новый тип тренировки успешно сохранен");
        return typeWorkoutMapper.toTypeWorkoutResponseDtoFromEntity(createdTypeWorkoutEntity);
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
        validationOfPermissions(requesterId, UPDATE);

        DetailOfTypeWorkoutEntity detailOfTypeWorkoutEntity = typeWorkoutMapper.toEntityDetailOfTypeFromDtoResponse(
                findDetailOfTypeByDetailOfTypeId(typeWorkoutUpdateDto.getDetailOfTypeId())
        );

        TypeWorkoutEntity typeWorkoutEntityForUpdate = getTypeWorkoutEntityByTypeWorkoutId(typeWorkoutId);
        typeWorkoutEntityForUpdate.setDetailOfTypeWorkoutEntity(detailOfTypeWorkoutEntity);

        System.out.println("Объединяем данные в сущности");
        typeWorkoutMapper.updateTypeWorkoutEntityFromDtoUpdate(typeWorkoutEntityForUpdate, typeWorkoutUpdateDto);

        System.out.println("Сохраняю получившийся тип тренировки: " + typeWorkoutEntityForUpdate);
        TypeWorkoutEntity updatedDiaryFromRepository =
                typeWorkoutRepository.updateTypeWorkout(typeWorkoutEntityForUpdate)
                        .orElseThrow(() -> new NotUpdatedException(typeWorkoutUpdateDto.toString()));

        System.out.println("Тип тренировки обновлен в БД: " + updatedDiaryFromRepository
                + " начинаю маппить и отправлять");
        return typeWorkoutMapper.toTypeWorkoutResponseDtoFromEntity(updatedDiaryFromRepository);
    }

    @Override
    public boolean isExistTypeWorkoutByTypeWorkoutId(Long typeWorkoutId) {
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

    @Override
    public DetailOfTypeWorkoutResponseDto findDetailOfTypeByDetailOfTypeId(Integer detailOfTypeId) {
        System.out.println("Начинаю получать тип деталей тренировки=" + detailOfTypeId);
        return typeWorkoutMapper.toDtoDetailOfTypeResponseFromEntity(
                typeWorkoutRepository.findDetailOfTypeWorkout(detailOfTypeId)
                        .orElseThrow(() -> new NotFoundException(
                                String.format("DetailOfTypeWorkout by [detailOfTypeId=%s]", detailOfTypeId)
                        )));
    }

    @Override
    public List<DetailOfTypeWorkoutResponseDto> findAllDetailOfTypeWorkoutNoSort() {
        System.out.println("Начинаем получать все возможные детали для типов тренировок");
        List<DetailOfTypeWorkoutEntity> listDetailOfTypeWorkoutNoSort =
                typeWorkoutRepository.findAllDetailOfTypeWorkoutNoSort();
        System.out.println("Получен список из доступных деталей в количестве=" + listDetailOfTypeWorkoutNoSort.size());
        System.out.println("Начинаем мапппить и отправлять");
        return typeWorkoutMapper.toListDetailResponseDtoFromEntity(listDetailOfTypeWorkoutNoSort);
    }

    @Override
    public boolean isExistDetailOfTypeByDetailOfTypeId(Integer detailOfTypeId) {
        System.out.println("Проверяю существует ли параметр детализации с detailOfTypeId=" + detailOfTypeId);
        return typeWorkoutRepository.findDetailOfTypeWorkout(detailOfTypeId).isPresent();
    }

    private TypeWorkoutEntity getTypeWorkoutEntityByTypeWorkoutId(Long typeWorkoutId) {
        System.out.println("Начинаю получать тип тренировки по typeWorkoutId=" + typeWorkoutId);
        return typeWorkoutRepository.findByTypeWorkoutId(typeWorkoutId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("TypeWorkout by [typeWorkoutId=%s]", typeWorkoutId)
                ));
    }

    private void validationOfPermissions(Long requesterId, FlagPermissions flagPermissions) {
        System.out.println("Пользователь с requesterId="
                + requesterId + "запрашивает разрешение на операцию: " + flagPermissions);
        if (groupPermissionsService.getPermissionsForService(getGroupPermissionsId(requesterId),
                SERVICE_TYPE_WORKOUT_ID, flagPermissions)) {
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
}
