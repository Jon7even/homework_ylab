package com.github.jon7even.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.github.jon7even.constants.ControllerContent.DEFAULT_CONTENT_JSON;
import static com.github.jon7even.constants.ControllerContent.DEFAULT_ENCODING;

/**
 * Класс отвечающий за обработку ошибок в сервлетах
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
public class ErrorHandlerServlet {
    private static ErrorHandlerServlet instance;
    private final ObjectMapper objectMapper;

    private ErrorHandlerServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public static ErrorHandlerServlet getInstance() {
        if (instance == null) {
            instance = new ErrorHandlerServlet();
        }
        return instance;
    }

    /**
     * @param respToUser       ссылка для отправки ответа пользователю
     * @param realErrorMessage сообщение с реальной ошибкой
     * @param reasonToUser     название ошибки
     * @param messageToUser    сообщение, которое будет показано пользователю
     * @param statusToUser     статус ответа, который вернется пользователю
     */
    public void handleError(HttpServletResponse respToUser,
                            String realErrorMessage,
                            String reasonToUser,
                            String messageToUser,
                            int statusToUser) throws IOException {
        System.out.println(realErrorMessage);
        respToUser.setContentType(DEFAULT_CONTENT_JSON);
        respToUser.setCharacterEncoding(DEFAULT_ENCODING);

        ApiError error = ApiError.builder()
                .reason(reasonToUser)
                .message(messageToUser)
                .timestamp(LocalDateTime.now())
                .build();
        respToUser.getWriter().write(objectMapper.writeValueAsString(error));
        respToUser.setStatus(statusToUser);
    }
}
