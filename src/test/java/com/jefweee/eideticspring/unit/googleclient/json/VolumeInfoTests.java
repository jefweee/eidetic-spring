package com.jefweee.eideticspring.unit.googleclient.json;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.json.ImageLinks;
import com.jefweee.eideticspring.googleclient.json.VolumeInfo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VolumeInfoTests {

    @Test
    public void canCreateDomainBookFromVolumeInfo(){
        VolumeInfo volume = new VolumeInfo();
        List<String> authors = Stream.of("Genevieve Cogman", "JRR Tolkein", "Robin Hobb").collect(Collectors.toList());
        List<String> categories = Stream.of("Fiction", "Fantasy").collect(Collectors.toList());
        String description = "A work of master fiction by no less than three authors!";
        String publisher = "Big Books Ltd";
        ImageLinks links = new ImageLinks("www.test.com");
        String title = "Once Upon a Story";

        volume.setAuthors(authors);
        volume.setCategories(categories);
        volume.setDescription(description);
        volume.setPublisher(publisher);
        volume.setImageLinks(links);
        volume.setTitle(title);

        Book book = volume.convertToBook();

        assertNotNull(book);
        assertTrue(book.getAuthors().containsAll(authors));
        assertTrue(book.getCategories().containsAll(categories));
        assertTrue(book.getTitle().equals(title));
        assertTrue(book.getDescription().equals(description));
        assertTrue(book.getThumbnailLink().equals(links.getThumbnail()));
    }
}
