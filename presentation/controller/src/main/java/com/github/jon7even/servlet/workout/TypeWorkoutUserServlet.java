package com.github.jon7even.servlet.workout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import com.github.jon7even.services.TypeWorkoutService;
import com.github.jon7even.validator.impl.ParamValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.jon7even.constants.ControllerContent.DEFAULT_CONTENT_JSON;
import static com.github.jon7even.constants.ControllerContent.DEFAULT_ENCODING;
import static com.github.jon7even.constants.ControllerContext.TYPE_WORKOUT_SERVICE;
import static com.github.jon7even.constants.ControllerParam.PARAM_TYPE_WORKOUT_ID;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_TYPE_WORKOUT;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_USER;

/**
 * Обработка Http запросов от пользователей для работы с типами тренировок
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet(PATH_URL_USER + PATH_URL_TYPE_WORKOUT)
public class TypeWorkoutUserServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private TypeWorkoutService typeWorkoutService;
    private final ParamValidator validator;

    public TypeWorkoutUserServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validator = ParamValidator.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.typeWorkoutService = (TypeWorkoutService) getServletContext().getAttribute(TYPE_WORKOUT_SERVICE);
    }

    /**
     * @param req  запрос с параметром ID - получение типа тренировки по ID возвращает подробный ответ в виде
     *             TypeWorkoutResponseDto, без параметра выводит все существующие типы в виде списка краткой формы
     *             ответа TypeWorkoutShortDto
     * @param resp ответ пользователю статус 200 и найденный тип тренировки или список
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
        if (userFromSession == null) {
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
            if (req.getParameter(PARAM_TYPE_WORKOUT_ID) == null) {
                List<TypeWorkoutShortDto> findAllTypeWorkoutsNoSort = typeWorkoutService.findAllTypeWorkoutsNoSort();

                resp.setContentType(DEFAULT_CONTENT_JSON);
                resp.setCharacterEncoding(DEFAULT_ENCODING);
                resp.getWriter().write(objectMapper.writeValueAsString(findAllTypeWorkoutsNoSort));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                String typeWorkoutId = req.getParameter(PARAM_TYPE_WORKOUT_ID);

                try {
                    validator.validate(typeWorkoutId, PARAM_TYPE_WORKOUT_ID);
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

                try {
                    TypeWorkoutResponseDto typeWorkoutResponseDto = typeWorkoutService.findTypeWorkoutByTypeWorkoutId(
                            Long.valueOf(typeWorkoutId)
                    );

                    resp.setContentType(DEFAULT_CONTENT_JSON);
                    resp.setCharacterEncoding(DEFAULT_ENCODING);
                    resp.getWriter().write(objectMapper.writeValueAsString(typeWorkoutResponseDto));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } catch (NotFoundException e) {
                    System.out.println(e.getMessage());
                    resp.setContentType(DEFAULT_CONTENT_JSON);
                    resp.setCharacterEncoding(DEFAULT_ENCODING);
                    ApiError error = ApiError.builder()
                            .reason("NOT_FOUND")
                            .message("Типа тренировки с таким ID не существует")
                            .timestamp(LocalDateTime.now())
                            .build();
                    resp.getWriter().write(objectMapper.writeValueAsString(error));
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
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
