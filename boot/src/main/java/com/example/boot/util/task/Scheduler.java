package com.example.boot.util.task;

import com.example.boot.data.DataScrap;
import com.example.boot.entity.vo.HistorysVo;
import com.example.boot.service.impl.DeviceServiceImpl;
import com.example.boot.util.cache.CacheManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimerTask;


import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lishuai
 * @since 2023/2/2
 */
@Component
public class Scheduler extends TimerTask {
    //    @Autowired
    private DataScrap dataScrap;
    //    @Autowired
    private DeviceServiceImpl deviceService;
//}


//public class Test extends TimerTask {

    private String jobName = "";

    public Scheduler(DataScrap dataScrap, DeviceServiceImpl deviceService) {
        super();
        this.dataScrap = dataScrap;
        this.deviceService = deviceService;
    }

//    public Scheduler(String jobName) {
//        super();
//        this.jobName = jobName;
//    }

    @Override
    public void run() {
        Date date = new Date();
        System.out.println("执行任务 ：Date = " + date + ", execute " + jobName);

        long endTime = date.getTime();
        long startTime = endTime - (1000L * 60 * 60 * 24 * 2);
//        long startTime = endTime - (1000L * 60 * 60 * 24 * 365L);

        List<HistorysVo> historysVos = dataScrap.collectHistoryDataToObject(startTime/1000, endTime/1000);
//        1671638400  1671724800
        int index = 0;
        for (HistorysVo historysVo : historysVos) {
            index++;
            dataScrap.dealHistoryVoAndInsertOrUpdate(index, historysVo, false);
        }
        deviceService.refreshCache();
    }

//    private void refreshCache() {
//
//        new CacheManagerImpl().clearAll();
//        deviceService.getAllHistoryData();
//        deviceService.getAllHistoryDetailData();
//        deviceService.getDetailAndDeviceMap();
//    }

    /**
     * YEAR + MONTH + DAY_OF_MONTH
     * YEAR + MONTH + WEEK_OF_MONTH + DAY_OF_WEEK
     * YEAR + MONTH + DAY_OF_WEEK_IN_MONTH + DAY_OF_WEEK
     * YEAR + DAY_OF_YEAR
     * YEAR + DAY_OF_WEEK + WEEK_OF_YEAR
     * <p>
     * 计算从当前时间currentDate开始，满足条件dayOfWeek, hourOfDay,
     * minuteOfHour, secondOfMinite的最近时间
     *
     * @return
     */
    public Calendar getEarliestDate(Calendar currentDate, int dayOfWeek,
                                    int hourOfDay, int minuteOfHour, int secondOfMinite) {
        //计算当前时间的WEEK_OF_YEAR,DAY_OF_WEEK, HOUR_OF_DAY, MINUTE,SECOND等各个字段值
        int currentWeekOfYear = currentDate.get(Calendar.WEEK_OF_YEAR);
        int currentDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
        int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentDate.get(Calendar.MINUTE);
        int currentSecond = currentDate.get(Calendar.SECOND);

        //如果输入条件中的dayOfWeek小于当前日期的dayOfWeek,则WEEK_OF_YEAR需要推迟一周
        boolean weekLater = false;
        if (dayOfWeek < currentDayOfWeek) {
            weekLater = true;
        } else if (dayOfWeek == currentDayOfWeek) {
            //当输入条件与当前日期的dayOfWeek相等时，如果输入条件中的
            //hourOfDay小于当前日期的
            //currentHour，则WEEK_OF_YEAR需要推迟一周
            if (hourOfDay < currentHour) {
                weekLater = true;
            } else if (hourOfDay == currentHour) {
                //当输入条件与当前日期的dayOfWeek, hourOfDay相等时，
                //如果输入条件中的minuteOfHour小于当前日期的
                //currentMinute，则WEEK_OF_YEAR需要推迟一周
                if (minuteOfHour < currentMinute) {
                    weekLater = true;
                } else if (minuteOfHour == currentSecond) {
                    //当输入条件与当前日期的dayOfWeek, hourOfDay，
                    //minuteOfHour相等时，如果输入条件中的
                    //secondOfMinite小于当前日期的currentSecond，
                    //则WEEK_OF_YEAR需要推迟一周
                    if (secondOfMinite < currentSecond) {
                        weekLater = true;
                    }
                }
            }
        }
        if (weekLater) {
            //设置当前日期中的WEEK_OF_YEAR为当前周推迟一周
            currentDate.set(Calendar.WEEK_OF_YEAR, currentWeekOfYear + 1);
        }
        // 设置当前日期中的DAY_OF_WEEK,HOUR_OF_DAY,MINUTE,SECOND为输入条件中的值。
        currentDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        currentDate.set(Calendar.MINUTE, minuteOfHour);
        currentDate.set(Calendar.SECOND, secondOfMinite);
        return currentDate;

    }

//    public static void main(String[] args) {
//        new Scheduler().startJob();
//    }


}