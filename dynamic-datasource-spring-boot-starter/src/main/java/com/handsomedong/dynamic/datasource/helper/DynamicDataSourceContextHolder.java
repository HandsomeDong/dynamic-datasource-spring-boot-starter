package com.handsomedong.dynamic.datasource.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * Created by HandsomeDong on 2021/9/30 23:21
 * 切换数据源相关操作的类
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    public static final String DEFAULT_KEY = "default";

    private static final ThreadLocal<String> LOOKUP_KEY_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源key，后面路由的时候要取
     * @param key 数据源key
     */
    public static void setDSKey(String key) {
        if (key == null) {
            return;
        }
        log.info("switch db: {}", key);
        LOOKUP_KEY_HOLDER.set(key);
    }


    /**
     * 获取数据源key
     * @return 数据源key
     */
    public static String getDSKey() {
        final String key = LOOKUP_KEY_HOLDER.get();
        if (!StringUtils.hasText(key)) {
            return DEFAULT_KEY;
        } else {
            return LOOKUP_KEY_HOLDER.get();
        }
    }


    /**
     * 用完清除，免得内存泄露
     */
    public static void clearDSKey() {
        LOOKUP_KEY_HOLDER.remove();
    }
}
