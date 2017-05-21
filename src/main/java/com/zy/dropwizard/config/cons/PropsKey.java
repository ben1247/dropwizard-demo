package com.zy.dropwizard.config.cons;

/**
 * Component:
 * Description:
 * Date: 17/5/15
 *
 * @author yue.zhang
 */
public enum PropsKey {

    // 数据库
    DB_DRIVER_CLASS(prefixKey() + "database.driverClass"),
    DB_URL(prefixKey() + "database.url"),
    DB_USER(prefixKey() + "database.user"),
    DB_PASSWORD(prefixKey() + "database.password"),

    // migration
    MIGRATION_PROFILE(prefixKey() + "migration.profile");

    private final String value;

    PropsKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static String prefixKey(){
        return "zy.dropwizard.";
    }
}
