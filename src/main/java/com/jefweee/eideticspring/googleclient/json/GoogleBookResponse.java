package com.jefweee.eideticspring.googleclient.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookResponse {

    String kind;
    List<GoogleBook> items;
}
