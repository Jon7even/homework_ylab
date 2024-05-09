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
import com.github.jon7even.services.DiaryService;
import com.github.jon7even.servlet.handler.ErrorHandlerServlet;
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

import static com.github.jon7even.constants.ControllerContent.DEFAULT_CONTENT_JSON;
import static com.github.jon7even.constants.ControllerContent.DEFAULT_ENCODING;
import static com.github.jon7even.constants.ControllerContext.DIARY_SERVICE;
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
    private final ErrorHandlerServlet errorHandlerServlet;

    public DiaryUserServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validatorDtoCreate = DiaryCreateDtoValidatorDto.getInstance();
        this.validatorDtoUpdate = DiaryUpdateDtoValidatorDto.getInstance();
        this.errorHandlerServlet = ErrorHandlerServlet.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.diaryService = (DiaryService) getServletContext().getAttribute(DIARY_SERVICE);
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
            errorHandlerServlet.handleError(resp, "Пользователь не авторизовался",
                    "UNAUTHORIZED",
                    "Вы не авторизованы",
                    HttpServletResponse.SC_UNAUTHORIZED
            );
            return;
        }
        try {
            DiaryResponseDto diaryResponseDto = diaryService.getDiaryDtoByUserId(userId);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(diaryResponseDto));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NotFoundException e) {
            errorHandlerServlet.handleError(resp, e.getMessage(),
                    "NOT_FOUND",
                    "Дневника с таким ID не существует",
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
                errorHandlerServlet.handleError(resp, e.getMessage(),
                        "BAD_REQUEST",
                        "Тело запроса не может быть пустым",
                        HttpServletResponse.SC_BAD_REQUEST
                );
                return;
            }

            var session = req.getSession();
            UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
            if (userFromSession != null) {
                diaryCreateDto.setUserId(userFromSession.getId());
            } else {
                errorHandlerServlet.handleError(resp, "Пользователь не авторизовался",
                        "UNAUTHORIZED",
                        "Вы не авторизованы",
                        HttpServletResponse.SC_UNAUTHORIZED
                );
                return;
            }

            try {
                validatorDtoCreate.validate(diaryCreateDto);
            } catch (MethodArgumentNotValidException e) {
                errorHandlerServlet.handleError(resp, e.getMessage(),
                        "BAD_REQUEST",
                        e.getMessage(),
                        HttpServletResponse.SC_BAD_REQUEST
                );
                return;
            }

            DiaryResponseDto diaryResponseDto = diaryService.createDiary(diaryCreateDto);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(diaryResponseDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (AlreadyExistException e) {
            errorHandlerServlet.handleError(resp, e.getMessage(),
                    "CONFLICT",
                    "Дневник уже существует",
                    HttpServletResponse.SC_CONFLICT
            );
            return;
        } catch (NotCreatedException e) {
            errorHandlerServlet.handleError(resp, e.getMessage(),
                    "NOT_FOUND",
                    "Дневник уже существует",
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
                errorHandlerServlet.handleError(resp, e.getMessage(),
                        "BAD_REQUEST",
                        "Тело запроса не может быть пустым",
                        HttpServletResponse.SC_BAD_REQUEST
                );
                return;
            }

            var session = req.getSession();
            UserLogInResponseDto userFromSession = (UserLogInResponseDto) session.getAttribute("user");
            if (userFromSession != null) {
                diaryUpdateDto.setUserId(userFromSession.getId());
            } else {
                errorHandlerServlet.handleError(resp, "Пользователь не авторизовался",
                        "UNAUTHORIZED",
                        "Вы не авторизованы",
                        HttpServletResponse.SC_UNAUTHORIZED
                );
                return;
            }

            try {
                validatorDtoUpdate.validate(diaryUpdateDto);
            } catch (MethodArgumentNotValidException e) {
                errorHandlerServlet.handleError(resp, e.getMessage(),
                        "BAD_REQUEST",
                        e.getMessage(),
                        HttpServletResponse.SC_BAD_REQUEST
                );
                return;
            }

            DiaryResponseDto diaryResponseDto = diaryService.updateDiary(diaryUpdateDto);

            resp.setContentType(DEFAULT_CONTENT_JSON);
            resp.setCharacterEncoding(DEFAULT_ENCODING);
            resp.getWriter().write(objectMapper.writeValueAsString(diaryResponseDto));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NotFoundException e) {
            errorHandlerServlet.handleError(resp, e.getMessage(),
                    "NOT_FOUND",
                    "Дневника с таким ID не существует",
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
