package com.example.boot.controller;

import com.example.boot.constant.CommonConstant;
import com.example.boot.entity.vo.LoginVo;
import com.example.boot.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * @author lishuai
 * @since 2022/11/23
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginVo login(String userName, String password) {
       return userService.login(userName,password);
    }

    @PostMapping("/user")
    public String addUser(String userName, String password,String name) {
        userService.addUser(userName, password,name);
        return CommonConstant.SUCCESS;
    }

    @DeleteMapping("/user")
    public String deleteUser(String userName) {
        userService.deleteUser(userName);
        return CommonConstant.SUCCESS;
    }
}
