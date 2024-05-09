import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.DataTimeValidator;
import com.github.jon7even.validator.impl.NumberValidator;
import com.github.jon7even.validator.impl.ParamValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TestValidator {
    private NumberValidator numberValidator;
    private ParamValidator paramValidator;

    @BeforeEach
    public void setUp() {
        this.numberValidator = NumberValidator.getInstance();
        this.paramValidator = ParamValidator.getInstance();
    }

    @Test
    @DisplayName("Валидация чисел должна пройти без ошибок")
    public void should_ValidatedNumbers_NotThrowException() {
        Long longVariable = 1L;
        Integer integerVariable = 1;
        Float floatVariable = 1.1f;
        String longString = "1";

        numberValidator.validate(longVariable, "longVariable");
        numberValidator.validate(integerVariable, "integerVariable");
        numberValidator.validate(floatVariable, "floatVariable");
        paramValidator.validate(longString, "longVariable");
    }

    @Test
    @DisplayName("Парсинг чисел при отрицательном значении должен вызвать исключение")
    public void should_ValidatedNegativeNumbers_ThrowException() {
        Long longVariable = -1L;
        Integer integerVariable = -1;
        Float floatVariable = -1.1f;
        String longString = "-1";

        assertThatThrownBy(() -> {
            numberValidator.validate(longVariable, "longVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[longVariable] not validated, reason: [должен быть положительным]");

        assertThatThrownBy(() -> {
            numberValidator.validate(integerVariable, "integerVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[integerVariable] not validated, reason: [должен быть положительным]");

        assertThatThrownBy(() -> {
            numberValidator.validate(floatVariable, "floatVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[floatVariable] not validated, reason: [должен быть положительным]");

        assertThatThrownBy(() -> {
            paramValidator.validate(longString, "longString");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[longString] not validated, reason: [должен быть положительным]");
    }

    @Test
    @DisplayName("Парсинг чисел при разных ситуациях должен вызвать исключение")
    public void should_ValidatedNumbers_ThrowException() {
        Boolean booleanVariable = true;
        String stringVariable = "";
        Float floatVariable = 0.1f;
        Object obj = null;

        assertThatThrownBy(() -> {
            numberValidator.validate(booleanVariable, "longVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[longVariable] not validated, reason: [должен быть числом]");

        assertThatThrownBy(() -> {
            numberValidator.validate(stringVariable, "integerVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[integerVariable] not validated, reason: [не может быть пустым]");

        assertThatThrownBy(() -> {
            numberValidator.validate(floatVariable, "floatVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[floatVariable] not validated, reason: [должен быть положительным]");

        assertThatThrownBy(() -> {
            numberValidator.validate(obj, "obj");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[obj] not validated, reason: [не может быть пустым]");

        assertThatThrownBy(() -> {
            paramValidator.validate(obj, "obj");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[obj] not validated, reason: [не может быть пустым]");

        assertThatThrownBy(() -> {
            paramValidator.validate(stringVariable, "integerVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[integerVariable] not validated, reason: [не может быть пустым]");

        assertThatThrownBy(() -> {
            paramValidator.validate(floatVariable, "floatVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[floatVariable] not validated, reason: [должен быть положительным]");

        assertThatThrownBy(() -> {
            paramValidator.validate(booleanVariable, "longVariable");
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[longVariable] not validated, reason: [должен быть числом]");
    }

    @Test
    @DisplayName("Валидация времени должна пройти без ошибок")
    public void should_ValidatedTimesVariable_NotThrowException() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(2024, 5, 7, 12, 7);
        LocalDateTime endTime = LocalDateTime.of(2024, 5, 7, 14, 7);
        Long durationOfRest = 20L;

        DataTimeValidator.validationTimeOfStart(currentTime, startTime);
        DataTimeValidator.validationTimeOfEnd(startTime, endTime);
        DataTimeValidator.validationTimeOfRest(startTime, endTime, durationOfRest);
    }

    @Test
    @DisplayName("Парсинг времени при разных ситуациях должен вызвать исключение")
    public void should_ValidatedTimesVariable_ThrowException() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTimeFuture = LocalDateTime.of(2037, 5, 7, 12, 7);

        assertThatThrownBy(() -> {
            DataTimeValidator.validationTimeOfStart(currentTime, startTimeFuture);
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[timeStartOn] not validated, reason: [не может быть в будущем]");

        LocalDateTime startTime = LocalDateTime.of(2024, 5, 7, 12, 7);
        LocalDateTime endTime = LocalDateTime.of(2024, 5, 8, 14, 7);

        assertThatThrownBy(() -> {
            DataTimeValidator.validationTimeOfEnd(startTime, endTime);
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[timeEndOn] not validated, reason: [не может быть указано следующим днем]");

        LocalDateTime startTimePlusDay = startTime.plusDays(2);

        assertThatThrownBy(() -> {
            DataTimeValidator.validationTimeOfEnd(startTimePlusDay, endTime);
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[timeEndOn] not validated, reason: [не может быть раньше начала тренировки]");

        Long durationOfRest = 121L;
        LocalDateTime endTimeMinusDay = endTime.minusDays(1);

        assertThatThrownBy(() -> {
            DataTimeValidator.validationTimeOfRest(startTime, endTimeMinusDay, durationOfRest);
        }).isInstanceOf(MethodArgumentNotValidException.class)
                .hasMessage("[timeOfRest] not validated, reason: [не может быть больше чем сама тренировка]");
    }
}
