package services;

import com.github.jon7even.services.impl.ServiceCalculationOfStatsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import services.setup.ContainersSetup;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ServiceCalculationOfStatsTest extends ContainersSetup {
    private ServiceCalculationOfStatsImpl serviceCalculationOfStats;

    @BeforeEach
    public void setup() {
        serviceCalculationOfStats = ServiceCalculationOfStatsImpl.getInstance();
        initTypeWorkoutDto();
        initWorkoutDto();
        initLocalDateTime();
    }

    @Test
    @DisplayName("Должен правильно посчитать количество сожженных Калорий нза одну тренировку")
    public void shouldCalculateCalorieOfWorkout_ReturnRightCalorie() {
        Integer actualResult = serviceCalculationOfStats.getTotalCalorieFromWorkoutDto(workoutFullResponseDtoFirst);
        int caloriePerHourExpected = workoutFullResponseDtoFirst.getTypeWorkoutResponseDto().getCaloriePerHour();

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(caloriePerHourExpected);
    }

    @Test
    @DisplayName("Должен правильно посчитать реальное количество минут тренировки")
    public void shouldCalculateMinutesOfWorkoutMinusRest_ReturnRightMinutes() {
        Integer actualResult = serviceCalculationOfStats.getRealMinutesOfWorkoutFromWorkoutDto(
                workoutFullResponseDtoFirst
        );
        long timeOfWorkout = Duration.between(timeStartFirst, timeEndFirst).toMinutes();
        int timeOfWorkoutMinusRestExpected
                = (int) (timeOfWorkout - workoutFullResponseDtoFirst.getTimeOfRest());

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(timeOfWorkoutMinusRestExpected);
    }
}

