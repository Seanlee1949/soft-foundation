package com.example.boot.util.task;

import com.example.boot.data.DataScrap;
import com.example.boot.service.impl.DeviceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lishuai
 * @since 2023/2/2
 */
@Component
public class SchedulerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerUtils.class);
    @Autowired
    private Scheduler scheduler;

    public void startJob() {

//        Scheduler test = new Scheduler(dataScrap);
        //获取当前时间
        Calendar currentDate = Calendar.getInstance();
        long currentDateLong = currentDate.getTime().getTime();

        // 先执行一次
        LOGGER.debug("启动执行一次");
        scheduler.run();

        System.out.println("Current Date = " + currentDate.getTime().toString());
        //计算满足条件的最近一次执行时间
        currentDate.add(Calendar.DAY_OF_YEAR, 1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
//        currentDate.add(Calendar.SECOND, 11);
//        Calendar earliestDate = test
//                .getEarliestDate(currentDate, 3, 16, 38, 10);
        long earliestDateLong = currentDate.getTime().getTime();
        System.out.println("Earliest Date = "
                + currentDate.getTime().toString());
        //计算从当前时间到最近一次执行时间的时间间隔
        long delay = earliestDateLong - currentDateLong;
        //计算执行周期为一天
        long period = 24 * 60 * 60 * 1000;
//        long period = 60 * 1000;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        //从现在开始delay毫秒之后，每隔一天执行一次job1
        service.scheduleAtFixedRate(scheduler, delay, period,
                TimeUnit.MILLISECONDS);
    }
}
