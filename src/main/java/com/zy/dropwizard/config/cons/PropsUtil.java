package com.zy.dropwizard.config.cons;

/**
 * Component:
 * Description:
 * Date: 17/5/15
 *
 * @author yue.zhang
 */
public class PropsUtil {

    public static String getSys(String key){
        return System.getProperty(key);
    }

    public static String getSys(PropsKey key){
        return getSys(key.getValue());
    }

    public static void setSys(String key , String value){
        if(null != value){
            System.setProperty(key,value);
        }
    }

    public static void setSys(PropsKey key, String value){
        setSys(key.getValue(),value);
    }

}
