package com.jefweee.eideticspring.unit.domain;

import com.jefweee.eideticspring.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BookTest {

    @ParameterizedTest
    @NullAndEmptySource
    public void noFullSizedImageUrlConstructedWhenThumbnailUrlEmpty(String thumbnailLink){

        String expectedFullSizeLink = "";

        Book book = new Book();
        book.setThumbnailLink(thumbnailLink);

        assertThat(book.generateFullSizeCoverImageLink(), is(expectedFullSizeLink));
    }

    @Test
    public void fullSizedImageUrlConstructedFromThumbnailImageUrl(){
        String thumbnailLink = "https://books.google.com/books/content?id=WkC9DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api";
        String expectedFullSizeLink = "https://books.google.com/books/content?id=WkC9DwAAQBAJ&printsec=frontcover&img=1&edge=curl&source=gbs_api&zoom=0";

        Book book = new Book();
        book.setThumbnailLink(thumbnailLink);

        assertThat(book.generateFullSizeCoverImageLink(), is(expectedFullSizeLink));
    }
}
