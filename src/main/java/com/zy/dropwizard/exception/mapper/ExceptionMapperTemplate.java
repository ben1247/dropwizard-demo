package com.zy.dropwizard.exception.mapper;

import com.google.common.collect.Maps;
import com.zy.dropwizard.base.HttpStatus;
import com.zy.dropwizard.base.LinkFetcher;
import com.zy.dropwizard.exception.BaseException;
import com.zy.dropwizard.exception.Problem;
import com.zy.dropwizard.exception.Retryable;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Collection;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 17/5/24
 *
 * @author yue.zhang
 */
@Slf4j
public abstract class ExceptionMapperTemplate<T extends BaseException> implements ExceptionMapper<T> {

    private LinkFetcher linkFetcher;

    public ExceptionMapperTemplate(){
        super();
    }

    @Override
    public Response toResponse(T exception) {
        final Response.ResponseBuilder builder = Response.status(status()) // status
                .entity(buildResponseEntity(exception)) // body
                .type(MediaType.APPLICATION_JSON_TYPE);
        if(linkFetcher != null){
            final Collection<String> nextStepIds = exception.getNextStepIds();
            if(nextStepIds != null && !nextStepIds.isEmpty()){
                final Link[] links = linkFetcher.getLinks(nextStepIds);
                if (links != null && links.length > 0){
                    builder.links(links);
                }
            }
        }
        final Response res = builder.build();
        toLog(exception,res);
        return res;
    }

    protected void toLog(final T exception , final Response res){
        log.error("捕获异常：" + exception.getClass()
                + "(id=" + exception.getId() + ",message=" + exception.getMessage() + ")，"
                + "将返回响应：" + res.getStatus() + ' ' + res.getEntity());
    }

    protected abstract HttpStatus status();

    protected Object buildResponseEntity(final T exception){
        final Map<String,Object> map = Maps.newHashMap();
        map.put("message",exception.getMessage());
        if(exception instanceof Problem){
            final Problem problem = (Problem) exception;
            map.put("errorCode",problem.errorCode());
            map.put("message",problem.errorMsg());
        }
        if(exception instanceof Retryable){
            final Retryable retryable = (Retryable)exception;
            map.put("retryInterval",retryable.getRetryInterval());
        }
        return map;
    }

    public LinkFetcher getLinkFetcher() {
        return linkFetcher;
    }

    public void setLinkFetcher(LinkFetcher linkFetcher) {
        this.linkFetcher = linkFetcher;
    }

}
