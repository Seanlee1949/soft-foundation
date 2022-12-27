package com.example.boot.controller;

import com.example.boot.constant.CommonConstant;
import com.example.boot.service.impl.DeviceServiceImpl;
import com.example.boot.util.CommonUtils;
import com.example.boot.util.ExcelUtils;
import com.example.boot.util.cache.CacheManagerImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author lishuai
 * @since 2022/12/2
 */
@RestController
@RequestMapping("api/file/")
public class FileController {
    @Autowired
    ExcelUtils excelUtils;

    @PostMapping("/import")
    public String importFile(MultipartFile file) throws Exception {

        excelUtils.importExcel(file);
        return CommonConstant.SUCCESS;
    }

    @DeleteMapping("/delete")
    public String delete(MultipartFile file) throws  Exception {
        excelUtils.delete(file);
        return CommonConstant.SUCCESS;
    }

    @PostMapping("/export")
    public ResponseEntity<Resource> exportFile(Long beginTime,Long endTime,String deviceKey,String pileDescribe) throws Exception {
        String time = CommonUtils.getCurrentTime().replaceAll(":", "：").replaceAll(" ", " ");
        String fileName = "history" + time.substring(0, time.length() - 4) + ".xls";
//        ConnectionContext connection = ThreadContext.get(UniLicConstant.THREAD_CONTEXT_CONNECTION);
        try {
            // 导出文件内容
//            String json = FasterJson.MAPPER.writeValueAsString(debugCommandEntity);
//            String fileName = connection.getDeviceId();
//            if (json.contains(CommandTypeEnum.TLV_BYTES.name())) {
//                json = convertTlvBytes(debugCommandEntity);
//                fileName = json.substring(0, INT_90);
//            }
//            InputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
            InputStream inputStream = excelUtils.exportExcel(fileName,beginTime,endTime,deviceKey,pileDescribe);
            Resource resource = new InputStreamResource(inputStream);


            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            String.format("attachment;filename=%s", fileName))
                    .body(resource);
        } catch (JsonProcessingException e) {
//            throw new UnilicToolException(UnilicToolErrorCode.DEBUG_COMMAND_EXCEPTION, e);
        }


//        String time = CommonUtils.getCurrentTime().replaceAll(":", "：").replaceAll(" ", " ");
//        String fileName = "history" + time.substring(0, time.length() - 4) + ".xls";
//        excelUtils.exportExcel("history" + time.substring(0, time.length() - 4) + ".xls");

        return null;
//        return new FileOutputStream();
    }


}
