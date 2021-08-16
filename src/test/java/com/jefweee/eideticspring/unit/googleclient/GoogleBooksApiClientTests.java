package com.jefweee.eideticspring.unit.googleclient;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.adapter.GoogleBooksApiAdapter;
import com.jefweee.eideticspring.googleclient.GoogleBooksApiClient;
import com.jefweee.eideticspring.googleclient.adapter.GoogleBooksApiParameters;
import com.jefweee.eideticspring.googleclient.IGoogleBooksApiClient;
import com.jefweee.eideticspring.googleclient.json.GoogleBook;
import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;
import com.jefweee.eideticspring.googleclient.json.VolumeInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GoogleBooksApiClientTests {

    Logger logger = LoggerFactory.getLogger(GoogleBooksApiClientTests.class);

    @Mock
    GoogleBooksApiAdapter mockGoogleBooksApiAdapter;

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 23, 26})
    public void cantFetchMultipleBooks(int numBooksToFetch){
        GoogleBookResponse mockReturnedBookResponse = new GoogleBookResponse();
        mockReturnedBookResponse.setItems(generateGoogleBooks(numBooksToFetch));

        when(mockGoogleBooksApiAdapter.fetchVolumes(Mockito.any(GoogleBooksApiParameters.class))).thenReturn(mockReturnedBookResponse);

        IGoogleBooksApiClient googleBooksApiClient = new GoogleBooksApiClient(mockGoogleBooksApiAdapter,
                "test_api_key",
                "test_api_base_url");

        List<Book> booksFromGoogle = googleBooksApiClient.getFictionBooks(numBooksToFetch);
        assertThat(booksFromGoogle.size(), is(numBooksToFetch));
    }

    private List<GoogleBook> generateGoogleBooks(int numBooksToCreate){
        List<GoogleBook> googleBooks = new ArrayList<>();
        GoogleBook googleBook = new GoogleBook();
        googleBook.setVolumeInfo(new VolumeInfo());
        IntStream.rangeClosed(1, numBooksToCreate).forEach(i -> googleBooks.add(googleBook));
        return googleBooks;
    }
}
