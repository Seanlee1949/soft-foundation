package com.example.boot.common;

import com.example.boot.service.impl.DeviceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 项目初始化时，做一些操作
 *
 * @author lishuai
 * @since 2022/1/17
 */
@Order(2)
@Component
public class UnilicInitRunner implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnilicInitRunner.class);
    DeviceServiceImpl deviceService;

    public UnilicInitRunner(DeviceServiceImpl deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        deviceService.getAllHistoryDetailData();
    }

    private void init() {
    }
}
