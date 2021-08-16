package com.jefweee.eideticspring.googleclient.adapter;

import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;

public interface IGoogleBooksApiAdapter {

    GoogleBookResponse fetchVolumes(GoogleBooksApiParameters apiCallParameters);
}
