package org.vld.books.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.vld.books.interceptor.CorrelationIdInterceptor

@Configuration
@ComponentScan(basePackages = ["org.vld.books"])
@EnableWebMvc
open class BookWebMvcConfiguration : WebMvcConfigurer {

    @Bean
    open fun correlationIdInterceptor(): HandlerInterceptor = CorrelationIdInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry?.addInterceptor(correlationIdInterceptor())
    }
}
