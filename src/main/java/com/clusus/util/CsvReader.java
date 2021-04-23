package com.clusus.util;

import com.clusus.entity.Deal;
import com.clusus.enums.CurrencyCode;
import com.clusus.service.DealService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CsvReader {
    private final DealService dealService;
    private static final Logger logger = Logger.getLogger(CsvReader.class.getName());
    private static final String COMMA_SEPARATOR = ",";
    private static final Integer TOTAL_COLUMN = 5;

    public CsvReader(DealService dealService) {
        this.dealService = dealService;
    }

    public void processCsvFile() {
        List<Deal> dealList = new ArrayList<>();
        Boolean skip = true;
        try (BufferedReader br = new BufferedReader(new FileReader("deal.csv"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                if (skip) {
                    logger.info("Skipping header of csv file");
                    skip = false;
                    continue;
                }
                Deal deal = new Deal();
                String[] data = line.split(COMMA_SEPARATOR);
/*                if (data.length < TOTAL_COLUMN) {
//                    throw new ArrayIndexOutOfBoundsException("Array is out of bound");
                    logger.log(Level.WARNING, "Incomplete data of deal");
                    continue;
                }*/
                deal.setDealId(data[0]);
                deal.setFromCurrencyCode(data[1]);
                deal.setToCurrencyCode(data[2]);
                if (StringUtils.isNotBlank(data[3]) && DateValidator.isValid(data[3])) {
                    deal.setDealTime(getTimestamp(data[3]));
                }
                if (StringUtils.isNotBlank(data[4])) {
                    deal.setDealAmount(new BigDecimal(data[4]));
                }

                dealList.add(deal);
            }
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        dealService.saveDealList(dealList);
        System.out.println(dealList);
    }

    private Date getTimestamp(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Error converting the date", e);
        }
        return null;
    }
}
