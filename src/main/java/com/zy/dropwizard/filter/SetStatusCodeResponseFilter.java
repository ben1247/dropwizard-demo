package com.zy.dropwizard.filter;

import com.google.common.collect.Maps;
import com.google.common.net.HttpHeaders;
import jdk.net.SocketFlow;

import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Component: 设置返回的状态码过滤器
 * Description:
 * Date: 17/5/22
 *
 * @author yue.zhang
 */
@Priority(Priorities.ENTITY_CODER)
public class SetStatusCodeResponseFilter implements ContainerResponseFilter{

    public SetStatusCodeResponseFilter(){
        super();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
       final String method = requestContext.getMethod();

       if (HttpMethod.GET.equalsIgnoreCase(method)){
           checkGet(requestContext,responseContext);
       }else if(HttpMethod.POST.equalsIgnoreCase(method)){
           checkPost(requestContext,responseContext);
       }else if(HttpMethod.PUT.equalsIgnoreCase(method)){
           checkPut(requestContext,responseContext);
       }else if(HttpMethod.DELETE.equalsIgnoreCase(method)){
           checkDelete(requestContext,responseContext);
       }else if("PATCH".equalsIgnoreCase(method)){
           checkPatch(requestContext,responseContext);
       }

    }

    private void checkGet(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext){
        final int status = responseContext.getStatus();

        // 将204统一改成返回200，{} 对象
        if (status == Response.Status.NO_CONTENT.getStatusCode()){
            responseContext.setStatus(Response.Status.OK.getStatusCode());
            responseContext.setEntity(Maps.newHashMap());
            return;
        }

        if(status == Response.Status.OK.getStatusCode()){
            final Object entity = responseContext.getEntity();
            if (entity == null) {
                responseContext.setEntity(Maps.newHashMap());
                return;
            }
        }

        //将range请求改为206.
        final String range = requestContext.getHeaderString(HttpHeaders.RANGE);
        if (range != null && !range.trim().isEmpty()) {
            responseContext.setStatus(Response.Status.PARTIAL_CONTENT.getStatusCode());
        }
    }

    private void checkPost(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        checkNoContent(requestContext,responseContext);
    }

    private void checkPut(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        checkNoContent(requestContext,responseContext);
    }

    private void checkDelete(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        checkNoContent(requestContext,responseContext);
    }

    private void checkPatch(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        checkNoContent(requestContext,responseContext);
    }

    private void checkNoContent(ContainerRequestContext requestContext, ContainerResponseContext responseContext){
        final int status = responseContext.getStatus();
        if(status == Response.Status.OK.getStatusCode()){
            final Object entity = responseContext.getEntity();
            if(entity == null){
                responseContext.setEntity(Maps.newHashMap());
            }
        }
    }

}
