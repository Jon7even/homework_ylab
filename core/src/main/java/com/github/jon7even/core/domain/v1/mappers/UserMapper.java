package com.github.jon7even.core.domain.v1.mappers;


import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;
import com.github.jon7even.core.domain.v1.dto.user.UserUpdateDto;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс для маппинга DTO и сущностей пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userCreateDto.login", target = "login")
    @Mapping(source = "userCreateDto.password", target = "password")
    @Mapping(source = "idGroupPermissions", target = "idGroupPermissions")
    UserEntity toEntityFromDtoCreate(UserCreateDto userCreateDto, Integer idGroupPermissions);

    @Mapping(source = "idUser", target = "id")
    @Mapping(source = "userLogin", target = "login")
    @Mapping(source = "userUpdateDto.password", target = "password")
    @Mapping(source = "userUpdateDto.idGroupPermissions", target = "idGroupPermissions")
    UserEntity toEntityFromDtoUpdate(UserUpdateDto userUpdateDto, Long idUser, String userLogin);

    @Mapping(source = "userEntity.login", target = "login")
    UserShortResponseDto toShortDtoFromEntity(UserEntity userEntity);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.login", target = "login")
    @Mapping(source = "userEntity.idGroupPermissions", target = "idGroupPermissions")
    UserLogInResponseDto toInMemoryDtoFromEntity(UserEntity userEntity);
}
