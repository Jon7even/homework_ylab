package com.github.jon7even.core.domain.v1.entities.workout;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    private Long userId;
    private Float weightUser;
    private Float growthUser;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Integer idTypeService;
}
