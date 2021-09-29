package com.handsomedong.dynamic.datasource.annotation;

import java.lang.annotation.*;

/**
 * Created by HandsomeDong on 2021/9/30 0:17
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    /**
     * 数据源名称
     * @return 你想要切换的数据源
     */
    String value();
}
