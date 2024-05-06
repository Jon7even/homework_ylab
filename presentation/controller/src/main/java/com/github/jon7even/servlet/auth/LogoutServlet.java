package com.github.jon7even.servlet.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import com.github.jon7even.services.AuthorizationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.github.jon7even.constants.ControllerContent.DEFAULT_CONTENT_JSON;
import static com.github.jon7even.constants.ControllerContent.DEFAULT_ENCODING;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_AUTH;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_SIGN_OUT;

/**
 * Обработка Http запросов на выход пользователя из системы
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet(PATH_URL_AUTH + PATH_URL_SIGN_OUT)
public class LogoutServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private AuthorizationService authService;

    public LogoutServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.authService = (AuthorizationService) getServletContext().getAttribute("authService");
    }

    /**
     * @param req  запрос на выход из приложения
     * @param resp ответ пользователю статус 204 и удаление его сессии
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");

        if (userFromSession != null) {
            session.invalidate();
            authService.processLogOut(userFromSession);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("FORBIDDEN")
                    .message("Вы не авторизованы")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
