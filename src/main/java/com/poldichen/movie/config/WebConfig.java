package com.poldichen.movie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态文件服务：把后端 8080 端口的 /pictures/** 映射到本地磁盘 D:/@git/movie/pictures/，
 * 替代原项目设计中由 nginx/IIS 在 80 端口提供的图片访问服务.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String PICTURE_DIR = "file:D:/@git/movie/pictures/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pictures/**")
                .addResourceLocations(PICTURE_DIR);
    }
}
