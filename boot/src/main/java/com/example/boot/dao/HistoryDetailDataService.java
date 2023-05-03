package com.example.boot.dao;

import com.example.boot.entity.dto.HistoryDetailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryDetailDataService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public HistoryDetailDataService() {

    }

    public void batchInsert(List<HistoryDetailData> historyDetailDataList) {

        jdbcTemplate.batchUpdate("insert into SF_HISTORY_DETAIL_DATA values(?,?,?,?,?,?,?,?,?,?,?,?)",
                historyDetailDataList.stream().map(historyDetailData -> {
                    return new Object[]{
                            historyDetailData.getId(),
                            historyDetailData.getPartTime(),
                            historyDetailData.getPartDownSpeed(),
                            historyDetailData.getPartDeep(),
                            historyDetailData.getPartId(),
                            historyDetailData.getPartPressure(),
                            historyDetailData.getPartDensity(),
                            historyDetailData.getPartPulp(),
                            historyDetailData.getPartCurrent(),
                            historyDetailData.getPartAsh(),
                            historyDetailData.getDeviceKey(),
                            historyDetailData.getPileDescribe()
                    };
                }).collect(Collectors.toList()));
    }
}
