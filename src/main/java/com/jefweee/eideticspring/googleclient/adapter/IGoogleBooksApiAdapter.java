package com.jefweee.eideticspring.googleclient.adapter;

import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;
import org.springframework.http.ResponseEntity;

public interface IGoogleBooksApiAdapter {

    ResponseEntity<GoogleBookResponse> fetchVolumes(GoogleBooksApiParameters apiCallParameters);
}
