package com.jefweee.eideticspring.googleclient.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.json.serialize.VolumeInfoSerializer;
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
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule("VolumeInfoSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(VolumeInfo.class, new VolumeInfoSerializer());
        mapper.registerModule(module);

        Book book = mapper.convertValue(this, Book.class);
        return book;
    }
}
