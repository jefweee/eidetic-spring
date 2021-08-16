package com.jefweee.eideticspring.googleclient;

import com.jefweee.eideticspring.domain.Book;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface IGoogleBooksApiClient {

    List<Book> getFictionBooks(@Min(1) int numBooksToFetch);
    String getGoogleBookApiKey();
    String getGoogleBookApiBaseUrl();

}
