package org.vld.books.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.vld.books.domain.Book
import org.vld.books.interceptor.CorrelationIdInterceptor

@Configuration
@ComponentScan(basePackages = ["org.vld.books"])
@EnableWebMvc
open class BookConfig : WebMvcConfigurer {

    @Bean
    open fun books(): MutableList<Book> = mutableListOf(
            Book("1", "Spring REST"),
            Book("2", "Pro Spring 5"),
            Book("3", "Spring 5 Recipes")
    )

    @Bean
    open fun correlationIdInterceptor(): HandlerInterceptor = CorrelationIdInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry?.addInterceptor(correlationIdInterceptor())
    }
}
