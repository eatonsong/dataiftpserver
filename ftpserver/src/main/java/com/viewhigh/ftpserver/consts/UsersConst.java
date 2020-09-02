package com.viewhigh.ftpserver.consts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class UsersConst {

    private static final Logger logger = LoggerFactory.getLogger(UsersConst.class);
    public static final String homedirectory;
    public static final String username = "admin";
    public static final String userpassword;
    public static final String ipaddress = "127.0.0.1";
    public static final int port = 3131;

    public static final String ftp = "ftp://";
    public static String ip;

    static {
        logger.info("初始化用户配置..");
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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
