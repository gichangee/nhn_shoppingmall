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

@RequestMapping(method = RequestMapping.Method.POST, value = "/singupAction.do")
public class SignupPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String userid = req.getParameter("user_id");
        String username = req.getParameter("user_name");
        String password = req.getParameter("user_password");
        String userbirth = req.getParameter("user_birth");

        User user = new User(userid,username, password,userbirth,User.Auth.ROLE_USER,100_0000, LocalDateTime.now(),null);

        userService.saveUser(user);
        if (user != null) {
            return "redirect:/login.do";
        } else {
            req.setAttribute("errorMessage", "Invalid username or password");
            return "shop/login/signup_form";
        }
    }
}
