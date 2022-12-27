package com.example.boot.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lishuai
 * @since 2022/11/26
 */
public class JsonUtils {
    public static String writeAsJson(Object o) {
        return JSONObject.toJSONString(o);
    }

    public static <T> T parseObject(String str, Class<T> clazz) {
        return JSONObject.parseObject(str, clazz);
    }

}
