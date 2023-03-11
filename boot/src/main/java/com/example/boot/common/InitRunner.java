package com.example.boot.common;

import com.example.boot.service.impl.DeviceServiceImpl;
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
public class InitRunner implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitRunner.class);
    DeviceServiceImpl deviceService;
    SchedulerUtils schedulerUtils;

    public InitRunner(DeviceServiceImpl deviceService,
                      SchedulerUtils schedulerUtils ) {
        this.deviceService = deviceService;
        this.schedulerUtils = schedulerUtils;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.debug("项目初始化：刷新历史详情缓存");
        deviceService.getAllHistoryDetailData();
        LOGGER.debug("项目初始化：刷新历史数据缓存");
        deviceService.getAllHistoryData();
        LOGGER.debug("项目初始化：刷新设备和详情map缓存");
        deviceService.getDetailAndDeviceMap();
        LOGGER.debug("项目初始化：开始定时任务");
        schedulerUtils.startJob();
    }

    private void init() {
    }
}
