package com.example.boot.entity.vo;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public class NewestVo {
    private double depth;
    private String deviceTypeName;
    private String id;
    private String pileDescribe;
    private long ts;


    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
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

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
//depth          20.152
//        deviceTypeName          "搅拌桩"
//        id          "6380bf4ff79b1c58055e7bdf"
//        pileDescribe          "0+0_C414"
//        ts          1669381967