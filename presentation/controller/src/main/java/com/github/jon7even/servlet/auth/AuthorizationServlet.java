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
import com.github.jon7even.services.AuthorizationService;
import com.github.jon7even.services.UserService;
import com.github.jon7even.servlet.handler.ErrorHandlerServlet;
import com.github.jon7even.validator.ValidatorDto;
import com.github.jon7even.validator.impl.UserLogInAuthDtoValidatorDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.github.jon7even.constants.ControllerContent.DEFAULT_CONTENT_JSON;
import static com.github.jon7even.constants.ControllerContent.DEFAULT_ENCODING;
import static com.github.jon7even.constants.ControllerContext.AUTH_SERVICE;
import static com.github.jon7even.constants.ControllerContext.USER_SERVICE;
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
    private final ValidatorDto<UserLogInAuthDto> validatorDto;
    private UserService userService;
    private AuthorizationService authService;
    private final ErrorHandlerServlet errorHandlerServlet;

    public AuthorizationServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validatorDto = UserLogInAuthDtoValidatorDto.getInstance();
        this.errorHandlerServlet = ErrorHandlerServlet.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = (UserService) getServletContext().getAttribute(USER_SERVICE);
        this.authService = (AuthorizationService) getServletContext().getAttribute(AUTH_SERVICE);
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
                errorHandlerServlet.handleError(resp, e.getMessage(),
                        "BAD_REQUEST",
                        "Тело запроса не может быть пустым",
                        HttpServletResponse.SC_BAD_REQUEST
                );
                return;
            }

            try {
                validatorDto.validate(userLogInAuthDto);
            } catch (MethodArgumentNotValidException e) {
                errorHandlerServlet.handleError(resp, e.getMessage(),
                        "BAD_REQUEST",
                        e.getMessage(),
                        HttpServletResponse.SC_BAD_REQUEST
                );
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
                errorHandlerServlet.handleError(resp, "Пользователь указал неверный пароль",
                        "BAD_REQUEST",
                        "Пароль указан неверно",
                        HttpServletResponse.SC_BAD_REQUEST
                );
                return;
            }
        } catch (NotFoundException e) {
            errorHandlerServlet.handleError(resp, "Пользователь указал неверный логин",
                    "NOT_FOUND",
                    "Такой пользователь не найден",
                    HttpServletResponse.SC_NOT_FOUND
            );
            return;
        } catch (Exception e) {
            errorHandlerServlet.handleError(resp, e.getMessage(),
                    "INTERNAL_SERVER_ERROR",
                    "Ошибка сервера",
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );
        }
    }
}
