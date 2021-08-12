package com.jefweee.eideticspring.googleclient.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jefweee.eideticspring.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo {

    String title;
    List<String> authors;
    String publisher;
    String description;
    ImageLinks imageLinks;
    List<String> categories;

    public Book convertToBook() {
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.convertValue(this, Book.class);
        return book;
    }
}
