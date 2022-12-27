package com.example.boot.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public class CommonUtils {
    public static String getCurrentTime() {
        return LocalDate.now() + " " + LocalTime.now().toString();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        return s.replace("-", "");
    }
}
