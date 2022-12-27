package com.example.boot.util;

import com.example.boot.entity.dto.HistoryDetailData;
import com.example.boot.util.cache.CacheListener;
import com.example.boot.util.cache.CacheManagerImpl;
import com.example.boot.util.cache.EntityCache;

import java.util.List;

/**
 * @author lishuai
 * @since 2022/11/28
 */
public class CacheUtils {
    public static CacheManagerImpl cacheManagerImpl;

    static {
        cacheManagerImpl = new CacheManagerImpl();
        CacheListener cacheListener = new CacheListener(cacheManagerImpl);
        cacheListener.startListen();
    }


}
