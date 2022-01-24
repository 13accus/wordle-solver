package com.baccus.worlde.solve.service;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public interface FilterService {

    SortedSet<String> filter(String unformedWord,
                             Map<Character, Integer> knownLetters,
                             Set<String> prevTries,
                             boolean onlyDistinctCharacters);

}
