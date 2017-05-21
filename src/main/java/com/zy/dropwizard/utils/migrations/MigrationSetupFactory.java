package com.zy.dropwizard.utils.migrations;

/**
 * Component: migration启动工厂类
 * Description:
 * Date: 17/5/16
 *
 * @author yue.zhang
 */
public class MigrationSetupFactory {

    private static final MigrationSetup DEFAULT_INSTANCE = new DefaultMigrationSetup();

    public static MigrationSetup defaultMigrationSetup(){
        return DEFAULT_INSTANCE;
    }

}
