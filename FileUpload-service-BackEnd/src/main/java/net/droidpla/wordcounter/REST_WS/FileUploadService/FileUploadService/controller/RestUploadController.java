package net.droidpla.wordcounter.REST_WS.FileUploadService.FileUploadService.controller;

import net.droidpla.wordcounter.REST_WS.FileUploadService.FileUploadService.model.UploadModel;
import net.droidpla.wordcounter.REST_WS.FileUploadService.FileUploadService.service.UploadService;
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
    private String UPLOAD_FOLDER = "/Users/min2ha/temp/";

    @Autowired
    UploadService uploadService; //Service which will do all data retrieval/manipulation work

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Single file upload!");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            uploadService.saveUploadedFiles(Arrays.asList(uploadfile), UPLOAD_FOLDER);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("extraField") String extraField,
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


    @PostMapping("/upload/multi/model")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {

        logger.debug("Multiple file upload! With UploadModel");
        try {
            uploadService.saveUploadedFiles(Arrays.asList(model.getFiles()),UPLOAD_FOLDER);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);

    }

    @GetMapping(value = "/getFileList", produces = MediaType.APPLICATION_JSON_VALUE)
    //get file list in Upload Folder //InUploadFolder
    public ResponseEntity<List<String>> getFileList() {
        logger.debug("Get File List");
        return new ResponseEntity<>(uploadService.findAllFiles(UPLOAD_FOLDER), HttpStatus.OK);
    }

}
