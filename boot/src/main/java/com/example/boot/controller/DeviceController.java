package com.example.boot.controller;

import com.example.boot.data.HttpRequest;
import com.example.boot.entity.ConfigParams;
import com.example.boot.entity.dto.DeviceGroup;
import com.example.boot.entity.dto.DeviceGroupVo;
import com.example.boot.entity.response.CommonResponse;
import com.example.boot.entity.vo.*;
import com.example.boot.service.DeviceService;
import com.example.boot.util.JsonUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lishuai
 * @since 2022/11/25
 */
@RestController
@RequestMapping("/api")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public static String token = "/KfKIFu+USTe4bk6MbApucs3+FWDIbDUKVCM43WXkvkXDsOKsBO5NjjphxiepCWc";
    public static String replaceUrl = "https://platform.hzcjkj.com";

    /**
     * 在线一览： 查询设备分组
     *
     * @return
     */
    @GetMapping("/deviceGroups")
    public List<DeviceGroupVo> getDeviceGroups() {
        List<DeviceGroup> deviceGroups = deviceService.getDeviceGroups();
        List<DeviceGroupVo> collect = deviceGroups.stream().map(new Function<DeviceGroup, DeviceGroupVo>() {
            @Override
            public DeviceGroupVo apply(DeviceGroup deviceGroup) {
                DeviceGroupVo deviceGroupVo = new DeviceGroupVo();
                deviceGroupVo.setLabel(deviceGroup.getLabel());
                deviceGroupVo.setName(deviceGroup.getName());

                return deviceGroupVo;
            }
        }).collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/devices")
    public Object getDevices(int size, int current, String deviceType, @CookieValue("userName") String userName) {
//        String url = replaceUrl + "/api/devices?" + "size=" + size + "&current=" + current + "&deviceType=" + deviceType;
//        String s = HttpRequest.sendGet(url, "", token);

        return deviceService.getDevices(size, current, deviceType);
//        return JsonUtils.parseObject(s, Object.class);
    }

//    /**
//     * 获取历史数据列表
//     * <p>
//     * 总数量 = page * size
//     *
//     * @param startTime    开始时间 单位 秒
//     * @param endTime
//     * @param deviceType   JBJ
//     * @param page         第几页
//     * @param deviceKey    设备DI
//     * @param size         每页多少个
//     * @param pileDescribe 桩描述（手动输入的桩号）
//     * @return
//     */
//    @GetMapping("/historys")
//    public HistorysVo getHistory(String deviceType, int page, String deviceKey, int size, String pileDescribe,
//                                 Long startTime, Long endTime,
//                                 Long minDepth, Long maxDepth,
//                                 Long minAsh, Long maxAsh,
//                                 Long minAvgAsh, Long maxAvgAsh,
//                                 Long minPileTime, Long maxPileTime) {
//        return deviceService.getHistoryData(deviceType, page, deviceKey, size, pileDescribe,
//                startTime, endTime,
//                minDepth, maxDepth,
//                minAsh, maxAsh,
//                minAvgAsh, maxAvgAsh,
//                minPileTime, maxPileTime);
//
//
//    }

    /**
     * 获取历史数据列表
     * <p>
     * 总数量 = page * size
     *
     * @param startTime    开始时间 单位 秒
     * @param endTime
     * @param deviceType   JBJ
     * @param page         第几页
     * @param deviceKey    设备DI
     * @param size         每页多少个
     * @param pileDescribe 桩描述（手动输入的桩号）
     * @return
     */
    @GetMapping("/historys")
    public Object getHistory(String deviceType, int page, String deviceKey, int size, String pileDescribe,
                             Long startTime, Long endTime,
                             Long minDepth, Long maxDepth,
                             Long minAsh, Long maxAsh,
                             Long minAvgAsh, Long maxAvgAsh,
                             Long minPileTime, Long maxPileTime) {
        return JsonUtils.writeAsJson(deviceService.getHistoryData(deviceType, page, deviceKey, size, pileDescribe,
                startTime, endTime,
                minDepth, maxDepth,
                minAsh, maxAsh,
                minAvgAsh, maxAvgAsh,
                minPileTime, maxPileTime));


    }


    @GetMapping("/groupSum")
    public List<GroupSumVo> groupSum() {
        String url = replaceUrl + "/api/groupSum";
        String s = HttpRequest.sendGet(url, "", token);
        return JsonUtils.parseObject(s, List.class);
//        return new GroupSumVo[0];
    }


    @GetMapping("/newest")
    public List<NewestVo> getNewest() {
        String url = replaceUrl + "/api/newest";
        String s = HttpRequest.sendGet(url, "", token);
        return JsonUtils.parseObject(s, List.class);
//        return new NewestVo[0];
    }


    @GetMapping("/historyWarns")
//    public HistoryWarnVo getHistoryWarns(String deviceType, int page, String deviceKey, int size, String pileDescribe) {
    public Object getHistoryWarns(String deviceType, int page, String deviceKey, int size, String pileDescribe) {
        // todo 暂时不做
        String url = replaceUrl + "/api/historyWarns?" + "deviceType=" + deviceType + "&page=" + page
                + "&deviceKey" + deviceKey + "&size=" + size + "&pileDescribe=" + pileDescribe;
        String s = HttpRequest.sendGet(url, "", token);
        return JsonUtils.parseObject(s, Object.class);
//        return new HistoryWarnVo();
    }

    @GetMapping("/lastData")
    public Object getLastData(String deviceKey, int pileId) {
        String url = replaceUrl + "/api/lastData?" + "deviceKey=" + deviceKey + "&pileId=" + pileId;
        String s = HttpRequest.sendGet(url, "", token);
        return JsonUtils.parseObject(s, Object.class);
//        return new Object();
    }

    @GetMapping("/pileIds")
    public Object getPileIds(String deviceKey) {
        String url = replaceUrl + "/api/pileIds?" + "deviceKey=" + deviceKey;
        String s = HttpRequest.sendGet(url, "", token);
        return JsonUtils.parseObject(s, Object.class);
//        return null;
    }

    @GetMapping("/datas")
    public Object getDatas(String deviceKey, int pileId, String[] fields) {
        String url = replaceUrl + "/api/lastData?" + "deviceKey=" + deviceKey + "&pileId=" + pileId +
                "&fields=" + fields;
        String s = HttpRequest.sendGet(url, "", token);
        return JsonUtils.parseObject(s, Object.class);
//        return null;
    }

    @GetMapping("/analysis")
    public AnalysisVo analysis(int limit, String deviceKey, Long beginDate, Long endDate, String deviceType) {
        return deviceService.analysis(limit, deviceKey, beginDate, endDate, deviceType);
    }

    @GetMapping("/historyAnalysis")
    public Object getHistoryAnalysis(String deviceKey, String deviceType, long beginDate, long endDate) {
        String url = replaceUrl + "/api/historyAnalysis?" + "deviceKey=" + deviceKey + "&deviceType=" + deviceType
                + "&beginData=" + beginDate + "&endData=" + endDate;
        String s = HttpRequest.sendGet(url, "", token);
        return JsonUtils.parseObject(s, Object.class);

//        return deviceService.getHistoryAnalysis(deviceKey, deviceType, beginDate, endDate);
    }

    /**
     * 在线一览--》设计参数
     * <p>
     * 低优先级
     * todo
     *
     * @return
     */
    @PostMapping("/editConfig")
    public CommonResponse editConfig(long deviceId, ConfigParams configParams) {
        // todo 如何下发配置？ 暂时不做
        return new CommonResponse(true);
    }
}
