package com.baccus.worlde.solve.controller;

import com.baccus.worlde.solve.request.SolveRequestBodyDto;
import com.baccus.worlde.solve.service.ConverterService;
import com.baccus.worlde.solve.service.FilterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.SortedSet;

@RestController
@Log4j2
public class SolveController {

    @Autowired
    ConverterService converterService;

    @Autowired
    FilterService filterService;

    @GetMapping("/solve")
    public SortedSet<String> solve(@RequestBody @Validated SolveRequestBodyDto solveRequestBodyDto) {

        log.info("RECEIVED REQUEST: {}", solveRequestBodyDto.toString());

        return filterService
                .filter(
                        solveRequestBodyDto.getUnknownWord(),
                        converterService.charCountMapFrom(solveRequestBodyDto.getKnownCharacters()),
                        solveRequestBodyDto.getPreviousWords(),
                        solveRequestBodyDto.isDistinctCharactersOnly()
                );
    }
}
