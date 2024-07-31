package com.ite.authservice.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig{

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/admin/authenticate")
//                .allowedOrigins("http://192.168.1.91:8092")
//                .allowedMethods("GET", "POST")
//                .allowCredentials(true);
//    }