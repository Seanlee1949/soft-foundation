package com.example.boot.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author lishuai
 * @since 2022/11/26
 */
@TableName("SF_HISTORY_DETAIL_DATA")
public class HistoryDetailData {
    @TableId
    private String id;
    private int partTime;
    private double partDownSpeed;
    private double partDeep;
    private int partId;
    private int partPressure;
    private double partDensity;
    private double partPulp;
    private double partCurrent;
    private double partAsh;
    private String deviceKey;
    private String pileDescribe;

    public void setPartTime(int partTime) {
        this.partTime = partTime;
    }

    public int getPartTime() {
        return partTime;
    }

    public void setPartDownSpeed(double partDownSpeed) {
        this.partDownSpeed = partDownSpeed;
    }

    public double getPartDownSpeed() {
        return partDownSpeed;
    }

    public void setPartDeep(double partDeep) {
        this.partDeep = partDeep;
    }

    public double getPartDeep() {
        return partDeep;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartPressure(int partPressure) {
        this.partPressure = partPressure;
    }

    public int getPartPressure() {
        return partPressure;
    }

    public void setPartDensity(double partDensity) {
        this.partDensity = partDensity;
    }

    public double getPartDensity() {
        return partDensity;
    }

    public void setPartPulp(double partPulp) {
        this.partPulp = partPulp;
    }

    public double getPartPulp() {
        return partPulp;
    }

    public void setPartCurrent(double partCurrent) {
        this.partCurrent = partCurrent;
    }

    public double getPartCurrent() {
        return partCurrent;
    }

    public void setPartAsh(double partAsh) {
        this.partAsh = partAsh;
    }

    public double getPartAsh() {
        return partAsh;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPileDescribe() {
        return pileDescribe;
    }

    public void setPileDescribe(String pileDescribe) {
        this.pileDescribe = pileDescribe;
    }
}