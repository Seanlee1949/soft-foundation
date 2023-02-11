package com.example.boot.service;

import com.example.boot.entity.vo.LoginVo;

/**
 * @author lishuai
 * @since 2022/11/23
 */
public interface UserService {
    LoginVo login(String userName, String password);

    void addUser(String userName, String password,String name);

    void deleteUser(String userName);
}
