package com.example.boot.common;

import com.example.boot.service.impl.DeviceServiceImpl;
import com.example.boot.util.task.Scheduler;
import com.example.boot.util.task.SchedulerUtils;
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
    SchedulerUtils schedulerUtils;

    public UnilicInitRunner(DeviceServiceImpl deviceService,
                            SchedulerUtils schedulerUtils ) {
        this.deviceService = deviceService;
        this.schedulerUtils = schedulerUtils;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        deviceService.getAllHistoryDetailData();
        deviceService.getAllHistoryData();
        deviceService.getDetailAndDeviceMap();
        schedulerUtils.startJob();
    }

    private void init() {
    }
}
