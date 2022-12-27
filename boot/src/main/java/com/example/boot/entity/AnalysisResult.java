package com.example.boot.entity;

import java.util.Date;

/**
 * @author lishuai
 * @since 2022/11/28
 */
public class AnalysisResult {
    private String  date;
    // 施工桩长
    private double depth;
    private int pileAmount;


    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getDepth() {
        return depth;
    }

    public void setPileAmount(int pileAmount) {
        this.pileAmount = pileAmount;
    }

    public int getPileAmount() {
        return pileAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}