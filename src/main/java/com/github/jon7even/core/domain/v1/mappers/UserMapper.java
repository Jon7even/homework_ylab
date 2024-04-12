package com.github.jon7even.core.domain.v1.mappers;


import com.github.jon7even.application.dto.user.UserCreateDto;
import com.github.jon7even.application.dto.user.UserShortResponseDto;
import com.github.jon7even.application.dto.user.UserUpdateDto;
import com.github.jon7even.core.domain.v1.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userCreateDto.login", target = "login")
    @Mapping(source = "userCreateDto.password", target = "password")
    @Mapping(source = "userCreateDto.idGroupPermissions", target = "idGroupPermissions")
    UserEntity toEntityFromDtoCreate(UserCreateDto userCreateDto);

    @Mapping(source = "idUser", target = "id")
    @Mapping(source = "userLogin", target = "login")
    @Mapping(source = "userUpdateDto.password", target = "password")
    @Mapping(source = "userUpdateDto.idGroupPermissions", target = "idGroupPermissions")
    UserEntity toEntityFromDtoUpdate(UserUpdateDto userUpdateDto, Long idUser, Long userLogin);

    @Mapping(source = "userEntity.login", target = "login")
    UserShortResponseDto toDtoFromEntity(UserEntity userEntity);
}
