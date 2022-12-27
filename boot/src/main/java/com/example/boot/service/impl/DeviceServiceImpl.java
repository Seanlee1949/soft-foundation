package com.example.boot.service.impl;

import com.example.boot.dao.DeviceGroupMapper;
import com.example.boot.dao.HistoryDataMapper;
import com.example.boot.dao.HistoryDetailDataMapper;
import com.example.boot.dao.RecordMapper;
import com.example.boot.entity.AnalysisResult;
import com.example.boot.entity.dto.DeviceGroup;
import com.example.boot.entity.dto.HistoryData;
import com.example.boot.entity.dto.HistoryDetailData;
import com.example.boot.entity.dto.Record;
import com.example.boot.entity.vo.*;
import com.example.boot.service.DeviceService;
import com.example.boot.util.CacheUtils;
import com.example.boot.util.cache.EntityCache;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lishuai
 * @since 2022/11/25
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceGroupMapper deviceGroupMapper;
    private final HistoryDataMapper historyDataMapper;
    private final HistoryDetailDataMapper historyDetailDataMapper;
    private final RecordMapper recordMapper;

    public DeviceServiceImpl(DeviceGroupMapper deviceGroupMapper,
                             HistoryDataMapper historyDataMapper,
                             HistoryDetailDataMapper historyDetailDataMapper,
                             RecordMapper recordMapper) {
        this.deviceGroupMapper = deviceGroupMapper;
        this.historyDataMapper = historyDataMapper;
        this.historyDetailDataMapper = historyDetailDataMapper;
        this.recordMapper = recordMapper;
    }

    @Override
    public AnalysisVo analysis(Integer limit, String deviceKey, Long beginDate, Long endDate, String deviceType) {
        List<HistoryData> historyDataList = historyDataMapper.selectList(null);
        List<HistoryData> collect = historyDataList.stream()
                .filter(historyData -> {
                    if (!StringUtils.isEmpty(deviceKey)) {
                        return historyData.getDeviceKey().equals(deviceKey);
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (!StringUtils.isEmpty(deviceType)) {
                        return historyData.getDeviceType().equals(deviceType);
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (beginDate != null) {
                        return historyData.getEndTime() > beginDate;
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (endDate != null) {
                        return historyData.getEndTime() < endDate;
                    } else {
                        return true;
                    }
                }).collect(Collectors.toList());
        Map<String, List<HistoryData>> stringHistoryDataMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int time1 = Integer.parseInt(o1.replaceAll("-", ""));
                int time2 = Integer.parseInt(o2.replaceAll("-", ""));
                return time2 - time1;
            }
        });
        collect.forEach(historyData -> {
//            String date = new Date(historyData.getEndTime()).toString();

//            Date date = new Date(historyDataList.get(0).getEndTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date(historyData.getEndTime() * 1000));

            if (stringHistoryDataMap.containsKey(date)) {
                stringHistoryDataMap.get(date).add(historyData);
            } else {
                List<HistoryData> historyDataListTemp = new ArrayList<>();
                historyDataListTemp.add(historyData);
                stringHistoryDataMap.put(date, historyDataListTemp);
            }
        });

        int count = 0;
        List<AnalysisResult> analysisResultList = new ArrayList<>();
        for (Map.Entry<String, List<HistoryData>> entry : stringHistoryDataMap.entrySet()) {
            AnalysisResult analysisResult = new AnalysisResult();
            double totalDepth = 0;
            for (HistoryData historyData : entry.getValue()) {
                totalDepth = totalDepth + historyData.getFrDepth();
            }
            analysisResult.setDate(entry.getKey());
            analysisResult.setDepth(totalDepth);
            analysisResult.setPileAmount(entry.getValue().size());
            analysisResultList.add(analysisResult);
            count++;
            if (limit != null && count >= limit) {
                break;
            }
        }
        AnalysisVo analysisVo = new AnalysisVo();
        analysisVo.setSuccess(true);
        analysisVo.setResult(analysisResultList);
        return analysisVo;
    }


    @Override
    public List<DeviceGroup> getDeviceGroups() {
        return deviceGroupMapper.selectList(null);
    }

    /**
     * @param deviceType
     * @param page
     * @param deviceKey
     * @param size
     * @param pileDescribe
     * @param startTime
     * @param endTime
     * @param minDepth     施工桩长
     * @param maxDepth
     * @param minAsh       累计灰量
     * @param maxAsh
     * @param minAvgAsh    平均灰量
     * @param maxAvgAsh
     * @param minPileTime  成桩时长
     * @param maxPileTime
     * @return
     */
    @Override
    public HistorysVo getHistoryData(String deviceType, int page, String deviceKey, int size, String pileDescribe,
                                     Long startTime, Long endTime,
                                     Long minDepth, Long maxDepth,
                                     Long minAsh, Long maxAsh,
                                     Long minAvgAsh, Long maxAvgAsh,
                                     Long minPileTime, Long maxPileTime) {

        List<HistoryData> historyDataList = historyDataMapper.selectByDeviceType(deviceType);
        historyDataList.sort((o1, o2) -> {
            return (int) (o2.getEndTime() - o1.getEndTime());
        });
        List<HistoryData> collect = historyDataList.stream()
                // deviceKey
                .filter(historyData -> {
                    if (!StringUtils.isEmpty(deviceKey)) {
                        return historyData.getDeviceKey().equals(deviceKey);
                    } else {
                        return true;
                    }
                })
                // pileDescribe
                .filter(historyData -> {
                    if (!StringUtils.isEmpty(pileDescribe)) {
                        return historyData.getPileDescribe().equals(pileDescribe);
                    } else {
                        return true;
                    }
                })
                // Long startTime, Long endTime,
                .filter(historyData -> {
                    if (startTime != null) {
                        return historyData.getEndTime() > startTime;
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (endTime != null) {
                        return historyData.getEndTime() < endTime;
                    } else {
                        return true;
                    }
                })
                // Long minDepth, Long maxDepth,
                .filter(historyData -> {
                    if (minDepth != null) {
                        return historyData.getFrDepth() > minDepth;
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (maxDepth != null) {
                        return historyData.getFrDepth() < maxDepth;
                    } else {
                        return true;
                    }
                })
                // Long minAsh, Long maxAsh,
                .filter(historyData -> {
                    if (minDepth != null) {
                        return historyData.getCumulativeAsh() > minDepth;
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (maxDepth != null) {
                        return historyData.getCumulativeAsh() < maxDepth;
                    } else {
                        return true;
                    }
                })
                //  Long minAvgAsh, Long maxAvgAsh,
                .filter(historyData -> {
                    if (minAvgAsh != null) {
                        return historyData.getAverageAsh() > minAvgAsh;
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (maxAvgAsh != null) {
                        return historyData.getAverageAsh() < maxAvgAsh;
                    } else {
                        return true;
                    }
                })
                // Long minPileTime, Long maxPileTime
                .filter(historyData -> {
                    if (minPileTime != null) {
                        return historyData.getPileTime() > minPileTime;
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (maxPileTime != null) {
                        return historyData.getPileTime() < maxPileTime;
                    } else {
                        return true;
                    }
                })
                .collect(Collectors.toList());


        int totalElements = collect.size();
        int totalPages = getTotalPages(size, totalElements);

        int startIndex = size * page;
        int endIndex = size * (page + 1);
        List<HistoryData> content = new ArrayList<>();
        if (totalElements <= 0) {
            content = new ArrayList<>();
        } else if (endIndex > totalElements) {
            content = collect.subList(startIndex, totalElements);
        } else {
            content = collect.subList(startIndex, endIndex);
        }

        List<HistoryDetailData> historyDetailDataListAll = getAllHistoryDetailData();
        for (HistoryData historyData : content) {
            String deviceKeyTemp = historyData.getDeviceKey();
            String pileDescribeTemp = historyData.getPileDescribe();
            List<HistoryDetailData> historyDetailDataList = historyDetailDataListAll.stream().filter(historyDetailData -> {
                return historyDetailData.getDeviceKey().equals(deviceKeyTemp) && historyDetailData.getPileDescribe().equals(pileDescribeTemp);
            }).collect(Collectors.toList());
//            List<HistoryDetailData> historyDetailDataList = historyDetailDataMapper.selectByDeviceKey(deviceKeyTemp, pileDescribeTemp);
            historyData.setData(historyDetailDataList);
        }
        Sort sort = new Sort();
        sort.setEmpty(true);
        sort.setSorted(false);
        sort.setUnsorted(true);

        Pageable pageable = new Pageable();
        pageable.setPaged(true);
        pageable.setOffset(0);
        pageable.setPageNumber(page);
        pageable.setPageSize(size);
        pageable.setUnpaged(false);
        pageable.setSort(sort);

        HistorysVo historysVo = new HistorysVo();
        historysVo.setContent(content);
        historysVo.setEmpty(content.isEmpty());
        historysVo.setFirst(page == 0);
        historysVo.setLast(page == totalPages - 1);
        historysVo.setNumber(page);
        historysVo.setNumberOfElements(content.size());
        historysVo.setPageable(pageable);
        historysVo.setSize(size);
        historysVo.setSort(sort);
        historysVo.setTotalElements(totalElements);
        historysVo.setTotalPages(totalPages);

        return historysVo;
    }

    @Override
    public DeviceResponse getDevices(int size, int current, String deviceType) {
        List<Record> recordList = recordMapper.selectList(null);
        if (!StringUtils.isEmpty(deviceType)) {
            recordList = recordList.stream().filter(record -> record.getType().equals(deviceType)).collect(Collectors.toList());
        }
        int total = recordList.size();
        DeviceResult deviceResult = new DeviceResult();
        deviceResult.setRecords(recordList.toArray(new Record[0]));
        deviceResult.setTotal(total);
        deviceResult.setSize(size);
        deviceResult.setSearchCount(true);
        deviceResult.setOrders(new int[0]);
        deviceResult.setPages(1);
        deviceResult.setCurrent(1);

        deviceResult.setSuccess(true);

        DeviceResponse deviceResponse = new DeviceResponse();
        deviceResponse.setResult(deviceResult);
        deviceResponse.setSuccess(true);
        return deviceResponse;

    }

    @Override
    public HistoryAnalysis getHistoryAnalysis(String deviceKey, String deviceType, long beginDate, long endDate) {


        return null;
    }

    private int getTotalPages(int size, Integer totalElements) {
        int totalPages;
        if (totalElements % size == 0) {
            totalPages = totalElements / size;
        } else {
            totalPages = totalElements / size + 1;
        }
        return totalPages;
    }

    public List<HistoryDetailData> getAllHistoryDetailData() {
        String key = "historyDetailDataListAll";
        EntityCache entityCache = CacheUtils.cacheManagerImpl.getCacheByKey(key);
        if (entityCache == null) {
            List<HistoryDetailData> historyDetailDataListAll = historyDetailDataMapper.selectList(null);
            CacheUtils.cacheManagerImpl.putCache(key, historyDetailDataListAll, 0);
            return historyDetailDataListAll;
        } else {
            return (List<HistoryDetailData>) entityCache.getDatas();
        }
    }
}
