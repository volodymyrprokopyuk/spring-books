package org.vld.books.domain

data class Book(val id: String? = null, val title: String)

class BookValidationException(message: String, val errors: List<String>) : Exception(message)

fun Book.validate() {
    val errors = mutableListOf<String>()
    if (id?.isEmpty() ?: false) errors.add("Book.id=$id is empty")
    if (title.isEmpty()) errors.add("Book.title=$title is empty")
    if (errors.isNotEmpty()) throw BookValidationException("Invalid Book", errors)
}
