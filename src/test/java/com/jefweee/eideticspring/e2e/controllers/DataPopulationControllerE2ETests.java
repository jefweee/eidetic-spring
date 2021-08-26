package com.jefweee.eideticspring.e2e.controllers;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.domain.BookRepository;
import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataPopulationControllerE2ETests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;


    @BeforeEach
    @AfterEach
    public void clearDownDatabase(){
        bookRepository.deleteAll();
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/datapopulator/hello",
                String.class)).contains("Hello! I am a data populator");
    }

    @Test
    public void dataStatusShouldReturnCountOfBooksInStorage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/datapopulator/datastatus",
                String.class)).contains("There are currently 0 books in our storage");
    }

    @Test
    public void populateShouldAdd5BooksToDatabaseAndReturnOkStatus() throws Exception {
        ResponseEntity<Object> addBooksResponse = this.restTemplate.getForEntity("http://localhost:" + port + "/datapopulator/populate/5", Object.class);
        List<Book> booksInStorage = bookRepository.findAll();

        assertThat(addBooksResponse.getStatusCode().is2xxSuccessful());
        assertThat(addBooksResponse.getBody().toString().contains("5 books have been added to our storage"));
        assertThat(booksInStorage).hasSize(5);
    }
}