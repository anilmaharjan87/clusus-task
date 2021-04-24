package com.clusus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateValidator {
    private static final Logger logger = LoggerFactory.getLogger(DateValidator.class);

    public static Boolean isValid(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("Cannot parse the date",e);
            return false;
        }
        return true;
    }
}
