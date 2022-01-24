package com.baccus.worlde.solve.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class FilterServiceImpl implements FilterService {

    @Autowired
    List<String> dictionary;

    @Autowired
    RulesService rulesService;

    @Override
    public SortedSet<String> filter(@NonNull String unformedWord,
                                    @NonNull Map<Character, Integer> knownLetters,
                                    @NonNull Set<String> prevTries,
                                    boolean distinctCharacterOnly) {
        return dictionary
                .parallelStream()
                .filter(word -> rulesService.isConsistentWithUnformedWord(unformedWord, word))
                .filter(word -> rulesService.isConsistentWithKnownLetters(word, knownLetters))
                .filter(word -> rulesService.isConsistentWithPreviousTries(unformedWord, word, knownLetters.keySet(), prevTries))
                .filter(word -> rulesService.isConsistentWithDistinctCharacters(distinctCharacterOnly, word))
                .collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
    }
}
