package com.baccus.worlde.solve.service;

import java.util.Map;
import java.util.Set;

public interface RulesService {

    boolean isConsistentWithUnformedWord(String unformedWord, String word);

    boolean isConsistentWithKnownLetters(String word, Map<Character, Integer> knownLetters);

    boolean isConsistentWithPreviousTries(String unformedWord, String word, Set<Character> characters, Set<String> prevTries);

    boolean isConsistentWithDistinctCharacters(boolean distinctCharacterOnly, String word);

}
