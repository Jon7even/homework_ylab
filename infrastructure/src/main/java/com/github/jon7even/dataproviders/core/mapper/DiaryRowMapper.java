package com.github.jon7even.dataproviders.core.mapper;

import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import com.github.jon7even.dataproviders.core.RowMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryRowMapper implements RowMapper {
    private static DiaryRowMapper instance;

    public static DiaryRowMapper getInstance() {
        if (instance == null) {
            instance = new DiaryRowMapper();
        }
        return instance;
    }

    @Override
    public DiaryEntity mapRow(ResultSet rs) throws SQLException {
        return DiaryEntity.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .weightUser(rs.getFloat("weight"))
                .growthUser(rs.getFloat("growth"))
                .createdOn(rs.getObject("created_on", LocalDateTime.class))
                .updatedOn(rs.getObject("updated_on", LocalDateTime.class))
                .build();
    }
}