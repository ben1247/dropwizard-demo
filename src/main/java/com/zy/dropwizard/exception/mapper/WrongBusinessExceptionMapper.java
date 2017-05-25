package com.zy.dropwizard.exception.mapper;

import com.zy.dropwizard.base.HttpStatus;
import com.zy.dropwizard.exception.WrongBusinessException;

/**
 * Component:
 * Description:
 * Date: 17/5/25
 *
 * @author yue.zhang
 */
public class WrongBusinessExceptionMapper extends ExceptionMapperTemplate<WrongBusinessException> {

    public static final WrongBusinessExceptionMapper instance = new WrongBusinessExceptionMapper();

    public WrongBusinessExceptionMapper(){
        super();
    }

    @Override
    protected HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
