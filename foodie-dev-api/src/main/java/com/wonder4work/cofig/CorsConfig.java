package com.wonder4work.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @since 1.0.0 2020/3/29
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        // 跨域配置基本信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.setAllowCredentials(true); // 凭证 是否发送cookie信息
        corsConfiguration.addAllowedMethod("*"); // 设置允许请求的方式
        corsConfiguration.addAllowedHeader("*"); // 设置允许的header

        // 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfiguration);

        // 返回定义好的source
        return new CorsFilter(corsSource);

    }

}
