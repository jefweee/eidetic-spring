package com.jefweee.eideticspring.googleclient;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.adapter.GoogleBooksApiAdapter;
import com.jefweee.eideticspring.googleclient.adapter.GoogleBooksApiParameters;
import com.jefweee.eideticspring.googleclient.json.GoogleBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleBooksApiClient implements IGoogleBooksApiClient {

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
    public List<Book> getFictionBooks(int numBooksToFetch) {
        String fictionOnlyQuery = "subject:fiction";
        List<GoogleBook> googleBooks = getBooks(fictionOnlyQuery, numBooksToFetch);
        List<Book> books = googleBooks
                                    .stream()
                                    .map(b -> b.getVolumeInfo().convertToBook())
                                    .collect(Collectors.toList());
        return books;
    }

    private List<GoogleBook> getBooks(@NotBlank @NotEmpty String searchTerm, @Min(0) int numBooksToFetch) {
        String booksOnlyPrintType = "books";

        List<GoogleBook> books = new ArrayList<>();
        int defaultBookBatchSize = 10;
        int currentBookBatchSize = Math.min(defaultBookBatchSize, numBooksToFetch-books.size());

        while(books.size() < numBooksToFetch){
            GoogleBooksApiParameters searchParameters = new GoogleBooksApiParameters(googleBookApiKey, googleBookApiBaseUrl, searchTerm,
                    booksOnlyPrintType, currentBookBatchSize, books.size());
            books.addAll(googleBooksApiAdapter.fetchVolumes(searchParameters).getItems());
            currentBookBatchSize = Math.min(defaultBookBatchSize, numBooksToFetch-books.size());
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
