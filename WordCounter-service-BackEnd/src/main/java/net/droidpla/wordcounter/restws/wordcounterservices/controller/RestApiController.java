package net.droidpla.wordcounter.restws.wordcounterservices.controller;


import net.droidpla.wordcounter.restws.wordcounterservices.service.WordCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    private String UPLOAD_FOLDER = "/Users/min2ha/temp/";
    private ConcurrentHashMap<String, LongAdder> mapWordCounts;

    @Autowired
    WordCounter wordCounterService; //Service which will do all data retrieval/manipulation work

    @GetMapping("/getProcessStatus")
    //get file list in Upload Folder
    public ResponseEntity getFileList() {
        logger.debug("Get File List");
        mapWordCounts = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        File[] files = new File(UPLOAD_FOLDER).listFiles();

        List<String> uploadedFileNames = Arrays
                .asList(files)
                .parallelStream()
                .map(file -> file.getName())
                .collect(Collectors.toList());
        uploadedFileNames.forEach(name -> executorService.
                execute(() -> {
                    System.out.println("Asynchronous task - " + name);
                    wordCounterService.processFile(mapWordCounts, UPLOAD_FOLDER+name, 10);
        }));
        executorService.shutdown();
        return new ResponseEntity("Gerai!", HttpStatus.OK);
    }
}