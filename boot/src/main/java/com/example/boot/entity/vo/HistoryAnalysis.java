package com.example.boot.entity.vo;

/**
 * @author lishuai
 * @since 2022/11/28
 */
public class HistoryAnalysis {
    private double totalCumulativeAsh;
    private double totalCumulativePulp;
    private int totalPileNum;
    private double totalDepth;
    private double avgAsh;
    private double avgPulp;
    private long beginTime;
    private long endTime;

    public void setTotalCumulativeAsh(double totalCumulativeAsh) {
        this.totalCumulativeAsh = totalCumulativeAsh;
    }

    public double getTotalCumulativeAsh() {
        return totalCumulativeAsh;
    }

    public void setTotalCumulativePulp(double totalCumulativePulp) {
        this.totalCumulativePulp = totalCumulativePulp;
    }

    public double getTotalCumulativePulp() {
        return totalCumulativePulp;
    }

    public void setTotalPileNum(int totalPileNum) {
        this.totalPileNum = totalPileNum;
    }

    public int getTotalPileNum() {
        return totalPileNum;
    }

    public void setTotalDepth(double totalDepth) {
        this.totalDepth = totalDepth;
    }

    public double getTotalDepth() {
        return totalDepth;
    }

    public void setAvgAsh(double avgAsh) {
        this.avgAsh = avgAsh;
    }

    public double getAvgAsh() {
        return avgAsh;
    }

    public void setAvgPulp(double avgPulp) {
        this.avgPulp = avgPulp;
    }

    public double getAvgPulp() {
        return avgPulp;
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

}