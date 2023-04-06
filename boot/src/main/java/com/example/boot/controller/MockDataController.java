package com.example.boot.controller;

import com.example.boot.constant.CommonConstant;
import com.example.boot.dao.RecordMapper;
import com.example.boot.data.DataScrap;
import com.example.boot.data.HttpRequest;
import com.example.boot.entity.dto.HistoryData;
import com.example.boot.entity.dto.HistoryDetailData;
import com.example.boot.entity.dto.Record;
import com.example.boot.entity.vo.DeviceResponse;
import com.example.boot.entity.vo.HistorysVo;
import com.example.boot.service.impl.DeviceServiceImpl;
import com.example.boot.util.CommonUtils;
import com.example.boot.util.FileUtils;
import com.example.boot.util.JsonUtils;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.boot.data.DataScrap.getDoubleRandom;

/**
 * @author lishuai
 * @since 2023/3/11
 */
@RestController
@RequestMapping(value = "/api/data")
public class MockDataController {
    public static String token = "/KfKIFu+USTe4bk6MbApucs3+FWDIbDUKVCM43WXkvkXDsOKsBO5NjjphxiepCWc";
    public static String replaceUrl = "https://platform.hzcjkj.com";
    public static int partId = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataController.class);
    @Autowired
    private DataScrap dataScrap;
    @Autowired
    private DeviceServiceImpl deviceService;

    @Autowired
    private RecordMapper recordMapper;

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

    @GetMapping("update-device")
    public void updateDevices() {
        String url = replaceUrl + "/api/devices?" + "size=" + 20 + "&current=" + 0 + "&deviceType=" + "JBZ";
        String s = HttpRequest.sendGet(url, "", token);
        DeviceResponse deviceResponse = JsonUtils.parseObject(s, DeviceResponse.class);

        recordMapper.delete(null);

        Record[] records = deviceResponse.getResult().getRecords();
        for (Record record : records) {
            recordMapper.insert(record);
        }
        recordMapper.selectList(null);
        LOGGER.info("刷新设备数据成功");
    }

    public static void main(String[] args) throws IOException {
        new MockDataController().updateHistoryDataByFile2();
    }
    @SneakyThrows
    @GetMapping("/update-history-data2")
    public void updateHistoryDataByFile2() throws IOException {
        String deviceKey = "MX01808023020020";
//        String path1 = "C:\\Users\\25455\\Desktop\\1111.csv";
        String filePath = "C:\\Users\\25455\\Desktop\\4、水泥土搅拌桩(细微版)20221109.xlsx";
//        String path2 = "C:\\Users\\25455\\Desktop\\2222.csv";
//        List<String> lines1 = FileUtils.readFileByLine(path1);
        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workbook.getSheet("施工记录1-39不变的数据");
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {

        }
    }

    @GetMapping("/update-history-data")
    public void updateHistoryDataByFile() {
        String deviceKey = "MX01808023020020";
        String path1 = "C:\\Users\\25455\\Desktop\\1111.csv";
        String path2 = "C:\\Users\\25455\\Desktop\\2222.csv";
        List<String> lines1 = FileUtils.readFileByLine(path1);
        List<String> lines2 = FileUtils.readFileByLine(path2);
//        List<String> lines = FileUtils.readFileByLine("C:\\Users\\25455\\Desktop\\test.txt");

        {
            String pieDiscribe = "444";
            long beginTime = 1662250500L;//2022-09.04 08:15   单位S
            long endTime = 1662254760L;//2022-09.04 09:26:00        单位S
            dealFirstLine(deviceKey, lines1, pieDiscribe, beginTime, endTime);

        }
//        {
//            String pieDiscribe = "427";
//            long beginTime = 1662255000L;//2022-09.04 09:30:00      单位S
//            long endTime = 1662259200L;//2022-09.04 10:40:00        单位S
//            dealFirstLine(deviceKey, lines2, pieDiscribe, beginTime, endTime);
//        }
        deviceService.refreshCache();
    }

    private void dealFirstLine(String deviceKey, List<String> lines, String pieDiscribe, long beginTime, long endTime) {
        // 循环所有行
        for (int i = 0; i < 18; i++) {

            System.out.println(i);

            String line = lines.get(i);
            if (line.contains("时间,,喷量")) {
                // 处理一个桩 4行
                String firstDown = lines.get(i + 1);
                String firstUp = lines.get(i + 2);
                String secondDown = lines.get(i + 3);
                String secondUp = lines.get(i + 4);

                String[] splits1 = firstDown.split(",");
                List<String> validSplits = new ArrayList<>();
                String pileNum = splits1[2];
                List<HistoryDetailData> historyDetailDataList = new ArrayList<>();
                // 处理第一次下降
                dealWithFirst(deviceKey, i, splits1, pileNum, 1, historyDetailDataList);
                String[] splits2 = firstUp.split(",");
                dealWithFirst(deviceKey, i, splits2, pileNum, -1, historyDetailDataList);
                String[] splits3 = secondDown.split(",");
                dealWithFirst(deviceKey, i, splits3, pileNum, 1, historyDetailDataList);
                String[] splits4 = secondUp.split(",");
                dealWithFirst(deviceKey, i, splits4, pileNum, -1, historyDetailDataList);

                double pileTime = Double.parseDouble(splits1[48]) +
                        Double.parseDouble(splits2[48]) +
                        Double.parseDouble(splits3[48]) +
                        Double.parseDouble(splits4[48]);
                HistoryData historyData = new HistoryData();
                historyData.set_id(CommonUtils.getUUID());
                historyData.setData(historyDetailDataList);
                historyData.setPackageAmount(Integer.parseInt(pieDiscribe));
                historyData.setPileId(Integer.parseInt(pieDiscribe));
                historyData.setVersion("2.03");
                historyData.setDeviceKey(deviceKey);
                historyData.setMachineKey("0");
                historyData.setPileDescribe(pieDiscribe);
                historyData.setBeginTime(beginTime);
                historyData.setEndTime(endTime);
                historyData.setProcessType("00000000");
                historyData.setLongitude(113.17780543700835);
                historyData.setLatitude(22.094602099999996);
                historyData.setHoleLatitude(22.094602099999996);
                historyData.setHoleLongitude(113.1775438703417);
                historyData.setDepth(12.7);
                historyData.setFrDepth(historyData.getDepth());
                historyData.setReDepth(historyData.getDepth());
                historyData.setEmDepth(0.5);
                historyData.setWaterCementRatio(0.6000);
                historyData.setDownSpeed(getDoubleRandom(50, 60));
                historyData.setUpSpeed(getDoubleRandom(70, 80));
                historyData.setCumulativePulp(Double.parseDouble(splits1[51]));
//                historyData.setAveragePulp(historyData.getCumulativePulp() / (historyData.getEndTime() - historyData.getBeginTime()));
                historyData.setAveragePulp(historyData.getCumulativePulp() / 12.7);
//                historyData.setCumulativeAsh(historyData.getCumulativePulp() * 0.6 * 3 / 2);
                historyData.setCumulativeAsh(historyData.getCumulativePulp() * 1.6 / 1.73);
//                historyData.setAverageAsh(historyData.getAveragePulp() * 0.6 * 3 / 2);
                historyData.setAverageAsh(historyData.getAveragePulp() * 1.6 / 1.73);
                historyData.setMaxCurrent(getDoubleRandom(68, 72));
                historyData.setAverageCurrent(getDoubleRandom(50, 52));
                historyData.setAveragePressure(0);
                historyData.setMaxDownSpeed(historyData.getDownSpeed() + 2);
                historyData.setMaxUpSpeed(historyData.getUpSpeed() + 2);
                historyData.setXAngle(0.3);
                historyData.setYAngle(0.3);
                historyData.setTTypeLength(0);
                historyData.setTTypePulp(0);
                historyData.setBottomPartPulp(780.0999755859375);
                historyData.setTTypeAsh(0);
                historyData.setBottomPartAsh(871.1116943359375);
                historyData.setPartCount(partId);
                historyData.setDeviceType("JBZ");
                historyData.setDeviceTypeName("搅拌桩");
                historyData.setTs(historyData.getEndTime());
                historyData.setStandardAsh(0);
                historyData.setPileTopCurrent(58.2);
                historyData.setMaxAngle(0);
                historyData.setPileTime(pileTime * 60);
                historyData.setMileNo("0");
                historyData.setPartNo("0");
                historyData.setMilePartNo("0+0");
                historyData.setPileNo(pieDiscribe);

                System.out.println();


                HistorysVo historysVo = new HistorysVo();
                historysVo.setContent(Collections.singletonList(historyData));

                // 对调
                {
                    double tempPulp = historyData.getCumulativePulp();
                    double tempAsh = historyData.getCumulativeAsh();
                    historyData.setCumulativeAsh(tempPulp);
                    historyData.setCumulativePulp(tempAsh);
                }
                {
                    double tempPulp = historyData.getAveragePulp();
                    double tempAsh = historyData.getAverageAsh();
                    historyData.setAverageAsh(tempPulp);
                    historyData.setAveragePulp(tempAsh);
                }
                for (HistoryDetailData datum : historyData.getData()) {
                    double tempPulp = datum.getPartPulp();
                    double tempAsh = datum.getPartAsh();
                    datum.setPartAsh(tempPulp);
                    datum.setPartPulp(tempAsh);
                }

                dataScrap.dealHistoryVoAndInsertOrUpdate(1, historysVo, true);
            }
        }
    }

    {
//            for (String line : lines) {
//            if (line.contains())
//            ( for (String s : line.split("\\t")) {
//
//            }
//
//            replace.replaceAll("  ", " ");
//            System.out.println();
//
//        // 污水泵项目
//
//        //顺序号
//        String order = "1";
//        // 排桩号
//        String pileDiscribe = "510";
//        // 开始时间
//        long beginTime = 123123;
//        // 结束时间
//        long endTime = 123123;
//        // 施工总桩长  DEPTH
//        double depth = 24.05;

        // 电流(A)  MAX_CURRENT  PILE_TOP_CURRENT

        // 第一次下钻速度
        // 第一次上升速度
        // 第二次下钻速度
        // 第二次上升速度
        // 压力   AVERAGE_PRESSURE
        // 流量
        // 单桩喷浆量  CUMULATIVE_PULP
        // 垂直度    Y_ANGEL
        // 水泥浆比重
        // 水泥用量  CUMULATIVE_ASH

    }

    private void dealWithFirst(String deviceKey, int i, String[] splits, String pileNum, int direection, List<HistoryDetailData> historyDetailDataList) {

        double partDeep;
        if (direection == 1) {
            partDeep = 0;
        } else {
            partDeep = 12.75;
        }
        int part2Count = 0;//七段计数
        for (int j = 4; j < 31; j = j + 4) {
            part2Count++;// 七段计数

            // 处理每一小段，共7段
            double sumDeep = 2;
            System.out.println(j);
            double sumTime = Double.parseDouble(splits[j]);
            double sumPulp = Double.parseDouble(splits[j + 2]);
            int[] randomTime = {1, -1, 0, -2, 1, 2, 0, -1};
            if (part2Count % 7 == 0) {
                double aveDeep = sumDeep / 3;// 单位m
                double aveTime = sumTime * 60 / 3;  // 单位S
                double avePulp = sumPulp / 3;// 单位kg
                // 每段生成8个数据
                for (int k = 0; k < 3; k++) {
                    partId++;
                    partDeep = partDeep + 0.25 * direection;
                    HistoryDetailData historyDetailData = new HistoryDetailData();
                    historyDetailData.setId(CommonUtils.getUUID());
                    historyDetailData.setPartTime((int) aveTime + randomTime[k]);
                    historyDetailData.setPartDownSpeed(25 * 60 / historyDetailData.getPartTime() * direection);
                    historyDetailData.setPartDeep(partDeep == 12.75 ? 12.7 : partDeep);
                    historyDetailData.setPartId(partId);
                    historyDetailData.setPartPressure(0);
                    historyDetailData.setPartDensity(getDoubleRandom(1.720, 1.730));
                    historyDetailData.setPartPulp(getDoubleRandom(avePulp - 0.05, avePulp + 0.05));
                    historyDetailData.setPartCurrent(getDoubleRandom(49, 54));
//                    historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 0.6 * 3 / 2);
                    historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 1.6 / 1.73);
                    historyDetailData.setDeviceKey(deviceKey);
                    historyDetailData.setPileDescribe(pileNum);
                    historyDetailDataList.add(historyDetailData);

                }
            } else {


                double aveDeep = sumDeep / 8;// 单位m
                double aveTime = sumTime * 60 / 8;  // 单位S
                double avePulp = sumPulp / 8;// 单位kg
                // 每段生成8个数据
                for (int k = 0; k < 8; k++) {
                    partDeep = partDeep + 0.25 * direection;
                    partId++;
                    HistoryDetailData historyDetailData = new HistoryDetailData();
                    historyDetailData.setId(CommonUtils.getUUID());
                    historyDetailData.setPartTime((int) aveTime + randomTime[k]);
                    historyDetailData.setPartDownSpeed(25 * 60 / historyDetailData.getPartTime() * direection);
                    historyDetailData.setPartDeep(partDeep == 12.75 ? 12.7 : partDeep);
                    historyDetailData.setPartId(partId);
                    historyDetailData.setPartPressure(0);
                    historyDetailData.setPartDensity(getDoubleRandom(1.720, 1.730));
                    historyDetailData.setPartPulp(getDoubleRandom(avePulp - 0.05, avePulp + 0.05));
                    historyDetailData.setPartCurrent(getDoubleRandom(49, 54));
//                    historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 0.6 * 3 / 2);
                    historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 1.6 / 1.73);
                    historyDetailData.setDeviceKey(deviceKey);
                    historyDetailData.setPileDescribe(pileNum);
                    historyDetailDataList.add(historyDetailData);
                }
            }
            System.out.println();
        }
    }


//    @GetMapping("auto-update")
//    public void autoUpdatedata(){
//        //
//        long currentTime = CommonUtils.getCurrentTime();
//        long originTime =
//
//    }

}
