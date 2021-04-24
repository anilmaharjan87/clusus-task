package com.clusus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class AmountValidator {
    private static final Logger logger = LoggerFactory.getLogger(AmountValidator.class);

    public static Boolean checkIfAmountIsNumber(String dealAmount) {
        try {
            BigDecimal amount = new BigDecimal(dealAmount);
            return true;
        } catch (NumberFormatException ex) {
            logger.error("Number format exception occurred", ex);
            return false;
        }
    }
}
