package com.jefweee.eideticspring.data;

import com.jefweee.eideticspring.domain.exception.FailedToSaveBooksException;
import com.jefweee.eideticspring.googleclient.exception.FailedToFetchBooksFromGoogleException;
import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.domain.BookRepository;
import com.jefweee.eideticspring.googleclient.GoogleBooksApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataPopulator {

    @Value("${failedtosavebooksexception.statuscode:errorcode}")
    String failedToSaveBooksExceptionStatusCode;

    GoogleBooksApiClient googleBooksApiClient;
    BookRepository bookRepository;

    @Autowired
    public DataPopulator(GoogleBooksApiClient googleBooksApiClient, BookRepository bookRepository) {
        this.googleBooksApiClient = googleBooksApiClient;
        this.bookRepository = bookRepository;
    }

    public void populateBooksFromGoogle(int booksToPopulate) throws FailedToFetchBooksFromGoogleException, FailedToSaveBooksException {
        List<Book> books = googleBooksApiClient.getFictionBooks(booksToPopulate);

        try{
            books.stream().forEach(b -> bookRepository.save(b));
        }
        catch(Exception e){
            throw new FailedToSaveBooksException();
        }

    }

}
