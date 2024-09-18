package com.collin.booksocial.file;

import com.collin.booksocial.book.Book;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

/**
 * Service for handling file storage operations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    /**
     * The base directory path where uploaded photos will be stored.
     * This path is configured via the external application properties file using the key `application.file.upload.photos-output-path`.
     */
    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    /**
     * Saves a file to a specific user directory.
     *
     * @param sourceFile the file to be saved.
     * @param userId the ID of the user for whom the file is being saved.
     * @return the path where the file was saved, or null if the save operation failed.
     */
    public String saveFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull Integer userId
    ) {
        final String fileUploadSubPath = "users" + separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    /**
     * Uploads the given file to a specified sub-path within the upload directory.
     *
     * @param sourceFile The file to be uploaded. Must not be null.
     * @param fileUploadSubPath The sub-path within the upload directory where the file should be stored. Must not be null.
     * @return The path to the uploaded file if the upload is successful; null otherwise.
     */
    private String uploadFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull String fileUploadSubPath
    ) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);

        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Failed to create the target folder: " + targetFolder);
                return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to: " + targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File was not saved", e);
        }
        return null;
    }

    /**
     * Extracts the file extension from a given file name.
     *
     * @param fileName The name of the file from which to extract the extension.
     * @return The file extension in lower case, or an empty string if the file name
     *         does not contain an extension or is null/empty.
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}
