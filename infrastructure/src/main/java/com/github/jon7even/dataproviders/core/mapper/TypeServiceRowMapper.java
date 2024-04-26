package com.github.jon7even.dataproviders.core.mapper;

import com.github.jon7even.core.domain.v1.entities.permissions.NameType;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import com.github.jon7even.dataproviders.core.RowMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeServiceRowMapper implements RowMapper<TypeServiceEntity> {
    private static TypeServiceRowMapper instance;

    public static TypeServiceRowMapper getInstance() {
        if (instance == null) {
            instance = new TypeServiceRowMapper();
        }
        return instance;
    }

    @Override
    public TypeServiceEntity mapRow(ResultSet rs) throws SQLException {
        return TypeServiceEntity.builder()
                .id(rs.getInt("id"))
                .nameType(NameType.builder()
                        .id(rs.getInt("service_id"))
                        .name(rs.getString("service_name"))
                        .build())
                .write(rs.getBoolean("write"))
                .read(rs.getBoolean("read"))
                .update(rs.getBoolean("update"))
                .delete(rs.getBoolean("delete"))
                .build();
    }
}