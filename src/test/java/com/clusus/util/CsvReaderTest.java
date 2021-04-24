package com.clusus.util;

import com.clusus.dto.DealDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CsvReaderTest {
    CsvReader csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReader();
    }

    @Test
    public void testRowCountInCSVFile() {
        InputStream inputStream = asStream("deal_id,from_currency,to_currency,deal_timestamp,amount \n" +
                "SN01,AUD,INR, 1222,9834\n" +
                "SN02,USD,INR,2021-04-24 10:25:15,1293\n" +
                "SN03,AUD,NPR,2021-04-25 10:30:15,2356\n" +
                "SN04,JPY,NPR,2021-04-26 10:11:15,0.002");
        List<DealDto> dealDtoList = csvReader.processCsvFile(inputStream);
        assertEquals(4, dealDtoList.size());
    }

    private InputStream asStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}
