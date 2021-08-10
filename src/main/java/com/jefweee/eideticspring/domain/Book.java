package com.jefweee.eideticspring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private final String ZOOM_PARAMETER_NAME = "zoom";
    private final String ZOOM_PARAMETER_VALUE_FULL_SIZE = "0";

    String title;
    String[] authors;
    String description;
    String thumbnailLink;


    public String generateFullSizeCoverImageLink() {
        String fullSizeLink = "";

        if (thumbnailLink != null && !thumbnailLink.isEmpty()){
            fullSizeLink = UriComponentsBuilder.fromUriString(thumbnailLink)
                                                .replaceQueryParam(ZOOM_PARAMETER_NAME, ZOOM_PARAMETER_VALUE_FULL_SIZE)
                                                .toUriString();
        }

        return fullSizeLink;
    }
}
