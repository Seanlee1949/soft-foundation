package com.example.boot.service.impl;

import com.example.boot.dao.UserMapper;
import com.example.boot.entity.dto.UserInfo;
import com.example.boot.entity.ClientUser;
import com.example.boot.entity.vo.LoginVo;
import com.example.boot.service.UserService;
import com.example.boot.util.CommonUtils;
import org.springframework.stereotype.Service;

import static com.example.boot.util.CommonUtils.getCurrentTime;

/**
 * @author lishuai
 * @since 2022/11/23
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public LoginVo login(String userName, String password) {
        LoginVo loginVo = new LoginVo();
        ClientUser clientUser = new ClientUser();
        clientUser.setUserName(userName);
        clientUser.setPassword(password);

        UserInfo userInfo = userMapper.selectByUserName(userName);


        if (userInfo == null) {
            loginVo.setSuccess(false);
            return loginVo;
        }
        if (userInfo.getPassword().equals(password)) {
            loginVo.setSuccess(true);
        } else {
            loginVo.setSuccess(false);
        }

        clientUser.setName(userInfo.getName());
        loginVo.setClientUser(clientUser);
        loginVo.setToken(CommonUtils.getUUID());
        return loginVo;
    }

    @Override
    public void addUser(String userName, String password,String name) {
        String currentTime = getCurrentTime();
        UserInfo userInfo = new UserInfo().setUserName(userName).setPassword(password).setName(name).setCreateTime(currentTime);
        userMapper.insert(userInfo);
    }

    @Override
    public void deleteUser(String userName) {
        userMapper.deleteByUserName(userName);
    }
}
