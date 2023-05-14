package com.example.boot.controller;

import com.example.boot.data.DataScrap;
import com.example.boot.entity.dto.HistoryData;
import com.example.boot.entity.dto.HistoryDetailData;
import com.example.boot.entity.vo.HistorysVo;
import com.example.boot.service.impl.DeviceServiceImpl;
import com.example.boot.util.CommonUtils;
import com.example.boot.util.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

import static com.example.boot.data.DataScrap.*;

/**
 * 污水泵项目：
 * 先做了一条数据
 * 又做了全部 700条数据
 * <p>
 * 端口： 9095
 * 账号： 沙龙涌北侧地块周边市政道路配套工程
 * 密码：  88888888
 * <p>
 * 设备ID： MX01808023020020
 * <p>
 * <p>
 * 下钻速度修改为78至83之间浮动
 * 提钻速度控制在76至82之间浮动
 * 倾斜度修改为在0.2至0.8之间浮动
 * 水灰比修改为0.56至0.59之间浮动
 * 密度值修改为1.72至1.75之间浮动
 * <p>
 * 1、泥浆比重= 1+[1= (水灰比 x3+1) x2]
 * 2、泥浆比重= 1+水灰比/ (1水泥比重 +水灰比)
 * “吨/立方注:计算
 */
@RestController
@RequestMapping(value = "/api/data")
public class MockData3 {

    private static double minDownSpeed = 78;
    private static double maxDownSpeed = 83;
    private static double minUpSpeed = 76;
    private static double maxUpSpeed = 82;
    private static double minAngle = 0.2;
    private static double maxAngle = 0.8;
    private static double minWaterCementRatio = 0.56;
    private static double maxWaterCementRatio = 0.59;
    private static double minDensity = 1.72;
    private static double maxDensity = 1.75;
    private static int partId = 0;

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataController.class);
    @Autowired
    private DataScrap dataScrap;
    @Autowired
    private DeviceServiceImpl deviceService;


    @GetMapping("/update-history-data5")
    public void updateHistoryDataByFile5() throws IOException {
        String deviceKey = "MX01808023020020";
//        String filePath = "C:\\Users\\25455\\Desktop\\4、水泥土搅拌桩(细微版)20221109.xlsx";
        String filePath = "H:\\my-project\\污水泵\\4、水泥土搅拌桩(细微版)20230414-1.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(filePath);
        Map<String, List<XSSFRow>> pileNumAndDataMap = new LinkedHashMap<>();
        pileNumAndDataMap.putAll(getPileNumAndDataMap(xssfWorkbook.getSheetAt(7)));


        // 拆分为一个桩的数据
//        Map<String, List<XSSFRow>> pileNumAndDataMap = getPileNumAndDataMap(sheetAt);
        LOGGER.debug("处理完毕，共{}组桩数据", pileNumAndDataMap.size());


        List<HistoryData> historyDataList = new ArrayList<>();
        // 将桩数据转化为 平台支持的导入格式
        for (Map.Entry<String, List<XSSFRow>> entry : pileNumAndDataMap.entrySet()) {
            LOGGER.debug("正在处理{}", entry.getKey());
            String pileDesc = entry.getKey();
            List<XSSFRow> onePileData = entry.getValue();
            HistoryData historyData = dealPiceOfData(deviceKey, pileDesc.replace("#", ""), onePileData);
            historyDataList.add(historyData);
        }

        HistorysVo historysVo = new HistorysVo();
        historysVo.setContent(historyDataList);
        dataScrap.dealHistoryVoAndInsertOrUpdate(historyDataList.size(), historysVo, true);


        deviceService.refreshCache();
    }

    private Map<String, List<XSSFRow>> getPileNumAndDataMap(XSSFSheet sheetAt) {
        Map<String, List<XSSFRow>> pileNumAndDataMap = new LinkedHashMap<>();
        for (int i = 10; i < sheetAt.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheetAt.getRow(i);
            LOGGER.debug("正在处理cellNum->{}", i);
            if (row == null || row.getCell(3) == null) {
                continue;
            }
            String rawValue = row.getCell(3).toString();

            if ("搅拌下沉".equals(rawValue)) {
                LOGGER.debug("处理第{}行", i);
                List<XSSFRow> onePileInfo = new ArrayList<>();
                onePileInfo.add(sheetAt.getRow(i));
                onePileInfo.add(sheetAt.getRow(i + 1));
                onePileInfo.add(sheetAt.getRow(i + 2));
                onePileInfo.add(sheetAt.getRow(i + 3));
                pileNumAndDataMap.put(row.getCell(2).toString(), onePileInfo);
                i = i + 3;
            }
            System.out.println();
        }
        return pileNumAndDataMap;
    }


    //    @GetMapping("/update-history-data")
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

    private HistoryData dealPiceOfData(String deviceKey, String pileNum, List<XSSFRow> onePileData) {
        // 声明全局变量


        String pieDiscribe = pileNum;
        // 总共四行： 第一次下降，第一次上升，第二次下降，第二次上升

        // 获取详情数据
        List<HistoryDetailData> historyDetailDataList =
                getHistoryDetailData(deviceKey, onePileData, pileNum);

        String[] fistDownArray = changeCellsToStringArray(onePileData.get(0));
        String[] firstUp = changeCellsToStringArray(onePileData.get(1));
        String[] secondDown = changeCellsToStringArray(onePileData.get(2));
        String[] secondUp = changeCellsToStringArray(onePileData.get(3));

        boolean isTen = onePileData.get(0).getSheet().getSheetName().contains("10m");
        int pileTimeIndex = isTen ? 53 : 55;
        double pileTime = Double.parseDouble(fistDownArray[pileTimeIndex]) +
                Double.parseDouble(firstUp[pileTimeIndex]) +
                Double.parseDouble(secondDown[pileTimeIndex]) +
                Double.parseDouble(secondUp[pileTimeIndex]);
        HistoryData historyData = new HistoryData();
        historyData.set_id(CommonUtils.getUUID());
        historyData.setData(historyDetailDataList);
        historyData.setPackageAmount(Integer.parseInt(pieDiscribe.replace("#", "")));
        historyData.setPileId(Integer.parseInt(pieDiscribe.replace("#", "")));
        historyData.setVersion("2.03");
        historyData.setDeviceKey(deviceKey);
        historyData.setMachineKey("0");
        historyData.setPileDescribe(pieDiscribe);
        int startDataIndex = isTen ? 49 : 51;
        Date day = onePileData.get(0).getCell(1).getDateCellValue();
        Date startDate = onePileData.get(0).getCell(startDataIndex).getDateCellValue();
        startDate.setYear(day.getYear());
        startDate.setMonth(day.getMonth());
        startDate.setDate(day.getDate());
        historyData.setBeginTime(startDate.getTime() / 1000);
        int endDataIndex = isTen ? 52 : 54;
        Date endDate = onePileData.get(3).getCell(endDataIndex).getDateCellValue();
        endDate.setYear(day.getYear());
        endDate.setMonth(day.getMonth());
        endDate.setDate(day.getDate());
        historyData.setEndTime(endDate.getTime() / 1000);
        historyData.setProcessType("00000000");
        historyData.setLongitude(113.17780543700835);
        historyData.setLatitude(22.094602099999996);
        historyData.setHoleLatitude(22.094602099999996);
        historyData.setHoleLongitude(113.1775438703417);
        historyData.setDepth(10);
        historyData.setFrDepth(historyData.getDepth());
        historyData.setReDepth(historyData.getDepth());
        historyData.setEmDepth(0.5);
        historyData.setWaterCementRatio(0.6000);
        historyData.setDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed));
        historyData.setUpSpeed(getDoubleRandom(minUpSpeed, maxUpSpeed));
        int pulpIndex = isTen ? 56 : 58;
        historyData.setCumulativePulp(Double.parseDouble(fistDownArray[pulpIndex]));
//                historyData.setAveragePulp(historyData.getCumulativePulp() / (historyData.getEndTime() - historyData.getBeginTime()));
        historyData.setAveragePulp(historyData.getCumulativePulp() / 10);
//                historyData.setCumulativeAsh(historyData.getCumulativePulp() * 0.6 * 3 / 2);
        historyData.setCumulativeAsh(historyData.getCumulativePulp() * 1.6 / 1.73);
//                historyData.setAverageAsh(historyData.getAveragePulp() * 0.6 * 3 / 2);
        historyData.setAverageAsh(historyData.getAveragePulp() * 1.6 / 1.73);
        historyData.setMaxCurrent(getDoubleRandom(68, 72));
        historyData.setAverageCurrent(getDoubleRandom(50, 52));
        historyData.setAveragePressure(0);
        historyData.setMaxDownSpeed(historyData.getDownSpeed() + 2);
        historyData.setMaxUpSpeed(historyData.getUpSpeed() + 2);
        historyData.setXAngle(getDoubleRandom(minAngle, maxAngle));
        historyData.setYAngle(getDoubleRandom(minAngle, maxAngle));
        historyData.setTTypeLength(0);
        historyData.setTTypePulp(0);
        historyData.setBottomPartPulp(780.0999755859375);
        historyData.setTTypeAsh(0);
        historyData.setBottomPartAsh(871.1116943359375);
// todo
        historyData.setPartCount(1);
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
        System.out.println("处理数据完成" + pieDiscribe);
        return historyData;


//
//        HistorysVo historysVo = new HistorysVo();
//        historysVo.setContent(Collections.singletonList(historyData));
//        dataScrap.dealHistoryVoAndInsertOrUpdate(1, historysVo, true);
        // 对调
//        {
//            double tempPulp = historyData.getCumulativePulp();
//            double tempAsh = historyData.getCumulativeAsh();
//            historyData.setCumulativeAsh(tempPulp);
//            historyData.setCumulativePulp(tempAsh);
//        }
//        {
//            double tempPulp = historyData.getAveragePulp();
//            double tempAsh = historyData.getAverageAsh();
//            historyData.setAverageAsh(tempPulp);
//            historyData.setAveragePulp(tempAsh);
//        }
//        for (HistoryDetailData datum : historyData.getData()) {
//            double tempPulp = datum.getPartPulp();
//            double tempAsh = datum.getPartAsh();
//            datum.setPartAsh(tempPulp);
//            datum.setPartPulp(tempAsh);
//        }


    }

    private List<HistoryDetailData> getHistoryDetailData(String deviceKey, List<XSSFRow> onePileData, String pileNum) {
        String[] fistDownArray = changeCellsToStringArray(onePileData.get(0));
        String[] firstUp = changeCellsToStringArray(onePileData.get(1));
        String[] secondDown = changeCellsToStringArray(onePileData.get(2));
        String[] secondUp = changeCellsToStringArray(onePileData.get(3));

        List<HistoryDetailData> historyDetailDataList = new LinkedList<>();
        partId = 0;

        if (onePileData.get(0).getSheet().getSheetName().contains("10m")) {

            dealWithFirst10(deviceKey, fistDownArray, pileNum, 1, historyDetailDataList);
            dealWithFirst10(deviceKey, firstUp, pileNum, -1, historyDetailDataList);
            dealWithFirst10(deviceKey, secondDown, pileNum, 1, historyDetailDataList);
            dealWithFirst10(deviceKey, secondUp, pileNum, -1, historyDetailDataList);
        } else {

            dealWithFirst(deviceKey, fistDownArray, pileNum, 1, historyDetailDataList);
            dealWithFirst(deviceKey, firstUp, pileNum, -1, historyDetailDataList);
            dealWithFirst(deviceKey, secondDown, pileNum, 1, historyDetailDataList);
            dealWithFirst(deviceKey, secondUp, pileNum, -1, historyDetailDataList);
        }
        return historyDetailDataList;
    }

    private String[] changeCellsToStringArray(XSSFRow xssfRow) {
        List<String> aLinelist = new ArrayList<>();
        for (int i = 0; i < xssfRow.getPhysicalNumberOfCells() - 2; i++) {
            XSSFCell cell = xssfRow.getCell(i);
            if (cell == null) {
                LOGGER.debug("第{}个，cell为null", i);
                aLinelist.add("");
            } else {
                aLinelist.add(xssfRow.getCell(i).toString());
            }
        }
        return aLinelist.toArray(new String[0]);
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
                int partId = 0;
                dealWithFirst(deviceKey, splits1, pileNum, 1, historyDetailDataList);
                String[] splits2 = firstUp.split(",");
                dealWithFirst(deviceKey, splits2, pileNum, -1, historyDetailDataList);
                String[] splits3 = secondDown.split(",");
                dealWithFirst(deviceKey, splits3, pileNum, 1, historyDetailDataList);
                String[] splits4 = secondUp.split(",");
                dealWithFirst(deviceKey, splits4, pileNum, -1, historyDetailDataList);

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
                historyData.setDepth(10);
                historyData.setFrDepth(historyData.getDepth());
                historyData.setReDepth(historyData.getDepth());
                historyData.setEmDepth(0.5);
                historyData.setWaterCementRatio(0.6000);
                historyData.setDownSpeed(getDoubleRandom(50, 60));
                historyData.setUpSpeed(getDoubleRandom(70, 80));
                historyData.setCumulativePulp(Double.parseDouble(splits1[51]));
//                historyData.setAveragePulp(historyData.getCumulativePulp() / (historyData.getEndTime() - historyData.getBeginTime()));
                historyData.setAveragePulp(historyData.getCumulativePulp() / 10);
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

    private List<HistoryDetailData> dealWithFirst10(String deviceKey, String[] oneTimeDetailDataArray, String pileNum,
                                                    int direection, List<HistoryDetailData> historyDetailDataList) {


        double partDeep;
        if (direection == 1) {
            partDeep = 0;
        } else {
            partDeep = 10.0;
        }
        int part2Count = 0;//七段计数

        //  按照3个一组拆分单元格

        List<Part2MData> partOF2MDataList = new ArrayList<>();
        int dataGroup = 0;
        for (int j = 4; j < 38; ) {
            Part2MData part2MData = new Part2MData();
            part2MData.setPartTime(oneTimeDetailDataArray[j]);
            part2MData.setKg(oneTimeDetailDataArray[j + 2]);
            part2MData.setL(oneTimeDetailDataArray[j + 4]);
            partOF2MDataList.add(part2MData);
            dataGroup++;
            if (dataGroup == 5) {
                break;
            }
            j = j + 5;
        }


        // 循环7组数据
        for (int i = 0; i < 5; i++) {

            Part2MData part2MData = partOF2MDataList.get(i);
            double sumTime = Double.parseDouble(part2MData.getPartTime()) * 60;
            double sumPulp = Double.parseDouble(part2MData.getL());
            long[] timeArray = getLongDataBySum(8, (long) sumTime, 2);
            double[] pulpArray = getDoubleDataBySum(8, sumPulp, 1);

            // 每个数据可以拆成8分
            for (int k = 0; k < 8; k++) {
                partDeep = partDeep + 0.25 * direection;
                partId++;

                HistoryDetailData historyDetailData = new HistoryDetailData();
                historyDetailData.setId(CommonUtils.getUUID());
                historyDetailData.setPartTime((int) timeArray[k]);
                // 总长度 除以总时间
//                historyDetailData.setPartDownSpeed(25 * 60 / historyDetailData.getPartTime() * direection);
                if (direection > 0) {
                    historyDetailData.setPartDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed) * direection);

                } else {
                    historyDetailData.setPartDownSpeed(getDoubleRandom(minUpSpeed, maxUpSpeed) * direection);
                }

                historyDetailData.setPartDeep(partDeep);
                historyDetailData.setPartId(partId);
                historyDetailData.setPartPressure(0);
                historyDetailData.setPartDensity(getDoubleRandom(1.720, 1.730));
                // todo
                historyDetailData.setPartPulp(pulpArray[k]);
                historyDetailData.setPartCurrent(getDoubleRandom(49, 54));
//                historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 0.6 * 3 / 2);
                historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 1.6 / 1.73);
                historyDetailData.setDeviceKey(deviceKey);
                historyDetailData.setPileDescribe(pileNum);

                historyDetailData.setPileKey(historyDetailData.getDeviceKey() +
                        "-" + historyDetailData.getPileDescribe());
                historyDetailDataList.add(historyDetailData);
            }
        }
        System.out.println();
        return historyDetailDataList;

    }

    private List<HistoryDetailData> dealWithFirst(String deviceKey, String[] oneTimeDetailDataArray, String pileNum,
                                                  int direection, List<HistoryDetailData> historyDetailDataList) {


        double partDeep;
        if (direection == 1) {
            partDeep = 0;
        } else {
            partDeep = 10;
        }
        int part2Count = 0;//七段计数

        //  按照3个一组拆分单元格

        List<Part2MData> partOF2MDataList = new ArrayList<>();
        for (int j = 4; j < 38; ) {
            Part2MData part2MData = new Part2MData();
            part2MData.setPartTime(oneTimeDetailDataArray[j]);
            part2MData.setKg(oneTimeDetailDataArray[j + 2]);
            part2MData.setL(oneTimeDetailDataArray[j + 4]);
            partOF2MDataList.add(part2MData);
            j = j + 5;
        }


        // 循环7组数据
        for (int i = 0; i < 7; i++) {
            //第六组数据只有0.7，需要特殊处理
//            if ((i == 6 && direection == 1) || (i == 0 && direection == -1)) {
            if (i == 6) {
                Part2MData part2MData = partOF2MDataList.get(i);
                double sumTime = Double.parseDouble(part2MData.getPartTime()) * 60;
                double sumPulp = Double.parseDouble(part2MData.getL());
                long[] timeArray = getLongDataBySum(3, (long) sumTime, 2);
                double[] pulpArray = getDoubleDataBySum(3, sumPulp, 1);

                // 每个数据可以拆成3分
                for (int k = 0; k < 3; k++) {
                    if (partDeep == 10 && direection == -1) {
                        partDeep = 12.5;
                    } else if (partDeep == 12.5 && direection == 1) {
                        partDeep = 10;
                    } else {
                        partDeep = partDeep + 0.25 * direection;
                    }
                    partId++;

                    HistoryDetailData historyDetailData = new HistoryDetailData();
                    historyDetailData.setId(CommonUtils.getUUID());
                    historyDetailData.setPartTime((int) timeArray[k]);
                    // 总长度 除以总时间
//                    historyDetailData.setPartDownSpeed(25 * 60 / historyDetailData.getPartTime() * direection);
                    if (direection > 0) {
                        historyDetailData.setPartDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed) * direection);

                    } else {
                        historyDetailData.setPartDownSpeed(getDoubleRandom(minUpSpeed, maxUpSpeed) * direection);
                    }
                    historyDetailData.setPartDeep(partDeep);
                    historyDetailData.setPartId(partId);
                    historyDetailData.setPartPressure(0);
                    historyDetailData.setPartDensity(getDoubleRandom(1.720, 1.730));
                    // todo
                    historyDetailData.setPartPulp(pulpArray[k]);
                    historyDetailData.setPartCurrent(getDoubleRandom(49, 54));
//                historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 0.6 * 3 / 2);
                    historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 1.6 / 1.73);
                    historyDetailData.setDeviceKey(deviceKey);
                    historyDetailData.setPileDescribe(pileNum);

                    historyDetailData.setPileKey(historyDetailData.getDeviceKey() +
                            "-" + historyDetailData.getPileDescribe());
                    historyDetailDataList.add(historyDetailData);
                }
                continue;
            }

            Part2MData part2MData = partOF2MDataList.get(i);
            double sumTime = Double.parseDouble(part2MData.getPartTime()) * 60;
            double sumPulp = Double.parseDouble(part2MData.getL());
            long[] timeArray = getLongDataBySum(8, (long) sumTime, 2);
            double[] pulpArray = getDoubleDataBySum(8, sumPulp, 1);

            // 每个数据可以拆成8分
            for (int k = 0; k < 8; k++) {
//                if (k == 0 && direection == -1) {
//                    partDeep = partDeep - 0.2;
//                } else if (k == 6 && direection == 1) {
//                partDeep = partDeep + 0.2;
//                } else {
                if (partDeep == 10 && direection == -1) {
                    partDeep = 12.5;
                } else if (partDeep == 12.5 && direection == 1) {
                    partDeep = 10;
                } else {
                    partDeep = partDeep + 0.25 * direection;
                }
//                partDeep = partDeep + 0.25 * direection;
//                }
                partId++;

                HistoryDetailData historyDetailData = new HistoryDetailData();
                historyDetailData.setId(CommonUtils.getUUID());
                historyDetailData.setPartTime((int) timeArray[k]);
                // 总长度 除以总时间
//                historyDetailData.setPartDownSpeed(25 * 60 / historyDetailData.getPartTime() * direection);
                if (direection > 0) {
                    historyDetailData.setPartDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed) * direection);

                } else {
                    historyDetailData.setPartDownSpeed(getDoubleRandom(minUpSpeed, maxUpSpeed) * direection);
                }

                historyDetailData.setPartDeep(partDeep);
                historyDetailData.setPartId(partId);
                historyDetailData.setPartPressure(0);
                historyDetailData.setPartDensity(getDoubleRandom(1.720, 1.730));
                // todo
                historyDetailData.setPartPulp(pulpArray[k]);
                historyDetailData.setPartCurrent(getDoubleRandom(49, 54));
//                historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 0.6 * 3 / 2);
                historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 1.6 / 1.73);
                historyDetailData.setDeviceKey(deviceKey);
                historyDetailData.setPileDescribe(pileNum);

                historyDetailData.setPileKey(historyDetailData.getDeviceKey() +
                        "-" + historyDetailData.getPileDescribe());
                historyDetailDataList.add(historyDetailData);
            }
        }
        System.out.println();
        return historyDetailDataList;

    }


    // 从第四个单元格处理   第一个时间为 第四个单元格
//        for(
//    int j = 4;
//    j< 31;j =j +4)
//
//    {
//        part2Count++;// 七段计数
//
//        // 处理每一小段，共7段
//        double sumDeep = 2;
//        System.out.println(j);
//        // 8段数据：时间、喷量  时间单位分钟，转换为秒计算
//        double sumTime = Double.parseDouble(oneTimeDetailDataArray[j]) * 60;
//        double sumPulp = Double.parseDouble(oneTimeDetailDataArray[j + 2]);
//        // 可误差2S内
//        long[] timeArray = getLongDataBySum(8, (long) sumTime, 2);
//        double[] pulpArray = getDoubleDataBySum(8, sumPulp, 1);
//
////            if (i==7)
//        if (part2Count % 7 == 0) {
////                double aveDeep = sumDeep / 3;// 单位m
////                double aveTime = sumTime * 60 / 3;  // 单位S
////                double avePulp = sumPulp / 3;// 单位kg
//            // 每段生成8个数据
////                int partId = 1;
//            for (int k = 0; k < 3; k++) {
//                partId++;
//                partDeep = partDeep + 0.25 * direection;
//                HistoryDetailData historyDetailData = new HistoryDetailData();
//                historyDetailData.setId(CommonUtils.getUUID());
//                // todo
////                    historyDetailData.setPartTime((int) aveTime + randomTime[k]);
//                historyDetailData.setPartDownSpeed(25 * 60 / historyDetailData.getPartTime() * direection);
//                historyDetailData.setPartDeep(partDeep == 105 ? 10 : partDeep);
//                historyDetailData.setPartId(partId);
//                historyDetailData.setPartPressure(0);
//                historyDetailData.setPartDensity(getDoubleRandom(1.720, 1.730));
//                // todo
////                    historyDetailData.setPartPulp(getDoubleRandom(avePulp - 0.05, avePulp + 0.05));
//                historyDetailData.setPartCurrent(getDoubleRandom(49, 54));
////                    historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 0.6 * 3 / 2);
//                historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 1.6 / 1.73);
//                historyDetailData.setDeviceKey(deviceKey);
//                historyDetailData.setPileDescribe(pileNum);
//                historyDetailDataList.add(historyDetailData);
//
//            }
//        } else {
//
//
////                double aveDeep = sumDeep / 8;// 单位m
////                double aveTime = sumTime * 60 / 8;  // 单位S
////                double avePulp = sumPulp / 8;// 单位kg
//            // 每段生成8个数据
//            for (int k = 0; k < 8; k++) {
//                partDeep = partDeep + 0.25 * direection;
//                partId++;
//
//                HistoryDetailData historyDetailData = new HistoryDetailData();
//                historyDetailData.setId(CommonUtils.getUUID());
//                historyDetailData.setPartTime((int) timeArray[k]);
//                // 总长度 除以总时间
//                historyDetailData.setPartDownSpeed(25 * 60 / historyDetailData.getPartTime() * direection);
//
//                historyDetailData.setPartDeep(partDeep == 105 ? 10 : partDeep);
////                    historyDetailData.setPartDeep(partDeep);
//                historyDetailData.setPartId(partId);
//                historyDetailData.setPartPressure(0);
//                historyDetailData.setPartDensity(getDoubleRandom(1.720, 1.730));
//                // todo
////                    historyDetailData.setPartPulp(getDoubleRandom(avePulp - 0.05, avePulp + 0.05));
//                historyDetailData.setPartCurrent(getDoubleRandom(49, 54));
////                    historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 0.6 * 3 / 2);
//                historyDetailData.setPartAsh(historyDetailData.getPartPulp() * 1.6 / 1.73);
//                historyDetailData.setDeviceKey(deviceKey);
//                historyDetailData.setPileDescribe(pileNum);
//                historyDetailDataList.add(historyDetailData);
//            }
//        }
//        System.out.println();


    private static double[] getDoubleDataBySum(int dataCount, double sum, double offset) {
        double[] resultArray = new double[dataCount];

        double average = keepDouble2(sum / dataCount);
//        double d = dataCount / 2.0;
//        int firstArrayLength = (d == 0) ? (dataCount / 2) : (dataCount / 2 + 1);
        int firstArrayLength = dataCount / 2;

        for (int i = 0; i < firstArrayLength; i++) {
            Double doubleRandom = getDoubleRandom(average - offset / 2, average + offset / 2);
            resultArray[i] = doubleRandom;
        }
        for (int i = firstArrayLength; i < dataCount - 1; i++) {
            resultArray[i] = average * 2 - resultArray[i - firstArrayLength];
        }

        double lastData = sum;
        for (int i = 0; i < dataCount - 1; i++) {
            lastData = lastData - resultArray[i];
        }
        resultArray[dataCount - 1] = lastData;
        return resultArray;
    }

    private static long[] getLongDataBySum(int dataCount, long sum, int offset) {
        long[] resultArray = new long[dataCount];
        long average = sum / dataCount;
        int firstArrayLength = dataCount / 2;

        for (int i = 0; i < firstArrayLength; i++) {
            long doubleRandom = getLongRandom(average - offset, average + offset);
            resultArray[i] = doubleRandom;
        }
        for (int i = firstArrayLength; i < dataCount - 1; i++) {
            resultArray[i] = average * 2 - resultArray[i - firstArrayLength];
        }

        long lastData = sum;
        for (int i = 0; i < dataCount - 1; i++) {
            lastData = lastData - resultArray[i];
        }
        resultArray[dataCount - 1] = lastData;
        return resultArray;
    }

//    @GetMapping("auto-update")
//    public void autoUpdatedata(){
//        //
//        long currentTime = CommonUtils.getCurrentTime();
//        long originTime =
//
//    }


    //    @GetMapping("/refreshCache")
    public void refreshCache() {

        deviceService.refreshCache();
    }

    public static void main(String[] args) throws IOException {
        new MockData2().updateHistoryDataByFile2();
    }


}
