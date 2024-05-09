import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.impl.NumberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TestValidator {
    private NumberValidator numberValidator;

    @BeforeEach
    public void setUp() {
        this.numberValidator = NumberValidator.getInstance();
    }

    @Test
    @DisplayName("Валидация чисел должна пройти без ошибок")
    public void should_ValidatedNumbers_NotThrowException() {
        Long longVariable = 1L;
        Integer integerVariable = 1;
        Float floatVariable = 1.1f;

        numberValidator.validate(longVariable, "longVariable");
        numberValidator.validate(integerVariable, "integerVariable");
        numberValidator.validate(floatVariable, "floatVariable");
    }

    @Test
    @DisplayName("Парсинг чисел при отрицательном значении должна вызвать исключение")
    public void should_ValidatedNegativeNumbers_ThrowException() {
        Long longVariable = -1L;
        Integer integerVariable = -1;
        Float floatVariable = -1.1f;

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
    }

    @Test
    @DisplayName("Парсинг чисел при разных ситуациях должна вызвать исключение")
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
    }
}
