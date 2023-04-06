package com.example.boot.service;

import com.example.boot.entity.dto.DeviceGroup;
import com.example.boot.entity.vo.*;

import java.util.List;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public interface DeviceService {
    AnalysisVo analysis(Integer limit, String deviceKey, Long beginDate, Long endDate, String deviceType);


    List<DeviceGroup> getDeviceGroups();

    HistorysVo getHistoryData(String deviceType, int page, String deviceKey, int size, String pileDescribe,
                              Long startTime, Long endTime,
                              Long minDepth, Long maxDepth,
                              Long minAsh, Long maxAsh,
                              Long minAvgAsh, Long maxAvgAsh,
                              Long minPileTime, Long maxPileTime);


    DeviceResponse getDevices(int size, int current, String deviceType);


    HistoryAnalysis getHistoryAnalysis(String deviceKey, String deviceType, long beginDate, long endDate);

    List<GroupSumVo> groupSum();

    List<NewestVo> getNewest();
}
