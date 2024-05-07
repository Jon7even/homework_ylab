package com.github.jon7even.servlet.diary;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryResponseDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.exception.AlreadyExistException;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import com.github.jon7even.services.DiaryService;
import com.github.jon7even.validator.ValidatorDto;
import com.github.jon7even.validator.impl.DiaryCreateDtoValidatorDto;
import com.github.jon7even.validator.impl.DiaryUpdateDtoValidatorDto;
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
import static com.github.jon7even.constants.ControllerPath.PATH_URL_DIARY;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_USER;

/**
 * Обработка Http запросов от пользователей для работы с дневником
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet(PATH_URL_USER + PATH_URL_DIARY)
public class DiaryUserServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final ValidatorDto<DiaryCreateDto> validatorDtoCreate;
    private final ValidatorDto<DiaryUpdateDto> validatorDtoUpdate;
    private DiaryService diaryService;

    public DiaryUserServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validatorDtoCreate = DiaryCreateDtoValidatorDto.getInstance();
        this.validatorDtoUpdate = DiaryUpdateDtoValidatorDto.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.diaryService = (DiaryService) getServletContext().getAttribute("diaryService");
    }

    /**
     * @param req  запрос на получение дневника по ID из сессии
     * @param resp ответ пользователю статус 200 и найденный дневник DiaryResponseDto
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId;
        var session = req.getSession();
        UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
        if (userFromSession != null) {
            userId = userFromSession.getId();
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
            DiaryResponseDto diaryResponseDto = diaryService.getDiaryDtoByUserId(userId);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(diaryResponseDto));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("NOT_FOUND")
                    .message("Такого дневника не существует")
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
     * @param req  запрос на создание нового дневника DiaryCreateDto
     * @param resp ответ пользователю статус 201 и созданный дневник DiaryResponseDto
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DiaryCreateDto diaryCreateDto = null;

            try {
                diaryCreateDto = objectMapper.readValue(req.getReader(), DiaryCreateDto.class);
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
                diaryCreateDto.setUserId(userFromSession.getId());
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
                validatorDtoCreate.validate(diaryCreateDto);
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

            DiaryResponseDto diaryResponseDto = diaryService.createDiary(diaryCreateDto);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(diaryResponseDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (AlreadyExistException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("CONFLICT")
                    .message("Дневник уже существует")
                    .timestamp(LocalDateTime.now())
                    .build();
            resp.getWriter().write(objectMapper.writeValueAsString(error));
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        } catch (NotCreatedException e) {
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
     * @param req  запрос на обновление существующего дневника DiaryUpdateDto
     * @param resp ответ пользователю статус 200 и обновленный дневник DiaryResponseDto
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DiaryUpdateDto diaryUpdateDto = null;
            try {
                diaryUpdateDto = objectMapper.readValue(req.getReader(), DiaryUpdateDto.class);
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
                diaryUpdateDto.setUserId(userFromSession.getId());
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
                validatorDtoUpdate.validate(diaryUpdateDto);
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

            DiaryResponseDto diaryResponseDto = diaryService.updateDiary(diaryUpdateDto);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(diaryResponseDto));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            ApiError error = ApiError.builder()
                    .reason("NOT_FOUND")
                    .message("Такого дневника не существует")
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
}
