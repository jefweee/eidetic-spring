package com.jefweee.eideticspring.data;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.domain.BookRepository;
import com.jefweee.eideticspring.googleclient.GoogleBooksApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataPopulator {

    GoogleBooksApiClient googleBooksApiClient;
    BookRepository bookRepository;

    @Autowired
    public DataPopulator(GoogleBooksApiClient googleBooksApiClient, BookRepository bookRepository) {
        this.googleBooksApiClient = googleBooksApiClient;
        this.bookRepository = bookRepository;
    }

    public void populateBooksFromGoogle(int booksToPopulate) {
        List<Book> books = googleBooksApiClient.getFictionBooks(booksToPopulate);
        books.stream().forEach(b -> bookRepository.save(b));
    }

}
