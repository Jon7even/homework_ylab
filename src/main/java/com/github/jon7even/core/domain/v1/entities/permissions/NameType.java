package com.github.jon7even.core.domain.v1.entities.permissions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NameType {
    private Integer id;
    private String name;
}
