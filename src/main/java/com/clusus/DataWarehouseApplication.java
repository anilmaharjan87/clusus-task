package com.clusus;

import com.clusus.util.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class DataWarehouseApplication implements CommandLineRunner {
    @Autowired
    private CsvReader csvReader;
    public static void main(String[] args) {
        SpringApplication.run(DataWarehouseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        csvReader.processCsvFile();
    }
}
