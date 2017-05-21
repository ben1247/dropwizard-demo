package com.zy.dropwizard.resource.request;

import com.zy.dropwizard.utils.page.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * Component:
 * Description:
 * Date: 17/5/21
 *
 * @author yue.zhang
 */
@Setter
@Getter
public class BookSearchRequest extends Page{

    private String name;

}
