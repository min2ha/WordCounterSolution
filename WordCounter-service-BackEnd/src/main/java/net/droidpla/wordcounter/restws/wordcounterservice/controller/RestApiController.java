package net.droidpla.wordcounter.restws.wordcounterservice.controller;


import net.droidpla.wordcounter.restws.wordcounterservice.service.WordCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;

/**
 * Created by min2ha on 21/10/2017.
 */
@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    private String UPLOAD_FOLDER = "/wordcounter/uploads/";
    private String RESULT_FILE = "results.txt";
    private int TOP_RECORD_COUNT = 20;

    private ConcurrentHashMap<String, LongAdder> mapWordCounts;

    /**
     * WordCounter - Service which will do all data retrieval/manipulation/output work
     */
    @Autowired
    WordCounter wordCounterService;

    @GetMapping(value = "/getWordCountProcessStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getWordCountProcessStatus(){//@RequestParam("toplistnumber") String toplistnumber) {
        logger.debug("Get File List");
        mapWordCounts = new ConcurrentHashMap<>();

        //this.TOP_RECORD_COUNT = Integer.valueOf(toplistnumber); //for future needs

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        File[] files = new File(UPLOAD_FOLDER).listFiles();

        List<String> uploadedFileNames = Arrays
                .asList(files)
                .parallelStream()
                .map(file -> file.getName())
                .collect(Collectors.toList());
        //----------------------------------------------
        // Multi Threading - ExecutorService
        //----------------------------------------------
        uploadedFileNames.forEach(name -> executorService.
                execute(() -> {
                    System.out.println("Asynchronous task - " + name);
                    //-------------ConcurrentHashMap wordCounts, String pathFileName , int topLimit
                    wordCounterService.processFile(mapWordCounts, UPLOAD_FOLDER+name, TOP_RECORD_COUNT);
        }));
        executorService.shutdown();
        return new ResponseEntity("Ok!", HttpStatus.OK);
    }

    @GetMapping(value = "/getMapToSortedListStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    //@GetMapping("/getMapToSortedListStatus")
    //get file list in Upload Folder
    public ResponseEntity<List<String>> getMapToSortedListStatus() {
        logger.info("getMapSortedStatus");
        logger.debug("getMapSortedStatus");
        //----------------------------------------------
        //sort and present results:
        //----------------------------------------------

        final List<String> sortedStatsAllWords;

        if (mapWordCounts != null) {

            sortedStatsAllWords = wordCounterService.mapToList(mapWordCounts,TOP_RECORD_COUNT);
            sortedStatsAllWords.forEach(System.out::println);
        }
        else{
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
                    //.status(HttpStatus.NO_CONTENT);
                    //.body("Map Word Count Map is NULL");

        }
        return new ResponseEntity<>(sortedStatsAllWords, HttpStatus.OK);
    }


    //@GetMapping("/getResultOutputToFileStatus")
    @GetMapping(value = "/getResultOutputToFileStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    //get file list in Upload Folder
    public ResponseEntity getResultOutputToFileStatus() {
        logger.debug("getResultOutputToFileStatus");

        logger.debug("UPLOAD_FOLDER+RESULT_FILE = " + UPLOAD_FOLDER+RESULT_FILE);
        if (mapWordCounts != null){

            try {
                FileWriter fw = new FileWriter(UPLOAD_FOLDER+RESULT_FILE);
                wordCounterService.writeMapDataToFile(mapWordCounts, fw, TOP_RECORD_COUNT);
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Map Word Count Map is NULL");

        }
        return new ResponseEntity("Ok!", HttpStatus.OK);
    }

    @GetMapping(value = "/getClearAllStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    //get file list in Upload Folder
    public ResponseEntity getClearAllStatus() {
        logger.debug("getClearAllStatus");
        logger.info("getClearAllStatus");

        //in UPLOAD folder:
        //- delete files
        //- delete results
        Arrays.stream(new File(UPLOAD_FOLDER).listFiles()).forEach(File::delete);

        if (mapWordCounts != null){
            //
            // NULL mapWordCounts
            mapWordCounts.clear();
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Map Word Count Map is NULL");

        }
        return new ResponseEntity("Clear All Status Ok!", HttpStatus.OK);
    }

}