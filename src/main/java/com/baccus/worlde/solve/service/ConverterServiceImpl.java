package com.baccus.worlde.solve.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ConverterServiceImpl implements ConverterService {
    @Override
    public Map<Character, Integer> charCountMapFrom(List<Character> characters) {
        return characters
                .parallelStream()
                .map(character -> Map.entry(character,getCount(characters,character)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Integer getCount(List<Character> characters, @NonNull Object key) {
        return (int) characters
                .stream()
                .filter(character -> character.equals(key))
                .count();
    }
}
