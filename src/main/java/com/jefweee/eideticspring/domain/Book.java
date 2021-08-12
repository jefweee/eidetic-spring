package com.jefweee.eideticspring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Transient
    private final String ZOOM_PARAMETER_NAME = "zoom";
    @Transient
    private final String ZOOM_PARAMETER_VALUE_FULL_SIZE = "0";

    @Id
    private Long id;

    private String title;
    private String[] authors;
    private String description;
    private String thumbnailLink;
    private String[] categories;

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
