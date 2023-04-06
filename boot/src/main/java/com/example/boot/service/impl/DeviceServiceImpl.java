package com.example.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.boot.constant.CommonConstant;
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
import com.example.boot.util.cache.CacheManagerImpl;
import com.example.boot.util.cache.EntityCache;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author lishuai
 * @since 2022/11/25
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);
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
        List<String> validKeyList = getvalidKeys();
//        QueryWrapper<HistoryData> queryWrapper = new QueryWrapper<>();
//        // deviceType
//        if (deviceType != null) {
//            queryWrapper.eq("DEVICE_TYPE", deviceType);
//        }
//        // deviceKey
//        if (!StringUtils.isEmpty(deviceKey)) {
//            queryWrapper.eq("DEVICE_KEY", deviceKey);
//        }
//        // pileDescribe
//        if (!StringUtils.isEmpty(pileDescribe)) {
//            queryWrapper.eq("PILE_DESCRIBE", pileDescribe);
//        }
//        // Long startTime, Long endTime,
//        if (startTime != null) {
//            queryWrapper.ge("END_TIME", startTime);
//        }
//        if (endTime != null) {
//            queryWrapper.lt("END_TIME", endTime);
//        }
//        // Long minDepth, Long maxDepth,
//        if (minDepth != null) {
//            queryWrapper.gt("FR_DEPTH", minDepth);
//        }
//        if (maxDepth != null) {
//            queryWrapper.lt("FR_DEPTH", minDepth);
//        }
//        // Long minAsh, Long maxAsh,
//        if (minAsh != null) {
//            queryWrapper.ge("CUMULATIVE_ASH", minAsh);
//        }
//        if (maxAsh != null) {
//            queryWrapper.lt("CUMULATIVE_ASH", maxAsh);
//        }
//        //  Long minAvgAsh, Long maxAvgAsh,
//        if (minAvgAsh != null) {
//            queryWrapper.ge("AVERAGE_ASH", minAvgAsh);
//        }
//        if (maxAvgAsh != null) {
//            queryWrapper.lt("AVERAGE_ASH", maxAvgAsh);
//        }
//        // Long minPileTime, Long maxPileTime
//        if (minPileTime != null) {
//            queryWrapper.ge("PILE_TIME", minPileTime);
//        }
//        if (maxPileTime != null) {
//            queryWrapper.lt("PILE_TIME", maxPileTime);
//        }
//        queryWrapper.orderBy(true,false,"END_TIME");
//        List<HistoryData>  collect = historyDataMapper.selectList(queryWrapper);
//        Integer totalElements = historyDataMapper.selectCount(queryWrapper);

//        List<HistoryData> historyDataList = historyDataMapper.selectByDeviceType(deviceType);
        List<HistoryData> historyDataList = getAllHistoryData().stream().filter(new Predicate<HistoryData>() {
            @Override
            public boolean test(HistoryData historyData) {
                return validKeyList.contains(historyData.getDeviceKey());
            }
        }).collect(Collectors.toList());
//        historyDataList.sort((o1, o2) -> {
//            return (int) (o2.getEndTime() - o1.getEndTime());
//        });
        List<HistoryData> collect = historyDataList.stream()
                .filter(historyData -> {
                    return historyData.getDeviceType().equals(deviceType);
                })
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
                    if (minAsh != null) {
                        return historyData.getCumulativeAsh() > minAsh;
                    } else {
                        return true;
                    }
                })
                .filter(historyData -> {
                    if (maxAsh != null) {
                        return historyData.getCumulativeAsh() < maxAsh;
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
        Map<String, List<HistoryDetailData>> detailAndDeviceMap = getDetailAndDeviceMap();
        for (HistoryData historyData : content) {
            String deviceKeyTemp = historyData.getDeviceKey();
            String pileDescribeTemp = historyData.getPileDescribe();
            String identifier = deviceKeyTemp + "-" + pileDescribeTemp;
            List<HistoryDetailData> historyDetailDataList = detailAndDeviceMap.get(identifier);
//            List<HistoryDetailData> historyDetailDataList = historyDetailDataListAll.stream().filter(historyDetailData -> {
//                return historyDetailData.getDeviceKey().equals(deviceKeyTemp)
//                        && historyDetailData.getPileDescribe().equals(pileDescribeTemp);
//            }).collect(Collectors.toList());
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
        List<String> validKeyList = getvalidKeys();


        if (current == 0) {
            current = 1;
        }

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.in("KEY_", validKeyList);
        List<Record> recordList = recordMapper.selectList(queryWrapper);
        if (!StringUtils.isEmpty(deviceType)) {
            recordList = recordList.stream().filter(record -> record.getType().equals(deviceType)).collect(Collectors.toList());
        }
        int total = recordList.size();
        int pages = total % size == 0 ? total / size : total / size + 1;


        DeviceResult deviceResult = new DeviceResult();
        int start = (current - 1) * size + 1 - 1;
        int end = Math.min(current * size, total);
        List<Record> showRecords = recordList.subList(start, end);
        deviceResult.setRecords(showRecords.toArray(new Record[0]));
        deviceResult.setTotal(total);
        deviceResult.setSize(size);
        deviceResult.setSearchCount(true);
        deviceResult.setOrders(new int[0]);
        deviceResult.setPages(pages);
        deviceResult.setCurrent(1);

        deviceResult.setSuccess(true);

        DeviceResponse deviceResponse = new DeviceResponse();
        deviceResponse.setResult(deviceResult);
        deviceResponse.setSuccess(true);
        return deviceResponse;

    }

    private List<String> getvalidKeys() {
        List<String> validKeyList = new ArrayList<>();

        String userName = ThreadContext.get(CommonConstant.USER_NAME);
        if (userName.equals("污水泵站")) {
            validKeyList.add("MX01808023020020");
        } else {
            validKeyList = recordMapper.selectAllKeys();
        }
        return validKeyList;
    }


    @Override
    public HistoryAnalysis getHistoryAnalysis(String deviceKey, String deviceType, long beginDate, long endDate) {
        List<HistoryData> allHistoryData = getAllHistoryData();
        List<HistoryData> historyDataList = allHistoryData.stream().filter(historyData -> {
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
                    return historyData.getEndTime() > beginDate;
                })
                .filter(historyData -> {
                    return historyData.getEndTime() < endDate;
                }).collect(Collectors.toList());
        HistoryAnalysis historyAnalysis = new HistoryAnalysis();
        long startTime = -1;
        long endTime = -1;
        double totalDepth = 0.0;
        double totalCumulativeAsh = 0.0;
        double totalCumulativePulp = 0.0;
        double totalPileNum = 0.0;

        for (int i = 0; i < historyDataList.size(); i++) {
            HistoryData historyData = historyDataList.get(i);
            if (startTime == -1 || startTime < historyData.getBeginTime()) {
                startTime = historyData.getBeginTime();
            }
            if (endTime == -1 || endTime > historyData.getEndTime()) {
                endTime = historyData.getEndTime();
            }
            totalDepth = totalDepth + historyData.getDepth();
            totalCumulativeAsh = totalCumulativeAsh + historyData.getCumulativeAsh();
            totalCumulativePulp = totalCumulativePulp + historyData.getCumulativePulp();


        }
        historyAnalysis.setTotalDepth(totalDepth);
        historyAnalysis.setTotalCumulativeAsh(totalCumulativeAsh);
        historyAnalysis.setTotalCumulativePulp(totalCumulativePulp);
        historyAnalysis.setTotalPileNum(historyDataList.size());
        historyAnalysis.setBeginTime(startTime);
        historyAnalysis.setEndTime(endTime);
        historyAnalysis.setAvgAsh(totalCumulativeAsh / totalDepth);
        historyAnalysis.setAvgPulp(totalCumulativePulp / totalDepth);

        return historyAnalysis;
    }

    @Override
    public List<GroupSumVo> groupSum() {
        GroupSumVo groupSumVo = new GroupSumVo();
        groupSumVo.setDeviceType("JBZ");
        List<HistoryData> allHistoryData = getAllHistoryData();

        double totalDepth = 0;
        for (int i = 0; i < allHistoryData.size(); i++) {
            totalDepth = totalDepth + allHistoryData.get(i).getDepth();
        }
        groupSumVo.setTotal(allHistoryData.size());
        groupSumVo.setTotal_depth(totalDepth);
        return Collections.singletonList(groupSumVo);

    }

    @Override
    public List<NewestVo> getNewest() {
        List<HistoryData> allHistoryData = getAllHistoryData();
        int dataNum;
        if (allHistoryData.size() >= 7) {
            dataNum = 7;
        } else {
            dataNum = allHistoryData.size();
        }
        List<NewestVo> newestVoList = new ArrayList<>();
        for (int i = 0; i < dataNum; i++) {
            HistoryData historyData = allHistoryData.get(i);
            NewestVo newestVo = new NewestVo();
            newestVo.setId(historyData.getId());
            newestVo.setDeviceTypeName(historyData.getDeviceTypeName());
            newestVo.setDepth(historyData.getDepth());
            newestVo.setPileDescribe(historyData.getPileDescribe());
            newestVo.setTs(historyData.getTs());
            newestVoList.add(newestVo);
        }

        return newestVoList;
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

    public void refreshCache() {
        LOGGER.debug("开始刷新缓存");

        new CacheManagerImpl().clearAll();
        getAllHistoryData();
        getAllHistoryDetailData();
        getDetailAndDeviceMap();
        LOGGER.debug("刷新缓存完毕");
    }

    public List<HistoryDetailData> getAllHistoryDetailData() {

        String key = "historyDetailDataListAll";
        EntityCache entityCache = CacheUtils.cacheManagerImpl.getCacheByKey(key);
        if (entityCache == null) {
            LOGGER.debug("开始刷新缓存historyDetailDataListAll");
            List<HistoryDetailData> historyDetailDataListAll = historyDetailDataMapper.selectList(null);
            CacheUtils.cacheManagerImpl.putCache(key, historyDetailDataListAll, 0);
            LOGGER.debug("刷新缓存完毕historyDetailDataListAll");
            return historyDetailDataListAll;
        } else {
            LOGGER.debug("获取缓存数据historyDetailDataListAll");
            return (List<HistoryDetailData>) entityCache.getDatas();
        }
    }


    public Map<String, List<HistoryDetailData>> getDetailAndDeviceMap() {
        String key = "deviceIdentifierAndDetailMap";
        EntityCache entityCache = CacheUtils.cacheManagerImpl.getCacheByKey(key);
        if (entityCache == null) {
            LOGGER.debug("开始刷新缓存deviceIdentifierAndDetailMap");
            Map<String, List<HistoryDetailData>> deviceIdentifierAndDetailMap = new LinkedHashMap<>();
            List<HistoryDetailData> allHistoryDetailData = getAllHistoryDetailData();
            for (HistoryDetailData historyDetailData : allHistoryDetailData) {
                String identifier = historyDetailData.getDeviceKey() + "-" + historyDetailData.getPileDescribe();

                List<HistoryDetailData> temp = deviceIdentifierAndDetailMap.get(identifier);
                if (temp == null) {
                    List<HistoryDetailData> historyDetailDataList = new ArrayList<>();
                    historyDetailDataList.add(historyDetailData);
                    deviceIdentifierAndDetailMap.put(identifier, historyDetailDataList);

                } else {

                    temp.add(historyDetailData);
                }
            }
            CacheUtils.cacheManagerImpl.putCache(key, deviceIdentifierAndDetailMap, 0);
            LOGGER.debug("刷新缓存完毕deviceIdentifierAndDetailMap");
            return deviceIdentifierAndDetailMap;
        } else {
            LOGGER.debug("获取缓存数据deviceIdentifierAndDetailMap");
            return (Map<String, List<HistoryDetailData>>) entityCache.getDatas();
        }
    }

    public List<HistoryData> getAllHistoryData() {
        String key = "historyDataAll";
        EntityCache entityCache = CacheUtils.cacheManagerImpl.getCacheByKey(key);
        if (entityCache == null) {
            LOGGER.debug("开始刷新缓存historyDataAll");
            List<HistoryData> historyDataList = historyDataMapper.selectList(null);
            historyDataList.sort((o1, o2) -> {
                return (int) (o2.getEndTime() - o1.getEndTime());
            });
            CacheUtils.cacheManagerImpl.putCache(key, historyDataList, 0);
            LOGGER.debug("刷新缓存完毕historyDataAll");
            return historyDataList;
        } else {
            LOGGER.debug("获取缓存数据historyDataAll");
            return (List<HistoryData>) entityCache.getDatas();
        }
    }
}
