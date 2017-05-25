package com.zy.dropwizard.base;

import javax.ws.rs.core.Link;
import java.util.Collection;

/**
 * Component: 参考HATEOAS。
 * 响应中的link元素指明本次响应结束之后，请求方可以发起的下一步的请求列表，是一种建议。
 * Description:
 * Date: 17/5/24
 *
 * @author yue.zhang
 */
public interface LinkFetcher {

    Link getLink(String id);

    Link[] getLinks(Collection<String> ids);

}
