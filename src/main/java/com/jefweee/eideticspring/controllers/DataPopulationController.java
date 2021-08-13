package com.jefweee.eideticspring.controllers;

import com.jefweee.eideticspring.data.DataPopulatorStateMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/datapopulator")
public class DataPopulationController {

    DataPopulatorStateMonitor dataMonitor;

    @Autowired
    public DataPopulationController(DataPopulatorStateMonitor dataMonitor) {
        this.dataMonitor = dataMonitor;
    }

    @GetMapping("/hello")
    public String helloDataPopulator(){
        return "Hello! I am a data populator";
    }

    @GetMapping("/datastatus")
    public String whatsTheStatusOfOurData(){
        return dataMonitor.getCurrentDataStatus();
    }
}
