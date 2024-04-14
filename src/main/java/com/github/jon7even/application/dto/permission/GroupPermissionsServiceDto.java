package com.github.jon7even.application.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO отображает разрешения для определенного сервиса и определенной группы
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupPermissionsServiceDto {
    private Integer id;
    private String nameGroup;
    private String nameService;
    private Boolean write;
    private Boolean read;
    private Boolean update;
    private Boolean delete;
}
