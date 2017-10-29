package net.droidpla.wordcounter.REST_WS.FileUploadService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by min2ha on 21/10/2017.
 */
@Service("uploadService")
public class UploadService {
    private final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private static final AtomicLong counter = new AtomicLong();
    private static List<String> files;

    /**
     * Find All Files
     * @param UPLOAD_FOLDER
     * @return
     */
    public List<String> findAllFiles(String UPLOAD_FOLDER) {
        logger.debug("findAllFiles");

        File[] files = new File(UPLOAD_FOLDER).listFiles();
        List<String> fileNames = Arrays
                .asList(files)
                .parallelStream()
                .map(file -> file.getName())
                .collect(Collectors.toList());

        for(String fileName : fileNames) {
            System.out.println(fileName.toString());
        }

        return fileNames;//files;
    }

    /**
     * Save Files to the Folder specified in parameter
     * @param files
     * @param UPLOADED_FOLDER
     * @throws IOException
     */
    public void saveUploadedFiles(List<MultipartFile> files, String UPLOADED_FOLDER) throws IOException {

        logger.debug("saveUploadedFiles");

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; //next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }

}
