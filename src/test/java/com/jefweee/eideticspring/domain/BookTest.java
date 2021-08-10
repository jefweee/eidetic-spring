package com.jefweee.eideticspring.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BookTest {

    @Test
    public void testFullSizedImageUrlConstructed(){
        String thumbnailLink = "https://books.google.com/books/content?id=WkC9DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api";
        String expectedFullSizeLink = "https://books.google.com/books/content?id=WkC9DwAAQBAJ&printsec=frontcover&img=1&edge=curl&source=gbs_api&zoom=0";

        Book book = new Book();
        book.setThumbnailLink(thumbnailLink);

        assertThat(book.generateFullSizeCoverImageLink(), is(expectedFullSizeLink));
    }
}
