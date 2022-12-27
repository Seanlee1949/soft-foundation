//package com.example.boot.util;
//
//import org.apache.catalina.User;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.VerticalAlignment;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.swing.filechooser.FileSystemView;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * https://blog.csdn.net/vbirdbest/article/details/72870714
// *
// * @author lishuai
// * @since 2022/11/29
// */
//public class ExcelUtils_copy {
//    public static void main(String[] args) throws IOException {
////        createExcel();
//        readExcel();
//
//
//    }
//
//    public static void createExcel() throws IOException {
//        // 获取桌面路径
//        FileSystemView fsv = FileSystemView.getFileSystemView();
//        String desktop = fsv.getHomeDirectory().getPath();
//        String filePath = desktop + "/template.xls";
//
//        File file = new File(filePath);
//        OutputStream outputStream = new FileOutputStream(file);
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Sheet1");
//        HSSFRow row = sheet.createRow(0);
//        row.createCell(0).setCellValue("id");
//        row.createCell(1).setCellValue("订单号");
//        row.createCell(2).setCellValue("下单时间");
//        row.createCell(3).setCellValue("个数");
//        row.createCell(4).setCellValue("单价");
//        row.createCell(5).setCellValue("订单金额");
//        row.setHeightInPoints(30); // 设置行的高度
//
//        HSSFRow row1 = sheet.createRow(1);
//        row1.createCell(0).setCellValue("1");
//        row1.createCell(1).setCellValue("NO00001");
//
//        // 日期格式化
//        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
//        HSSFCreationHelper creationHelper = workbook.getCreationHelper();
//        cellStyle2.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
//        sheet.setColumnWidth(2, 20 * 256); // 设置列的宽度
//
//        HSSFCell cell2 = row1.createCell(2);
//        cell2.setCellStyle(cellStyle2);
//        cell2.setCellValue(new Date());
//
//        row1.createCell(3).setCellValue(2);
//
//
//        // 保留两位小数
//        HSSFCellStyle cellStyle3 = workbook.createCellStyle();
//        cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
//        HSSFCell cell4 = row1.createCell(4);
//        cell4.setCellStyle(cellStyle3);
//        cell4.setCellValue(29.5);
//
//
//        // 货币格式化
//        HSSFCellStyle cellStyle4 = workbook.createCellStyle();
//        HSSFFont font = workbook.createFont();
//        font.setFontName("华文行楷");
//        font.setFontHeightInPoints((short) 15);
//        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
//        cellStyle4.setFont(font);
//
//        HSSFCell cell5 = row1.createCell(5);
//        cell5.setCellFormula("D2*E2");  // 设置计算公式
//
//        // 获取计算公式的值
//        HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(workbook);
//        cell5 = e.evaluateInCell(cell5);
//        System.out.println(cell5.getNumericCellValue());
//
//
//        workbook.setActiveSheet(0);
//        workbook.write(outputStream);
//        outputStream.close();
//    }
//
//    public static void readExcel() throws IOException {
//        FileSystemView fsv = FileSystemView.getFileSystemView();
//        String desktop = fsv.getHomeDirectory().getPath();
//        String filePath = desktop + "/template.xls";
//
//        FileInputStream fileInputStream = new FileInputStream(filePath);
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
//        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
//        HSSFSheet sheet = workbook.getSheet("Sheet1");
//
//        int lastRowIndex = sheet.getLastRowNum();
//        System.out.println(lastRowIndex);
//        for (int i = 0; i <= lastRowIndex; i++) {
//            HSSFRow row = sheet.getRow(i);
//            if (row == null) {
//                break;
//            }
//
//            short lastCellNum = row.getLastCellNum();
//            for (int j = 0; j < lastCellNum; j++) {
//                String cellValue = row.getCell(j).getStringCellValue();
//                System.out.println(cellValue);
//            }
//        }
//
//
//        bufferedInputStream.close();
//    }
//
//    public static  void importExcel(MultipartFile file) throws Exception {
//        InputStream inputStream = file.getInputStream();
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
//        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
//        //HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
//        HSSFSheet sheet = workbook.getSheetAt(0);
//
//        int lastRowNum = sheet.getLastRowNum();
//        for (int i = 2; i <= lastRowNum; i++) {
//            // 按行解析
//             /* HSSFRow row = sheet.getRow(i);
//            int id = (int) row.getCell(0).getNumericCellValue();
//            String name = row.getCell(1).getStringCellValue();
//            int age = (int) row.getCell(2).getNumericCellValue();
//
//            System.out.println(id + "-" + name + "-" + age);
//      */
//        }
//    }
//
//    public static  void exportExcel( String name) throws Exception {
//
//        String[] tableHeaders = {"id", "姓名", "年龄"};
//
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Sheet1");
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        Font font = workbook.createFont();
//        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
//        font.setBold(true);
//        cellStyle.setFont(font);
//
//        // 将第一行的三个单元格给合并
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
//        HSSFRow row = sheet.createRow(0);
//        HSSFCell beginCell = row.createCell(0);
//        beginCell.setCellValue("通讯录");
//        beginCell.setCellStyle(cellStyle);
//
//        row = sheet.createRow(1);
//        // 创建表头
//        for (int i = 0; i < tableHeaders.length; i++) {
//            HSSFCell cell = row.createCell(i);
//            cell.setCellValue(tableHeaders[i]);
//            cell.setCellStyle(cellStyle);
//        }
//
//        List<User> users = new ArrayList<>();
//        users.add(new User(1L, "张三", 20));
//        users.add(new User(2L, "李四", 21));
//        users.add(new User(3L, "王五", 22));
//
//        for (int i = 0; i < users.size(); i++) {
//            row = sheet.createRow(i + 2);
//
//            User user = users.get(i);
//            row.createCell(0).setCellValue(user.getId());
//            row.createCell(1).setCellValue(user.getName());
//            row.createCell(2).setCellValue(user.getAge());
//        }
//
////        OutputStream outputStream = response.getOutputStream();
////        response.reset();
////        response.setContentType("application/vnd.ms-excel");
////        response.setHeader("Content-disposition", "attachment;filename=template.xls");
//
////        workbook.write(outputStream);
////        outputStream.flush();
////        outputStream.close();
//    }
//
//
//}