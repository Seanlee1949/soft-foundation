package com.example.boot.controller;

import com.example.boot.constant.CommonConstant;
import com.example.boot.data.DataScrap;
import com.example.boot.entity.vo.HistorysVo;
import com.example.boot.service.impl.DeviceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lishuai
 * @since 2023/3/11
 */
@RestController
@RequestMapping(value = "/api/data")
public class MockDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataController.class);
    @Autowired
    private DataScrap dataScrap;
    @Autowired
    private DeviceServiceImpl deviceService;

    @GetMapping()
    public String updateDataBase(String data) {
        return CommonConstant.SUCCESS;
    }


    @GetMapping("/addDeviceData")
    public String addDeviceData() {
//        String filePath = "./history_1.txt";
        String historyPath = "./history_2.txt";
        String devicePath = "./device.txt";
//        dataScrap.addHistoryDatabaseByFile(filePath);
        dataScrap.addDeviceDatabaseByFile(devicePath);
        return CommonConstant.SUCCESS;
    }

    @GetMapping("/addHistoryData")
    public String addHistoryData() {
//        String filePath = "./history_1.txt";
        String historyPath = "./history_1.txt";
//        String devicePath = "./device.txt";
        dataScrap.addHistoryDatabaseByFile(historyPath);
//        dataScrap.addDeviceDatabaseByFile(devicePath);
        return CommonConstant.SUCCESS;
    }


    @PostMapping("/updateHistoryData")
    public String updateHistoryData(int startTime, int endTime, boolean updateRepeat) {

        List<HistorysVo> historysVos = dataScrap.collectHistoryDataToObject(startTime, endTime);
        LOGGER.info("查询到的历史数据数量：{}", historysVos.size());
//        1671638400  1671724800
        int index = 0;
        for (HistorysVo historysVo : historysVos) {
            index++;
            dataScrap.dealHistoryVoAndInsertOrUpdate(index, historysVo, updateRepeat);
        }
        refreshCache();
        return CommonConstant.SUCCESS;
    }

    @GetMapping("/refreshCache")
    public void refreshCache() {

        deviceService.refreshCache();
    }
//    @GetMapping("auto-update")
//    public void autoUpdatedata(){
//        //
//        long currentTime = CommonUtils.getCurrentTime();
//        long originTime =
//
//    }
}
