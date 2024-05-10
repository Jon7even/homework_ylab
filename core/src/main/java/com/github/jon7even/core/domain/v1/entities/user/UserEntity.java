package com.github.jon7even.core.domain.v1.entities.user;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Класс описывающий сущность пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class UserEntity {
    private Long id;

    private String login;

    @ToString.Exclude
    private String password;

    private Integer idGroupPermissions;
}
