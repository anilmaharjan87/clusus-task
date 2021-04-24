package com.clusus.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmountValidatorTest {
    @Test
    void checkIfStringAmountIsValid() {
        assertTrue(AmountValidator.checkIfAmountIsNumber("938484"));
    }
    @Test
    void checkIfStringAmountIsInValid() {
        assertFalse(AmountValidator.checkIfAmountIsNumber("randomstring"));
    }
}
