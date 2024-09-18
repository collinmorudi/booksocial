package com.collin.booksocial.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * FileUtils is a utility class that provides methods for file operations.
 * It currently supports reading a file from a specified location.
 */
@Slf4j
public class FileUtils {

    /**
     * Reads a file from the specified location and returns its contents as a byte array.
     *
     * @param fileUrl the location of the file to read
     * @return the contents of the file as a byte array, or null if the file cannot be read
     */
    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return null;
        }
        try {
            Path filePath = new File(fileUrl).toPath();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.warn("Nou file found in the path {}", fileUrl);
        }
        return null;
    }
}