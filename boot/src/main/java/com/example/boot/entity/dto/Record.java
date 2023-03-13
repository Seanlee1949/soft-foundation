package com.example.boot.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.example.boot.entity.vo.RealTime;

/**
 * @author lishuai
 * @since 2022/11/25
 */
@TableName(value = "SF_RECORD",autoResultMap = true)
public class Record {
    private long activeAt;
    private String alias;
    private String cameraLive;
    private long clientId;
    private String clientName;
    private String comment;
    private String content;
    private long createAt;
    private long createBy;
    private String designParam;
    private String firmware;
    private long id;
    private int isMove;
    @TableField(value = "KEY_")
    private String key;
    private long lastAt;
    private int locked;
    private String machineKey;
    private String name;
    private String notifyUsers;
    private int onlineStatus;
    private long onlineTime;
    private String position;
    private int productId;
    @TableField(typeHandler = FastjsonTypeHandler.class,value = "REALTIME")
    private RealTime realtime;
    private int status;
    private String thumbnailBaseUrl;
    private String thumbnailPath;
    private String type;
    private String typeName;
    private long updateAt;
    private int updateBy;
    private String urlData;
    private String urlHistory;
    private String urlOnline;
    private String urlServer;
    private String urlToken;

    public long getActiveAt() {
        return activeAt;
    }

    public void setActiveAt(long activeAt) {
        this.activeAt = activeAt;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCameraLive() {
        return cameraLive;
    }

    public void setCameraLive(String cameraLive) {
        this.cameraLive = cameraLive;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public String getDesignParam() {
        return designParam;
    }

    public void setDesignParam(String designParam) {
        this.designParam = designParam;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsMove() {
        return isMove;
    }

    public void setIsMove(int isMove) {
        this.isMove = isMove;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getLastAt() {
        return lastAt;
    }

    public void setLastAt(long lastAt) {
        this.lastAt = lastAt;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(String machineKey) {
        this.machineKey = machineKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotifyUsers() {
        return notifyUsers;
    }

    public void setNotifyUsers(String notifyUsers) {
        this.notifyUsers = notifyUsers;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public RealTime getRealtime() {
        return realtime;
    }

    public void setRealtime(RealTime realtime) {
        this.realtime = realtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getThumbnailBaseUrl() {
        return thumbnailBaseUrl;
    }

    public void setThumbnailBaseUrl(String thumbnailBaseUrl) {
        this.thumbnailBaseUrl = thumbnailBaseUrl;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public String getUrlData() {
        return urlData;
    }

    public void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    public String getUrlHistory() {
        return urlHistory;
    }

    public void setUrlHistory(String urlHistory) {
        this.urlHistory = urlHistory;
    }

    public String getUrlOnline() {
        return urlOnline;
    }

    public void setUrlOnline(String urlOnline) {
        this.urlOnline = urlOnline;
    }

    public String getUrlServer() {
        return urlServer;
    }

    public void setUrlServer(String urlServer) {
        this.urlServer = urlServer;
    }

    public String getUrlToken() {
        return urlToken;
    }

    public void setUrlToken(String urlToken) {
        this.urlToken = urlToken;
    }
}
//    activeAt         1664867837
//    alias         ""
//    cameraLive         null
//    clientId         82
//    clientName         "庆平路项目"
//    comment         null
//    content         "{}"
//    createAt         1664713499
//    createBy         0
//    designParam         null
//    firmware         ""
//    id         1025
//    isMove         0
//    key         "MX01808022090091"
//    lastAt         1669371881
//    locked         0
//    machineKey         ""
//    name         "庆平路西段-3号机"
//    notifyUsers         ""
//    onlineStatus         0
//    onlineTime         1967979
//    position         "113.17507993700835,22.09313513333333"
//    productId         43
//    realtime Object
//    status         1
//    thumbnailBaseUrl         ""
//    thumbnailPath         ""
//    type         "JBZ"
//    typeName         "搅拌桩"
//    updateAt         1669378609
//    updateBy         0
//    urlData         ""
//    urlHistory         ""
//    urlOnline         ""
//    urlServer         ""
//    urlToken         ""
