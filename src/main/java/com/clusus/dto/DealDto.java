package com.clusus.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DealDto {
    private String dealId;
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private Date dealTime;
    private BigDecimal dealAmount;
}
