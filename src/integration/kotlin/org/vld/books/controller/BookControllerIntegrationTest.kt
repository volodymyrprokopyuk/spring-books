package org.vld.books.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.vld.books.configuration.BookWebMvcConfiguration
import org.vld.books.domain.Book

/*
#!/usr/bin/env bash

set -eu

access_token=$(curl -s -X POST 'http://localhost:8080/spring-books-1.0.0/oauth/token?grant_type=password&username=bill&password=abc123' \
    -H 'Accept: application/json;charset=UTF-8' \
    -u 'trusted-client:secret' | jq -r '.access_token')

echo $access_token

curl -v -X GET "http://localhost:8080/spring-books-1.0.0/books/?access_token=$access_token" \
    -H 'Accept: application/json;charset=UTF-8' \
    -H "Authorization: Bearer $access_token"
*/
@RunWith(SpringRunner::class)
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookWebMvcConfiguration::class])
@WebAppConfiguration
class BookControllerIntegrationTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    private val jsonMapper: ObjectMapper = jacksonObjectMapper()

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
    fun `Given a new Book when create a new Book then return the created Book Location 201 CREATED`() {
        val newBook = Book(title = "New Book")
        val mvcResult = mockMvc.perform(post("/books").accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated()).andReturn()
        assertThat(mvcResult.response.getHeader("Location")).startsWith("http://localhost/books/")
    }

    @Test
    @DirtiesContext
    fun `Given an existing Book when update the Book then return the updated Book 200 OK`() {
        val book = Book(title = "New Title")
        mockMvc.perform(put("/books/{id}", 1).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
    }

    @Test
    fun `Given a non-existing Book when update the Book then return 404 NOT FOUND`() {
        val book = Book(title = "New Title")
        mockMvc.perform(put("/books/{id}", 0).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonMapper.writeValueAsString(book)))
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

    @Test
    fun `Given a malformed request when create a Book then return 400 BAD REQUEST`() {
        mockMvc.perform(post("/books").accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{invalid request}"))
                .andExpect(status().isBadRequest())
    }

    @Test
    fun `Given an invalid request when create a Book then return 400 BAD REQUEST`() {
        val newBook = Book(id = "", title = "")
        mockMvc.perform(post("/books").accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonMapper.writeValueAsString(newBook)))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `Given a list of Books when find all the Books then return the list of Books 200 OK XML`() {
        mockMvc.perform(get("/books").accept("application/xml;charset=UTF-8"))
                .andExpect(status().isOk()).andDo(print())
    }

    @Test
    fun `Given a Book with id when find the Book by id then return the Book with id 200 OK XML`() {
        mockMvc.perform(get("/books/{id}", 1).accept("application/xml;charset=UTF-8"))
                .andExpect(status().isOk())
    }
}
