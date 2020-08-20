package com.wwh.controller;

import com.wwh.entity.User;
import com.wwh.result.Result;
import com.wwh.result.ResultFactory;
import com.wwh.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    UserService userService;

    // 登陆方法
    @PostMapping(value = "/api/login")
    public Result login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("username:   " + username);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        // isAuthenticated 为 false 证明不是登录过的，
        // isRememberd 为true 证明是没登陆直接通过记住我功能进来的
        usernamePasswordToken.setRememberMe(true);

        try {
            subject.login(usernamePasswordToken);
            User user = userService.findByUsername(username);

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("user:   " + user);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            // isEnabled 判断该用户是否被禁用
            if (!user.isEnabled()) {
                return ResultFactory.buildFailResult("该用户已被禁用");
            }
            return ResultFactory.buildSuccessResult(username);
            // IncorrectCredentialsException 凭证异常
        } catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("密码错误");
            // UnknownAccountException 未知账户异常
        } catch (UnknownAccountException e) {
            return ResultFactory.buildFailResult("账户不存在");
        }
    }

    // 注册方法
    @PostMapping("/api/register")
    public Result register(@RequestBody User user) {
        int status = userService.register(user);
        if (status == 0) {
            return ResultFactory.buildFailResult("用户名和密码不能为空");
        } else if (status == 1) {
            return ResultFactory.buildSuccessResult("注册成功");
        } else if (status == 2) {
            return ResultFactory.buildFailResult("用户已存在");
        } else {
            return ResultFactory.buildFailResult("未知错误");
        }
    }

    // 登出方法
    @GetMapping("/api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return ResultFactory.buildSuccessResult("成功登出");
    }

    // 身份认证
    @GetMapping("/api/authentication")
    public String authentication() {
        return "身份认证成功";
    }

    @GetMapping("/api/userdesc")
    public User getUserDesc(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        return userService.findByUsername("username");
    }
}
