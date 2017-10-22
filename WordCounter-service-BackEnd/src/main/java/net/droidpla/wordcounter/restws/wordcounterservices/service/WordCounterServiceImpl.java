package net.droidpla.wordcounter.restws.wordcounterservices.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;

/**
 * Created by min2ha on 21/10/2017.
 */
@Service("wordCounterService")
public class WordCounterServiceImpl implements WordCounter{
    private final Logger logger = LoggerFactory.getLogger(WordCounterServiceImpl.class);

    @Override
    @Async
    public void processFile(ConcurrentHashMap<String, LongAdder> wordCounts, String fileName, int topLimit) {

        logger.debug("Process File: " + fileName);

        long start = Instant.now().toEpochMilli();
        Path filePath = Paths.get(fileName);

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
            wordCounts
                    .keySet()
                    .stream()
                    .map(key -> String.format("%-10d %s", wordCounts.get(key).intValue(), key))
                    .sorted((prev, next) -> compare(parseInt(next.split("\\s+")[0]), parseInt(prev.split("\\s+")[0])))
                    .limit(topLimit)
                    .forEach(t -> System.out.println("\t" + t));

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
    public List<String> findAllWords() {
        return null;
    }

    @Override
    public void updateConcurrentHashMap() {

    }


}
