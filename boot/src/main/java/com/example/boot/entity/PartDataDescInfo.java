package com.example.boot.entity;

public class PartDataDescInfo {
    private double partDeep;
    private int partId;

    public PartDataDescInfo(double partDeep, int partId) {
        this.partDeep = partDeep;
        this.partId = partId;
    }

    public double getPartDeep() {
        return partDeep;
    }

    public void setPartDeep(double partDeep) {
        this.partDeep = partDeep;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public void addPartId() {
        this.partId++;
    }

    public void addPartDeep(double partdeep) {
        this.partDeep = this.partDeep + partDeep;
    }

    public void addPartDeepDefault(int direction) {
        this.partDeep = this.partDeep + 0.25 * direction;
    }
}
