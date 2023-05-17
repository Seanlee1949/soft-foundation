package com.example.boot.controller;

import com.example.boot.constant.CommonConstant;
import com.example.boot.dao.RecordMapper;
import com.example.boot.data.DataScrap;
import com.example.boot.data.HttpRequest;
import com.example.boot.entity.dto.Record;
import com.example.boot.entity.vo.DeviceResponse;
import com.example.boot.entity.vo.HistorysVo;
import com.example.boot.service.impl.DeviceServiceImpl;
import com.example.boot.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * /**
 * * 沙龙涌站
 * * <p>
 * * <p>
 * * 下钻速度修改为74至80之间浮动
 * * 提钻速度控制在76至81之间浮动
 * * <p>
 * * 倾斜度修改为在0.2至0.8之间浮动
 * * 水灰比修改为0.56至0.59之间浮动
 * * 密度值修改为1.72至1.75之间浮动
 * *
 *
 * @author lishuai
 * @since 2023/3/11
 */
@RestController
@RequestMapping(value = "/api/data")
public class shalongyong2Controller {
    // 沙涌路token
    public static String token = "FDP6DucQZXk9ONHoCeX2p0E3Qft/1l7r8kUBjHvwEm+JrbxkeUCSL8tmmRk/A8q9a7qJK1139wcbtoshz7oCKlYP6liu06+8NWSK2X3G3uQ=";
    public static String replaceUrl = "https://platform.hzcjkj.com";
    public static int partId = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(shalongyong2Controller.class);
    @Autowired
    private DataScrap dataScrap;
    @Autowired
    private DeviceServiceImpl deviceService;
    @Autowired
    private RecordMapper recordMapper;

    /**
     * 账号
     * 沙龙涌北侧地块周边市政道路配套工程
     * 密码
     * 88888888
     * <p>
     * 沙龙涌项目数据获取
     */
    @GetMapping("updateHistoryData0513")
    public String updateHistoryData() {
        LocalDate startDate = LocalDate.of(2022, 10, 11);
        long startTime = startDate.atTime(0, 0, 0).toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
//        LocalDate endDate = LocalDate.of(2022, 11, 13);
        LocalDate endDate = LocalDate.of(2023, 5, 11);
        long endTime = endDate.atTime(0, 0, 0).toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

        // 沙龙涌 token
        String token = "FDP6DucQZXk9ONHoCeX2p0E3Qft/1l7r8kUBjHvwEm+JrbxkeUCSL8tmmRk/A8q9a7qJK1139wcbtoshz7oCKlYP6liu06+8NWSK2X3G3uQ=";

        List<HistorysVo> historysVos =
                dataScrap.collectHistoryDataToObject(startTime / 1000, endTime / 1000, token);

        LOGGER.info("查询到的历史数据数量：{}", historysVos.size());

        int pageCount = 0;
        for (HistorysVo historysVo : historysVos) {
            // 循环每一页,每页10条数据
            pageCount++;
            dataScrap.dealHistoryVoAndInsertOrUpdate(pageCount, historysVo, false);
        }
        // 刷新缓存
        deviceService.refreshCache();
        return CommonConstant.SUCCESS;
    }

    @GetMapping("/refreshCache0513")
    public void refreshCache() {
        deviceService.refreshCache();
    }

    @GetMapping("update-device0513")
    public void updateDevices() {
        String url = replaceUrl + "/api/devices?" + "size=" + 30 + "&current=" + 0 + "&deviceType=" + "JBZ";
        String s = HttpRequest.sendGet(url, "", token);
        DeviceResponse deviceResponse = JsonUtils.parseObject(s, DeviceResponse.class);

        recordMapper.delete(null);

        Record[] records = deviceResponse.getResult().getRecords();
        List<Record> collect = Arrays.stream(records).sorted(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getLastAt() > o2.getLastAt() ? 1 : -1;
            }
        }).collect(Collectors.toList());

        for (Record record : collect) {
            recordMapper.insert(record);
        }
        recordMapper.selectList(null);
        LOGGER.info("刷新设备数据成功");
    }


    /**
     * 账号
     * 沙龙涌北侧地块周边市政道路配套工程
     * 密码
     * 88888888
     * <p>
     * 沙龙涌项目数据获取
     */
    @GetMapping("updateHistoryData05132")
    public String updateHistoryData2() {
        LocalDate startDate = LocalDate.of(2022, 10, 11);
        long startTime = startDate.atTime(0, 0, 0).toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
//        LocalDate endDate = LocalDate.of(2022, 10, 13);
        LocalDate endDate = LocalDate.of(2023, 5, 11);
        long endTime = endDate.atTime(0, 0, 0).toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

        // 沙龙涌 token
        String token = "FDP6DucQZXk9ONHoCeX2p0E3Qft/1l7r8kUBjHvwEm+JrbxkeUCSL8tmmRk/A8q9a7qJK1139wcbtoshz7oCKlYP6liu06+8NWSK2X3G3uQ=";

        List<HistorysVo> historysVos =
                dataScrap.collectHistoryDataToObject(startTime / 1000, endTime / 1000, token);

        LOGGER.info("查询到的历史数据数量：{}", historysVos.size());

        int pageCount = 0;
        for (HistorysVo historysVo : historysVos) {
            // 循环每一页,每页10条数据
            pageCount++;
            dataScrap.dealHistoryVoAndInsertOrUpdate(pageCount, historysVo, false);
        }
        // 刷新缓存
        deviceService.refreshCache();
        return CommonConstant.SUCCESS;
    }

}
