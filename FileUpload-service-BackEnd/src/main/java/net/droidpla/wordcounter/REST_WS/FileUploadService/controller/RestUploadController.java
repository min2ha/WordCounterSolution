package net.droidpla.wordcounter.REST_WS.FileUploadService.controller;

import net.droidpla.wordcounter.REST_WS.FileUploadService.model.UploadModel;
import net.droidpla.wordcounter.REST_WS.FileUploadService.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by min2ha on 19/10/2017.
 */
@RestController
@RequestMapping("/api")
public class RestUploadController {

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);
    private String UPLOAD_FOLDER = "/wordcounter/uploads/";

    @Autowired
    UploadService uploadService; //Service which will do all data retrieval/manipulation work

    /**
     * Upload Multiple Files
     * @param uploadfiles
     * @return
     */
    @PostMapping("/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            //@RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] uploadfiles) {
        logger.debug("Multiple file upload!");
        // Get file name
        String uploadedFileName =
                Arrays
                        .stream(uploadfiles)
                        .map(x -> x.getOriginalFilename())
                        .filter(x -> !StringUtils.isEmpty(x))
                        .collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            uploadService.saveUploadedFiles(Arrays.asList(uploadfiles),UPLOAD_FOLDER);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - "
                + uploadedFileName, HttpStatus.OK);
    }


    /**
     * Get File List in Upload Folder
     * @return
     */
    @GetMapping(value = "/getFileList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getFileList() {
        logger.debug("Get File List");
        return new ResponseEntity<>(uploadService.findAllFiles(UPLOAD_FOLDER), HttpStatus.OK);
    }

}
