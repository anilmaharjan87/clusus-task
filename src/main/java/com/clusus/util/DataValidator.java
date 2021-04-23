package com.clusus.util;

import com.clusus.entity.Deal;
import com.clusus.enums.CurrencyCode;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;

public class DataValidator {
    public static Boolean isDataValid(Deal deal) {
        Boolean status = checkIfCurrencyISOCodeExist(deal);
        if(StringUtils.isBlank(deal.getDealId())
                || StringUtils.isBlank(deal.getFromCurrencyCode())
                ||StringUtils.isBlank(deal.getToCurrencyCode())
        ||StringUtils.isBlank((CharSequence) deal.getDealTime()))
    }

    private static Boolean checkIfCurrencyISOCodeExist(Deal deal) {
        return Arrays.asList(CurrencyCode.values()).contains(deal.getFromCurrencyCode()) && Arrays.asList(CurrencyCode.values()).contains(deal.getToCurrencyCode());
    }
}
