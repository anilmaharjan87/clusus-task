package com.clusus.controller;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.service.DealService;
import com.clusus.util.CsvReader;
import com.clusus.util.DealValidator;
import com.clusus.util.ExtractCleanRecords;
import com.clusus.util.FileFormatValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/deal")
public class DealController {
    private final DealService dealService;
    private final CsvReader csvReader;
    private final DealValidator dealValidator;

    public DealController(DealService dealService, CsvReader csvReader, DealValidator dealValidator) {
        this.dealService = dealService;
        this.csvReader = csvReader;
        this.dealValidator = dealValidator;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (FileFormatValidator.isValidFileFormat(file)) {
            List<DealDto> csvRecords = csvReader.processCsvFile(file.getInputStream());
            Map<Integer, Object> invalidCSVRowMap = dealValidator.validateDeals(csvRecords);
            List<Deal> cleanRecords = ExtractCleanRecords.getCleanRecord(csvRecords, invalidCSVRowMap);
            dealService.saveDealList(cleanRecords);
            return new ResponseEntity<>(invalidCSVRowMap, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a csv file!");
    }
}
