package com.github.jon7even.servlet.auth;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import com.github.jon7even.services.AuthorizationService;
import com.github.jon7even.services.UserService;
import com.github.jon7even.validator.Validator;
import com.github.jon7even.validator.impl.UserLogInAuthDtoValidator;
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
import static com.github.jon7even.constants.ControllerPath.PATH_URL_SIGN_IN;

/**
 * Обработка Http запросов на авторизацию пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet(PATH_URL_AUTH + PATH_URL_SIGN_IN)
public class AuthorizationServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final Validator<UserLogInAuthDto> validator;
    private UserService userService;
    private AuthorizationService authService;

    public AuthorizationServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validator = UserLogInAuthDtoValidator.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = (UserService) getServletContext().getAttribute("userService");
        this.authService = (AuthorizationService) getServletContext().getAttribute("authService");
    }

    /**
     * @param req  запрос на авторизацию пользователя UserLogInAuthDto
     * @param resp ответ пользователю статус 200 и удаление сессии у пользователя
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserLogInAuthDto userLogInAuthDto = null;
            try {
                userLogInAuthDto = objectMapper.readValue(req.getReader(), UserLogInAuthDto.class);
            } catch (JsonMappingException e) {
                System.out.println(e.getMessage());
                resp.setContentType(DEFAULT_CONTENT_JSON);
                resp.setCharacterEncoding(DEFAULT_ENCODING);
                ApiError error = ApiError.builder()
                        .reason("BAD_REQUEST")
                        .message("Тело запроса не может быть пустым")
                        .timestamp(LocalDateTime.now())
                        .build();
                resp.getWriter().write(objectMapper.writeValueAsString(error));
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                validator.validate(userLogInAuthDto);
            } catch (MethodArgumentNotValidException e) {
                resp.setContentType(DEFAULT_CONTENT_JSON);
                resp.setCharacterEncoding(DEFAULT_ENCODING);
                ApiError error = ApiError.builder()
                        .reason("BAD_REQUEST")
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();
                resp.getWriter().write(objectMapper.writeValueAsString(error));
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            UserLogInResponseDto userLogInResponseDto = userService.findUserForAuthorization(userLogInAuthDto);

            if (authService.processAuthorization(userLogInAuthDto)) {
                resp.setContentType(DEFAULT_CONTENT_JSON);
                resp.setCharacterEncoding(DEFAULT_ENCODING);
                req.getSession().setAttribute("user", userLogInResponseDto);
                resp.getWriter().write(objectMapper.writeValueAsString(userLogInResponseDto));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setContentType(DEFAULT_CONTENT_JSON);
                resp.setCharacterEncoding(DEFAULT_ENCODING);
                ApiError error = ApiError.builder()
                        .reason("BAD_REQUEST")
                        .message("Пароль указан неверно")
                        .timestamp(LocalDateTime.now())
                        .build();
                resp.getWriter().write(objectMapper.writeValueAsString(error));
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (
                NotFoundException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("NOT_FOUND")
                    .message("Такой пользователь не найден")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("INTERNAL_SERVER_ERROR")
                    .message("Ошибка сервера")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
