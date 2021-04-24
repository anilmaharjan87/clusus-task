package com.clusus.util;

import com.clusus.dto.DealDto;
import com.clusus.service.DealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {
    private static final String COMMA_SEPARATOR = ",";
    private static final Integer TOTAL_COLUMN = 5;
    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

    public List<DealDto> processCsvFile(InputStream recordStream) {
        List<DealDto> dealDtoList = new ArrayList<>();
        Boolean skip = true;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(recordStream))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                if (skip) {
                    logger.info("Skipping header of csv file");
                    skip = false;
                    continue;
                }
                DealDto dealDto = new DealDto();
                String[] data = line.split(COMMA_SEPARATOR);
        /*        if (data.length < TOTAL_COLUMN) {
//                    throw new ArrayIndexOutOfBoundsException("Array is out of bound");
                    logger.log(Level.WARNING, "Incomplete data of deal");
                    continue;
                }*/
                dealDto.setDealId(data[0]);
                dealDto.setFromCurrencyCode(data[1]);
                dealDto.setToCurrencyCode(data[2]);
                dealDto.setDealTime((data[3]));
                dealDto.setDealAmount((data[4]));
                dealDtoList.add(dealDto);
            }
        } catch (FileNotFoundException ex) {
            logger.error("File not found", ex);
        } catch (IOException ex) {
            logger.error("IOException occurred", ex);
        }
        return dealDtoList;
    }
}
