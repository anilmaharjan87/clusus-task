package com.clusus.util;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.mapper.DealMapper;
import com.clusus.service.DealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Component
public class CsvReader {
    private final DealService dealService;
    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);
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
                DealDto dealDto = new DealDto();
                String[] data = line.split(COMMA_SEPARATOR);
/*                if (data.length < TOTAL_COLUMN) {
//                    throw new ArrayIndexOutOfBoundsException("Array is out of bound");
                    logger.log(Level.WARNING, "Incomplete data of deal");
                    continue;
                }*/
                dealDto.setDealId(data[0]);
                dealDto.setFromCurrencyCode(data[1]);
                dealDto.setToCurrencyCode(data[2]);
                dealDto.setDealTime((data[3]));
                dealDto.setDealAmount((data[4]));
                Boolean isValid = DataValidator.isDataValid(dealDto);
                if (isValid) {
                    Deal deal = DealMapper.INSTANCE.toEntity(dealDto);
//                    Deal deal = populateDealEntity(dealDto);
                    dealList.add(deal);
                }
            }
        } catch (FileNotFoundException ex) {
            logger.error("File not found", ex);
        } catch (IOException ex) {
            logger.error("IOException occurred", ex);
        }
        dealService.saveDealList(dealList);
    }

    private Deal populateDealEntity(DealDto dealDto) {
        Deal deal = new Deal();
        deal.setDealId(dealDto.getDealId());
        deal.setFromCurrencyCode(dealDto.getFromCurrencyCode());
        deal.setToCurrencyCode(dealDto.getToCurrencyCode());
        deal.setDealTime(convertDate(dealDto.getDealTime()));
        deal.setDealAmount(new BigDecimal(dealDto.getDealAmount()));
        return deal;
    }

    private Date convertDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("Error converting date", e);
        }
        return null;
    }
}
