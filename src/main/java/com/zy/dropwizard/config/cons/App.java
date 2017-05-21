package com.zy.dropwizard.config.cons;

/**
 * Component:
 * Description:
 * Date: 17/5/15
 *
 * @author yue.zhang
 */
public class App {

    public static void setDbUrl(String url){
        PropsUtil.setSys(PropsKey.DB_URL,url);
    }

    public static String getDbUrl(){
        return PropsUtil.getSys(PropsKey.DB_URL);
    }

    public static void setDbDriverClass(String driverClass){
        PropsUtil.setSys(PropsKey.DB_DRIVER_CLASS,driverClass);
    }

    public static String getDbDriverClass(){
        return PropsUtil.getSys(PropsKey.DB_DRIVER_CLASS);
    }

    public static void setDbUser(String user){
        PropsUtil.setSys(PropsKey.DB_USER,user);
    }

    public static String getDbUser(){
        return PropsUtil.getSys(PropsKey.DB_USER);
    }

    public static void setDbPassword(String password){
        PropsUtil.setSys(PropsKey.DB_PASSWORD,password);
    }

    public static String getDbPassword(){
        return PropsUtil.getSys(PropsKey.DB_PASSWORD);
    }

    public static void setMigrationProfile(String profile){
        PropsUtil.setSys(PropsKey.MIGRATION_PROFILE,profile);
    }

    public static String getMigrationProfile(){
        return PropsUtil.getSys(PropsKey.MIGRATION_PROFILE);
    }

}
