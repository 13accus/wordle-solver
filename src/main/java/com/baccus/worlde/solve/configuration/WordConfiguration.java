package com.baccus.worlde.solve.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Configuration
public class WordConfiguration {

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    List<String> dictionary(@Value("${dictionary.file.name}") String dictionaryFilename) throws IOException {
        return Files.readAllLines(
                resourceLoader
                        .getResource(dictionaryFilename)
                        .getFile()
                        .toPath()
        );
    }

}
