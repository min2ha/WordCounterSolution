package net.droidpla.wordcounter.restws.wordcounterservice.service;

import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by min2ha on 21/10/2017.
 */
public interface WordCounter {

    void processFile(ConcurrentHashMap<String, LongAdder> wordCounts, String pathFileName , int topLimit) ;
    void writeMapDataToFile(ConcurrentHashMap<String, LongAdder> wordCounts, FileWriter fw, int topLimit) ;
    List<String> mapToList(ConcurrentHashMap<String, LongAdder> wordCounts, int topLimit) ;

    //List<String> findAllWords();
    //void updateConcurrentHashMap();

}
