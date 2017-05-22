package com.zy.dropwizard.filter;

import com.zy.dropwizard.model.HandleEntityResponse;
import com.zy.dropwizard.utils.JsonUtil;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Component: 将返回的对象处理成想要的对象
 * Description:
 * Date: 17/5/14
 *
 * @author yue.zhang
 */
@Provider
@Priority(Priorities.ENTITY_CODER)
public class HandleEntityResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        final Object entity = responseContext.getEntity();
        if(entity != null && entity instanceof HandleEntityResponse){
            responseContext.setEntity(JsonUtil.writeValueAsString(entity));
        }
    }
}
