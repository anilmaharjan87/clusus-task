package com.clusus.util;

import org.springframework.web.multipart.MultipartFile;

public class FileFormatValidator {
    public static String FILE_FORMAT = "text/csv";

    public static boolean isValidFileFormat(MultipartFile file) {
        if (!FILE_FORMAT.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
}
