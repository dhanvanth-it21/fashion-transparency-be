package com.trustrace.fashion_transparency_be.usage;

import com.trustrace.fashion_transparency_be.billing.ApiUsageLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {

    @Autowired
    private ApiUsageTracker apiUsageTracker;

    @Autowired
    private ApiUsageLogger apiUsageLogger;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiUsageLogger);
    }
}
