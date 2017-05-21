package com.zy.dropwizard.config;

import com.zy.dropwizard.config.props.DatabaseProperties;
import com.zy.dropwizard.config.props.MigrationProperties;
import com.zy.dropwizard.config.props.SpringProperties;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Component:
 * Description:
 * Date: 17/5/13
 *
 * @author yue.zhang
 */
@Getter
@Setter
public class HelloWorldConfiguration extends Configuration {

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @NotNull
    private DatabaseProperties database;

    @NotNull
    private MigrationProperties migration;

    @NotNull
    private SpringProperties spring;


}
