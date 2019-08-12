package com.company.editor.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StringInterpretation {

    public static String getStringFromStrings(String[] firstStrings, String[] secondStrings, String chosenString) {
        if (firstStrings.length != secondStrings.length)
            throw new IllegalArgumentException();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < secondStrings.length; i++) {
            map.put(firstStrings[i], secondStrings[i]);
        }
        return map.get(chosenString);
    }

}
