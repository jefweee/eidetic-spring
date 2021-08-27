package com.jefweee.eideticspring.googleclient;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.exception.FailedToFetchBooksFromGoogleException;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface IGoogleBooksApiClient {

    List<Book> getFictionBooks(@Min(1) int numBooksToFetch) throws FailedToFetchBooksFromGoogleException;
    String getGoogleBookApiKey();
    String getGoogleBookApiBaseUrl();

}
