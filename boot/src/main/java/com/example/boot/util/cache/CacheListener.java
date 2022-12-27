package com.example.boot.util.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lishuai
 * @since 2022/11/28
 */
public class CacheListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheListener.class);
    private CacheManagerImpl cacheManagerImpl;

    public CacheListener(CacheManagerImpl cacheManagerImpl) {
        this.cacheManagerImpl = cacheManagerImpl;
    }

    public void startListen() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    for (String key : cacheManagerImpl.getAllKeys()) {
                        if (cacheManagerImpl.isTimeOut(key)) {
                            cacheManagerImpl.clearByKey(key);
                            LOGGER.info(key + "缓存被清除");
                        }
                    }
                }
            }
        }.start();

    }
}
