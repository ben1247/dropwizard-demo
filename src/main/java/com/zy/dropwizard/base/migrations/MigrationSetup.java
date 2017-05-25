package com.zy.dropwizard.base.migrations;

import com.zy.dropwizard.config.cons.App;
import org.apache.ibatis.datasource.pooled.PooledDataSource;

import javax.sql.DataSource;

/**
 * Component:
 * Description:
 * Date: 17/5/16
 *
 * @author yue.zhang
 */
public interface MigrationSetup {

    default void execute() throws Exception{
        String driver = App.getDbDriverClass();
        String url = App.getDbUrl();
        String user = App.getDbUser();
        String password = App.getDbPassword();
        String profile = App.getMigrationProfile();

        if(null != url && null != driver && null != profile){
            PooledDataSource dataSource = new PooledDataSource(driver,url,user,password);
            dataSource.setDefaultAutoCommit(true);
            try {
                execute(dataSource,profile);
            }finally {
                dataSource.forceCloseAll();
            }
        }
    }

    void execute(DataSource dataSource, String profile) throws Exception;

}
