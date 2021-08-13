package com.jefweee.eideticspring.unit.controllers;

import com.jefweee.eideticspring.controllers.DataPopulationController;
import com.jefweee.eideticspring.data.DataPopulatorStateMonitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
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

    @Test
    public void canFetchStatus() throws Exception {
        when(dataStateMonitor.getCurrentDataStatus()).thenReturn("Hello, this is the status of the data");

        this.mockMvc.perform(get("/datapopulator/datastatus"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, this is the status of the data")));
    }
}
