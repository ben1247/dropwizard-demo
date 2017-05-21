package com.zy.dropwizard.config.props;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Component:
 * Description:
 * Date: 17/5/15
 *
 * @author yue.zhang
 */
@Getter
@Setter
public class DatabaseProperties {

    @NotEmpty
    private String driverClass;

    @NotEmpty
    private String url;

    private String user;

    private String password;

    @Min(1)
    @Max(100)
    private int minSize;

    @Min(1)
    @Max(100)
    private int maxSize;

}
