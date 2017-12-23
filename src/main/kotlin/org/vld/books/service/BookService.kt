package org.vld.books.service

import org.springframework.stereotype.Service
import org.vld.books.domain.Book
import org.vld.books.repository.BookRepository

interface BookService {
    fun findAllBooks(): List<Book>
    fun findBookById(id: String): Book?
    fun createBook(book: Book): Book
    fun updateBook(book: Book): Book
    fun deleteBookById(id: String)
}

@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {

    override fun findAllBooks(): List<Book> = bookRepository.findAll()

    override fun findBookById(id: String): Book? = bookRepository.findById(id)

    override fun createBook(book: Book): Book = bookRepository.save(book)

    override fun updateBook(book: Book): Book = bookRepository.save(book)

    override fun deleteBookById(id: String) = bookRepository.deleteById(id)
}
