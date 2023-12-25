package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/updateAction.do")
public class UpdatePostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String userid = req.getParameter("user_id");
        String username = req.getParameter("user_name");
        String password = req.getParameter("user_password");
        String userbirth = req.getParameter("user_birth");
        HttpSession session = req.getSession();
        User a = (User) session.getAttribute("user");

        User user = new User(userid,username, password,userbirth,a.getUserAuth(),100_0000, LocalDateTime.now(),null);
        userService.updateUser(user);
        if (user != null) {
            session.setAttribute("user",user);
            return "shop/mypage/mypage_form";
        } else {
            req.setAttribute("errorMessage", "Invalid username or password");
            return "shop/mypage/mypage_form";
        }
    }
}
