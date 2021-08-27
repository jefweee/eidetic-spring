package com.jefweee.eideticspring.controllers;

import com.jefweee.eideticspring.data.DataPopulator;
import com.jefweee.eideticspring.data.DataPopulatorStateMonitor;
import com.jefweee.eideticspring.domain.exception.FailedToSaveBooksException;
import com.jefweee.eideticspring.googleclient.exception.FailedToFetchBooksFromGoogleException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@Log4j2
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

    @GetMapping("/populatebooks/{numberOfBooksToPopulate}")
    public ResponseEntity<String> populateBookData(@PathVariable String numberOfBooksToPopulate)  throws FailedToFetchBooksFromGoogleException, FailedToSaveBooksException, InvalidParameterException {
        HttpStatus status = HttpStatus.OK;
        String message = "";
        try{
            int numberOfBooksToPopulateAsInt = Integer.parseInt(numberOfBooksToPopulate);
            dataPopulator.populateBooksFromGoogle(numberOfBooksToPopulateAsInt);
            message = numberOfBooksToPopulate + " books have been added to our storage";
        }
        catch( NumberFormatException numberFormatException){
            throw new InvalidParameterException();
        }

        return new ResponseEntity<>(message, status);
    }

}
