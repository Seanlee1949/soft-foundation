package com.example.boot.util;

import com.example.boot.dao.HistoryDataMapper;
import com.example.boot.entity.dto.HistoryData;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * https://blog.csdn.net/vbirdbest/article/details/72870714
 *
 * @author lishuai
 * @since 2022/11/29
 */
@Component
public class ExcelUtils {
    @Autowired
    HistoryDataMapper historyDataMapper;

    public static void main(String[] args) throws IOException {
//        createExcel();
        readExcel();


    }

    public static void createExcel() throws IOException {
        // 获取桌面路径
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("订单号");
        row.createCell(2).setCellValue("下单时间");
        row.createCell(3).setCellValue("个数");
        row.createCell(4).setCellValue("单价");
        row.createCell(5).setCellValue("订单金额");
        row.setHeightInPoints(30); // 设置行的高度

        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("NO00001");

        // 日期格式化
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        HSSFCreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle2.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        sheet.setColumnWidth(2, 20 * 256); // 设置列的宽度

        HSSFCell cell2 = row1.createCell(2);
        cell2.setCellStyle(cellStyle2);
        cell2.setCellValue(new Date());

        row1.createCell(3).setCellValue(2);


        // 保留两位小数
        HSSFCellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        HSSFCell cell4 = row1.createCell(4);
        cell4.setCellStyle(cellStyle3);
        cell4.setCellValue(29.5);


        // 货币格式化
        HSSFCellStyle cellStyle4 = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("华文行楷");
        font.setFontHeightInPoints((short) 15);
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        cellStyle4.setFont(font);

        HSSFCell cell5 = row1.createCell(5);
        cell5.setCellFormula("D2*E2");  // 设置计算公式

        // 获取计算公式的值
        HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(workbook);
        cell5 = e.evaluateInCell(cell5);
        System.out.println(cell5.getNumericCellValue());


        writrFile(workbook, "template.xls");

    }

//    public InputStream transToInputStream(HSSFWorkbook workbook, String fileName) {
//        FileSystemView fsv = FileSystemView.getFileSystemView();
//        String desktop = fsv.getHomeDirectory().getPath();
//        String filePath = desktop + "/" + fileName;
//
//        File file = new File(filePath);
//        InputStream inputStream = new ByteArrayInputStream(workbook.getBytes());
//
//        workbook.setActiveSheet(0);
//        workbook.write(outputStream);
//        outputStream.close();
//    }

    private static OutputStream writrFile(HSSFWorkbook workbook, String fileName) throws IOException {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop + "/" + fileName;

        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);

        workbook.setActiveSheet(0);
        workbook.write(outputStream);
        outputStream.close();
        return outputStream;
    }


    public static void readExcel() throws IOException {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop + "/template.xls";

        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workbook.getSheet("Sheet1");

        int lastRowIndex = sheet.getLastRowNum();
        System.out.println(lastRowIndex);
        for (int i = 0; i <= lastRowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                break;
            }

            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                String cellValue = row.getCell(j).getStringCellValue();
                System.out.println(cellValue);
            }
        }


        bufferedInputStream.close();
    }

    public void delete(MultipartFile file) throws Exception {

        InputStream inputStream = file.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        //HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);

        List<HistoryData> historyDataList = getHistoryDataListForDelete(sheet);

        for (HistoryData historyData : historyDataList) {
            HistoryData historyDataTemp = historyDataMapper.selectById(historyData.getId());
            if (historyDataTemp == null) {
                continue;
            } else {
                historyDataMapper.deleteById(historyData.getId());
            }
        }
    }


    public void importExcel(MultipartFile file) throws Exception {

        InputStream inputStream = file.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        //HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);

        List<HistoryData> historyDataList = getHistoryDataList(sheet);

        for (HistoryData historyData : historyDataList) {
            HistoryData historyDataTemp = historyDataMapper.selectById(historyData.getId());
            if (historyDataTemp == null) {
                historyDataMapper.insert(historyData);
            } else {
                historyDataMapper.updateById(historyData);
            }
        }
    }

    private List<HistoryData> getHistoryDataList(HSSFSheet sheet) {
        List<HistoryData> historyDataList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 4; i <= lastRowNum; i++) {
            // 按行解析

            HistoryData historyData = new HistoryData();
            HSSFRow row = sheet.getRow(i);
            if (row.getCell(0) == null) {
                continue;
            }
            historyData.set_id(getValue(String.class, 0, row));
            historyData.setPackageAmount(getValue(int.class, 1, row));
            //特殊处理
            historyData.setPileId(Long.parseLong(getValue(String.class, 2, row)));
            historyData.setVersion(getValue(String.class, 3, row));
            historyData.setDeviceKey(getValue(String.class, 4, row));
            historyData.setMachineKey(getValue(String.class, 5, row));
            historyData.setPileDescribe(getValue(String.class, 6, row));
            historyData.setBeginTime(getValue(Date.class, 7, row).getTime());
            historyData.setEndTime(getValue(Date.class, 8, row).getTime());
            historyData.setProcessType(getValue(String.class, 9, row));
            historyData.setLongitude(getValue(double.class, 10, row));
            historyData.setLatitude(getValue(double.class, 11, row));
            historyData.setHoleLongitude(getValue(double.class, 12, row));
            historyData.setHoleLatitude(getValue(double.class, 13, row));
            historyData.setDepth(getValue(double.class, 14, row));
            historyData.setFrDepth(getValue(double.class, 15, row));
            historyData.setReDepth(getValue(double.class, 16, row));
            historyData.setEmDepth(getValue(double.class, 17, row));
            historyData.setWaterCementRatio(getValue(double.class, 18, row));
            historyData.setDownSpeed(getValue(double.class, 19, row));
            historyData.setUpSpeed(getValue(double.class, 20, row));
            historyData.setCumulativePulp(getValue(double.class, 21, row));
            historyData.setAveragePulp(getValue(double.class, 22, row));
            historyData.setCumulativeAsh(getValue(double.class, 23, row));
            historyData.setAverageAsh(getValue(double.class, 24, row));
            historyData.setMaxCurrent(getValue(double.class, 25, row));
            historyData.setAverageCurrent(getValue(double.class, 26, row));
            historyData.setAveragePressure(getValue(int.class, 27, row));
            historyData.setMaxDownSpeed(getValue(double.class, 28, row));
            historyData.setMaxUpSpeed(getValue(double.class, 29, row));
            historyData.setXAngle(getValue(double.class, 30, row));
            historyData.setYAngle(getValue(double.class, 31, row));
            historyData.setTTypeLength(getValue(int.class, 32, row));
            historyData.setTTypePulp(getValue(int.class, 33, row));
            historyData.setBottomPartPulp(getValue(double.class, 34, row));
            historyData.setTTypeAsh(getValue(int.class, 35, row));
            historyData.setBottomPartAsh(getValue(int.class, 36, row));
            historyData.setPartCount(getValue(int.class, 37, row));
            historyData.setDeviceType(getValue(String.class, 38, row));
            historyData.setDeviceTypeName(getValue(String.class, 39, row));
            historyData.setTs(Long.parseLong(getValue(String.class, 40, row)));
            historyData.setStandardAsh(getValue(int.class, 41, row));
            historyData.setPileTopCurrent(getValue(double.class, 42, row));
            historyData.setMaxAngle(getValue(int.class, 43, row));
            historyData.setPileTime(getValue(double.class, 44, row));
            historyData.setMileNo(getValue(String.class, 45, row));
            historyData.setPartNo(getValue(String.class, 46, row));
            historyData.setMilePartNo(getValue(String.class, 47, row));
            historyData.setPileNo(getValue(String.class, 48, row));
            historyDataList.add(historyData);
        }
        return historyDataList;
    }

    private List<HistoryData> getHistoryDataListForDelete(HSSFSheet sheet) {
        List<HistoryData> historyDataList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 4; i <= lastRowNum; i++) {
            // 按行解析

            HistoryData historyData = new HistoryData();
            HSSFRow row = sheet.getRow(i);
            if (row.getCell(0) == null) {
                continue;
            }
            historyData.set_id(getValue(String.class, 0, row));
            historyDataList.add(historyData);
        }
        return historyDataList;
    }

    public <T> T getValue(Class<T> tClass, int cellNum, HSSFRow row) {
        Object numericCellValue = "";
        if (tClass == int.class) {
            numericCellValue = (int) row.getCell(cellNum).getNumericCellValue();

        } else if (tClass == long.class) {
            numericCellValue = (long) row.getCell(cellNum).getNumericCellValue();
        } else if (tClass == String.class) {
            numericCellValue = (String) row.getCell(cellNum).getStringCellValue();
        } else if (tClass == Date.class) {
            numericCellValue = row.getCell(cellNum).getDateCellValue();
        } else if (tClass == double.class) {
            numericCellValue = (double) row.getCell(cellNum).getNumericCellValue();
        }
        return (T) numericCellValue;
    }

    public InputStream exportExcel(String fileName, Long beginTime, Long endTime, String deviceKey, String pileDescribe) throws Exception {
        List<HistoryData> historyDataListAll = historyDataMapper.selectList(null);
        List<HistoryData> historyDataList = historyDataListAll.stream()
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
                        String temp = pileDescribe;
                        if (pileDescribe.charAt(1) == ' ') {
                             temp = pileDescribe.replaceFirst(" ", "+");
                        }

                        return historyData.getPileDescribe().equals(temp);
                    } else {
                        return true;
                    }
                })
                // Long startTime, Long endTime,
                .filter(historyData -> {
                    if (beginTime != null) {
                        return historyData.getEndTime() > beginTime;
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
//                // Long minDepth, Long maxDepth,
//                .filter(historyData -> {
//                    if (minDepth != null) {
//                        return historyData.getFrDepth() > minDepth;
//                    } else {
//                        return true;
//                    }
//                })
//                .filter(historyData -> {
//                    if (maxDepth != null) {
//                        return historyData.getFrDepth() < maxDepth;
//                    } else {
//                        return true;
//                    }
//                })
//                // Long minAsh, Long maxAsh,
//                .filter(historyData -> {
//                    if (minDepth != null) {
//                        return historyData.getCumulativeAsh() > minDepth;
//                    } else {
//                        return true;
//                    }
//                })
//                .filter(historyData -> {
//                    if (maxDepth != null) {
//                        return historyData.getCumulativeAsh() < maxDepth;
//                    } else {
//                        return true;
//                    }
//                })
//                //  Long minAvgAsh, Long maxAvgAsh,
//                .filter(historyData -> {
//                    if (minAvgAsh != null) {
//                        return historyData.getAverageAsh() > minAvgAsh;
//                    } else {
//                        return true;
//                    }
//                })
//                .filter(historyData -> {
//                    if (maxAvgAsh != null) {
//                        return historyData.getAverageAsh() < maxAvgAsh;
//                    } else {
//                        return true;
//                    }
//                })
//                // Long minPileTime, Long maxPileTime
//                .filter(historyData -> {
//                    if (minPileTime != null) {
//                        return historyData.getPileTime() > minPileTime;
//                    } else {
//                        return true;
//                    }
//                })
//                .filter(historyData -> {
//                    if (maxPileTime != null) {
//                        return historyData.getPileTime() < maxPileTime;
//                    } else {
//                        return true;
//                    }
//                })
                .collect(Collectors.toList());


        String[] tableFirstHeaders = {"ID", "PACKAGE_AMOUNT", "PILE_ID", "VERSION", "DEVICE_KEY",
                "MACHINE_KEY", "PILE_DESCRIBE", "BEGIN_TIME", "END_TIME", "PROCESS_TYPE",
                "LONGITUDE", "LATITUDE", "HOLE_LONGITUDE", "HOLE_LATITUDE", "DEPTH",
                "FR_DEPTH", "RE_DEPTH", "EM_DEPTH", "WATER_CEMENT_RATIO", "DOWN_SPEED",
                "UP_SPEED", "CUMULATIVE_PULP", "AVERAGE_PULP", "CUMULATIVE_ASH", "AVERAGE_ASH",
                "MAX_CURRENT", "AVERAGE_CURRENT", "AVERAGE_PRESSURE", "MAX_DOWN_SPEED", "MAX_UP_SPEED",
                "X_ANGLE", "Y_ANGLE", "T_TYPE_LENGTH", "T_TYPE_PULP", "BOTTOM_PART_PULP",
                "T_TYPE_ASH", "BOTTOM_PART_ASH", "PART_COUNT", "DEVICE_TYPE", "DEVICE_TYPE_NAME",
                "TS", "STANDARD_ASH", "PILE_TOP_CURRENT", "MAX_ANGLE", "PILE_TIME",
                "MILE_NO", "PART_NO", "MILE_PART_NO", "PILE_NO"};
        String[] tableSecondHeaders = {"数据ID/忽略", "xx", "桩ID/忽略", "版本", "设备编号",
                "机器序号", "桩号", "开始时间", "结束时间", "未知",
                "经度", "纬度", "xx经度", "xx纬度", "施工桩长",
                "初搅深度", "复搅深度", "空搅深度", "水灰比", "下降速度",
                "上升速度", "累计浆量", "平均浆量", "累计灰量", "平均灰量",
                "最大电流", "平均电流", "平均喷压", "最大下降速度", "最大上升速度",
                "x", "y", "扩大头长度", "扩大头浆量", "下部桩浆量",
                "扩大头灰量", "下部桩灰量", "未知", "设备类型", "设备类型名",
                "未知", "标准灰量", "桩端电流", "未知", "成桩时间/s",
                "未知", "未知", "未知", "未知"};

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("历史数据表");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);

        // 将第一行的三个单元格给合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        HSSFRow row = sheet.createRow(0);
        HSSFCell beginCell = row.createCell(0);
        beginCell.setCellValue("历史数据表");
        beginCell.setCellStyle(cellStyle);

        row = sheet.createRow(1);
        for (int i = 0; i < tableSecondHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(i);
            cell.setCellStyle(cellStyle);
        }
        row = sheet.createRow(2);
        // 创建表头
        for (int i = 0; i < tableFirstHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(tableFirstHeaders[i]);
            cell.setCellStyle(cellStyle);
        }
        row = sheet.createRow(3);
        for (int i = 0; i < tableSecondHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(tableSecondHeaders[i]);
            cell.setCellStyle(cellStyle);
        }


//        List<User> users = new ArrayList<>();
//        users.add(new User(1L, "张三", 20));
//        users.add(new User(2L, "李四", 21));
//        users.add(new User(3L, "王五", 22));

        //样式对象

        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
        //数据格式对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        //给样式对象设置数据格式
        cellStyle1.setDataFormat(dataFormat.getFormat("yyyy-MM-dd HH:mm:ss"));


        for (int i = 0; i < historyDataList.size(); i++) {
            row = sheet.createRow(i + 4);

            HistoryData historyData = historyDataList.get(i);
            row.createCell(0).setCellValue(historyDataList.get(i).get_id());
            row.createCell(1).setCellValue(historyDataList.get(i).getPackageAmount());
            {
                HSSFCell cell2 = row.createCell(2);
                cell2.setCellValue(String.valueOf(historyDataList.get(i).getPileId()));
            }
            row.createCell(3).setCellValue(historyDataList.get(i).getVersion());
            row.createCell(4).setCellValue(historyDataList.get(i).getDeviceKey());
            row.createCell(5).setCellValue(historyDataList.get(i).getMachineKey());
            row.createCell(6).setCellValue(historyDataList.get(i).getPileDescribe());
            {


                HSSFCell cell7 = row.createCell(7);
                cell7.setCellValue(new Date(historyDataList.get(i).getBeginTime()));
                cell7.setCellStyle(cellStyle1);

                HSSFCell cell8 = row.createCell(8);
                cell8.setCellValue(new Date(historyDataList.get(i).getEndTime()));
                cell8.setCellStyle(cellStyle1);

            }
            row.createCell(9).setCellValue(historyDataList.get(i).getProcessType());
            row.createCell(10).setCellValue(historyDataList.get(i).getLongitude());
            row.createCell(11).setCellValue(historyDataList.get(i).getLatitude());
            row.createCell(12).setCellValue(historyDataList.get(i).getHoleLongitude());
            row.createCell(13).setCellValue(historyDataList.get(i).getHoleLatitude());
            row.createCell(14).setCellValue(historyDataList.get(i).getDepth());
            row.createCell(15).setCellValue(historyDataList.get(i).getFrDepth());
            row.createCell(16).setCellValue(historyDataList.get(i).getReDepth());
            row.createCell(17).setCellValue(historyDataList.get(i).getEmDepth());
            row.createCell(18).setCellValue(historyDataList.get(i).getWaterCementRatio());
            row.createCell(19).setCellValue(historyDataList.get(i).getDownSpeed());
            row.createCell(20).setCellValue(historyDataList.get(i).getUpSpeed());
            row.createCell(21).setCellValue(historyDataList.get(i).getCumulativePulp());
            row.createCell(22).setCellValue(historyDataList.get(i).getAveragePulp());
            row.createCell(23).setCellValue(historyDataList.get(i).getCumulativeAsh());
            row.createCell(24).setCellValue(historyDataList.get(i).getAverageAsh());
            row.createCell(25).setCellValue(historyDataList.get(i).getMaxCurrent());
            row.createCell(26).setCellValue(historyDataList.get(i).getAverageCurrent());
            row.createCell(27).setCellValue(historyDataList.get(i).getAveragePressure());
            row.createCell(28).setCellValue(historyDataList.get(i).getMaxDownSpeed());
            row.createCell(29).setCellValue(historyDataList.get(i).getMaxUpSpeed());
            row.createCell(30).setCellValue(historyDataList.get(i).getXAngle());
            row.createCell(31).setCellValue(historyDataList.get(i).getYAngle());
            row.createCell(32).setCellValue(historyDataList.get(i).getTTypeLength());
            row.createCell(33).setCellValue(historyDataList.get(i).getTTypePulp());
            row.createCell(34).setCellValue(historyDataList.get(i).getBottomPartAsh());
            row.createCell(35).setCellValue(historyDataList.get(i).getTTypeAsh());
            row.createCell(36).setCellValue(historyDataList.get(i).getBottomPartAsh());
            row.createCell(37).setCellValue(historyDataList.get(i).getPartCount());
            row.createCell(38).setCellValue(historyDataList.get(i).getDeviceType());
            row.createCell(39).setCellValue(historyDataList.get(i).getDeviceTypeName());
            {
                row.createCell(40).setCellValue(String.valueOf(historyDataList.get(i).getTs()));
            }
            row.createCell(41).setCellValue(historyDataList.get(i).getStandardAsh());
            row.createCell(42).setCellValue(historyDataList.get(i).getPileTopCurrent());
            row.createCell(43).setCellValue(historyDataList.get(i).getMaxAngle());
            {
                HSSFCell hssfCell = row.createCell(44);
//                hssfCell.setCellValue(new Date(historyData.getPileTime()).getTime());
                hssfCell.setCellValue(historyDataList.get(i).getPileTime());
            }
            row.createCell(45).setCellValue(historyDataList.get(i).getMileNo());
            row.createCell(46).setCellValue(historyDataList.get(i).getPartNo());
            row.createCell(47).setCellValue(historyDataList.get(i).getMilePartNo());
            row.createCell(48).setCellValue(historyDataList.get(i).getPileNo());
        }
        for (int i = 0; i < 50; i++) {
            sheet.autoSizeColumn(i);
        }
        writrFile(workbook, fileName);

        return new ByteArrayInputStream(workbook.getBytes());
//        OutputStream outputStream = response.getOutputStream();
//        response.reset();
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-disposition", "attachment;filename=template.xls");

//        workbook.write(outputStream);
//        outputStream.flush();
//        outputStream.close();
    }


}