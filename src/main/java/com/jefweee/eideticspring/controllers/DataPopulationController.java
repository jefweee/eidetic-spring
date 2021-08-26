package com.jefweee.eideticspring.controllers;

import com.jefweee.eideticspring.data.DataPopulator;
import com.jefweee.eideticspring.data.DataPopulatorStateMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/datapopulator")
public class DataPopulationController {

    DataPopulatorStateMonitor dataMonitor;
    DataPopulator dataPopulator;

    @Autowired
    public DataPopulationController(DataPopulatorStateMonitor dataMonitor, DataPopulator dataPopulator) {
        this.dataMonitor = dataMonitor;
        this.dataPopulator = dataPopulator;
    }

    @GetMapping("/hello")
    public String helloDataPopulator(){
        return "Hello! I am a data populator";
    }

    @GetMapping("/datastatus")
    public String whatsTheStatusOfOurData(){
        return dataMonitor.getCurrentDataStatus();
    }

    @GetMapping("/populate/{numberOfBooksToPopulate}")
    public ResponseEntity<String> populateBookData(@PathVariable String numberOfBooksToPopulate){
        HttpStatus status = HttpStatus.OK;
        String message = "";
        try{
            dataPopulator.populateBooksFromGoogle(Integer.parseInt(numberOfBooksToPopulate));
            message = numberOfBooksToPopulate + " books have been added to our storage";
        }
        catch(Exception e){
            status = HttpStatus.BAD_REQUEST;
            message = "There was an error populating books";
        }
        return new ResponseEntity<>(message, status);
    }
}
