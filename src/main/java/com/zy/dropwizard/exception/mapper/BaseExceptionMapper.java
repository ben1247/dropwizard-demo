package com.zy.dropwizard.exception.mapper;

import com.zy.dropwizard.exception.BaseException;
import com.zy.dropwizard.exception.UnknownException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 17/5/23
 *
 * @author yue.zhang
 */
@Slf4j
public class BaseExceptionMapper implements ExceptionMapper<BaseException> {

    public static final BaseExceptionMapper instance = new BaseExceptionMapper();

    private final List<ExceptionMapper<? extends BaseException>> exceptionMappers = Arrays.asList(
            UnknownExceptionMapper.instance,WrongBusinessExceptionMapper.instance
    );

    public BaseExceptionMapper(){
        super();
    }

    @Override
    public Response toResponse(BaseException exception) {

        @SuppressWarnings("unchecked")
        final ExceptionMapper<BaseException> exceptionMapper = (ExceptionMapper<BaseException>)findExceptionMapper(exception.getClass());

        if (exceptionMapper == null){
            log.info("捕获未注册的ShuyunException：" + exception.getClass() + '(' + exception.getId() + ')' + "，将按UnknownException处理。");
            return UnknownExceptionMapper.instance.toResponse(new UnknownException(exception));
        }else {
            log.info("捕获" + exception.getClass() + '(' + exception.getId() + ")，" + exceptionMapper + "将处理。");
            return exceptionMapper.toResponse(exception);
        }

    }

    private ExceptionMapper<? extends BaseException> findExceptionMapper(final Class<? extends BaseException> clazz){
        for (final ExceptionMapper<? extends BaseException> exceptionMapper : exceptionMappers){
            final Class<?> actualExceptionClass = getActualClassForSubclass(exceptionMapper.getClass());
            if(actualExceptionClass.isAssignableFrom(clazz)){
                return exceptionMapper;
            }
        }
        return null;
    }

    private static Class<?> getActualClassForSubclass(final Class<?> clazz) {
        final Type type = clazz.getGenericSuperclass();
        final ParameterizedType pt = (ParameterizedType) type;
        final Type[] ts = pt.getActualTypeArguments();
        return (Class<?>) ts[0];
    }
}
