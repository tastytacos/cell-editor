package com.company.editor.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringInterpretationTest {

    @Test
    public void testGenerateString(){
        String[] actual = {"Hello", "Test", "Here"};
        String expected = "HelloTestHere";
        assertEquals(expected, StringInterpretation.generateString(actual));
        String[] actual1 = {" Hello ", "Test ", "Here "};
        String expected1 = " Hello Test Here ";
        assertEquals(expected1, StringInterpretation.generateString(actual1));
        String[] actual3 = {"", "", ""};
        String expected3 = "";
        assertEquals(expected3, StringInterpretation.generateString(actual3));
    }

    @Test(expected = NullPointerException.class)
    public void testGenerateStringNull(){
        String[] actual = null;
        StringInterpretation.generateString(actual);
    }

    //todo write tests for getStringFromStrings method

}