package com.example.boot.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public class DeviceGroupVo {
    private String label;
    private String name;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
//label          "搅拌桩"
//        name          "JBZ"
