package com.github.jon7even.servlet.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import com.github.jon7even.services.AuthorizationService;
import com.github.jon7even.services.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.github.jon7even.constants.ControllerConstants.DEFAULT_CONTENT_JSON;
import static com.github.jon7even.constants.ControllerConstants.DEFAULT_ENCODING;

/**
 * Обработка Http запросов на авторизацию пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet("/auth/sign-in")
public class AuthorizationServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private UserService userService;
    private AuthorizationService authService;

    public AuthorizationServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = (UserService) getServletContext().getAttribute("userService");
        this.authService = (AuthorizationService) getServletContext().getAttribute("authService");
    }

    /**
     * @param req  запрос на выход из приложения
     * @param resp ответ пользователю статус 200 и удаление сессии у пользователя
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserLogInAuthDto userLogInAuthDto = objectMapper.readValue(req.getReader(), UserLogInAuthDto.class);
        UserLogInResponseDto userLogInResponseDto = userService.findUserForAuthorization(userLogInAuthDto);

        if (authService.processAuthorization(userLogInAuthDto)) {
            req.getSession().setAttribute("user", userLogInResponseDto);
            resp.getWriter().write(objectMapper.writeValueAsString(userLogInResponseDto));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("User is not Log-In")
                    .message("Такого пользователя не существует или логин был введен неверно")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
        }
    }
}
