package ru.unn.agile.StringCalculator.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringCalculatorTest {
    @Test
    public void canAddStringWithOneNumber() {
        assertEquals(1, StringCalculator.add("1"));
    }

    @Test
    public void canAddStringWithTwoNumbers() {
        assertEquals(3, StringCalculator.add("1,2"));
    }

    @Test
    public void canAddStringWithThreeNumbers() {
        assertEquals(6, StringCalculator.add("1,2,3"));
    }

    @Test
    public void canAddStringWithFourNumbers() {
        assertEquals(10, StringCalculator.add("1,2,3,4"));
    }

    @Test
    public void canAddStringWithALotOfNumbers() {
        assertEquals(210,
                StringCalculator.add("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20"));
    }

    @Test
    public void canAddStringWithNewLineSymbol() {
        assertEquals(6, StringCalculator.add("1\n2,3"));
    }

    @Test
    public void canAddStringWithNewDelimiter() {
        assertEquals(6, StringCalculator.add(";\n1;2;3"));
    }

    @Test
    public void canAddStringWithDotDelimiter() {
        assertEquals(6, StringCalculator.add(".\n1.2.3"));
    }

    @Test
    public void canAddStringWithDotDelimiterAndNewLineSymbol() {
        assertEquals(6, StringCalculator.add(".\n1\n2.3"));
    }

    @Test
    public void canAddStringWithNegativeNumbers() {
        try {
            StringCalculator.add("-1,2,3");
        } catch (IllegalArgumentException e) {
            assertEquals("Negatives -1 not allowed", e.getMessage());
        }
    }

    @Test
    public void canAddStringWithTwoNegativeNumbers() {
        try {
            StringCalculator.add("-1,2,-3");
        } catch (IllegalArgumentException e) {
            assertEquals("Negatives -1 -3 not allowed", e.getMessage());
        }
    }

    @Test
    public void canAddStringWithAllNegativeNumbers() {
        try {
            StringCalculator.add("-1,-2,-3");
        } catch (IllegalArgumentException e) {
            assertEquals("Negatives -1 -2 -3 not allowed", e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void canAddStringWithoutNumbers() {
        StringCalculator.add("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void canAddStringWithIncorrectData() {
        StringCalculator.add("sadfasdf");
    }

    @Test (expected = IllegalArgumentException.class)
    public void canAddStringWithMistake() {
        StringCalculator.add("1,2,3s");
    }

    @Test (expected = IllegalArgumentException.class)
    public void canAddStringWithNull() {
        StringCalculator.add(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void canAddStringWithDifferentDelimiters() {
        StringCalculator.add("1,2;3.4:5");
    }

    @Test (expected = IllegalArgumentException.class)
    public void canAddStringWithoutDelimiterAtBeginning() {
        StringCalculator.add("1.2.3.4.5");
    }

    @Test (expected = IllegalArgumentException.class)
    public void canAddStringWithDelimiterWithoutNewLine() {
        StringCalculator.add(";1;2;3");
    }

    @Test (expected = IllegalArgumentException.class)
    public void canAddStringWithBigNumbers() {
        StringCalculator.add("2147483647, 2147483647");
    }

    @Test (expected = IllegalArgumentException.class)
    public void canAddStringWithOneBigNumber() {
        StringCalculator.add("2147483648, 1");
    }

    @Test
    public void isBadFormatReturnsTrueWhenIncorrectData() {
         assertTrue(StringCalculator.isBadFormat("2,a"));
     }

    @Test
    public void isBadFormatReturnsTrueWhenNegativeNumberInData() {
        assertTrue(StringCalculator.isBadFormat("2,-2"));
    }

    @Test
    public void isBadFormatReturnsFalseWhenCorrectData() {
        assertFalse(StringCalculator.isBadFormat("2,2"));
    }

    @Test
    public void isBadFormatReturnsFalseWhenCorrectDelimiter() {
        assertFalse(StringCalculator.isBadFormat(";\n2;2"));
    }

    @Test
    public void isBadFormatReturnsTrueWhenIncorrectDelimiter() {
        assertTrue(StringCalculator.isBadFormat("2.4"));
    }

    @Test
    public void isBadFormatReturnsTrueWhenDelimiterWithoutNewLine() {
        assertTrue(StringCalculator.isBadFormat(".2.4"));
    }
}
