package com.github.jon7even.dataproviders.core.mapper;

import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import com.github.jon7even.dataproviders.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class HistoryRowMapper implements RowMapper {
    private static HistoryRowMapper instance;

    public static HistoryRowMapper getInstance() {
        if (instance == null) {
            instance = new HistoryRowMapper();
        }
        return instance;
    }

    private HistoryRowMapper() {
    }

    @Override
    public HistoryUserEntity mapRow(ResultSet rs) throws SQLException {
        return HistoryUserEntity.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .dateTimeOn(rs.getObject("created_on", LocalDateTime.class))
                .event(rs.getString("event"))
                .build();
    }
}