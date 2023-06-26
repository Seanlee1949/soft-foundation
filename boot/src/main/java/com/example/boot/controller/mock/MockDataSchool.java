package com.example.boot.controller.mock;

import com.example.boot.dao.RecordMapper;
import com.example.boot.data.DataScrap;
import com.example.boot.entity.PartDataDescInfo;
import com.example.boot.entity.dto.HistoryData;
import com.example.boot.entity.dto.HistoryDetailData;
import com.example.boot.entity.vo.HistorysVo;
import com.example.boot.service.impl.DeviceServiceImpl;
import com.example.boot.util.CommonUtils;
import org.apache.poi.ss.usermodel.CellType;
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
import java.time.LocalDate;
import java.util.*;

import static com.example.boot.data.DataScrap.*;

/**
 * 浆量少一些L
 * 灰量多一些kg
 * <p>
 * 咱们平台添加一个账号
 * 账号：横琴新区子期中学软基处理工程
 * 密码：666666
 * 添加4台设备
 * MX01808022000111
 * 一号机
 * MX01808022000112
 * 二号机
 * MX01808022000113
 * 三号机
 * MX01808022000114
 * 四号机
 * 然后对应机器里面一共塞进去500多条数据，格式就是平台导出的格式
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
@RequestMapping(value = "/api/data/school")
public class MockDataSchool {
    public static final double WATER_CEMENT_RATIO = 0.65;
    private static double minDownSpeed = 78;
    private static double maxDownSpeed = 83;
    private static double minUpSpeed = 76;
    private static double maxUpSpeed = 82;
    private static double minAngle = 0.01;
    private static double maxAngle = 0.01;
    private static double minWaterCementRatio = 0.56;
    private static double maxWaterCementRatio = 0.59;
    private static double minDensity = 1.72;
    private static double maxDensity = 1.75;
    private static int partId = 0;

    /**
     * ash = pulp * ash/pulp
     */
    public static double ashDivPulpCoefficient = 2471 / 2403;

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataController.class);
    @Autowired
    private DataScrap dataScrap;
    @Autowired
    private DeviceServiceImpl deviceService;

    @Autowired
    private RecordMapper recordMapper;

    public static Map<String, String> deviceKeyMap = new HashMap<>();

    static {
        deviceKeyMap.put("1号机", "MX01808022000111");
        deviceKeyMap.put("2号机", "MX01808022000112");
        deviceKeyMap.put("3号机", "MX01808022000113");
        deviceKeyMap.put("4号机", "MX01808022000114");
    }

    // 桩长15M
    public static final int totalLength = 15;
    public static final double waterAshRate = 0.65;

    public static void main(String[] args) throws IOException {
        new MockDataSchool().updateHistoryDataByFile5();
    }

    @GetMapping("/update-history-data")
    public void updateHistoryDataByFile5() throws IOException {
        String filePath = "D:\\project\\4-横琴新区子期中学\\222222.xlsx";

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(filePath);
        Map<String, List<XSSFRow>> pileNumAndDataMap = new LinkedHashMap<>();

        // 处理一个sheet
        // 按照桩号拆分为row列表
        Map<String, List<XSSFRow>> pileNumAndDataMapOfOneSheet = getPileNumAndDataMap(xssfWorkbook.getSheetAt(1));
        pileNumAndDataMap.putAll(pileNumAndDataMapOfOneSheet);
        LOGGER.debug("处理完毕，共{}组桩数据", pileNumAndDataMap.size());


        List<HistoryData> historyDataList = new ArrayList<>();
        // 将桩数据转化为 平台支持的导入格式
        for (Map.Entry<String, List<XSSFRow>> entry : pileNumAndDataMap.entrySet()) {

            LOGGER.debug("正在处理{}", entry.getKey());
            String pileDesc = entry.getKey();
            List<XSSFRow> onePileData = entry.getValue();
            String s = onePileData.get(0).getCell(16).toString();
            String deviceKey = deviceKeyMap.get(s);
            if (deviceKey == null) {
                continue;
            }
            HistoryData historyData = dealPiceOfData(deviceKey, pileDesc, onePileData);
            historyDataList.add(historyData);
        }

        HistorysVo historysVo = new HistorysVo();
        historysVo.setContent(historyDataList);
        dataScrap.dealHistoryVoAndInsertOrUpdate(historyDataList.size(), historysVo, true);


        deviceService.refreshCache();
    }

    private Map<String, List<XSSFRow>> getPileNumAndDataMap(XSSFSheet sheetAt) {
        Map<String, List<XSSFRow>> pileNumAndDataMap = new LinkedHashMap<>();
        for (int rowNum = 3; rowNum < sheetAt.getPhysicalNumberOfRows(); rowNum++) {
            XSSFRow row = sheetAt.getRow(rowNum);
            LOGGER.debug("正在处理cellNum->{}", rowNum);
            if (row == null || row.getCell(3) == null) {
                LOGGER.debug("第{}行为空，处理异常，rowNum->{}", rowNum, rowNum);
                continue;
            }
            String PileDesc = row.getCell(2).toString();

            pileNumAndDataMap.put(PileDesc, Collections.singletonList(row));
        }
        return pileNumAndDataMap;
    }

    private HistoryData dealPiceOfData(String deviceKey, String PileDesc, List<XSSFRow> onePileData) {
        // 桩ID
        String id = CommonUtils.getUUID();
        String pieDiscribe = PileDesc;
        // 总共四行： 第一次下降，第一次上升，第二次下降，第二次上升

// 解析基本信息
        XSSFRow cellsOfOneLine = onePileData.get(0);
        LocalDate localDate;
        if (cellsOfOneLine.getCell(1).getCellType() == CellType.STRING) {
            String[] strings = cellsOfOneLine.getCell(1).toString().replaceAll(" ", "").split("年|月|日");
            localDate = LocalDate.of(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2]));
        } else {
            Date dateCellValue = cellsOfOneLine.getCell(1).getDateCellValue();
            localDate = LocalDate.of(dateCellValue.getYear() + 1900, dateCellValue.getMonth(), dateCellValue.getDay());
        }
        cellsOfOneLine.getCell(20);
        Date startDate = buildData(cellsOfOneLine, 18, localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        Date endDate = buildData(cellsOfOneLine, 19, localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long sumTime = endTime - startTime;
        double sumPulp = cellsOfOneLine.getCell(6).getNumericCellValue();
        double sumAsh = cellsOfOneLine.getCell(9).getNumericCellValue();

        // 获取详情数据
        List<HistoryDetailData> historyDetailDataList =
                getHistoryDetailData(deviceKey, onePileData, PileDesc, id, sumTime, sumPulp);


        double pileTime = sumTime;

        HistoryData historyData = new HistoryData();
        historyData.set_id(id);
        historyData.setData(historyDetailDataList);
        historyData.setPackageAmount(Integer.parseInt(pieDiscribe.replaceAll("[a-zA-Z]", "")));
        historyData.setPileId(Integer.parseInt(pieDiscribe.replaceAll("[a-zA-Z]", "")));
        historyData.setVersion("2.03");
        historyData.setDeviceKey(deviceKey);
        historyData.setMachineKey("0");
        historyData.setPileDescribe(pieDiscribe);
        historyData.setBeginTime(startDate.getTime() / 1000);
        historyData.setEndTime(endDate.getTime() / 1000);
        historyData.setProcessType("00000000");
        historyData.setLongitude(113.17780543700835);
        historyData.setLatitude(22.094602099999996);
        historyData.setHoleLatitude(22.094602099999996);
        historyData.setHoleLongitude(113.1775438703417);
        Double length = getDoubleRandom(totalLength, totalLength + 0.6);
        historyData.setDepth(length);
        historyData.setFrDepth(historyData.getDepth());
        historyData.setReDepth(historyData.getDepth());
        historyData.setEmDepth(0.5);
        historyData.setWaterCementRatio(WATER_CEMENT_RATIO);
        historyData.setDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed));
        historyData.setUpSpeed(getDoubleRandom(minUpSpeed, maxUpSpeed));
        historyData.setCumulativePulp(sumPulp);
        historyData.setAveragePulp(historyData.getCumulativePulp() / totalLength);
        historyData.setCumulativeAsh(sumAsh);
        historyData.setAverageAsh(sumAsh / totalLength);
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
        historyData.setPileTime(pileTime / 1000);
        historyData.setMileNo("0");
        historyData.setPartNo("0");
        historyData.setMilePartNo("0+0");
        historyData.setPileNo(pieDiscribe);
        System.out.println("处理数据完成" + pieDiscribe);
        return historyData;
    }

    // 构造详情数据
    private List<HistoryDetailData> getHistoryDetailData(String deviceKey, List<XSSFRow> onePileData, String pileNum,
                                                         String pileId, long sumTime, double sumPulp) {

        long[] timeArray = getLongDataBySum(240, (long) sumTime, 2);
        double[] pulpArray = getDoubleDataBySum(240, sumPulp, 0.2);

        /**
         * 15米，按照0.25米一段，共4*15 = 60段 * 4 = 240段数据
         */
        List<HistoryDetailData> historyDetailDataList = new LinkedList<>();

        PartDataDescInfo partDataDescInfo = new PartDataDescInfo(0, 0);

        LOGGER.debug("处理下降阶段");
        int direction = 1;
        for (int i = 1; i <= 60; i++) {
            HistoryDetailData historyDetailData = new HistoryDetailData();
            // 时间、速度，灰量，浆量
            historyDetailData.setPartTime((int) timeArray[i]);
            historyDetailData.setPartDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed) * direction);

            historyDetailData.setPartPulp(pulpArray[i]);
            historyDetailData.setPartAsh(historyDetailData.getPartPulp() * ashDivPulpCoefficient);

            buildHistoryDetailData(deviceKey, pileNum, pileId, partDataDescInfo, direction, historyDetailData);

            historyDetailDataList.add(historyDetailData);
        }
        LOGGER.debug("处理上升阶段");
        direction = -1;
        for (int i = 1; i <= 60; i++) {
            HistoryDetailData historyDetailData = new HistoryDetailData();
            // 时间、速度，灰量，浆量
            historyDetailData.setPartTime((int) timeArray[i]);
            historyDetailData.setPartDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed) * direction);

            historyDetailData.setPartPulp(pulpArray[i]);
            historyDetailData.setPartAsh(historyDetailData.getPartPulp() * ashDivPulpCoefficient);

            buildHistoryDetailData(deviceKey, pileNum, pileId, partDataDescInfo, direction, historyDetailData);

            historyDetailDataList.add(historyDetailData);
        }

        LOGGER.debug("处理下降阶段");
        direction = 1;
        for (int i = 1; i <= 60; i++) {
            HistoryDetailData historyDetailData = new HistoryDetailData();
            // 时间、速度，灰量，浆量
            historyDetailData.setPartTime((int) timeArray[i]);
            historyDetailData.setPartDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed) * direction);

            historyDetailData.setPartPulp(pulpArray[i]);
            historyDetailData.setPartAsh(historyDetailData.getPartPulp() * ashDivPulpCoefficient);

            buildHistoryDetailData(deviceKey, pileNum, pileId, partDataDescInfo, direction, historyDetailData);

            historyDetailDataList.add(historyDetailData);
        }
        LOGGER.debug("处理上升阶段");
        direction = -1;
        for (int i = 1; i <= 60; i++) {
            HistoryDetailData historyDetailData = new HistoryDetailData();
            // 时间、速度，灰量，浆量
            historyDetailData.setPartTime((int) timeArray[i]);
            historyDetailData.setPartDownSpeed(getDoubleRandom(minDownSpeed, maxDownSpeed) * direction);

            historyDetailData.setPartPulp(pulpArray[i]);
            historyDetailData.setPartAsh(historyDetailData.getPartPulp() * ashDivPulpCoefficient);

            buildHistoryDetailData(deviceKey, pileNum, pileId, partDataDescInfo, direction, historyDetailData);

            historyDetailDataList.add(historyDetailData);
        }


        return historyDetailDataList;
    }

    private Date buildData(XSSFRow cellsOfOneLine, int i2, int year, int monthValue, int dayOfMonth) {
        Date startDate = cellsOfOneLine.getCell(i2).getDateCellValue();
        startDate.setYear(year - 1900);
        startDate.setMonth(monthValue);
        startDate.setDate(dayOfMonth);
        return startDate;
    }

    private void buildHistoryDetailData(String deviceKey, String pileNum, String pileId, PartDataDescInfo partDataDescInfo, int direction, HistoryDetailData historyDetailData) {
        // 唯一ID
        historyDetailData.setId(CommonUtils.getUUID());
        // 桩号，深度，递增或者递减
        partDataDescInfo.addPartId();
        partDataDescInfo.addPartDeepDefault(direction);
        historyDetailData.setPartId(partDataDescInfo.getPartId());
        historyDetailData.setPartDeep(partDataDescInfo.getPartDeep());

        // 喷压、密度、电流，一般不怎么关注
        historyDetailData.setPartPressure(0);
        historyDetailData.setPartDensity(getDoubleRandom(1.720, 1.730));
        historyDetailData.setPartCurrent(getDoubleRandom(49, 54));
        // 目前只有key有用，存桩ID
        historyDetailData.setDeviceKey(deviceKey);
        historyDetailData.setPileDescribe(pileNum);
        historyDetailData.setPileKey(pileId);
    }


    private static double[] getDoubleDataBySum(int dataCount, double sum, double offset) {
        double[] resultArray = new double[dataCount];

        double average = keepDouble2(sum / dataCount);
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


    @GetMapping("/refreshCache")
    public void refreshCache() {
        deviceService.refreshCache();
    }

    @GetMapping
    public void updateDeviceRecord() {

    }

}
