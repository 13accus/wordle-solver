package com.baccus.worlde.solve.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RulesServiceImpl implements  RulesService {

    private static final char WILDCARD = '*';
    private static final int MAX_WORD_INDEX = 4;
    private static final Integer CHARACTER_NOT_FOUND = -1;
    
    @Override
    public boolean isConsistentWithUnformedWord(String unformedWord, String word) {
        return isConsistentWithUnformedWord(unformedWord,word,0);
    }

    private static boolean isConsistentWithUnformedWord(String unformedWord, String word, int index) {
        if (index > MAX_WORD_INDEX)
            return true;

        return (unformedWord.charAt(index) == word.charAt(index) || unformedWord.charAt(index) == WILDCARD)
                && isConsistentWithUnformedWord(unformedWord, word, index + 1);
    }

    @Override
    public boolean isConsistentWithKnownLetters(String word, Map<Character, Integer> knownLetters) {
        return knownLetters
                .keySet()
                .stream()
                .map(c -> isConsistentWithKnownLetters(word, knownLetters.get(c), c, 0))
                .reduce(true, Boolean::logicalAnd);
    }

    private static Boolean isConsistentWithKnownLetters(String word, int minOccurence, Character letter, int index) {
        if (word.length() == index)
            return minOccurence < 1;

        return (word.charAt(index) == letter) ?
                isConsistentWithKnownLetters(word, minOccurence - 1, letter, index + 1) :
                isConsistentWithKnownLetters(word, minOccurence, letter, index + 1);
    }
    
    @Override
    public boolean isConsistentWithPreviousTries(String unformedWord, String word, Set<Character> characters, Set<String> prevTries) {
        return isConsistentWithPreviousTries(unformedWord, word, characters, prevTries,0);
    }

    private static boolean isConsistentWithPreviousTries(String unformedWord,
                                                         String word,
                                                         Set<Character> knownLetters,
                                                         Set<String> previousTries,
                                                         int index) {
        if (index > MAX_WORD_INDEX)
            return true;

        return previousTries
                .stream()
                .map(previousTry -> validPosition(word, previousTry, unformedWord, 0) &&
                        containOnlyValidLetters(unformedWord, word, knownLetters, previousTries) &&
                        isConsistentWithPreviousTries(unformedWord, word, knownLetters, previousTries, index + 1)
                )
                .reduce(true, Boolean::logicalAnd);
    }

    private static boolean validPosition(String word, String previousWord, String unformedWord, int index) {
        if (index > MAX_WORD_INDEX)
            return true;

        return (unformedWord.charAt(index) == word.charAt(index) || !(word.charAt(index) == previousWord.charAt(index)))
                && validPosition(word, previousWord, unformedWord, index + 1);
    }

    private static boolean containOnlyValidLetters(String unformedWord,
                                                   String word,
                                                   Set<Character> knownLetters,
                                                   Set<String> previousTries) {
        return previousTries
                .stream()
                .flatMap(previousTry -> previousTry.chars().mapToObj(e -> (char) e))
                .distinct()
                .filter(character -> !knownLetters.contains(character))
                .filter(character -> !unformedWord.contains(character.toString()))
                .map(word::indexOf)
                .map(CHARACTER_NOT_FOUND::equals)
                .reduce(true, Boolean::logicalAnd);
    }

    @Override
    public boolean isConsistentWithDistinctCharacters(boolean distinctCharacterOnly, String word) {
        return !distinctCharacterOnly || wordHasDistinctCharacters(word);
    }

    private static boolean wordHasDistinctCharacters(String word) {
        return word.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()).size() == word.length();
    }
}
