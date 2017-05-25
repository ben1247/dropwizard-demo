package com.zy.dropwizard.exception.mapper;

import com.zy.dropwizard.base.HttpStatus;
import com.zy.dropwizard.exception.UnknownException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;

/**
 * Component: 未知异常
 * Description:
 * Date: 17/5/23
 *
 * @author yue.zhang
 */
@Slf4j
public class UnknownExceptionMapper extends ExceptionMapperTemplate<UnknownException> {

    public static final UnknownExceptionMapper instance = new UnknownExceptionMapper();

    public UnknownExceptionMapper(){
        super();
    }

    @Override
    protected HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    protected void toLog(UnknownException exception, Response res) {
        super.toLog(exception, res);
        log.error("异常堆栈如下", exception);
    }
}
