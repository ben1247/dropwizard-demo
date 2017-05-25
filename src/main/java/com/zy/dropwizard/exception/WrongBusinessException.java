package com.zy.dropwizard.exception;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 17/5/25
 *
 * @author yue.zhang
 */
public class WrongBusinessException extends BaseException implements Problem{

    private static final String DEFAULT_ERROR_CODE = "wrong_business_exception";

    private String errorCode;
    private String errorMsg;

    public WrongBusinessException(String errorCode , String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public WrongBusinessException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = message;
    }

    public WrongBusinessException(String message, Collection<String> nextStepIds) {
        super(message, nextStepIds);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = message;
    }

    public WrongBusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = message;
    }

    public WrongBusinessException(String message, Throwable cause, Collection<String> nextStepIds) {
        super(message, cause, nextStepIds);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = message;
    }

    @Override
    public String errorCode() {
        return errorCode;
    }

    @Override
    public String errorMsg() {
        return errorMsg;
    }
}
