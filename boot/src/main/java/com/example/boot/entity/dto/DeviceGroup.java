package com.example.boot.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author lishuai
 * @since 2022/11/25
 */
@TableName("SF_DEVICE_GROUP")
public class DeviceGroup {
    private String id;
    private String label;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
