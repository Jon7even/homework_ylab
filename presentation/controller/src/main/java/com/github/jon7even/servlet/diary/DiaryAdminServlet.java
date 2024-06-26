package com.github.jon7even.servlet.diary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryResponseDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.model.ApiError;
import com.github.jon7even.services.DiaryService;
import com.github.jon7even.services.GroupPermissionsService;
import com.github.jon7even.servlet.handler.ErrorHandlerServlet;
import com.github.jon7even.validator.impl.ParamValidator;
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
import static com.github.jon7even.constants.ControllerContext.DIARY_SERVICE;
import static com.github.jon7even.constants.ControllerContext.PERMISSIONS_SERVICE;
import static com.github.jon7even.constants.ControllerParam.PARAM_USER_ID;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_ADMIN;
import static com.github.jon7even.constants.ControllerPath.PATH_URL_DIARY;
import static com.github.jon7even.services.impl.DiaryServiceImpl.SERVICE_DIARY_ID;

/**
 * Обработка Http запросов от групп, у которых есть на это разрешение с ресурсом дневник
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
@WebServlet(PATH_URL_ADMIN + PATH_URL_DIARY)
public class DiaryAdminServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final ParamValidator validator;
    private DiaryService diaryService;
    private GroupPermissionsService groupPermissionsService;
    private final ErrorHandlerServlet errorHandlerServlet;

    public DiaryAdminServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.validator = ParamValidator.getInstance();
        this.errorHandlerServlet = ErrorHandlerServlet.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.diaryService = (DiaryService) getServletContext().getAttribute(DIARY_SERVICE);
        this.groupPermissionsService = (GroupPermissionsService) getServletContext().getAttribute(PERMISSIONS_SERVICE);
    }

    /**
     * @param req  запрос на получение дневника по ID дневника
     * @param resp ответ пользователю статус 200 и найденный дневник DiaryResponseDto
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            boolean accessToAction = groupPermissionsService.getPermissionForService(
                    requesterId, userFromSession.getIdGroupPermissions(), SERVICE_DIARY_ID, FlagPermissions.READ
            );
            if (!accessToAction) {
                errorHandlerServlet.handleError(resp,
                        "Пользователь пытается совершить запрещенную ему операцию",
                        "FORBIDDEN",
                        "У вас нет доступа к этой операции",
                        HttpServletResponse.SC_FORBIDDEN
                );
                return;
            }

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

            DiaryResponseDto diaryResponseDto = diaryService.getDiaryDtoByUserId(Long.valueOf(userId));

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

