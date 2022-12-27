package com.example.boot.entity.vo;

import com.example.boot.entity.ClientUser;
import com.example.boot.entity.response.CommonResponse;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public class LoginVo extends CommonResponse {
    ClientUser clientUser;
    private String token;

    public ClientUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
