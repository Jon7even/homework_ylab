package mappers;

import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import com.github.jon7even.core.domain.v1.mappers.WorkoutMapper;
import com.github.jon7even.core.domain.v1.mappers.WorkoutMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import setup.PreparationForTests;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkoutMapperTest extends PreparationForTests {
    private WorkoutMapper workoutMapper;

    @BeforeEach
    public void setUp() {
        initWorkoutDto();
        workoutMapper = new WorkoutMapperImpl();
    }

    @Test
    @DisplayName("Маппинг из DTO в сущность для сохранения новой тренировки")
    public void should_toWorkoutEntityFromDtoCreate_ReturnEntityNotId() {
        WorkoutEntity actualResultFirst = workoutMapper.toWorkoutEntityFromDtoCreate(
                workoutCreateDtoFirst
        );

        assertThat(actualResultFirst)
                .isNotNull();
        assertThat(actualResultFirst.getId())
                .isNull();

        assertThat(actualResultFirst.getIdDiary())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getIdDiary());
        assertThat(actualResultFirst.getIdTypeWorkout())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getIdTypeWorkout());
        assertThat(actualResultFirst.getTimeStartOn())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getTimeStartOn());
        assertThat(actualResultFirst.getTimeEndOn())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getTimeEndOn());
        assertThat(actualResultFirst.getTimeOfRest())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getTimeOfRest());
        assertThat(actualResultFirst.getCurrentWeightUser())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getCurrentWeightUser());
        assertThat(actualResultFirst.getPersonalNote())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getPersonalNote());
        assertThat(actualResultFirst.getDetailOfWorkout())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getDetailOfWorkout());

        WorkoutEntity actualResultSecond = workoutMapper.toWorkoutEntityFromDtoCreate(
                workoutCreateDtoSecond
        );

        assertThat(actualResultSecond)
                .isNotNull();
        assertThat(actualResultSecond.getId())
                .isNull();

        assertThat(actualResultSecond.getIdDiary())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getIdDiary());
        assertThat(actualResultSecond.getIdTypeWorkout())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getIdTypeWorkout());
        assertThat(actualResultSecond.getTimeStartOn())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getTimeStartOn());
        assertThat(actualResultSecond.getTimeEndOn())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getTimeEndOn());
        assertThat(actualResultSecond.getTimeOfRest())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getTimeOfRest());
        assertThat(actualResultSecond.getCurrentWeightUser())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getCurrentWeightUser());
        assertThat(actualResultSecond.getPersonalNote())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getPersonalNote());
        assertThat(actualResultSecond.getDetailOfWorkout())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getDetailOfWorkout());

        WorkoutEntity actualResultThird = workoutMapper.toWorkoutEntityFromDtoCreate(
                workoutCreateDtoThird
        );

        assertThat(actualResultThird)
                .isNotNull();
        assertThat(actualResultThird.getId())
                .isNull();

        assertThat(actualResultThird.getIdDiary())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getIdDiary());
        assertThat(actualResultThird.getIdTypeWorkout())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getIdTypeWorkout());
        assertThat(actualResultThird.getTimeStartOn())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getTimeStartOn());
        assertThat(actualResultThird.getTimeEndOn())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getTimeEndOn());
        assertThat(actualResultThird.getTimeOfRest())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getTimeOfRest());
        assertThat(actualResultThird.getCurrentWeightUser())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getCurrentWeightUser());
        assertThat(actualResultThird.getPersonalNote())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getPersonalNote());
        assertThat(actualResultThird.getDetailOfWorkout())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getDetailOfWorkout());
    }

    @Test
    @DisplayName("Маппинг из сущности в DTO для предоставления полной информации о тренировке")
    public void should_toDiaryResponseDtoFromEntity_ReturnDtoResponse() {
        WorkoutFullResponseDto actualResultFirst = workoutMapper.toWorkoutFullResponseDtoFromEntity(
                workoutEntityFirst, typeWorkoutResponseDtoWalking
        );

        assertThat(actualResultFirst)
                .isNotNull();

        assertThat(actualResultFirst.getId())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getId());
        assertThat(actualResultFirst.getIdDiary())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getIdDiary());
        assertThat(actualResultFirst.getTypeWorkoutResponseDto().getTypeWorkoutId())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getIdTypeWorkout());
        assertThat(actualResultFirst.getTimeStartOn())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getTimeStartOn());
        assertThat(actualResultFirst.getTimeEndOn())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getTimeEndOn());
        assertThat(actualResultFirst.getTimeOfRest())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getTimeOfRest());
        assertThat(actualResultFirst.getCurrentWeightUser())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getCurrentWeightUser());
        assertThat(actualResultFirst.getPersonalNote())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getPersonalNote());
        assertThat(actualResultFirst.getDetailOfWorkout())
                .isNotNull()
                .isEqualTo(workoutEntityFirst.getDetailOfWorkout());

        WorkoutFullResponseDto actualResultSecond = workoutMapper.toWorkoutFullResponseDtoFromEntity(
                workoutEntitySecond, typeWorkoutResponseDtoStrength
        );

        assertThat(actualResultSecond)
                .isNotNull();

        assertThat(actualResultSecond.getIdDiary())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getIdDiary());
        assertThat(actualResultSecond.getTypeWorkoutResponseDto().getTypeWorkoutId())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getIdTypeWorkout());
        assertThat(actualResultSecond.getTimeStartOn())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getTimeStartOn());
        assertThat(actualResultSecond.getTimeEndOn())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getTimeEndOn());
        assertThat(actualResultSecond.getTimeOfRest())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getTimeOfRest());
        assertThat(actualResultSecond.getCurrentWeightUser())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getCurrentWeightUser());
        assertThat(actualResultSecond.getPersonalNote())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getPersonalNote());
        assertThat(actualResultSecond.getDetailOfWorkout())
                .isNotNull()
                .isEqualTo(workoutEntitySecond.getDetailOfWorkout());

        WorkoutFullResponseDto actualResultThird = workoutMapper.toWorkoutFullResponseDtoFromEntity(
                workoutEntityThird, typeWorkoutResponseDtoYoga
        );

        assertThat(actualResultThird)
                .isNotNull();

        assertThat(actualResultThird.getIdDiary())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getIdDiary());
        assertThat(actualResultThird.getTypeWorkoutResponseDto().getTypeWorkoutId())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getIdTypeWorkout());
        assertThat(actualResultThird.getTimeStartOn())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getTimeStartOn());
        assertThat(actualResultThird.getTimeEndOn())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getTimeEndOn());
        assertThat(actualResultThird.getTimeOfRest())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getTimeOfRest());
        assertThat(actualResultThird.getCurrentWeightUser())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getCurrentWeightUser());
        assertThat(actualResultThird.getPersonalNote())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getPersonalNote());
        assertThat(actualResultThird.getDetailOfWorkout())
                .isNotNull()
                .isEqualTo(workoutEntityThird.getDetailOfWorkout());
    }
}