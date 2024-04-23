package com.github.jon7even.dataproviders.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Интерфейс для парсинга ResultSet в сущности Entity, является аналогом из org.springframework.jdbc.core
 *
 * @author Jon7even
 * @version 1.0
 */
public interface RowMapper<T> {
    /**
     * Метод для парсинга строки ResultSet в сущность
     *
     * @param rs полученный ResultSet из БД
     * @return Entity с заполненным полем
     */
    T mapRow(ResultSet rs) throws SQLException;
}
