package com.jesusfernandez.superheroapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonParser {

    public static <T> String json(final T element) {
        try {
            return new ObjectMapper().writeValueAsString(element);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
