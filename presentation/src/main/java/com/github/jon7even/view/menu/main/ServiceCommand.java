package com.github.jon7even.view.menu.main;

import com.github.jon7even.config.BeanConfig;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.services.*;
import com.github.jon7even.services.impl.*;
import lombok.Data;

import java.util.Scanner;

/**
 * Абстрактный класс для вывода меню в консоль
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
public abstract class ServiceCommand {
    private Scanner scanner = new Scanner(System.in);
    private ServiceCommand commandNextMenu;
    private UserInMemoryDto userInMemory;
    private HistoryUserCreateDto historyUserCreateDto;
    private final BeanConfig beanConfig = BeanConfig.getInstance();
    private final UserService userService = new UserServiceImpl(beanConfig.getUserDao());
    private final AuthorizationService authorizationService = new AuthorizationServiceImpl(beanConfig.getUserDao());
    private final DiaryService diaryService = new DiaryServiceImpl(beanConfig.getDiaryDao());
    private final GroupPermissionsService groupPermissionsService = new GroupPermissionsServiceImpl(
            beanConfig.getGroupPermissionsDao());
    private final TypeWorkoutService typeWorkoutService = new TypeWorkoutServiceImpl(
            beanConfig.getUserDao(), beanConfig.getTypeWorkoutDao(), beanConfig.getGroupPermissionsDao()
    );
    private final HistoryUserService historyUserService = new HistoryUserServiceImpl(
            beanConfig.getUserDao(), beanConfig.getHistoryUserDao(), beanConfig.getGroupPermissionsDao()
    );
    private final WorkoutService workoutService = new WorkoutServiceImpl(
            beanConfig.getUserDao(), beanConfig.getWorkoutDao(), beanConfig.getDiaryDao(),
            beanConfig.getTypeWorkoutDao(), beanConfig.getGroupPermissionsDao()
    );

    /**
     * Абстрактный метод для обработки меню
     */
    public abstract void handle();
}
