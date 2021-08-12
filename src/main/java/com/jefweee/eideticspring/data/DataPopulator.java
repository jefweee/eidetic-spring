package com.jefweee.eideticspring.data;

import com.jefweee.eideticspring.googleclient.GoogleBooksApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPopulator {

    @Autowired
    GoogleBooksApiClient googleBooksApiClient;

    public void populateBooksFromGoogle(int booksToPopulate) {

        googleBooksApiClient.getFictionBooksFromGoogle(booksToPopulate);
    }
}
