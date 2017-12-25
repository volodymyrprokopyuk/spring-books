package org.vld.books.repository

import org.springframework.stereotype.Repository
import org.vld.books.domain.Book

interface BookRepository {
    fun findAll(): List<Book>
    fun findById(id: String): Book?
    fun save(book: Book): Book
    fun deleteById(id: String)
}

@Repository
class BookRepositoryImpl(private val books: MutableList<Book>) : BookRepository {

    private var lastBookId: Int = books.size

    override fun findAll(): List<Book> = books

    override fun findById(id: String): Book? = books.firstOrNull { it.id == id }

    override fun save(book: Book): Book {
        val bookIndex: Int = books.indexOf(book)
        if (bookIndex == -1) {
            val newBook = book.copy(id = (++lastBookId).toString())
            books.add(newBook)
            return newBook
        } else {
            books.add(bookIndex, book)
            return book
        }
    }

    override fun deleteById(id: String) {
        books.removeIf { it.id == id }
    }
}
