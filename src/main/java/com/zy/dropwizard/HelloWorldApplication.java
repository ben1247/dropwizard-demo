package com.zy.dropwizard;

import com.alibaba.druid.support.http.StatViewServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.dropwizard.config.HelloWorldConfiguration;
import com.zy.dropwizard.config.cons.App;
import com.zy.dropwizard.config.props.DatabaseProperties;
import com.zy.dropwizard.config.props.MigrationProperties;
import com.zy.dropwizard.filter.SetStatusCodeResponseFilter;
import com.zy.dropwizard.health.TemplateHealthCheck;
import com.zy.dropwizard.resource.TemplateResource;
import com.zy.dropwizard.utils.ClasspathOrFileConfigurationSourceProvider;
import com.zy.dropwizard.utils.JsonUtil;
import com.zy.dropwizard.utils.migrations.MigrationSetupFactory;
import io.dropwizard.Application;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Component:
 * Description:
 * Date: 17/5/13
 *
 * @author yue.zhang
 */
@Slf4j
public class HelloWorldApplication extends Application<HelloWorldConfiguration>{

    public static void main(String [] args) throws Exception {
        String ymlPath = "helloWorldApplication.yml";
        if(args != null && args.length > 0) {
            ymlPath = args[0];
        }
        args = new String[]{"server", ymlPath};

        new HelloWorldApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        super.initialize(bootstrap);

        // 用来加载classpath里的文件（xxx.yml）
        bootstrap.setConfigurationSourceProvider(new ClasspathOrFileConfigurationSourceProvider());

        // 设置ObjectMapper属性
        setupObjectMapper(bootstrap.getObjectMapper());
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {
        setupApp(configuration);
        executeMigration();
        setupHealthCheck(configuration,environment);
        setupResource(configuration,environment);
        setupFilter(environment);
        setupServletEnvironment(configuration,environment.servlets());
        // 开启文件上传组件
        environment.jersey().register(MultiPartFeature.class);

    }

    private void executeMigration(){
        try {
            MigrationSetupFactory.defaultMigrationSetup().execute();
        }catch (Exception e){
            if (! (e instanceof RuntimeException)){
                throw new RuntimeException();
            }
            throw (RuntimeException)e;
        }
    }

    private void setupResource(final HelloWorldConfiguration configuration,final Environment environment){
        environment.jersey().register(new TemplateResource(configuration.getTemplate(),configuration.getDefaultName()));
        environment.jersey().packages("com.zy.dropwizard");
    }

    /**
     * 注册健康检查
     * 访问地址：http://127.0.0.1:8081/dropwizard-demo-admin/healthcheck
     * @param configuration
     * @param environment
     */
    private void setupHealthCheck(final HelloWorldConfiguration configuration, final Environment environment){
        environment.healthChecks().register("template",new TemplateHealthCheck(configuration.getTemplate()));
    }

    private void setupFilter(final Environment environment){
//        environment.jersey().register(new HandleEntityResponseFilter());
//        environment.jersey().register(new HandleEntityRequestFilter());
        environment.jersey().register(new SetStatusCodeResponseFilter());
    }

    private void setupApp(final HelloWorldConfiguration configuration){
        // 数据库配置
        DatabaseProperties database = configuration.getDatabase();
        App.setDbDriverClass(database.getDriverClass());
        App.setDbUrl(database.getUrl());
        App.setDbUser(database.getUser());
        App.setDbPassword(database.getPassword());

        // migration
        MigrationProperties migration = configuration.getMigration();
        App.setMigrationProfile(migration.getProfile());
    }

    private void setupObjectMapper(final ObjectMapper mapper){
        JsonUtil.setMapper(mapper);
    }

    private void setupServletEnvironment(final HelloWorldConfiguration configuration ,final ServletEnvironment servlet){
        // spring 配置
        servlet.setInitParameter("contextClass", "org.springframework.web.context.support.XmlWebApplicationContext");
        servlet.setInitParameter("contextConfigLocation", configuration.getSpring().getContextConfigLocation());
        servlet.addServletListeners(new ContextLoaderListener(), new RequestContextListener());

        // Druid的内置监控页面
        servlet.addServlet("DruidStatView",StatViewServlet.class).addMapping("/druid/*");
    }
}
