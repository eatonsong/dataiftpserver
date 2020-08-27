package com.viewhigh.ftpserver.starter;

import com.viewhigh.ftpserver.config.FtpServerListener;
import com.viewhigh.ftpserver.config.MyFtpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @Classname Application
 * @Description 启动类
 * @Date 2020/8/25 2:39 下午
 * @Created by eatonsong
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.viewhigh.ftpserver")
@Configuration
public class Application implements ServletContextInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.addListener(FtpServerListener.class);
    }

    @Bean
    public MyFtpServer MyFtp(){
        return  new MyFtpServer();
    }

}
