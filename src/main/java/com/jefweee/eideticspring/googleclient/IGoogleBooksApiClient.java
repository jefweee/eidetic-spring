package com.jefweee.eideticspring.googleclient;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.json.GoogleBook;
import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;

import javax.validation.constraints.Min;
import java.util.List;

public interface IGoogleBooksApiClient {

    List<Book> getFictionBooks(@Min(1) int maxResults);
    List<GoogleBook> getFictionBooksFromGoogle(@Min(1) int numBooksToFetch);
    String getGoogleBookApiKey();
    String getGoogleBookApiBaseUrl();

}
