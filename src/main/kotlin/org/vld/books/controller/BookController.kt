package org.vld.books.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.vld.books.domain.Book
import org.vld.books.service.BookService

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun findAllBooks(): List<Book> = bookService.findAllBooks()

    @GetMapping("/{id}")
    fun findBookById(@PathVariable("id") id: String): Book? = bookService.findBookById(id)

    @PostMapping
    fun createBook(@RequestBody book: Book): Book = bookService.createBook(book)

    @PutMapping
    fun updateBook(@RequestBody book: Book): Book = bookService.updateBook(book)

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable("id") id: String) = bookService.deleteBookById(id)
}
