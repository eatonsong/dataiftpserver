package com.viewhigh.ftpserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FtpServerListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(FtpServerListener.class);
    private static final String SERVER_NAME="FTP-SERVER";


    //tomcat容器关闭时调用方法stop ftpServer
    public void contextDestroyed(ServletContextEvent sce) {
        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        MyFtpServer server=(MyFtpServer)ctx.getServletContext().getAttribute(SERVER_NAME);
        server.stop();
        sce.getServletContext().removeAttribute(SERVER_NAME);
    }

    //spring 容器初始化调用方法startFtpServer
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        //spring boot 启动类中创建bean,命名为MyFtp()
        MyFtpServer server = (MyFtpServer) ctx.getBean("MyFtp");
        sce.getServletContext().setAttribute(SERVER_NAME, server);
        try {
            server.initFtp();
            server.start();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("FTP启动失败", e);
        }
    }

}
