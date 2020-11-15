package com.df.tank.config;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置类最好是使用的时候才进行加载
 * 且内存中最好只有一个配置类的的信息
 * 因此 建议使用 懒加载单例模式
 */
public class TankWarConfig {

    private TankWarConfig(){}
    private static volatile Properties properties = null;



    public static Properties getInstance(){
        if(properties==null){
            synchronized (TankWarConfig.class){
                if(properties==null){
                    properties = new Properties();
                    try {
                        properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return properties;
    }

    public static String getProperty(String key){
        return getInstance().getProperty(key);
    }

    public static void setProperty(String key,String obj){
         getInstance().setProperty(key,obj);
    }
}
