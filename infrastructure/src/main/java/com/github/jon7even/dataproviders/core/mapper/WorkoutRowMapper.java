package com.github.jon7even.dataproviders.core.mapper;

import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import com.github.jon7even.dataproviders.core.RowMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkoutRowMapper implements RowMapper<WorkoutEntity> {
    private static WorkoutRowMapper instance;

    public static WorkoutRowMapper getInstance() {
        if (instance == null) {
            instance = new WorkoutRowMapper();
        }
        return instance;
    }

    @Override
    public WorkoutEntity mapRow(ResultSet rs) throws SQLException {
        return WorkoutEntity.builder()
                .id(rs.getLong("id"))
                .idDiary(rs.getLong("diary_id"))
                .idTypeWorkout(rs.getLong("type_workout_id"))
                .timeStartOn(rs.getObject("start_on", LocalDateTime.class))
                .timeEndOn(rs.getObject("end_on", LocalDateTime.class))
                .timeOfRest(Duration.ofMinutes(rs.getLong("time_rest")))
                .currentWeightUser(rs.getFloat("weight"))
                .personalNote(rs.getString("note"))
                .detailOfWorkout(rs.getString("detail"))
                .build();
    }
}