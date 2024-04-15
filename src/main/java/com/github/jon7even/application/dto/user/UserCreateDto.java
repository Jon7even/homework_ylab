package com.github.jon7even.application.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Класс DTO для создания нового пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private Integer idGroupPermissions;
}
