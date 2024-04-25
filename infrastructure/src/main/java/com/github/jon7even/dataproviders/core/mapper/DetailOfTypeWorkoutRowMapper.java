package com.github.jon7even.dataproviders.core.mapper;

import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.dataproviders.core.RowMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailOfTypeWorkoutRowMapper implements RowMapper<DetailOfTypeWorkoutEntity> {
    private static DetailOfTypeWorkoutRowMapper instance;

    public static DetailOfTypeWorkoutRowMapper getInstance() {
        if (instance == null) {
            instance = new DetailOfTypeWorkoutRowMapper();
        }
        return instance;
    }

    @Override
    public DetailOfTypeWorkoutEntity mapRow(ResultSet rs) throws SQLException {
        return DetailOfTypeWorkoutEntity.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .isFillingRequired(rs.getBoolean("filling_required"))
                .build();
    }
}
