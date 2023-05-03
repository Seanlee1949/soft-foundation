package com.example.boot.data;

import com.example.boot.dao.HistoryDataMapper;
import com.example.boot.dao.HistoryDetailDataMapper;
import com.example.boot.dao.HistoryDetailDataService;
import com.example.boot.dao.RecordMapper;
import com.example.boot.entity.dto.HistoryData;
import com.example.boot.entity.dto.HistoryDetailData;
import com.example.boot.entity.dto.Record;
import com.example.boot.entity.vo.DeviceResponse;
import com.example.boot.entity.vo.DeviceResult;
import com.example.boot.entity.vo.HistorysVo;
import com.example.boot.util.FileUtils;
import com.example.boot.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author lishuai
 * @since 2022/11/26
 */
//@SpringBootTest(classes = BootApplication.class)
//@MapperScan("com.example.boot.dao")
@Component
public class DataScrap {
    private final HistoryDataMapper historyDataMapper;
    private final HistoryDetailDataMapper historyDetailDataMapper;
    private final RecordMapper recordMapper;
    @Autowired
    HistoryDetailDataService historyDetailDataService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataScrap.class);


    public DataScrap(HistoryDataMapper historyDataMapper, HistoryDetailDataMapper historyDetailDataMapper, RecordMapper recordMapper) {
        this.historyDetailDataMapper = historyDetailDataMapper;
        this.historyDataMapper = historyDataMapper;
        this.recordMapper = recordMapper;
    }


    public void addDeviceDatabaseByFile(String filePath) {
        List<String> stringList = FileUtils.readFileByLine(filePath);
        for (String str : stringList) {
            DeviceResponse deviceResponse = JsonUtils.parseObject(str, DeviceResponse.class);
            DeviceResult result = deviceResponse.getResult();
            for (Record record : result.getRecords()) {
                recordMapper.insert(record);
            }

        }
    }

    public static int historyDataCount = 0;
    public static int historyDetailDataCount = 0;

    public void addHistoryDatabaseByFile(String filePath) {
        List<String> stringList = FileUtils.readFileByLine(filePath);
        int lineCount = 0;
        historyDataCount = 0;
        historyDetailDataCount = 0;
        for (String str : stringList) {
            lineCount++;
            HistorysVo historysVo = JsonUtils.parseObject(str, HistorysVo.class);

            dealHistoryVoAndInsert(lineCount, historysVo);
        }
        System.out.println("完成");
    }

    public void dealHistoryVoAndInsert(int lineCount, HistorysVo historysVo) {
        List<HistoryData> historyDataList = historysVo.getContent();
        LOGGER.debug("插入第{}行数据,historyData->{}", lineCount, historyDataList.size());
        for (HistoryData historyData : historyDataList) {
            historyDataCount++;


            historyDataMapper.insert(historyData);
            String deviceKey = historyData.getDeviceKey();
            String pileDescribe = historyData.getPileDescribe();
            List<HistoryDetailData> historyDetailDataList = historyData.getData();
            LOGGER.debug("插入第{}行数据,累计第{}条 历史数据，历史数据详情{}条",
                    lineCount, historyDataCount, historyDetailDataList.size());

            // 排序
            historyDetailDataList.sort(Comparator.comparingInt(HistoryDetailData::getPartId));

            Double doubleRandom = 0.0;
            // 处理随机数：庆平路
//            dealWithRandomNumber(historyDetailDataList, doubleRandom);
            // 处理随机数：沙涌路
            dealWithRandomNumber3(historyDetailDataList, doubleRandom);
            dealWithRandomData3(historyData);


            for (HistoryDetailData historyDetailData : historyDetailDataList) {
                historyDetailDataCount++;
                historyDetailData.setDeviceKey(deviceKey);
                historyDetailData.setPileDescribe(pileDescribe);
                historyDetailDataMapper.insert(historyDetailData);

                System.out.printf("lineCount->%s,HistoryDataCount->%s,historyDetailDataCount->%s%n",
                        lineCount,
                        historyDataCount,
                        historyDetailDataCount);
            }
        }
    }

    public void dealHistoryVoAndInsertOrUpdate(int lineCount, HistorysVo historysVo, boolean updateRepeat) {

        List<HistoryData> historyDataList = historysVo.getContent();
        LOGGER.debug("插入第{}行数据,historyData->{}", lineCount, historyDataList.size());
        for (HistoryData historyData : historyDataList) {
            historyDataCount++;
            {
                HistoryData historyDataTemp = historyDataMapper.selectById(historyData.getId());
                if (historyDataTemp != null) {

                    if (updateRepeat) {
                        historyDataMapper.deleteById(historyData.getId());
                        historyDetailDataMapper.deleteByPileDescribe(historyData.getPileDescribe());
                    } else {
                        // 重复的不处理
                        continue;
                    }
                }
            }

            historyDataMapper.insert(historyData);
            String deviceKey = historyData.getDeviceKey();
            String pileDescribe = historyData.getPileDescribe();
            List<HistoryDetailData> historyDetailDataList = historyData.getData();
            LOGGER.debug("插入第{}行数据,累计第{}条 历史数据，历史数据详情{}条",
                    lineCount, historyDataCount, historyDetailDataList.size());

            // 排序
            historyDetailDataList.sort(Comparator.comparingInt(HistoryDetailData::getPartId));

            Double doubleRandom = 0.0;
            // 处理随机数 : 庆平路
//            dealWithRandomNumber(historyDetailDataList, doubleRandom);
            // 处理随机数：沙涌路
//            dealWithRandomNumber3(historyDetailDataList, doubleRandom);


            for (HistoryDetailData historyDetailData : historyDetailDataList) {
                historyDetailDataCount++;
                historyDetailData.setDeviceKey(deviceKey);
                historyDetailData.setPileDescribe(pileDescribe);
                // todo
//                historyDetailDataMapper.insert(historyDetailData);
            }
            historyDetailDataService.batchInsert(historyDetailDataList);
            System.out.printf("lineCount->%s,HistoryDataCount->%s,historyDetailDataCount->%s%n",
                    lineCount,
                    historyDataCount,
                    historyDetailDataCount);
        }
    }

    /**
     * 沙龙涌站
     * <p>
     * <p>
     * 下钻速度修改为74至80之间浮动
     * 提钻速度控制在76至81之间浮动
     * <p>
     * 倾斜度修改为在0.2至0.8之间浮动
     * 水灰比修改为0.56至0.59之间浮动
     * 密度值修改为1.72至1.75之间浮动
     */
    private void dealWithRandomData3(HistoryData historyData) {
        historyData.setWaterCementRatio(getDoubleRandom(0.56, 0.59));
        historyData.setXAngle(getDoubleRandom(0.2, 0.8));
        historyData.setYAngle(getDoubleRandom(0.2, 0.8));

    }

    /**
     * 沙龙涌站
     * <p>
     * <p>
     * 下钻速度修改为74至80之间浮动
     * 提钻速度控制在76至81之间浮动
     * <p>
     * 倾斜度修改为在0.2至0.8之间浮动
     * 水灰比修改为0.56至0.59之间浮动
     * 密度值修改为1.72至1.75之间浮动
     *
     * @param historyDetailDataList
     * @param doubleRandom
     */
    private void dealWithRandomNumber3(List<HistoryDetailData> historyDetailDataList, Double doubleRandom) {
        for (int i = 0; i < historyDetailDataList.size(); i++) {
            HistoryDetailData historyDetailDataTemp = historyDetailDataList.get(i);

            if (i == 0) {
                {
                    // 处理随机数
                    if (historyDetailDataTemp.getPartDownSpeed() > 0) {
                        doubleRandom = getDoubleRandom(74, 80);
                        historyDetailDataTemp.setPartDownSpeed(getDoubleRandom(74, 80));
                    } else if (historyDetailDataTemp.getPartDownSpeed() < 0) {
                        historyDetailDataTemp.setPartDownSpeed(-getDoubleRandom(76, 81));
                        doubleRandom = getDoubleRandom(76, 81);
                    }
                }
                continue;
            }
            if (historyDetailDataList.get(i - 1).getPartDownSpeed() < 0
                    && historyDetailDataList.get(i).getPartDownSpeed() > 0) {
                doubleRandom = getDoubleRandom(74, 80);
            }
            if (historyDetailDataList.get(i - 1).getPartDownSpeed() > 0
                    && historyDetailDataList.get(i).getPartDownSpeed() < 0) {
                doubleRandom = getDoubleRandom(76, 81);
            }
            if (historyDetailDataTemp.getPartDownSpeed() > 0) {
                historyDetailDataTemp.setPartDownSpeed(getDoubleRandom(doubleRandom - 1, doubleRandom + 1));
            } else if (historyDetailDataTemp.getPartDownSpeed() < 0) {
                historyDetailDataTemp.setPartDownSpeed(-getDoubleRandom(doubleRandom - 1, doubleRandom + 1));
            }

            // 密度
            historyDetailDataTemp.setPartDensity(getDoubleRandom(1.72, 1.75));
        }
    }

    private void dealWithRandomNumber(List<HistoryDetailData> historyDetailDataList, Double doubleRandom) {
        for (int i = 0; i < historyDetailDataList.size(); i++) {
            HistoryDetailData historyDataTemp = historyDetailDataList.get(i);

            if (i == 0) {
                {
                    // 处理随机数
                    if (historyDataTemp.getPartDownSpeed() > 0) {
                        doubleRandom = getDoubleRandom(58, 63);
                        historyDataTemp.setPartDownSpeed(getDoubleRandom(58, 63));
                    } else if (historyDataTemp.getPartDownSpeed() < 0) {
                        historyDataTemp.setPartDownSpeed(-getDoubleRandom(78, 83));
                        doubleRandom = getDoubleRandom(78, 83);
                    }
                }
                continue;
            }
            if (historyDetailDataList.get(i - 1).getPartDownSpeed() < 0
                    && historyDetailDataList.get(i).getPartDownSpeed() > 0) {
                doubleRandom = getDoubleRandom(58, 63);
            }
            if (historyDetailDataList.get(i - 1).getPartDownSpeed() > 0
                    && historyDetailDataList.get(i).getPartDownSpeed() < 0) {
                doubleRandom = getDoubleRandom(78, 83);
            }
            if (historyDataTemp.getPartDownSpeed() > 0) {
                historyDataTemp.setPartDownSpeed(getDoubleRandom(doubleRandom - 2, doubleRandom + 2));
            } else if (historyDataTemp.getPartDownSpeed() < 0) {
                historyDataTemp.setPartDownSpeed(-getDoubleRandom(doubleRandom - 2, doubleRandom + 2));
            }
        }
    }

    /**
     * 获取0.68-6.88之间的随机数
     *
     * @return
     */
    public static Double getDoubleRandom(double min, double max) {
        Random rand = new Random();
        double result = 0;
        result = min + (rand.nextDouble() * (max - min));
        return result;
    }

    /**
     * 获取0.68-6.88之间的随机数
     *
     * @return
     */
    public static long getLongRandom(long min, long max) {
        Random rand = new Random();
        int nextInt = rand.nextInt((int) (max - min));
        long result = min + nextInt;
        return result;
    }

    public static void collectHistoryDataToFile() {
        String token = "/KfKIFu+USTe4bk6MbApucs3+FWDIbDUKVCM43WXkvkXDsOKsBO5NjjphxiepCWc";
//        for (int page = 0; page < 260; page++) {
        for (int page = 0; page < 2; page++) {
            System.out.println("加载第" + page + "页");
            String url = "https://platform.hzcjkj.com/api/historys?deviceType=JBZ&page=" + page + "&deviceKey=&size=10&pileDescribe=";
            String historyResponse = HttpRequest.sendGet(url, "", token);
            HistorysVo historysVo = JsonUtils.parseObject(historyResponse, HistorysVo.class);

            FileUtils.appendStringToFile("./history_2.txt", JsonUtils.writeAsJson(historysVo));
            FileUtils.appendStringToFile("./history_2.txt", "\n");
        }
        System.out.println();
        //发送 POST 请求
//        String sr=HttpRequest.sendPost("http://localhost:6144/Home/RequestPostString", "key=123&v=456");
//        System.out.println(sr);
    }


    public List<HistorysVo> collectHistoryDataToObject(long startTime, long endTime, String token) {
        int totalPages = getTotalPages(startTime, endTime, token);
        LOGGER.info("共查询到数据{}页", totalPages);

        List<HistorysVo> historyVoList = new ArrayList<>();
        for (int page = 0; page <= totalPages + 1; page++) {
            LOGGER.debug("加载第{}页", page);

            String url = "https://platform.hzcjkj.com/api/historys?deviceType=JBZ&page=" + page +
                    "&deviceKey=&size=10&pileDescribe=&startTime="
                    + startTime + "&endTime=" + endTime;
            String historyResponse = HttpRequest.sendGet(url, "", token);
            HistorysVo historysVo = JsonUtils.parseObject(historyResponse, HistorysVo.class);

            if (historysVo.getContent().size() == 0) {
                continue;
            }
            historyVoList.add(historysVo);
        }
        LOGGER.info("收集到的数据数量，historysVoList.size->{}", historyVoList.size());
        return historyVoList;
    }

    private int getTotalPages(long startTime, long endTime, String token) {
        int totalPages;
        String url = "https://platform.hzcjkj.com/api/historys?deviceType=JBZ&page=0&deviceKey=&size=10&pileDescribe=&startTime="
                + startTime + "&endTime=" + endTime;
        String historyResponse = HttpRequest.sendGet(url, "", token);
        HistorysVo historysVo = JsonUtils.parseObject(historyResponse, HistorysVo.class);
        totalPages = historysVo.getTotalPages();
        return totalPages;
    }

    public void collectDeviceDataToFile() {
        String token = "/KfKIFu+USTe4bk6MbApucs3+FWDIbDUKVCM43WXkvkXDsOKsBO5NjjphxiepCWc";
        String url = "https://platform.hzcjkj.com/api/devices?size=9999&current=1&deviceType=JBZ";
        String historyResponse = HttpRequest.sendGet(url, "", token);
        DeviceResponse deviceResponse = JsonUtils.parseObject(historyResponse, DeviceResponse.class);

        FileUtils.appendStringToFile("./device.txt", JsonUtils.writeAsJson(deviceResponse));
        FileUtils.appendStringToFile("./device.txt", "\n");
    }


    public static void main(String[] args) {
        String deviceFilePath = "";
//        new DataScrap(null, null).addDeviceDatabaseByFile(deviceFilePath);
        new DataScrap(null, null, null).collectDeviceDataToFile();
    }
}
