package com.clusus.controller;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.mapper.DealMapper;
import com.clusus.service.DealService;
import com.clusus.util.CSVHelper;
import com.clusus.util.CsvReader;
import com.clusus.util.FieldValidator;
import com.clusus.util.DealValidator;
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
@RequestMapping("/deal")
public class DealController {
    @Autowired
    private DealService dealService;
    @Autowired
    private FieldValidator fieldValidator;
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private DealValidator dealValidator;

  /*  public DealController(DealService dealService, FieldValidator fieldValidator, CsvReader csvReader, ValidationUtility validationUtility) {
        this.dealService = dealService;
        this.fieldValidator = fieldValidator;
        this.csvReader = csvReader;
        this.validationUtility = validationUtility;
    }*/

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Deal deal, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = fieldValidator.validate(bindingResult);
        if (errorMap != null) return errorMap;
        DealDto dealDto = dealService.save(deal);
        return new ResponseEntity<>(dealDto, HttpStatus.OK);
    }

    @GetMapping("/csv")
    public ResponseEntity<?> upload() {
//        Map<Integer, Object> errorRecords = csvReader.processCsvFile(new ArrayList<>());
        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            List<DealDto> csvRecords = csvReader.processCsvFile(file.getInputStream());
            Map<Integer, Object> errorMap = dealValidator.validateDeals(csvRecords);
            List<Deal> cleanRecords = new ArrayList<>();
            for (int i = 0; i < csvRecords.size(); i++) {
                if (!errorMap.containsKey(i)) {
                    cleanRecords.add(DealMapper.INSTANCE.toEntity(csvRecords.get(i)));
                }
            }
            dealService.saveDealList(cleanRecords);
            return new ResponseEntity<>(errorMap, HttpStatus.OK);
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
