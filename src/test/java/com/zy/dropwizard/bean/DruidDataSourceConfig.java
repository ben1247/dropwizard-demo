package com.zy.dropwizard.bean;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.zy.dropwizard.config.cons.App;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 17/5/19
 *
 * @author yue.zhang
 */
@Slf4j
@Configuration
public class DruidDataSourceConfig {

    @Bean
    public DataSource druidTestDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/dropwizarddemo?allowMultiQueries=true&useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        // 配置初始化大小、最小、最大
        dataSource.setInitialSize(2);
        dataSource.setMinIdle(2);
        dataSource.setMaxActive(5);

        // 配置获取连接等待超时时间
        dataSource.setMaxWait(60000);
        // 配置一个连接在池中最小生存的时间,单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间,单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(30000);
        // 定时将监控信息Log输出,单位是毫秒
        dataSource.setTimeBetweenLogStatsMillis(300000);

        // 配置监控统计拦截的filters
        List<Filter> filters = new ArrayList<>();
        filters.add(this.logFilter());
        dataSource.setProxyFilters(filters);


        dataSource.init();
        dataSource.setFilters("stat,log4j");
        return dataSource;
    }

    private Log4jFilter logFilter() {
        Log4jFilter log4jFilter = new Log4jFilter();
        log4jFilter.setStatementExecutableSqlLogEnable(true);
        return log4jFilter;
    }

}
