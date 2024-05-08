package com.github.jon7even.servlet.workout;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutUpdateDto;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.AlreadyExistException;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import com.github.jon7even.services.DiaryService;
import com.github.jon7even.services.WorkoutService;
import com.github.jon7even.validator.ValidatorDto;
import com.github.jon7even.validator.impl.LongValidator;
import com.github.jon7even.validator.impl.WorkoutCreateValidatorDto;
import com.github.jon7even.validator.impl.WorkoutUpdateValidatorDto;
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
import static com.github.jon7even.constants.ControllerContext.DIARY_SERVICE;
import static com.github.jon7even.constants.ControllerContext.WORKOUT_SERVICE;
import static com.github.jon7even.constants.ControllerParam.PARAM_USER_ID;
import static com.github.jon7even.constants.ControllerParam.PARAM_WORKOUT_ID;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_USER;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_WORKOUT;

/**
 * Обработка Http запросов от пользователей для работы с тренировками
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet(PATH_URL_USER + PATH_URL_WORKOUT)
public class WorkoutUserServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private WorkoutService workoutService;
    private DiaryService diaryService;
    private final LongValidator validator;
    private final ValidatorDto<WorkoutCreateDto> createValidatorDto;
    private final ValidatorDto<com.github.jon7even.core.domain.v1.dto.workout.WorkoutUpdateDto> updateValidatorDto;

    public WorkoutUserServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validator = LongValidator.getInstance();
        this.createValidatorDto = WorkoutCreateValidatorDto.getInstance();
        this.updateValidatorDto = WorkoutUpdateValidatorDto.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.workoutService = (WorkoutService) getServletContext().getAttribute(WORKOUT_SERVICE);
        this.diaryService = (DiaryService) getServletContext().getAttribute(DIARY_SERVICE);
    }

    /**
     * @param req  запрос с параметром ID - получение тренировки по ID возвращает подробный ответ в виде
     *             WorkoutFullResponseDto, без параметра выводит все существующие тренировки, сортированные по дате
     *             в виде списка краткой формы ответа WorkoutShortResponseDto
     * @param resp ответ пользователю статус 200 и найденная тренировка или список
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idDiary = null;
        var session = req.getSession();
        UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
        if (userFromSession != null) {
            idDiary = diaryService.getIdDiaryByUserId(userFromSession.getId());
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
            if (req.getParameter(PARAM_WORKOUT_ID) == null) {
                List<WorkoutShortResponseDto> findAllWorkoutByOwnerDiaryBySortByDeskDate =
                        workoutService.findAllWorkoutByOwnerDiaryBySortByDeskDate(idDiary, userFromSession.getId());

                resp.setContentType(DEFAULT_CONTENT_JSON);
                resp.setCharacterEncoding(DEFAULT_ENCODING);
                resp.getWriter().write(objectMapper.writeValueAsString(findAllWorkoutByOwnerDiaryBySortByDeskDate));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                String workoutId = req.getParameter(PARAM_WORKOUT_ID);

                try {
                    validator.validate(workoutId, PARAM_WORKOUT_ID);
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
                    WorkoutFullResponseDto workoutResponseDto = workoutService.getWorkoutById(Long.valueOf(workoutId));

                    resp.setContentType(DEFAULT_CONTENT_JSON);
                    resp.setCharacterEncoding(DEFAULT_ENCODING);
                    resp.getWriter().write(objectMapper.writeValueAsString(workoutResponseDto));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } catch (NotFoundException e) {
                    System.out.println(e.getMessage());
                    resp.setContentType(DEFAULT_CONTENT_JSON);
                    resp.setCharacterEncoding(DEFAULT_ENCODING);
                    ApiError error = ApiError.builder()
                            .reason("NOT_FOUND")
                            .message("Тренировки с таким ID не существует")
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

    /**
     * @param req  запрос на создание новой тренировки WorkoutCreateDto
     * @param resp ответ пользователю статус 201 и созданная тренировка в полном ответе WorkoutFullResponseDto
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            WorkoutCreateDto workoutCreateDto = null;

            try {
                workoutCreateDto = objectMapper.readValue(req.getReader(), WorkoutCreateDto.class);
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

            var session = req.getSession();
            UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
            if (userFromSession != null) {
                Long idDiary = diaryService.getIdDiaryByUserId(userFromSession.getId());
                workoutCreateDto.setIdDiary(idDiary);
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
                createValidatorDto.validate(workoutCreateDto);
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
            WorkoutFullResponseDto workoutFullResponseDto = workoutService.saveWorkout(workoutCreateDto);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(workoutFullResponseDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (AlreadyExistException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("CONFLICT")
                    .message("Тренировка с таким типом уже внесена в этот день")
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
     * @param req  запрос на обновление существующей тренировки WorkoutUpdateDto
     * @param resp ответ пользователю статус 200 и обновленная тренировка WorkoutFullResponseDto
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            WorkoutUpdateDto workoutUpdateDto = null;
            try {
                workoutUpdateDto = objectMapper.readValue(req.getReader(), WorkoutUpdateDto.class);
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

            var session = req.getSession();
            UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
            if (userFromSession != null) {
                Long idDiary = diaryService.getIdDiaryByUserId(userFromSession.getId());
                workoutUpdateDto.setIdDiary(idDiary);
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
                updateValidatorDto.validate(workoutUpdateDto);
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

            WorkoutFullResponseDto workoutFullResponseDto = workoutService.updateWorkout(workoutUpdateDto);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(workoutFullResponseDto));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("NOT_FOUND")
                    .message("Такой тренировки не существует")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
     * @param req  запрос на удаление существующей тренировки по ID
     * @param resp ответ пользователю статус 204 тренировка успешно удалена
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            String workoutId = req.getParameter(PARAM_WORKOUT_ID);

            try {
                validator.validate(workoutId, PARAM_USER_ID);
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

            workoutService.deleteWorkoutByWorkoutIdAndOwnerId(Long.valueOf(workoutId), requesterId);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("NOT_FOUND")
                    .message("Такой тренировки не существует")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (AccessDeniedException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("FORBIDDEN")
                    .message("Вы не являетесь владельцем этой тренировки")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
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
