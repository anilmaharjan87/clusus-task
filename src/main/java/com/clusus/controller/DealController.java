package com.clusus.controller;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.mapper.DealMapper;
import com.clusus.repository.DealRepository;
import com.clusus.service.DealService;
import com.clusus.util.CSVHelper;
import com.clusus.util.CsvReader;
import com.clusus.util.DealValidator;
import com.clusus.util.FieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    private FieldValidator fieldValidator;
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private DealValidator dealValidator;
    @Autowired
    private DealRepository dealRepository;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Deal deal, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = fieldValidator.validate(bindingResult);
        if (errorMap != null) return errorMap;
        DealDto dealDto = dealService.save(deal);
        return new ResponseEntity<>(dealDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> get() {
        List<Deal> list = dealRepository.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

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
