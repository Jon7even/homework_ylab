package com.github.jon7even.dataproviders.core.mapper;

import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.dataproviders.core.RowMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupPermissionsRowMapper implements RowMapper<GroupPermissionsEntity> {
    private static GroupPermissionsRowMapper instance;

    public static GroupPermissionsRowMapper getInstance() {
        if (instance == null) {
            instance = new GroupPermissionsRowMapper();
        }
        return instance;
    }

    @Override
    public GroupPermissionsEntity mapRow(ResultSet rs) throws SQLException {
        return GroupPermissionsEntity.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
