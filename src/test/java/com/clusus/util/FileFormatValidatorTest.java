package com.clusus.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileFormatValidatorTest {
    @Test
    void testFileFormatForCSV() throws IOException {
        FileInputStream fis = new FileInputStream("deal.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("deal","deal","text/csv",fis);
        assertTrue(FileFormatValidator.isValidFileFormat(multipartFile));
    }

    @Test
    void whenPassedOtherThanCSVFileFormat() throws IOException {
        FileInputStream fis = new FileInputStream("deal.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("deal","deal","applcation/json",fis);
        assertFalse(FileFormatValidator.isValidFileFormat(multipartFile));
    }
}
