package com.jefweee.eideticspring.googleclient.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Validated
public class GoogleBooksApiParameters {

    @NotNull @NotBlank
    private String googleBookApiKey;
    @NotNull @NotBlank
    private String googleBookApiBaseUrl;
    @NotNull @NotBlank
    private String searchTerm;
    @NotNull @NotBlank
    private String printType;
    @Min(1)
    private int numBooksToFetch;
    @Min(0)
    private int batchStartingIndex;

}
