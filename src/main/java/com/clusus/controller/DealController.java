package com.clusus.controller;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.mapper.DealMapper;
import com.clusus.repository.DealRepository;
import com.clusus.service.DealService;
import com.clusus.util.CSVHelper;
import com.clusus.util.CsvReader;
import com.clusus.util.DealValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/deal")
public class DealController {
    @Autowired
    private DealService dealService;
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private DealValidator dealValidator;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (CSVHelper.hasCSVFormat(file)) {
            List<DealDto> csvRecords = csvReader.processCsvFile(file.getInputStream());
            Map<Integer, Object> invalidCSVRowMap = dealValidator.validateDeals(csvRecords);
            List<Deal> cleanRecords = new ArrayList<>();
            for (int i = 0; i < csvRecords.size(); i++) {
                if (!invalidCSVRowMap.containsKey(i)) {
                    cleanRecords.add(DealMapper.INSTANCE.toEntity(csvRecords.get(i)));
                }
            }
            dealService.saveDealList(cleanRecords);
            return new ResponseEntity<>(invalidCSVRowMap, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a csv file!");
    }
}
