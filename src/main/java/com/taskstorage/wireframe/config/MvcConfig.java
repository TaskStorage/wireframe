package com.taskstorage.wireframe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //Для раздачи файлов
    @Value("${upload.path}")
    private String uploadPath;

    //Стандартный rest-контроллер спринга
    @Bean
    public RestTemplate getRestTemplate (){
        return new RestTemplate();
    }
    //Стандартный конфиг - https://spring.io/guides/gs/securing-web/
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Для раздачи файлов - все запросы по пути attachment будут лететь в uploadPath
        registry.addResourceHandler("/attachment/**")
                .addResourceLocations("file://" + uploadPath + "/");
        //Для сторонних элементов
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}