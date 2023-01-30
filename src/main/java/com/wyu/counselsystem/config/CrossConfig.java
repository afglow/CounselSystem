package com.wyu.counselsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author afglow
 * @Date Create in 2023-01-2023/1/20-17:41
 * @Description
 */
//解决跨域请求问题
@Configuration
public class CrossConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","DELETE","PUT","OPTIONS","HEAD")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
