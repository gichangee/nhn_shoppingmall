package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import javax.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@WebListener
@Slf4j
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.

        DbConnectionThreadLocal.initialize();
        registerUserIfNotExists("admin");
        registerUserIfNotExists("user");
        DbConnectionThreadLocal.reset();
    }

    private void registerUserIfNotExists(String username) {

        User user = userService.getUser(username);
        if (user == null) {
            if(username.equals("admin")){
                user = new User(username,username,"12345","19900505", User.Auth.ROLE_ADMIN,100_0000,
                        LocalDateTime.now(),null);
            }else{
                user = new User(username,username,"12345","19900505", User.Auth.ROLE_USER,100_0000,
                        LocalDateTime.now(),null);
            }
            userService.saveUser(user);
            log.info("Registered test account: {}", username);
        } else {
            log.info("Test account already exists: {}", username);
        }
    }
}
