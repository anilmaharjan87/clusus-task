package com.clusus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealDto {
    private String dealId;
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private String dealTime;
    private String dealAmount;
}
