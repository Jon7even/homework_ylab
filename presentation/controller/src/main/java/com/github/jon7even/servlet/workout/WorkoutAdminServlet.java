package com.github.jon7even.servlet.workout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.services.WorkoutService;
import com.github.jon7even.servlet.handler.ErrorHandlerServlet;
import com.github.jon7even.validator.impl.ParamValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.github.jon7even.constants.ControllerContent.DEFAULT_CONTENT_JSON;
import static com.github.jon7even.constants.ControllerContent.DEFAULT_ENCODING;
import static com.github.jon7even.constants.ControllerContext.WORKOUT_SERVICE;
import static com.github.jon7even.constants.ControllerParam.PARAM_USER_ID;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_ADMIN;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_WORKOUT;

/**
 * Обработка Http запросов от групп, у которых есть на это разрешение с ресурсом тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet(PATH_URL_ADMIN + PATH_URL_WORKOUT)
public class WorkoutAdminServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final ParamValidator validator;
    private WorkoutService workoutService;
    private final ErrorHandlerServlet errorHandlerServlet;

    public WorkoutAdminServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validator = ParamValidator.getInstance();
        this.errorHandlerServlet = ErrorHandlerServlet.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.workoutService = (WorkoutService) getServletContext().getAttribute(WORKOUT_SERVICE);
    }

    /**
     * @param req  запрос на получение списка тренировок по ID пользователя
     * @param resp ответ пользователю статус 200 и список тренировок отсортированный по дате в кратком представлении
     *             WorkoutShortResponseDto
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long requesterId;
        var session = req.getSession();
        UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
        if (userFromSession != null) {
            requesterId = userFromSession.getId();
        } else {
            errorHandlerServlet.handleError(resp, "Пользователь не авторизовался",
                    "UNAUTHORIZED",
                    "Вы не авторизованы",
                    HttpServletResponse.SC_UNAUTHORIZED
            );
            return;
        }
        try {
            String userId = req.getParameter(PARAM_USER_ID);

            try {
                validator.validate(userId, PARAM_USER_ID);
            } catch (MethodArgumentNotValidException e) {
                errorHandlerServlet.handleError(resp, e.getMessage(),
                        "BAD_REQUEST",
                        e.getMessage(),
                        HttpServletResponse.SC_BAD_REQUEST
                );
                return;
            }

            List<WorkoutShortResponseDto> workoutShortResponseDtoList =
                    workoutService.findAllWorkoutByAdminDiaryBySortByDeskDate(Long.valueOf(userId), requesterId);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(workoutShortResponseDtoList));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NotFoundException e) {
            errorHandlerServlet.handleError(resp, e.getMessage(),
                    "NOT_FOUND",
                    "Такого пользователя не существует",
                    HttpServletResponse.SC_NOT_FOUND
            );
            return;
        } catch (AccessDeniedException e) {
            errorHandlerServlet.handleError(resp, e.getMessage(),
                    "FORBIDDEN",
                    "У вас нет доступа к этой операции",
                    HttpServletResponse.SC_FORBIDDEN
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


