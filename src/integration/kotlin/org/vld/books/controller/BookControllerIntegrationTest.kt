package org.vld.books.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.vld.books.config.BookConfig
import org.vld.books.domain.Book

@RunWith(SpringRunner::class)
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookConfig::class])
@WebAppConfiguration
class BookControllerIntegrationTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `Given a list of Books when find all the Books then return the list of Books 200 OK`() {
        mockMvc.perform(get("/books").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
    }

    @Test
    fun `Given a Book with id when find the Book by id then return the Book with id 200 OK`() {
        mockMvc.perform(get("/books/{id}", 1).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
    }

    @Test
    fun `Given a Book with a non-existing id when find the Book by id then return 404 NOT FOUND`() {
        mockMvc.perform(get("/books/{id}", 0).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
    }

    @Test
    fun `Given a new Book when create a new Book then return the created Book with id 201 CREATED`() {
        val newBook = Book(title = "New Book")
        mockMvc.perform(post("/books").accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper().writeValueAsString(newBook)))
                .andExpect(status().isCreated())
    }

    @Test
    @DirtiesContext
    fun `Given an existing Book when update the Book then return the updated Book 200 OK`() {
        val book = Book(title = "New Title")
        mockMvc.perform(put("/books/{id}", 1).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
    }

    @Test
    fun `Given a non-existing Book when update the Book then return 404 NOT FOUND`() {
        val book = Book(title = "New Title")
        mockMvc.perform(put("/books/{id}", 0).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper().writeValueAsString(book)))
                .andExpect(status().isNotFound())
    }

    @Test
    @DirtiesContext
    fun `Given an existing Book when delete the Book then return 204 NO CONTENT`() {
        mockMvc.perform(delete("/books/{id}", 1).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent())
    }

    @Test
    fun `Given a non-existing Book when delete the Book then return 204 NO CONTENT`() {
        mockMvc.perform(delete("/books/{id}", 0).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent())
    }
}
