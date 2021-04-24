package com.clusus.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class CSVHelperTest {
    @Test
    void testFileFormatForCSV() throws IOException {
        FileInputStream fis = new FileInputStream("deal.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("deal","deal","text/csv",fis);
        String contentType = multipartFile.getContentType();
        assertTrue(CSVHelper.hasCSVFormat(multipartFile));
    }

}
