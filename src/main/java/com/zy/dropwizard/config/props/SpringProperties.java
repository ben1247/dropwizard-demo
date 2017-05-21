package com.zy.dropwizard.config.props;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Component:
 * Description:
 * Date: 17/5/19
 *
 * @author yue.zhang
 */
@Getter
@Setter
public class SpringProperties {

    @NotEmpty
    private String contextConfigLocation;

}
