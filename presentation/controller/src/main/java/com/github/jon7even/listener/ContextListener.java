package com.github.jon7even.listener;


import com.github.jon7even.dataproviders.core.LiquibaseManager;
import com.github.jon7even.dataproviders.core.impl.LiquibaseManagerImpl;
import com.github.jon7even.services.*;
import com.github.jon7even.services.config.BeanConfig;
import com.github.jon7even.services.impl.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import static com.github.jon7even.constants.ControllerContext.*;

/**
 * Слушатель в котором происходит инициализация миграции БД и внедрение бинов для сервисов на все сервлеты
 *
 * @author Jon7even
 * @version 1.0
 */
@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Инициализирую получение общей конфигурации");
        BeanConfig beanConfig = BeanConfig.getInstance();
        System.out.println("Инициализирую миграцию БД");
        LiquibaseManager liquibaseManager = new LiquibaseManagerImpl(beanConfig.getMainConfig());
        liquibaseManager.initMigrate();
        System.out.println("Сохраняю сервисы в контекст");
        ServletContext servletContext = event.getServletContext();

        UserService userService = new UserServiceImpl(beanConfig.getUserDao());
        servletContext.setAttribute(USER_SERVICE, userService);

        AuthorizationService authService = new AuthorizationServiceImpl(beanConfig.getUserDao());
        servletContext.setAttribute(AUTH_SERVICE, authService);

        DiaryService diaryService = new DiaryServiceImpl(beanConfig.getDiaryDao());
        servletContext.setAttribute(DIARY_SERVICE, diaryService);

        GroupPermissionsService groupPermissionsService = new GroupPermissionsServiceImpl(
                beanConfig.getGroupPermissionsDao()
        );
        servletContext.setAttribute(PERMISSIONS_SERVICE, groupPermissionsService);

        TypeWorkoutService typeWorkoutService = new TypeWorkoutServiceImpl(
                beanConfig.getUserDao(), beanConfig.getTypeWorkoutDao(), beanConfig.getGroupPermissionsDao()
        );
        servletContext.setAttribute(TYPE_WORKOUT_SERVICE, typeWorkoutService);

        HistoryUserService historyUserService = new HistoryUserServiceImpl(
                beanConfig.getUserDao(), beanConfig.getHistoryUserDao(), beanConfig.getGroupPermissionsDao()
        );
        servletContext.setAttribute(AUDIT_SERVICE, historyUserService);

        WorkoutService workoutService = new WorkoutServiceImpl(beanConfig.getUserDao(), beanConfig.getWorkoutDao(),
                beanConfig.getDiaryDao(), beanConfig.getTypeWorkoutDao(), beanConfig.getGroupPermissionsDao()
        );
        servletContext.setAttribute(WORKOUT_SERVICE, workoutService);

        System.out.println("Приложение успешно запущено");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Приложение успешно выключено");
    }
}
