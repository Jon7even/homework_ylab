package com.github.jon7even.dataproviders.core.mapper;

import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.dataproviders.core.RowMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRowMapper implements RowMapper {
    private static UserRowMapper instance;

    public static UserRowMapper getInstance() {
        if (instance == null) {
            instance = new UserRowMapper();
        }
        return instance;
    }

    @Override
    public UserEntity mapRow(ResultSet rs) throws SQLException {
        return UserEntity.builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .idGroupPermissions(rs.getInt("id_group"))
                .build();
    }
}
