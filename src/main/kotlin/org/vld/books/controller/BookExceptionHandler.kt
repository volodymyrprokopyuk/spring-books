package org.vld.books.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.vld.books.domain.BookValidationException

@ControllerAdvice
class BookExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BookValidationException::class)
    fun handleBookValidationException(ex: BookValidationException): ResponseEntity<List<String>> =
            ResponseEntity(ex.errors, HttpStatus.BAD_REQUEST)
}
