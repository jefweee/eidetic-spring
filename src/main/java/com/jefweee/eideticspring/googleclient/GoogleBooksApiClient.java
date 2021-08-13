package com.jefweee.eideticspring.googleclient;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.json.GoogleBook;
import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Validated
public class GoogleBooksApiClient implements IGoogleBooksApiClient {

    @Value("${google.books.api.key}")
    private String googleBookApiKey;

    @Value("${google.books.api.baseurl}")
    private String googleBookApiBaseUrl;

    public GoogleBooksApiClient() { }

    @Override
    public List<Book> getFictionBooks(@Min(1) int maxResults) {
        List<GoogleBook> googleBooks = getFictionBooksFromGoogle(maxResults);
        List<Book> books = googleBooks
                                    .stream()
                                    .map(b -> b.getVolumeInfo().convertToBook())
                                    .collect(Collectors.toList());
        return books;
    }

    @Override
    public List<GoogleBook> getFictionBooksFromGoogle(@Min(1) int maxResults) {
        String fictionOnlyQuery = "subject:fiction";
        String booksOnlyPrintType = "books";
        return getBooks(fictionOnlyQuery, booksOnlyPrintType, maxResults);
    }

    private List<GoogleBook> getBooks(@NotNull @NotBlank String searchTerm, @NotNull @NotBlank String printType, @Min(1) int totalBooksToFetch) {
        List<GoogleBook> books = new ArrayList<>();
        int defaultBookBatchSize = 10;
        int currentBookBatchSize = Math.min(defaultBookBatchSize, totalBooksToFetch-books.size());

        while(books.size() < totalBooksToFetch){
            books.addAll(fetchBatchOfBooks(searchTerm, printType, currentBookBatchSize, books.size()));
            currentBookBatchSize = Math.min(defaultBookBatchSize, totalBooksToFetch-books.size());
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

    private List<GoogleBook> fetchBatchOfBooks(String searchTerm, String printType,  int batchSize, int batchStartingIndex) {
        ResponseEntity<GoogleBookResponse> response = getWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", searchTerm)
                        .queryParam("printType", printType)
                        .queryParam("maxResults", batchSize)
                        .queryParam("startIndex", batchStartingIndex)
                        .queryParam("key", googleBookApiKey)
                        .build())
                .retrieve()
                .toEntity(GoogleBookResponse.class)
                .block();

        return response.getBody().getItems();
    }

    private WebClient getWebClient(){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(googleBookApiBaseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = WebClient
                .builder()
                .uriBuilderFactory(factory)
                .baseUrl(googleBookApiBaseUrl)
                .build();

        return webClient;
    }



}
