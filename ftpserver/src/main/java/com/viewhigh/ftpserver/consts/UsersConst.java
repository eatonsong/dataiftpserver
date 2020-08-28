package com.viewhigh.ftpserver.consts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class UsersConst {

    private static final Logger logger = LoggerFactory.getLogger(UsersConst.class);
    public static String homedirectory;
    public static String username = "admin";
    public static String userpassword;
    public static String ipaddress = "127.0.0.1";
    public static int port = 3131;

    static {
        logger.info("初始化用户配置..");
        ClassPathResource resource = new ClassPathResource("config/users.properties");
        Properties properties = null;
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        homedirectory = properties.getProperty("ftpserver.user.admin.homedirectory");
        userpassword = properties.getProperty("ftpserver.user.admin.userpassword");

    }


}
