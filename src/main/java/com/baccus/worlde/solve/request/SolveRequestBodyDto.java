package com.baccus.worlde.solve.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@ToString
public class SolveRequestBodyDto {

    @NotNull
    Set<String> previousWords;
    @NonNull
    List<Character> knownCharacters;
    @NotNull
    String unknownWord;

    boolean distinctCharactersOnly;

}
