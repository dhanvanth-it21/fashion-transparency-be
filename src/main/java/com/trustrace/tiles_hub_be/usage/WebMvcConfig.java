package com.trustrace.tiles_hub_be.usage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {

    @Autowired
    private ApiUsageTracker apiUsageTracker;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiUsageTracker);
    }
}
