package org.vld.books.repository

import org.vld.books.domain.Book

interface BookRepository {
    fun findAll(): List<Book>
    fun findById(id: String): Book?
    fun save(book: Book): Book
    fun deleteById(id: String): Boolean
}

class BookRepositoryImpl : BookRepository {

    private val books: MutableList<Book> = mutableListOf(
            Book("1", "Spring REST"),
            Book("2", "Pro Spring 5"),
            Book("3", "Spring 5 Recipes")
    )

    private var lastBookId: Int = books.size

    override fun findAll(): List<Book> = books

    override fun findById(id: String): Book? = books.first { it.id == id }

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

    override fun deleteById(id: String) = books.removeIf { it.id == id }
}
