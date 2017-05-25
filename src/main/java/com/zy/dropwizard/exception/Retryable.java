package com.zy.dropwizard.exception;

import java.util.concurrent.TimeUnit;

/**
 * Component: 可重试的
 * Description:
 * Date: 17/5/24
 *
 * @author yue.zhang
 */
public interface Retryable {

    String PROPERTY_NAME = "retryable";

    /**
     * 默认重试间隔时间
     */
    int DEFAULT_RETRY_INTERVAL = (int) TimeUnit.SECONDS.toMillis(1);

    /**
     * 建议重试的间隔时间。
     *
     * @return 为0表示马上重试；为正数表示该数字毫秒后重试；为负数表示不可重试。
     */
    int getRetryInterval();

    default int getMaxRetryTimes(){
        return 1;
    }
}
