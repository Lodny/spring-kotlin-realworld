package com.lodny.springkotlinrealworld.common

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
//@EnableWebMvc
class WebMvcConfig(val jwtInterceptor: JwtInterceptor): WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        println("> WebMvcConfig : addInterceptors()")
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
    }
}