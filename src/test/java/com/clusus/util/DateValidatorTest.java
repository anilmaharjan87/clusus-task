package com.clusus.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateValidatorTest {
    @Test
    void checkIfStringDateIsValid() {
        assertTrue(DateValidator.isValid("2021-04-26 10:11:15"));
    }

    @Test
    void checkIfStringDateIsNotValid() {
        assertFalse(DateValidator.isValid("2345"));
    }

    @Test
    void checkIfStringDateIsNotValidFormat() {
        assertFalse(DateValidator.isValid("2021-04-26"));
    }
}
