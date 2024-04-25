package com.github.jon7even.dataproviders.core.mapper;

import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import com.github.jon7even.dataproviders.core.RowMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeWorkoutRowMapper implements RowMapper<TypeWorkoutEntity> {
    private static TypeWorkoutRowMapper instance;

    public static TypeWorkoutRowMapper getInstance() {
        if (instance == null) {
            instance = new TypeWorkoutRowMapper();
        }
        return instance;
    }

    @Override
    public TypeWorkoutEntity mapRow(ResultSet rs) throws SQLException {
        return TypeWorkoutEntity.builder()
                .id(rs.getLong("id"))
                .typeName(rs.getString("type_name"))
                .caloriePerHour(rs.getInt("calorie"))
                .detailOfTypeWorkoutEntity(DetailOfTypeWorkoutEntity.builder()
                        .id(rs.getInt("detail_id"))
                        .name(rs.getString("name"))
                        .isFillingRequired(rs.getBoolean("filling_required"))
                        .build())
                .build();
    }
}
