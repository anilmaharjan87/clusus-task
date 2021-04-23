package com.clusus.controller;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.service.DealService;
import com.clusus.util.FieldValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/deal")
public class DealController {
    private final DealService dealService;
    private final FieldValidator fieldValidator;

    public DealController(DealService dealService, FieldValidator fieldValidator) {
        this.dealService = dealService;
        this.fieldValidator = fieldValidator;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Deal deal, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = fieldValidator.validate(bindingResult);
        if (errorMap != null) return errorMap;
        DealDto dealDto = dealService.save(deal);
        return new ResponseEntity<>(dealDto, HttpStatus.OK);
    }
}
