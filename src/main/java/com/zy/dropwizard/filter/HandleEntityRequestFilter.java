package com.zy.dropwizard.filter;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Component:
 * Description:
 * Date: 17/5/19
 *
 * @author yue.zhang
 */
@Provider
@Slf4j
public class HandleEntityRequestFilter implements ContainerRequestFilter{

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        log.info("HandleEntityRequestFilter method: {}",method);
    }
}
