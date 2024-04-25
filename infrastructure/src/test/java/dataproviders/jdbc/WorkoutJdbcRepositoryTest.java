package dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import dataproviders.jdbc.setup.ContainersSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkoutJdbcRepositoryTest extends ContainersSetup {
    private WorkoutEntity actualResultWorkoutFirst;
    private WorkoutEntity actualResultWorkoutSecond;
    private WorkoutEntity actualResultWorkoutThird;
    private final Integer SIZE_LIST_OF_WORKOUT = 2;

    @BeforeEach
    public void setUp() {
        initDiaryEntity();
        diaryJdbcRepository.createDiary(diaryEntityFirst).get();
        diaryJdbcRepository.createDiary(diaryEntitySecond).get();
        diaryJdbcRepository.createDiary(diaryEntityThird).get();
        initWorkoutEntity();
        actualResultWorkoutFirst = workoutJdbcRepository.createWorkout(workoutEntityFirst).get();
        actualResultWorkoutSecond = workoutJdbcRepository.createWorkout(workoutEntitySecond).get();
        actualResultWorkoutThird = workoutJdbcRepository.createWorkout(workoutEntityThird).get();
    }

    @Test
    @DisplayName("Новая тренировка должна создаться c релевантными полями")
    public void shouldCreateNewWorkout_ReturnNewWorkout() {
        assertThat(actualResultWorkoutFirst)
                .isNotNull()
                .isEqualTo(workoutEntityFirst);
        assertThat(actualResultWorkoutSecond)
                .isNotNull()
                .isEqualTo(workoutEntitySecond);
        assertThat(actualResultWorkoutThird)
                .isNotNull()
                .isEqualTo(workoutEntityThird);
    }

    @Test
    @DisplayName("Должен обновить тренировку")
    public void shouldUpdateWorkout_ReturnUpdatedWorkout() {
        WorkoutEntity workoutUpdateFirst = WorkoutEntity.builder()
                .id(firstIdLong)
                .idDiary(firstIdLong)
                .idTypeWorkout(typeWorkoutEntityStrength.getId())
                .timeStartOn(timeUpdateFirst)
                .timeEndOn(timeUpdateFirst.plusMinutes(100))
                .timeOfRest(periodOne.plusMinutes(5))
                .currentWeightUser(weightUserUpdateFirst)
                .personalNote("One Workout update 1")
                .detailOfWorkout("My One Workout update 1")
                .build();
        WorkoutEntity workoutUpdateSecond = WorkoutEntity.builder()
                .id(secondIdLong)
                .idDiary(secondIdLong)
                .idTypeWorkout(typeWorkoutEntityWalking.getId())
                .timeStartOn(timeUpdateSecond)
                .timeEndOn(timeUpdateSecond.plusMinutes(200))
                .timeOfRest(periodSecond.plusMinutes(7))
                .currentWeightUser(weightUserUpdateSecond)
                .personalNote("Second Workout update 2")
                .detailOfWorkout("My Second Workout update 2")
                .build();
        WorkoutEntity workoutUpdateThird = WorkoutEntity.builder()
                .id(thirdIdLong)
                .idDiary(thirdIdLong)
                .idTypeWorkout(typeWorkoutEntityWalking.getId())
                .timeStartOn(timeUpdateThird)
                .timeEndOn(timeUpdateThird.plusMinutes(120))
                .timeOfRest(periodThird.plusMinutes(8))
                .currentWeightUser(weightUserUpdateSecond)
                .personalNote("Third Workout update 3")
                .detailOfWorkout("Без деталей update 3")
                .build();

        Optional<WorkoutEntity> updatedWorkoutFirst = workoutJdbcRepository.updateWorkout(workoutUpdateFirst);
        Optional<WorkoutEntity> updatedWorkoutSecond = workoutJdbcRepository.updateWorkout(workoutUpdateSecond);
        Optional<WorkoutEntity> updatedWorkoutThird = workoutJdbcRepository.updateWorkout(workoutUpdateThird);

        assertThat(updatedWorkoutFirst.isPresent())
                .isTrue();
        assertThat(updatedWorkoutSecond.isPresent())
                .isTrue();
        assertThat(updatedWorkoutThird.isPresent())
                .isTrue();

        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(updatedWorkoutFirst.get().getId()))
                .isEqualTo(updatedWorkoutFirst);
        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(updatedWorkoutSecond.get().getId()))
                .isEqualTo(updatedWorkoutSecond);
        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(updatedWorkoutThird.get().getId()))
                .isEqualTo(updatedWorkoutThird);

        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(updatedWorkoutFirst.get().getId()))
                .isNotEqualTo(actualResultWorkoutSecond);
        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(updatedWorkoutSecond.get().getId()))
                .isNotEqualTo(actualResultWorkoutSecond);
        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(updatedWorkoutSecond.get().getId()))
                .isNotEqualTo(actualResultWorkoutSecond);
    }

    @Test
    @DisplayName("Должен найти тренировку по workoutId")
    public void shouldFindWorkoutById_ReturnWorkout() {
        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(actualResultWorkoutFirst.getId()).get())
                .isNotNull()
                .isEqualTo(actualResultWorkoutFirst);

        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(actualResultWorkoutSecond.getId()).get())
                .isNotNull()
                .isEqualTo(actualResultWorkoutSecond);

        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(actualResultWorkoutSecond.getId()).get())
                .isNotNull()
                .isEqualTo(actualResultWorkoutSecond);
    }

    @Test
    @DisplayName("Должен найти тренировку по idTypeWorkout и dayOfWorkout")
    public void shouldFindWorkoutByIdTypeWorkoutAndDayOfWorkout_ReturnWorkout() {
        assertThat(workoutJdbcRepository.findByWorkoutByTypeWorkoutIdAndDate(
                actualResultWorkoutFirst.getIdTypeWorkout(), actualResultWorkoutFirst.getTimeStartOn().toLocalDate())
        ).get()
                .isNotNull()
                .isEqualTo(actualResultWorkoutFirst);

        assertThat(workoutJdbcRepository.findByWorkoutByTypeWorkoutIdAndDate(
                actualResultWorkoutSecond.getIdTypeWorkout(), actualResultWorkoutSecond.getTimeStartOn().toLocalDate())
        ).get()
                .isNotNull()
                .isEqualTo(actualResultWorkoutSecond);

        assertThat(workoutJdbcRepository.findByWorkoutByTypeWorkoutIdAndDate(
                actualResultWorkoutThird.getIdTypeWorkout(), actualResultWorkoutThird.getTimeStartOn().toLocalDate())
        ).get()
                .isNotNull()
                .isEqualTo(actualResultWorkoutThird);
    }

    @Test
    @DisplayName("Получить список всех возможных тренировок пользователя по idWorkout")
    public void shouldFindAllDetailOfTypeWorkout_ReturnAllDetailOfTypeWorkout() {
        WorkoutEntity workoutForSecondCreate = WorkoutEntity.builder()
                .id(4L)
                .idDiary(firstIdLong)
                .idTypeWorkout(typeWorkoutEntityStrength.getId())
                .timeStartOn(timeUpdateFirst)
                .timeEndOn(timeUpdateFirst.plusMinutes(100))
                .timeOfRest(periodOne.plusMinutes(5))
                .currentWeightUser(weightUserUpdateFirst)
                .personalNote("One Workout update 1")
                .detailOfWorkout("My One Workout update 1")
                .build();
        workoutJdbcRepository.createWorkout(workoutForSecondCreate);

        assertThat(workoutJdbcRepository.findAllWorkoutByDiaryId(workoutForSecondCreate.getIdDiary()))
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(SIZE_LIST_OF_WORKOUT);

        workoutJdbcRepository.deleteWorkoutByWorkoutId(workoutForSecondCreate.getId());

        assertThat(workoutJdbcRepository.findAllWorkoutByDiaryId(workoutForSecondCreate.getIdDiary()))
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(SIZE_LIST_OF_WORKOUT - 1);
    }

    @Test
    @DisplayName("Корректно удалить тренировку по workoutId")
    public void shouldDeleteWorkoutByWorkoutId_ReturnNotException() {
        WorkoutEntity workoutForDelete = WorkoutEntity.builder()
                .id(4L)
                .idDiary(firstIdLong)
                .idTypeWorkout(typeWorkoutEntityStrength.getId())
                .timeStartOn(timeUpdateFirst)
                .timeEndOn(timeUpdateFirst.plusMinutes(100))
                .timeOfRest(periodOne.plusMinutes(5))
                .currentWeightUser(weightUserUpdateFirst)
                .personalNote("One Workout update 1")
                .detailOfWorkout("My One Workout update 1")
                .build();
        workoutJdbcRepository.createWorkout(workoutForDelete);

        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(workoutForDelete.getId()).get())
                .isNotNull()
                .isEqualTo(workoutForDelete);

        workoutJdbcRepository.deleteWorkoutByWorkoutId(workoutForDelete.getId());

        assertThat(workoutJdbcRepository.findWorkoutByWorkoutId(workoutForDelete.getId()).isPresent())
                .isFalse();
    }
}
