package com.jefweee.eideticspring.googleclient;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.adapter.GoogleBooksApiAdapter;
import com.jefweee.eideticspring.googleclient.adapter.GoogleBooksApiParameters;
import com.jefweee.eideticspring.googleclient.exception.FailedToFetchBooksFromGoogleException;
import com.jefweee.eideticspring.googleclient.json.GoogleBook;
import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleBooksApiClient implements IGoogleBooksApiClient {

    public static final int DEFAULT_BATCH_SIZE = 10;
    public static final String BOOKS_ONLY_PRINT_TYPE = "books";
    GoogleBooksApiAdapter googleBooksApiAdapter;
    String googleBookApiKey;
    String googleBookApiBaseUrl;

    public GoogleBooksApiClient() { }

    @Autowired
    public GoogleBooksApiClient(GoogleBooksApiAdapter googleBooksApiAdapter,
                                @Value("${google.books.api.key}") String googleBookApiKey,
                                @Value("${google.books.api.baseurl}") String googleBookApiBaseUrl) {
        this.googleBooksApiAdapter = googleBooksApiAdapter;
        this.googleBookApiKey = googleBookApiKey;
        this.googleBookApiBaseUrl = googleBookApiBaseUrl;
    }

    @Override
    public List<Book> getFictionBooks(int numBooksToFetch) throws FailedToFetchBooksFromGoogleException {
        String fictionOnlyQuery = "subject:fiction";
        List<Book> books = new ArrayList<>();

        try {
            List<GoogleBook> googleBooks = getBooksInBatches(fictionOnlyQuery, numBooksToFetch);
            books = googleBooks
                    .stream()
                    .map(b -> b.getVolumeInfo().convertToBook())
                    .collect(Collectors.toList());
        }
        catch(Exception e){
            throw new FailedToFetchBooksFromGoogleException();
        }
        return books;
    }

    private List<GoogleBook> getBooksInBatches(@NotBlank @NotEmpty String searchTerm, @Min(1) int numBooksToFetch) {

        List<GoogleBook> books = new ArrayList<>();
        int batchSize = Math.min(DEFAULT_BATCH_SIZE, numBooksToFetch);
        int numBatchesRequired = Math.floorDiv(numBooksToFetch, batchSize);
        int remainderBooksAfterBatches = Math.floorMod(numBooksToFetch, batchSize);

        for(int batch = 1; batch <= numBatchesRequired; batch++){
            GoogleBooksApiParameters searchParameters = new GoogleBooksApiParameters(googleBookApiKey, googleBookApiBaseUrl, searchTerm,
                    BOOKS_ONLY_PRINT_TYPE, batchSize, books.size());

            books.addAll(getABatchOfBooks(searchParameters));
        }

        if(remainderBooksAfterBatches > 0){
            GoogleBooksApiParameters searchParameters = new GoogleBooksApiParameters(googleBookApiKey, googleBookApiBaseUrl, searchTerm,
                    BOOKS_ONLY_PRINT_TYPE, remainderBooksAfterBatches, books.size());
            books.addAll(getABatchOfBooks(searchParameters));
        }
        return books;
    }

    private List<GoogleBook> getABatchOfBooks(GoogleBooksApiParameters searchParameters){
        List<GoogleBook> books = new ArrayList<>();
        ResponseEntity<GoogleBookResponse> fullResponse = googleBooksApiAdapter.fetchVolumes(searchParameters);

        if(fullResponse.getStatusCode().is2xxSuccessful()){
            GoogleBookResponse bookResponse = fullResponse.getBody();
            if(!CollectionUtils.isEmpty(bookResponse.getItems())){
                books = bookResponse.getItems();
            }
        }

        return books;
    }

    @Override
    public String getGoogleBookApiKey() {
        return googleBookApiKey;
    }

    @Override
    public String getGoogleBookApiBaseUrl() {
        return googleBookApiBaseUrl;
    }

}
