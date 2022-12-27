package com.example.boot.entity.vo;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public class GroupSumVo {
    private String deviceType;
    private long total;
    private double total_depth;
    private String _id;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public double getTotal_depth() {
        return total_depth;
    }

    public void setTotal_depth(double total_depth) {
        this.total_depth = total_depth;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
