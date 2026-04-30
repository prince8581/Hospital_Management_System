package com.hospital.ERP.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expose the "uploads/reports" directory under the "/reports/**" URL
        Path uploadDir = Paths.get("uploads/reports");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/reports/**")
                .addResourceLocations("file:/" + uploadPath + "/");



    }
}
