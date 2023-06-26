package com.example.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class ReportedController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportedController.class);

    /**
     * 数据实时上报
     */
    @PostMapping("/site/receive/cutOffWall/realTimeData")
    public void reportReTimeData(@RequestBody Object object) {
        LOGGER.debug("进入接口realTimeData");
    }

    /**
     * 上报设备在线离线状态
     */
    @PostMapping("/site/receive/cutOffWall/updateStatus")
    public void updateStatus(Object equipmentCode, Object status) {
        LOGGER.debug("进入接口updateStatus");
    }

    /**
     * 成桩数据上报
     */
    @PostMapping("/site/receive/cutOffWall/pileData")
    public void reportPileData(@RequestBody Object object) {
        LOGGER.debug("进入接口pileData");
    }
}
