package com.example.boot.data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.boot.BootApplication;
import com.example.boot.dao.HistoryDataMapper;
import com.example.boot.dao.HistoryDetailDataMapper;
import com.example.boot.dao.RecordMapper;
import com.example.boot.entity.dto.HistoryData;
import com.example.boot.entity.dto.HistoryDetailData;
import com.example.boot.entity.dto.Record;
import com.example.boot.entity.vo.DeviceResponse;
import com.example.boot.entity.vo.DeviceResult;
import com.example.boot.entity.vo.HistorysVo;
import com.example.boot.util.CacheUtils;
import com.example.boot.util.FileUtils;
import com.example.boot.util.JsonUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

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
            // 处理随机数
            dealWithRandomNumber(historyDetailDataList, doubleRandom);


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
            // 处理随机数
            dealWithRandomNumber(historyDetailDataList, doubleRandom);


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
//        double MAX = 6.88;
//        double MIN = 0.68;
        double result = 0;
//        for (int i = 0; i < 10; i++) {
        result = min + (rand.nextDouble() * (max - min));
//            result = (double) Math.round(result * 100) / 100;
//            System.out.println(result);
//        }
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


    public List<HistorysVo> collectHistoryDataToObject(long startTime, long endTime) {
//        1671638400  1671724800
        String token = "/KfKIFu+USTe4bk6MbApucs3+FWDIbDUKVCM43WXkvkXDsOKsBO5NjjphxiepCWc";
//        for (int page = 0; page < 260; page++) {

        int totalPages = getTotalPages(startTime, endTime, token);
        List<HistorysVo> historysVoList = new ArrayList<>();

        for (int page = 0; page <= totalPages + 1; page++) {
            System.out.println("加载第" + page + "页");
            String url = "https://platform.hzcjkj.com/api/historys?deviceType=JBZ&page=" + page + "&deviceKey=&size=10&pileDescribe=&startTime="
                    + startTime + "&endTime=" + endTime;
            String historyResponse = HttpRequest.sendGet(url, "", token);
            HistorysVo historysVo = JsonUtils.parseObject(historyResponse, HistorysVo.class);

            if (historysVo.getContent().size() == 0) {
                continue;
            }
            historysVoList.add(historysVo);
//            FileUtils.appendStringToFile("./history_2.txt", JsonUtils.writeAsJson(historysVo));
//            FileUtils.appendStringToFile("./history_2.txt", "\n");
        }
        return historysVoList;
        //发送 POST 请求
//        String sr=HttpRequest.sendPost("http://localhost:6144/Home/RequestPostString", "key=123&v=456");
//        System.out.println(sr);
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
