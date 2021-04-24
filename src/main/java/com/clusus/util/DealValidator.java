package com.clusus.util;

import com.clusus.dto.DealDto;
import com.clusus.dto.ErrorResponse;
import com.clusus.enums.CurrencyCode;
import com.clusus.repository.DealRepository;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DealValidator {
    @Autowired
    private DealRepository dealRepository;

    public Map<Integer, Object> validateDeals(List<DealDto> dealDtos) {
        Map<Integer, Object> errorMap = new HashMap();
        for (int row = 0; row < dealDtos.size(); row++) {
            List<ErrorResponse> errorResponses = new ArrayList<>();
            DealDto dealDto = dealDtos.get(row);
            if (StringUtils.isBlank(dealDto.getDealId())) {
                ErrorResponse errorResponse = populateError("Deal with invalid deal id", row, "dealId");
                errorResponses.add(errorResponse);
            } else {
                if (dealRepository.findByDealId(dealDto.getDealId()) != null) {
                    ErrorResponse errorResponse = populateError("Deal with given deal id  already exists", row, "dealId");
                    errorResponses.add(errorResponse);
                }
            }
            if (!EnumUtils.isValidEnum(CurrencyCode.class, dealDto.getFromCurrencyCode())) {
                ErrorResponse errorResponse = populateError("Deal with invalid from ISO currency code", row, "fromCurrencyCode");
                errorResponses.add(errorResponse);
            }
            if (!EnumUtils.isValidEnum(CurrencyCode.class, dealDto.getToCurrencyCode())) {
                ErrorResponse errorResponse = populateError("Deal with invalid to ISO currency code", row, "toCurrencyCode");
                errorResponses.add(errorResponse);
            }
            if (StringUtils.isBlank(dealDto.getDealTime()) || !DateValidator.isValid(dealDto.getDealTime())) {
                ErrorResponse errorResponse = populateError("Deal with invalid date format", row, "dealTime");
                errorResponses.add(errorResponse);
            }
            if (StringUtils.isBlank(dealDto.getDealAmount()) || !AmountValidator.checkIfAmountIsNumber(dealDto.getDealAmount())) {
                ErrorResponse errorResponse = populateError("Deal with invalid amount", row, "dealAmount");
                errorResponses.add(errorResponse);
            } else {
                if (new BigDecimal(dealDto.getDealAmount()).compareTo(BigDecimal.ZERO) <= 0) {
                    ErrorResponse errorResponse = populateError("Deal with  amount less than or zero", row, "dealAmount");
                    errorResponses.add(errorResponse);
                }
            }
            errorMap.put(row, errorResponses);
        }
        return errorMap;
    }

    public ErrorResponse populateError(String errorMessage, Integer row, String propertyName) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setRowNumber(row);
        errorResponse.setPropertyName(propertyName);
        return errorResponse;
    }
}
