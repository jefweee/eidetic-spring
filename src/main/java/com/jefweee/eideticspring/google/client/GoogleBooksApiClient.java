package com.jefweee.eideticspring.google.client;

import com.jefweee.eideticspring.google.json.GoogleBook;

import com.jefweee.eideticspring.google.json.GoogleBookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class GoogleBooksApiClient {

    @Value("${google.books.api.key}")
    private String googleBookApiKey;

    @Value("${google.books.api.baseurl}")
    private String googleBookApiBaseUrl;

    public GoogleBooksApiClient() {

    }

    public String getGoogleBookApiKey() {
        return googleBookApiKey;
    }

    public String getGoogleBookApiBaseUrl() {
        return googleBookApiBaseUrl;
    }

    public GoogleBookResponse getBooks(@NotNull @NotBlank String searchTerm, @NotNull @NotBlank String printType, @Min(1) int maxResults) {

        Mono<GoogleBookResponse> response = getWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", searchTerm)
                        .queryParam("printType", printType)
                        .queryParam("maxResults", maxResults)
                        .queryParam("key", googleBookApiKey)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<GoogleBookResponse>() {});

        return response.block();
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
