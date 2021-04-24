package com.clusus.util;

import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    public static String FILE_TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!FILE_TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
}
