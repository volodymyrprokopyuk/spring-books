package org.vld.books.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.vld.books.domain.Book

@Configuration
open class BookRepositoryConfiguration {

    @Bean
    open fun books(): MutableList<Book> = mutableListOf(
            Book("1", "Spring REST"),
            Book("2", "Pro Spring 5"),
            Book("3", "Spring 5 Recipes")
    )
}
