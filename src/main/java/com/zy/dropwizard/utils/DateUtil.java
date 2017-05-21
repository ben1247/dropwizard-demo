package com.zy.dropwizard.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Component:
 * Description:
 * Date: 17/5/13
 *
 * @author yue.zhang
 */
public class DateUtil {

    /**
     * 获取当前时间
     * @return
     */
    public static Timestamp getNow() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

}
