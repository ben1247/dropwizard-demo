package com.zy.dropwizard.exception;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;

/**
 * Component:
 * Description:
 * Date: 17/5/23
 *
 * @author yue.zhang
 */
@Slf4j
public abstract class BaseException extends RuntimeException{

    /**
     * 下一步可以重试
     */
    public static final Collection<String> NEXT_STEP_IDS_OF_RETRY = Collections.singleton("self");

    /**
     * 下一步可以报告问题
     */
    public static final Collection<String> NEXT_STEP_IDS_OF_REPORT_PROBLEM = Collections.singleton("reportProblem");

    /**
     * 下一步可以重试或者报告问题
     */
    public static final Collection<String> NEXT_STEP_IDS_OF_RETRY_OR_REPORT_PROBLEM = Sets.newHashSet("self","reportProblem");

    private final String id;

    private final Collection<String> nextStepIds;

    public BaseException(final String message){
        super(message);
        this.id = buildId();
        this.nextStepIds = NEXT_STEP_IDS_OF_RETRY_OR_REPORT_PROBLEM;
    }

    public BaseException(final String message , final Collection<String> nextStepIds){
        super(message);
        this.id = buildId();
        this.nextStepIds = Collections.unmodifiableCollection(nextStepIds);
    }

    public BaseException(final String message , final Throwable cause){
        super(message,cause);
        if (cause instanceof BaseException){
            final BaseException baseException = (BaseException) cause;
            this.id = baseException.getId();
        }else{
            this.id = buildId();
        }
        this.nextStepIds = Collections.emptyList();
    }

    public BaseException(final String message , final Throwable cause , final Collection<String> nextStepIds){
        super(message,cause);
        if (cause instanceof BaseException){
            final BaseException baseException = (BaseException) cause;
            this.id = baseException.getId();
        }else{
            this.id = buildId();
        }
        this.nextStepIds = Collections.unmodifiableCollection(nextStepIds);
    }

    private String buildId(){
        final Thread thread = Thread.currentThread();
        try {
            final InetAddress inetAddress = InetAddress.getLocalHost();
            return thread.getId() + "_" + System.currentTimeMillis() + "_" + inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("BaseException-buildId error , " + e.getMessage(),e);
            return thread.getId() + "_" + System.currentTimeMillis();
        }
    }

    public String getId(){
        return this.id;
    }

    public Collection<String> getNextStepIds() {
        return nextStepIds;
    }
}
