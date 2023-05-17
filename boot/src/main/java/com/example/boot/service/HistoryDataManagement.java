package com.example.boot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.boot.dao.HistoryDataMapper;
import com.example.boot.dao.HistoryDetailDataMapper;
import com.example.boot.entity.dto.HistoryData;
import com.example.boot.entity.dto.HistoryDetailData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryDataManagement {
    private HistoryDataMapper historyDataMapper;
    private HistoryDetailDataMapper historyDetailDataMapper;

    public HistoryDataManagement(HistoryDataMapper historyDataMapper,
                                 HistoryDetailDataMapper historyDetailDataMapper) {
        this.historyDataMapper = historyDataMapper;
        this.historyDetailDataMapper = historyDetailDataMapper;
    }

    public void insertHistory(HistoryData historyData) {
        historyDataMapper.insert(historyData);
        List<HistoryDetailData> data = historyData.getData();
        historyDetailDataMapper.batchInsert(data);
    }

    public HistoryData queryHistoryDataById(String id) {
        HistoryData historyData = historyDataMapper.selectById(id);
        QueryWrapper<HistoryDetailData> queryWrapper = new QueryWrapper();
        queryWrapper.eq("PILE_KEY", id);
        List<HistoryDetailData> historyDetailDataList = historyDetailDataMapper.selectList(queryWrapper);
        historyData.setData(historyDetailDataList);
        return historyData;
    }

    public void deleteHistoryDataById(String id) {
        historyDataMapper.deleteById(id);
        QueryWrapper<HistoryDetailData> queryWrapper = new QueryWrapper();
        queryWrapper.eq("PILE_KEY", id);
        historyDetailDataMapper.delete(queryWrapper);
    }
}
