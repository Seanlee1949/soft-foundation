package com.example.boot.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * @author lishuai
 * @since 2022/11/26
 */
@TableName("SF_HISTORY_DATA")
public class HistoryData {
//    @TableField(value = "id")
    @TableField(exist = false)
    private String _id;
    private String id;
    private int packageAmount;
    private long pileId;
    private String version;
    private String deviceKey;
    private String machineKey;
    private String pileDescribe;
    private long beginTime;
    private long endTime;
    private String processType;
    private double longitude;
    private double latitude;
    private double holeLongitude;
    private double holeLatitude;
    // 设计桩长
    private double depth;
    // 施工桩长
    private double frDepth;
    // 初搅深度
    private double reDepth;
    // 空搅深度
    private double emDepth;
    private double waterCementRatio;
    private double downSpeed;
    private double upSpeed;
    private double cumulativePulp;
    private double averagePulp;
    private double cumulativeAsh;
    private double averageAsh;
    private double maxCurrent;
    private double averageCurrent;
    private int averagePressure;
    private double maxDownSpeed;
    private double maxUpSpeed;
    private double xAngle;
    private double yAngle;
    private int tTypeLength;
    private int tTypePulp;
    private double bottomPartPulp;
    private int tTypeAsh;
    private double bottomPartAsh;
    private int partCount;
    private String deviceType;
    private String deviceTypeName;
    private long ts;
    private int standardAsh;
    private double pileTopCurrent;
    private int maxAngle;
    private double pileTime;
    private String mileNo;
    private String partNo;
    private String milePartNo;
    private String pileNo;
    @TableField(exist = false)
    private List<HistoryDetailData> data;

    public void set_id(String _id) {
        this._id = id;
        this.id = _id;
    }

    public String get_id() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPackageAmount(int packageAmount) {
        this.packageAmount = packageAmount;
    }

    public int getPackageAmount() {
        return packageAmount;
    }

    public void setPileId(long pileId) {
        this.pileId = pileId;
    }

    public long getPileId() {
        return pileId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setMachineKey(String machineKey) {
        this.machineKey = machineKey;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setPileDescribe(String pileDescribe) {
        this.pileDescribe = pileDescribe;
    }

    public String getPileDescribe() {
        return pileDescribe;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setHoleLongitude(double holeLongitude) {
        this.holeLongitude = holeLongitude;
    }

    public double getHoleLongitude() {
        return holeLongitude;
    }

    public void setHoleLatitude(double holeLatitude) {
        this.holeLatitude = holeLatitude;
    }

    public double getHoleLatitude() {
        return holeLatitude;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getDepth() {
        return depth;
    }

    public void setFrDepth(double frDepth) {
        this.frDepth = frDepth;
    }

    public double getFrDepth() {
        return frDepth;
    }

    public void setReDepth(double reDepth) {
        this.reDepth = reDepth;
    }

    public double getReDepth() {
        return reDepth;
    }

    public void setEmDepth(double emDepth) {
        this.emDepth = emDepth;
    }

    public double getEmDepth() {
        return emDepth;
    }

    public void setWaterCementRatio(double waterCementRatio) {
        this.waterCementRatio = waterCementRatio;
    }

    public double getWaterCementRatio() {
        return waterCementRatio;
    }

    public void setDownSpeed(double downSpeed) {
        this.downSpeed = downSpeed;
    }

    public double getDownSpeed() {
        return downSpeed;
    }

    public void setUpSpeed(double upSpeed) {
        this.upSpeed = upSpeed;
    }

    public double getUpSpeed() {
        return upSpeed;
    }

    public void setCumulativePulp(double cumulativePulp) {
        this.cumulativePulp = cumulativePulp;
    }

    public double getCumulativePulp() {
        return cumulativePulp;
    }

    public void setAveragePulp(double averagePulp) {
        this.averagePulp = averagePulp;
    }

    public double getAveragePulp() {
        return averagePulp;
    }

    public void setCumulativeAsh(double cumulativeAsh) {
        this.cumulativeAsh = cumulativeAsh;
    }

    public double getCumulativeAsh() {
        return cumulativeAsh;
    }

    public void setAverageAsh(double averageAsh) {
        this.averageAsh = averageAsh;
    }

    public double getAverageAsh() {
        return averageAsh;
    }

    public void setMaxCurrent(double maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public double getMaxCurrent() {
        return maxCurrent;
    }

    public void setAverageCurrent(double averageCurrent) {
        this.averageCurrent = averageCurrent;
    }

    public double getAverageCurrent() {
        return averageCurrent;
    }

    public void setAveragePressure(int averagePressure) {
        this.averagePressure = averagePressure;
    }

    public int getAveragePressure() {
        return averagePressure;
    }

    public void setMaxDownSpeed(double maxDownSpeed) {
        this.maxDownSpeed = maxDownSpeed;
    }

    public double getMaxDownSpeed() {
        return maxDownSpeed;
    }

    public void setMaxUpSpeed(double maxUpSpeed) {
        this.maxUpSpeed = maxUpSpeed;
    }

    public double getMaxUpSpeed() {
        return maxUpSpeed;
    }

    public void setXAngle(double xAngle) {
        this.xAngle = xAngle;
    }

    public double getXAngle() {
        return xAngle;
    }

    public void setYAngle(double yAngle) {
        this.yAngle = yAngle;
    }

    public double getYAngle() {
        return yAngle;
    }

    public void setTTypeLength(int tTypeLength) {
        this.tTypeLength = tTypeLength;
    }

    public int getTTypeLength() {
        return tTypeLength;
    }

    public void setTTypePulp(int tTypePulp) {
        this.tTypePulp = tTypePulp;
    }

    public int getTTypePulp() {
        return tTypePulp;
    }

    public void setBottomPartPulp(double bottomPartPulp) {
        this.bottomPartPulp = bottomPartPulp;
    }

    public double getBottomPartPulp() {
        return bottomPartPulp;
    }

    public void setTTypeAsh(int tTypeAsh) {
        this.tTypeAsh = tTypeAsh;
    }

    public int getTTypeAsh() {
        return tTypeAsh;
    }

    public void setBottomPartAsh(double bottomPartAsh) {
        this.bottomPartAsh = bottomPartAsh;
    }

    public double getBottomPartAsh() {
        return bottomPartAsh;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    public int getPartCount() {
        return partCount;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getTs() {
        return ts;
    }

    public void setStandardAsh(int standardAsh) {
        this.standardAsh = standardAsh;
    }

    public int getStandardAsh() {
        return standardAsh;
    }

    public void setPileTopCurrent(double pileTopCurrent) {
        this.pileTopCurrent = pileTopCurrent;
    }

    public double getPileTopCurrent() {
        return pileTopCurrent;
    }

    public void setMaxAngle(int maxAngle) {
        this.maxAngle = maxAngle;
    }

    public int getMaxAngle() {
        return maxAngle;
    }

    public void setPileTime(double pileTime) {
        this.pileTime = pileTime;
    }

    public double getPileTime() {
        return pileTime;
    }

    public void setMileNo(String mileNo) {
        this.mileNo = mileNo;
    }

    public String getMileNo() {
        return mileNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setMilePartNo(String milePartNo) {
        this.milePartNo = milePartNo;
    }

    public String getMilePartNo() {
        return milePartNo;
    }

    public void setPileNo(String pileNo) {
        this.pileNo = pileNo;
    }

    public String getPileNo() {
        return pileNo;
    }

    public void setData(List<HistoryDetailData> data) {
        this.data = data;
    }

    public List<HistoryDetailData> getData() {
        return data;
    }

}