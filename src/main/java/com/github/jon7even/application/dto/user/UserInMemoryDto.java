package com.github.jon7even.application.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для хранения пользователя в памяти приложения
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInMemoryDto {
    private Long id;
    private String login;
    private Integer idGroupPermissions;
}
