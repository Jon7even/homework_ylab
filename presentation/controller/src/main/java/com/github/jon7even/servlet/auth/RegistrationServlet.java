package com.github.jon7even.servlet.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
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
 * Обработка Http запросов на регистрацию новых пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet("/auth/sign-up")
public class RegistrationServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private UserService userService;

    public RegistrationServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    /**
     * @param req  запрос на создание нового пользователя UserCreateDto
     * @param resp ответ пользователю статус 201 и краткая форма UserShortResponseDto
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserCreateDto userCreateDto = objectMapper.readValue(req.getReader(), UserCreateDto.class);
            UserShortResponseDto userShortResponseDto = userService.createUser(userCreateDto);
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(userShortResponseDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (BadLoginException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("BAD_REQUEST")
                    .message("Такой логин зарезервирован системой")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (NotCreatedException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("BAD_REQUEST")
                    .message("Пользователь уже есть в системе")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (Exception e) {
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
