package com.example.boot.entity.vo;

import com.example.boot.entity.response.CommonResponse;

import java.util.List;

/**
 * @author lishuai
 * @since 2022/11/27
 */
public class DeviceResponse extends CommonResponse {
    DeviceResult result;

    public DeviceResult getResult() {
        return result;
    }

    public void setResult(DeviceResult result) {
        this.result = result;
    }
}
