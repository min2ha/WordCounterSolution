package net.droidpla.wordcounter.restws.wordcounterservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;

/**
 * Created by min2ha on 21/10/2017.
 */
@Service("wordCounterService")
public class WordCounterServiceImpl implements WordCounter{
    private final Logger logger = LoggerFactory.getLogger(WordCounterServiceImpl.class);

    @Override
    //@Async
    public void processFile(ConcurrentHashMap<String, LongAdder> wordCounts, String pathFileName, int topLimit) {

        logger.debug("Process File: " + pathFileName);

        long start = Instant.now().toEpochMilli();
        Path filePath = Paths.get(pathFileName);

        try {
            Files.readAllLines(filePath)
                    .parallelStream()                               // Start streaming the lines
                    .map(line -> line.split("\\s+"))                // Split line into individual words
                    .flatMap(Arrays::stream)                        // Convert stream of String[] to stream of String
                    .parallel()                                     // Convert to parallel stream
                    .filter(w -> w.matches("\\w+"))                 // Filter out non-word items
                    .map(String::toLowerCase)                       // Convert to lower case
                    .forEach(word -> {                              // Use an AtomicAdder to tally word counts
                        if (!wordCounts.containsKey(word))          // If a hashmap entry for the word doesn't exist yet
                            wordCounts.put(word, new LongAdder());  // Create a new LongAdder
                        wordCounts.get(word).increment();           // Increment the LongAdder for each instance of a word
                    });
            long end = Instant.now().toEpochMilli();
            logger.debug(String.format("\tCompleted in %d milliseconds", (end - start)));
        }
        catch (IOException e){
            logger.debug("IOException: " + e.toString());
        }
        catch (Exception e){
            logger.debug("Exception: " + e.toString());
        }
    }

    @Override
    public void writeMapDataToFile(ConcurrentHashMap<String, LongAdder> wordCounts2, FileWriter fw, int topLimit) {

        logger.debug("writeMapDataToFile");

        if (wordCounts2 != null){
            logger.info("writeMapDataToFile - wordCounts not null,\t TOPLimit = " + topLimit );
            logger.debug("writeMapDataToFile - wordCounts not null,\t TOPLimit = " + topLimit );

            wordCounts2
                    .keySet()
                    .stream()
                    .map(key -> String.format("%-10d %s", wordCounts2.get(key).intValue(), key))
                    .sorted((prev, next) -> compare(parseInt(next.split("\\s+")[0]), parseInt(prev.split("\\s+")[0])))
                    .limit(topLimit)
                    .forEach(t -> writeToFile(fw, t) );
        }
        else{
            logger.debug("writeMapDataToFile - wordCounts is NULL");
        }
    }

    @Override
    public List<String> mapToList(ConcurrentHashMap<String, LongAdder> mapWordCounts, int topLimit) {
        logger.debug("mapToList");
        return
                (mapWordCounts
                        .keySet()//.entrySet()
                        .stream()
                        .map(key -> String.format("%-10d %s", mapWordCounts.get(key).intValue(), key))
                        .sorted((prev, next) -> compare(parseInt(next.split("\\s+")[0]), parseInt(prev.split("\\s+")[0])))
                        .collect(Collectors.toList())).subList(0, topLimit); //all words - long list

    }

    private void writeToFile(FileWriter fw, String stringdata) {
        logger.debug("writeToFile");
        try {
            fw.write(String.format("%s%n", stringdata));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}