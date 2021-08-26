package com.jefweee.eideticspring.data;

import com.jefweee.eideticspring.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPopulatorStateMonitor {

    BookRepository bookRepo;

    @Autowired
    public DataPopulatorStateMonitor(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public String getCurrentDataStatus(){
        long numBooksInRepo = bookRepo.count();
        return "There are currently " + numBooksInRepo + " books in our storage";
    }
}
