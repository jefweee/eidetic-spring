package com.jefweee.eideticspring.unit.googleclient;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.exception.FailedToFetchBooksFromGoogleException;
import com.jefweee.eideticspring.googleclient.adapter.GoogleBooksApiAdapter;
import com.jefweee.eideticspring.googleclient.GoogleBooksApiClient;
import com.jefweee.eideticspring.googleclient.adapter.GoogleBooksApiParameters;
import com.jefweee.eideticspring.googleclient.IGoogleBooksApiClient;
import com.jefweee.eideticspring.googleclient.json.GoogleBook;
import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;
import com.jefweee.eideticspring.googleclient.json.VolumeInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @Mock
    GoogleBooksApiAdapter mockGoogleBooksApiAdapter;

    @ParameterizedTest
    @ValueSource(ints = {1, 9, 10})
    public void canFetchMultipleBooksInOneBatch(int numBooksToFetch) throws FailedToFetchBooksFromGoogleException {

        GoogleBookResponse mockGoogleBookResponse = new GoogleBookResponse();
        mockGoogleBookResponse.setItems(generateGoogleBooks(numBooksToFetch));
        ResponseEntity<GoogleBookResponse> mockResponse = new ResponseEntity<>(mockGoogleBookResponse, HttpStatus.OK);

        when(mockGoogleBooksApiAdapter.fetchVolumes(Mockito.any(GoogleBooksApiParameters.class))).thenReturn(mockResponse);

        IGoogleBooksApiClient googleBooksApiClient = new GoogleBooksApiClient(mockGoogleBooksApiAdapter,
                "test_api_key",
                "test_api_base_url");

        List<Book> booksFromGoogle = googleBooksApiClient.getFictionBooks(numBooksToFetch);
        assertThat(booksFromGoogle.size(), is(numBooksToFetch));
    }

    @Test
    public void canFetchMultipleBooksInMultipleBatchesWithNoRemainder() throws FailedToFetchBooksFromGoogleException {

        int numBooksToFetch = 20;

        GoogleBookResponse mockGoogleBookResponseFirstBatch = new GoogleBookResponse();
        mockGoogleBookResponseFirstBatch.setItems(generateGoogleBooks(10));

        GoogleBookResponse mockGoogleBookResponseSecondBatch = new GoogleBookResponse();
        mockGoogleBookResponseSecondBatch.setItems(generateGoogleBooks(10));

        ResponseEntity<GoogleBookResponse> mockResponseFirstBatch = new ResponseEntity<>(mockGoogleBookResponseFirstBatch, HttpStatus.OK);
        ResponseEntity<GoogleBookResponse> mockResponseSecondBatch = new ResponseEntity<>(mockGoogleBookResponseSecondBatch, HttpStatus.OK);

        when(mockGoogleBooksApiAdapter.fetchVolumes(Mockito.any(GoogleBooksApiParameters.class)))
                .thenReturn(mockResponseFirstBatch, mockResponseSecondBatch);

        IGoogleBooksApiClient googleBooksApiClient = new GoogleBooksApiClient(mockGoogleBooksApiAdapter,
                "test_api_key",
                "test_api_base_url");

        List<Book> booksFromGoogle = googleBooksApiClient.getFictionBooks(numBooksToFetch);
        
        assertThat(booksFromGoogle.size(), is(numBooksToFetch));
    }

    @Test
    public void canFetchMultipleBooksInSingleBatchWithRemainder() throws FailedToFetchBooksFromGoogleException {

        int numBooksToFetch = 11;

        GoogleBookResponse mockGoogleBookResponseFirstBatch = new GoogleBookResponse();
        mockGoogleBookResponseFirstBatch.setItems(generateGoogleBooks(10));

        GoogleBookResponse mockGoogleBookResponseRemainder = new GoogleBookResponse();
        mockGoogleBookResponseRemainder.setItems(generateGoogleBooks(1));

        ResponseEntity<GoogleBookResponse> mockResponseFirstBatch = new ResponseEntity<>(mockGoogleBookResponseFirstBatch, HttpStatus.OK);
        ResponseEntity<GoogleBookResponse> mockResponseRemainder = new ResponseEntity<>(mockGoogleBookResponseRemainder, HttpStatus.OK);

        when(mockGoogleBooksApiAdapter.fetchVolumes(Mockito.any(GoogleBooksApiParameters.class)))
                .thenReturn(mockResponseFirstBatch, mockResponseRemainder);

        IGoogleBooksApiClient googleBooksApiClient = new GoogleBooksApiClient(mockGoogleBooksApiAdapter,
                "test_api_key",
                "test_api_base_url");

        List<Book> booksFromGoogle = googleBooksApiClient.getFictionBooks(numBooksToFetch);
        assertThat(booksFromGoogle.size(), is(numBooksToFetch));
    }

    @Test
    public void canFetchMultipleBooksInMultipleBatchesWithRemainder() throws FailedToFetchBooksFromGoogleException {

        int numBooksToFetch = 21;

        GoogleBookResponse mockGoogleBookResponseFirstBatch = new GoogleBookResponse();
        mockGoogleBookResponseFirstBatch.setItems(generateGoogleBooks(10));

        GoogleBookResponse mockGoogleBookResponseSecondBatch = new GoogleBookResponse();
        mockGoogleBookResponseSecondBatch.setItems(generateGoogleBooks(10));

        GoogleBookResponse mockGoogleBookResponseRemainder = new GoogleBookResponse();
        mockGoogleBookResponseRemainder.setItems(generateGoogleBooks(1));

        ResponseEntity<GoogleBookResponse> mockResponseFirstBatch = new ResponseEntity<>(mockGoogleBookResponseFirstBatch, HttpStatus.OK);
        ResponseEntity<GoogleBookResponse> mockResponseSecondBatch = new ResponseEntity<>(mockGoogleBookResponseFirstBatch, HttpStatus.OK);
        ResponseEntity<GoogleBookResponse> mockResponseRemainder = new ResponseEntity<>(mockGoogleBookResponseRemainder, HttpStatus.OK);

        when(mockGoogleBooksApiAdapter.fetchVolumes(Mockito.any(GoogleBooksApiParameters.class))).
                thenReturn(mockResponseFirstBatch, mockResponseSecondBatch, mockResponseRemainder);

        IGoogleBooksApiClient googleBooksApiClient = new GoogleBooksApiClient(mockGoogleBooksApiAdapter,
                "test_api_key",
                "test_api_base_url");

        List<Book> booksFromGoogle = googleBooksApiClient.getFictionBooks(numBooksToFetch);
        assertThat(booksFromGoogle.size(), is(numBooksToFetch));
    }

    @Test
    public void canHandleNoBooksFoundByApi() throws FailedToFetchBooksFromGoogleException {
        int numBooksToFetch = 1;
        GoogleBookResponse mockGoogleBookResponse = new GoogleBookResponse();
        mockGoogleBookResponse.setItems(null);
        ResponseEntity<GoogleBookResponse> mockResponse = new ResponseEntity<>(mockGoogleBookResponse, HttpStatus.OK);

        when(mockGoogleBooksApiAdapter.fetchVolumes(Mockito.any(GoogleBooksApiParameters.class))).thenReturn(mockResponse);

        IGoogleBooksApiClient googleBooksApiClient = new GoogleBooksApiClient(mockGoogleBooksApiAdapter,
                "test_api_key",
                "test_api_base_url");

        List<Book> booksFromGoogle = googleBooksApiClient.getFictionBooks(numBooksToFetch);

        assertThat(booksFromGoogle.size(), is(0));
    }

    @Test
    public void canHandleErrorFromApi() throws FailedToFetchBooksFromGoogleException {
        int numBooksToFetch = 1;
        GoogleBookResponse mockGoogleBookResponse = new GoogleBookResponse();
        mockGoogleBookResponse.setItems(null);
        ResponseEntity<GoogleBookResponse> mockResponse = new ResponseEntity<>(mockGoogleBookResponse, HttpStatus.BAD_GATEWAY);

        when(mockGoogleBooksApiAdapter.fetchVolumes(Mockito.any(GoogleBooksApiParameters.class))).thenReturn(mockResponse);

        IGoogleBooksApiClient googleBooksApiClient = new GoogleBooksApiClient(mockGoogleBooksApiAdapter,
                "test_api_key",
                "test_api_base_url");

        List<Book> booksFromGoogle = googleBooksApiClient.getFictionBooks(numBooksToFetch);

        assertThat(booksFromGoogle.size(), is(0));
    }

    private List<GoogleBook> generateGoogleBooks(int numBooksToCreate){
        List<GoogleBook> googleBooks = new ArrayList<>();
        GoogleBook googleBook = new GoogleBook();
        googleBook.setVolumeInfo(new VolumeInfo());
        IntStream.rangeClosed(1, numBooksToCreate).forEach(i -> googleBooks.add(googleBook));
        return googleBooks;
    }
}
