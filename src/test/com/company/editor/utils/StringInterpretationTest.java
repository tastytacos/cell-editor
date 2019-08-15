package com.company.editor.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringInterpretationTest {

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionStringException() {
        String[] firstArray = new String[5];
        String[] secondArray = new String[3];
        StringInterpretation.getStringFromStrings(firstArray, secondArray, "does not matter");
    }

    @Test
    public void testGetStringFromStrings(){
        String[] firstArray = {"One", "Two", "Three"};
        String[] secondArray = {"Some", "Words", "Here"};
        String chosenWord = "Buzz";
        String actual = StringInterpretation.getStringFromStrings(firstArray, secondArray, chosenWord);
        assertNull(actual);

        chosenWord = "One";
        String expected = "Some";
        actual = StringInterpretation.getStringFromStrings(firstArray, secondArray, chosenWord);
        assertEquals(expected, actual);
    }

}