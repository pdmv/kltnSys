/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.configs;

import com.pdmv.components.StringToClassConverter;
import com.pdmv.components.StringToFacultyConverter;
import com.pdmv.components.StringToMajorConverter;
import java.util.concurrent.Executor;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author phamdominhvuong
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.pdmv.controllers",
    "com.pdmv.repositories",
    "com.pdmv.services",
    "com.pdmv.components",
    "com.pdmv.dto"
})
@EnableAsync
public class WebApplicationContextConfig implements WebMvcConfigurer {
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver() {
            @Override
            public boolean isMultipart(HttpServletRequest request) {
                return FileUploadBase.isMultipartContent(new ServletRequestContext(request));
            }
        };
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }
    
    @Bean
    public Executor taskExecutor() { 
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("EmailThread-");
        executor.initialize();
        return executor;
    }
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToFacultyConverter());
        registry.addConverter(stringToMajorConverter());
        registry.addConverter(stringToClassConverter()); 
    }

    @Bean
    public StringToFacultyConverter stringToFacultyConverter() {
        return new StringToFacultyConverter();
    }

    @Bean
    public StringToMajorConverter stringToMajorConverter() {
        return new StringToMajorConverter();
    }
    
    @Bean
    public StringToClassConverter stringToClassConverter() {
        return new StringToClassConverter();
    }
    
}
