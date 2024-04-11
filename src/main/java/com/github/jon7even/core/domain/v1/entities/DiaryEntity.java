package com.github.jon7even.core.domain.v1.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Класс описывающий сущность дневника тренировок
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class DiaryEntity {
    private Long id;
    private UserEntity userEntity;
    private Double weightUser;
    private Double growthUser;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Set<GroupPermissionsEntity> permissions;
}
