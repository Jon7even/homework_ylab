package dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import dataproviders.jdbc.setup.ContainersSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeWorkoutJdbcRepositoryTest extends ContainersSetup {
    private TypeWorkoutEntity actualResultTypeWorkoutFirst;
    private TypeWorkoutEntity actualResultTypeWorkoutSecond;
    private TypeWorkoutEntity testTypeWorkoutEntityFirst;
    private TypeWorkoutEntity testTypeWorkoutEntitySecond;
    private final Integer SIZE_LIST_TYPE_WORKOUT = 11;
    private final Integer SIZE_LIST_DETAIL_OF_WORKOUT = 3;

    @BeforeEach
    public void setUp() {
        initTypeWorkoutEntity();
        testTypeWorkoutEntityFirst = TypeWorkoutEntity.builder()
                .id(10L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("For Test 1")
                .caloriePerHour(300)
                .build();

        testTypeWorkoutEntitySecond = TypeWorkoutEntity.builder()
                .id(11L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityNotDetails)
                .typeName("For Test 2")
                .caloriePerHour(400)
                .build();

        actualResultTypeWorkoutFirst = typeWorkoutJdbcRepository.createTypeWorkout(testTypeWorkoutEntityFirst).get();
        actualResultTypeWorkoutSecond = typeWorkoutJdbcRepository.createTypeWorkout(testTypeWorkoutEntitySecond).get();
    }

    @Test
    @DisplayName("Новый тип тренировки должен создаться c релевантными полями")
    public void shouldCreateNewTypeWorkout_ReturnNewTypeWorkout() {
        assertThat(actualResultTypeWorkoutFirst)
                .isNotNull()
                .isEqualTo(testTypeWorkoutEntityFirst);
        assertThat(actualResultTypeWorkoutSecond)
                .isNotNull()
                .isEqualTo(testTypeWorkoutEntitySecond);
    }

    @Test
    @DisplayName("Должен обновить тип тренировки")
    public void shouldUpdateTypeWorkout_ReturnUpdatedTypeWorkout() {
        TypeWorkoutEntity typeWorkoutForUpdateFirst = TypeWorkoutEntity.builder()
                .id(10L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityNotDetails)
                .typeName("For Test update 1")
                .caloriePerHour(300)
                .build();

        TypeWorkoutEntity typeWorkoutForUpdateSecond = TypeWorkoutEntity.builder()
                .id(11L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("For Test update 2")
                .caloriePerHour(500)
                .build();

        Optional<TypeWorkoutEntity> updatedTypeWorkoutFirst =
                typeWorkoutJdbcRepository.updateTypeWorkout(typeWorkoutForUpdateFirst);
        Optional<TypeWorkoutEntity> updatedTypeWorkoutSecond =
                typeWorkoutJdbcRepository.updateTypeWorkout(typeWorkoutForUpdateSecond);

        assertThat(updatedTypeWorkoutFirst.isPresent())
                .isTrue();
        assertThat(updatedTypeWorkoutSecond.isPresent())
                .isTrue();

        assertThat(typeWorkoutJdbcRepository.findByTypeWorkoutId(typeWorkoutForUpdateFirst.getId()).get())
                .isEqualTo(typeWorkoutForUpdateFirst);
        assertThat(typeWorkoutJdbcRepository.findByTypeWorkoutId(typeWorkoutForUpdateSecond.getId()).get())
                .isEqualTo(typeWorkoutForUpdateSecond);

        assertThat(typeWorkoutJdbcRepository.findByTypeWorkoutId(typeWorkoutForUpdateFirst.getId()).get())
                .isNotEqualTo(actualResultTypeWorkoutFirst);
        assertThat(typeWorkoutJdbcRepository.findByTypeWorkoutId(typeWorkoutForUpdateSecond.getId()).get())
                .isNotEqualTo(actualResultTypeWorkoutSecond);
    }

    @Test
    @DisplayName("Должен найти тип тренировки по typeWorkoutId")
    public void shouldFindTypeWorkoutById_ReturnTypeWorkout() {
        assertThat(typeWorkoutJdbcRepository.findByTypeWorkoutId(actualResultTypeWorkoutFirst.getId()).get())
                .isNotNull()
                .isEqualTo(actualResultTypeWorkoutFirst);

        assertThat(typeWorkoutJdbcRepository.findByTypeWorkoutId(actualResultTypeWorkoutSecond.getId()).get())
                .isNotNull()
                .isEqualTo(actualResultTypeWorkoutSecond);
    }

    @Test
    @DisplayName("Получить все типы тренировок существующие в БД")
    public void shouldFindAllTypeWorkout_ReturnAllTypeWorkout() {
        assertThat(typeWorkoutJdbcRepository.findAllTypeWorkoutsNoSort())
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(SIZE_LIST_TYPE_WORKOUT);
    }

    @Test
    @DisplayName("Получить список всех возможных деталей типа тренировок")
    public void shouldFindAllDetailOfTypeWorkout_ReturnAllDetailOfTypeWorkout() {
        assertThat(typeWorkoutJdbcRepository.findAllDetailOfTypeWorkoutNoSort())
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(SIZE_LIST_DETAIL_OF_WORKOUT);
    }

    @Test
    @DisplayName("Должен найти сущность детали типа тренировки по по detailOfTypeId")
    public void shouldFindDetailOfTypeWorkoutById_ReturnAllDetailOfTypeWorkout() {
        assertThat(typeWorkoutJdbcRepository.findAllDetailOfTypeWorkoutNoSort())
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(SIZE_LIST_DETAIL_OF_WORKOUT);

        assertThat(typeWorkoutJdbcRepository.findDetailOfTypeWorkout(detailOfTypeWorkoutEntityTraveled.getId()).get())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityTraveled);

        assertThat(typeWorkoutJdbcRepository.findDetailOfTypeWorkout(detailOfTypeWorkoutEntityExercises.getId()).get())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityExercises);

        assertThat(typeWorkoutJdbcRepository.findDetailOfTypeWorkout(detailOfTypeWorkoutEntityNotDetails.getId()).get())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityNotDetails);
    }
}

