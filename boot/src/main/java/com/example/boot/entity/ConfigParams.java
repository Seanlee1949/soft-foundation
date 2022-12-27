package com.example.boot.entity;

/**
 * @author lishuai
 * @since 2022/11/26
 */
public class ConfigParams {
    private String projectName;
    private String worker;
    private String minPileTime;
    private String designDepth;
    private String designAsh;
    private String designPilePadding;
    private String pileWidth;
    private String designUpSpeed;
    private String designDownSpeed;

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getWorker() {
        return worker;
    }

    public void setMinPileTime(String minPileTime) {
        this.minPileTime = minPileTime;
    }

    public String getMinPileTime() {
        return minPileTime;
    }

    public void setDesignDepth(String designDepth) {
        this.designDepth = designDepth;
    }

    public String getDesignDepth() {
        return designDepth;
    }

    public void setDesignAsh(String designAsh) {
        this.designAsh = designAsh;
    }

    public String getDesignAsh() {
        return designAsh;
    }

    public void setDesignPilePadding(String designPilePadding) {
        this.designPilePadding = designPilePadding;
    }

    public String getDesignPilePadding() {
        return designPilePadding;
    }

    public void setPileWidth(String pileWidth) {
        this.pileWidth = pileWidth;
    }

    public String getPileWidth() {
        return pileWidth;
    }

    public void setDesignUpSpeed(String designUpSpeed) {
        this.designUpSpeed = designUpSpeed;
    }

    public String getDesignUpSpeed() {
        return designUpSpeed;
    }

    public void setDesignDownSpeed(String designDownSpeed) {
        this.designDownSpeed = designDownSpeed;
    }

    public String getDesignDownSpeed() {
        return designDownSpeed;
    }

}