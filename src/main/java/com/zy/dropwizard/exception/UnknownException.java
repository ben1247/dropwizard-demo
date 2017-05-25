package com.zy.dropwizard.exception;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 17/5/23
 *
 * @author yue.zhang
 */
public class UnknownException extends BaseException{

    public static final String DEFAULT_MSG = "发生未知错误，请稍后重试。";

    public UnknownException(){
        super(DEFAULT_MSG);
    }

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Collection<String> nextStepIds) {
        super(message, nextStepIds);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownException(String message, Throwable cause, Collection<String> nextStepIds) {
        super(message, cause, nextStepIds);
    }

    public UnknownException(Throwable cause){
        super(DEFAULT_MSG,cause);
    }
}
