package com.jefweee.eideticspring.googleclient.adapter;

import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.validation.Valid;

@Service
@NoArgsConstructor
public class GoogleBooksApiAdapter implements IGoogleBooksApiAdapter {

    @Override
    public ResponseEntity<GoogleBookResponse> fetchVolumes(@Valid GoogleBooksApiParameters apiCallParameters){
        ResponseEntity<GoogleBookResponse> response = getWebClient(apiCallParameters.getGoogleBookApiBaseUrl()).get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", apiCallParameters.getSearchTerm())
                        .queryParam("printType", apiCallParameters.getPrintType())
                        .queryParam("maxResults", apiCallParameters.getNumBooksToFetch())
                        .queryParam("startIndex", apiCallParameters.getBatchStartingIndex())
                        .queryParam("key", apiCallParameters.getGoogleBookApiKey())
                        .build())
                .retrieve()
                .toEntity(GoogleBookResponse.class)
                .block();

        return response;
    }

    private WebClient getWebClient(String baseUrl){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = WebClient
                .builder()
                .uriBuilderFactory(factory)
                .baseUrl(baseUrl)
                .build();

        return webClient;
    }
}
