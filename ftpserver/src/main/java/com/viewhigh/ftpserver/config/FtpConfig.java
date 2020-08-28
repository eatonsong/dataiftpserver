package com.viewhigh.ftpserver.config;

import com.viewhigh.ftpserver.consts.UsersConst;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public abstract class FtpConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //可以通过os来判断
        String os = System.getProperty("os.name");
        String homedirectory = UsersConst.homedirectory;
        //linux设置
//        registry.addResourceHandler("/ftp/**").addResourceLocations("file:/home/pic/");
        //windows设置
        //第一个方法设置访问路径前缀，第二个方法设置资源路径，既可以指定项目classpath路径，也可以指定其它非项目路径
        registry.addResourceHandler("/**").addResourceLocations("file:"+homedirectory);
    }

}
