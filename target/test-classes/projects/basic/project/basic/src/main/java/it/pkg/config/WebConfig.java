package it.pkg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    public static final String PREFIX_URL = "/**";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggerInterceptor()).addPathPatterns(PREFIX_URL);
//        registry.addInterceptor(new HeaderInterceptor()).addPathPatterns(PREFIX_URL);
    }

//    @Bean
//    public StrictHttpFirewall httpFirewall() {
//        var firewall = new StrictHttpFirewall();
//        firewall.setAllowedHeaderNames(header -> true);
//        firewall.setAllowedHeaderValues(header -> true);
//        firewall.setAllowedParameterNames(parameter -> true);
//        firewall.setAllowUrlEncodedSlash(true);
//        firewall.setAllowUrlEncodedPercent(true);
//        firewall.setAllowUrlEncodedPeriod(true);
//        firewall.setAllowBackSlash(true);
//        firewall.setAllowSemicolon(true);
//        return firewall;
//    }
}
