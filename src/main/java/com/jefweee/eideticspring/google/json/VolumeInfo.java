package com.jefweee.eideticspring.google.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo {

    String title;
    String[] authors;
    String publisher;
    String description;
    ImageLinks imageLinks;
}
