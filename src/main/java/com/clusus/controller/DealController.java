package com.clusus.controller;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.service.DealService;
import com.clusus.util.CsvReader;
import com.clusus.util.FieldValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/deal")
public class DealController {
    private final DealService dealService;
    private final FieldValidator fieldValidator;
    private final CsvReader csvReader;

    public DealController(DealService dealService, FieldValidator fieldValidator, CsvReader csvReader) {
        this.dealService = dealService;
        this.fieldValidator = fieldValidator;
        this.csvReader = csvReader;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Deal deal, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = fieldValidator.validate(bindingResult);
        if (errorMap != null) return errorMap;
        DealDto dealDto = dealService.save(deal);
        return new ResponseEntity<>(dealDto, HttpStatus.OK);
    }
    @GetMapping("/csv")
    public ResponseEntity<?> upload() {
        csvReader.processCsvFile();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
