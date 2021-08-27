package com.jefweee.eideticspring.unit.controllers;

import com.jefweee.eideticspring.controllers.DataPopulationController;
import com.jefweee.eideticspring.data.DataPopulator;
import com.jefweee.eideticspring.data.DataPopulatorStateMonitor;
import com.jefweee.eideticspring.domain.exception.FailedToSaveBooksException;
import com.jefweee.eideticspring.googleclient.exception.FailedToFetchBooksFromGoogleException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DataPopulationController.class)
public class DataPopulationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataPopulatorStateMonitor dataStateMonitor;

    @MockBean
    private DataPopulator dataPopulator;

    @Test
    public void canFetchStatus() throws Exception {
        when(dataStateMonitor.getCurrentDataStatus()).thenReturn("Hello, this is the status of the data");

        this.mockMvc.perform(get("/datapopulator/datastatus"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, this is the status of the data")));
    }

    @Test
    public void canHandleSuccessfullyPopulatingBooks() throws FailedToSaveBooksException, FailedToFetchBooksFromGoogleException, Exception {
        int numBooksToPopulate = 2;
        doNothing().when(dataPopulator).populateBooksFromGoogle(numBooksToPopulate);

        this.mockMvc.perform(get("/datapopulator/populatebooks/" + numBooksToPopulate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(numBooksToPopulate + " books have been added to our storage")));

        verify(dataPopulator,times(1)).populateBooksFromGoogle(numBooksToPopulate);

    }

    @Test
    public void canHandleInvalidNumberOfBooksToPopulateParameter() throws Exception {
        String invalidNumBooksToPopulate = "potato";

        this.mockMvc.perform(get("/datapopulator/populatebooks/" + invalidNumBooksToPopulate))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("Number of books to fetch must be an integer")));
    }

    @Test
    public void canHandleErrorWhenFetchingBooksFromGoogle() throws FailedToSaveBooksException, FailedToFetchBooksFromGoogleException, Exception {
        int numBooksToPopulate = 2;
        doThrow(new FailedToFetchBooksFromGoogleException("There was an error fetching books from Google API")).when(dataPopulator).populateBooksFromGoogle(numBooksToPopulate);

        this.mockMvc.perform(get("/datapopulator/populatebooks/" + numBooksToPopulate))
                .andDo(print())
                .andExpect(status().is(502))
                .andExpect(content().string(containsString("There was an error fetching books from Google API")));

        verify(dataPopulator,times(1)).populateBooksFromGoogle(numBooksToPopulate);

    }

    @Test
    public void canHandleErrorWhenSavingBooks() throws FailedToSaveBooksException, FailedToFetchBooksFromGoogleException, Exception {
        int numBooksToPopulate = 2;
        doThrow(new FailedToSaveBooksException("There was an error saving books")).when(dataPopulator).populateBooksFromGoogle(numBooksToPopulate);
        this.mockMvc.perform(get("/datapopulator/populatebooks/" + numBooksToPopulate))
                .andDo(print())
                .andExpect(status().is(500))
                .andExpect(content().string(containsString("There was an error saving books")));

        verify(dataPopulator,times(1)).populateBooksFromGoogle(numBooksToPopulate);

    }

}
