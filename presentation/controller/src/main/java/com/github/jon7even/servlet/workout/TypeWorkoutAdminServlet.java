package com.github.jon7even.servlet.workout;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutUpdateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotUpdatedException;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import com.github.jon7even.services.TypeWorkoutService;
import com.github.jon7even.validator.impl.TypeWorkoutCreateValidatorDto;
import com.github.jon7even.validator.impl.TypeWorkoutUpdateValidatorDto;
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
import static com.github.jon7even.constants.ControllerContext.TYPE_WORKOUT_SERVICE;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_ADMIN;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_TYPE_WORKOUT;

/**
 * Обработка Http запросов от групп, у которых есть на это разрешение с ресурсом типы тренировок
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet(PATH_URL_ADMIN + PATH_URL_TYPE_WORKOUT)
public class TypeWorkoutAdminServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private TypeWorkoutService typeWorkoutService;
    private final TypeWorkoutCreateValidatorDto validatorDtoCreate;
    private final TypeWorkoutUpdateValidatorDto validatorDtoUpdate;

    public TypeWorkoutAdminServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validatorDtoCreate = TypeWorkoutCreateValidatorDto.getInstance();
        this.validatorDtoUpdate = TypeWorkoutUpdateValidatorDto.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.typeWorkoutService = (TypeWorkoutService) getServletContext().getAttribute(TYPE_WORKOUT_SERVICE);
    }

    /**
     * @param req  запрос на создание нового типа тренировки дневника TypeWorkoutCreateDto
     * @param resp ответ пользователю статус 201 и созданный тип тренировки TypeWorkoutResponseDto
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long requesterId;
        var session = req.getSession();
        UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
        if (userFromSession != null) {
            requesterId = userFromSession.getId();
        } else {
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("UNAUTHORIZED")
                    .message("Вы не авторизованы")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            TypeWorkoutCreateDto typeWorkoutCreateDto = null;

            try {
                typeWorkoutCreateDto = objectMapper.readValue(req.getReader(), TypeWorkoutCreateDto.class);
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
                validatorDtoCreate.validate(typeWorkoutCreateDto);
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
            typeWorkoutCreateDto.setRequesterId(requesterId);
            TypeWorkoutResponseDto typeWorkoutResponseDto = typeWorkoutService.createTypeWorkout(typeWorkoutCreateDto);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(typeWorkoutResponseDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (AccessDeniedException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("FORBIDDEN")
                    .message("У вас нет доступа к этой операции")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        } catch (NotCreatedException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("CONFLICT")
                    .message("Такой тип тренировки уже существует")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
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

    /**
     * @param req  запрос на обновление существующего типа тренировки TypeWorkoutUpdateDto
     * @param resp ответ пользователю статус 200 и обновленный тип тренировки TypeWorkoutResponseDto
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long requesterId;
        var session = req.getSession();
        UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
        if (userFromSession != null) {
            requesterId = userFromSession.getId();
        } else {
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("UNAUTHORIZED")
                    .message("Вы не авторизованы")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            TypeWorkoutUpdateDto typeWorkoutUpdateDto = null;

            try {
                typeWorkoutUpdateDto = objectMapper.readValue(req.getReader(), TypeWorkoutUpdateDto.class);
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
                validatorDtoUpdate.validate(typeWorkoutUpdateDto);
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
            typeWorkoutUpdateDto.setRequesterId(requesterId);
            TypeWorkoutResponseDto typeWorkoutResponseDto = typeWorkoutService.updateTypeWorkout(typeWorkoutUpdateDto);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(typeWorkoutResponseDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (AccessDeniedException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("FORBIDDEN")
                    .message("У вас нет доступа к этой операции")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        } catch (NotUpdatedException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("CONFLICT")
                    .message("Такой тип тренировки уже существует")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
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