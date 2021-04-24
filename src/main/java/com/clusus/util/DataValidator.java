package com.clusus.util;

import com.clusus.dto.DealDto;
import com.clusus.enums.CurrencyCode;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class DataValidator {
    private static final Logger logger = LoggerFactory.getLogger(DataValidator.class);

    public static Boolean checkIfAmountIsNumber(String dealAmount) {
        try {
            BigDecimal amount = new BigDecimal(dealAmount);
            return true;
        } catch (NumberFormatException ex) {
            logger.error("Number format exception occurred", ex);
            return false;
        }
    }

    public static Boolean checkIfCurrencyISOCodeExist(DealDto deal) {
        return EnumUtils.isValidEnum(CurrencyCode.class, deal.getToCurrencyCode())
                && EnumUtils.isValidEnum(CurrencyCode.class, deal.getFromCurrencyCode());
    }
}
