package net.droidpla.wordcounter.restws.wordcounterservices.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by min2ha on 21/10/2017.
 */
public interface WordCounter {


    void processFile(ConcurrentHashMap<String, LongAdder> wordCounts, String fileName , int topLimit) ;

    List<String> findAllWords();

    void updateConcurrentHashMap();

}
