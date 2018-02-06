package ru.unn.agile.NumberSystemConverter.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConvertNumberTest {

    @Test
    public void utilClassWillBeCreatedButItsConstructorIsPrivate() throws Exception {
        Constructor constructor = NumberSystemConverter.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }

    @Test
    public void canConvertZeroInDEC2BIN() {
        String input = "0";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.BIN);
        assertEquals("0", output);
    }

    @Test
    public void canConvertZeroInDEC2OCT() {
        String input = "0";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.OCT);
        assertEquals("0", output);
    }

    @Test
    public void canConvertZeroInHEX2DEC() {
        String input = "0";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.HEX);
        assertEquals("0", output);
    }

    @Test
    public void canConvertZeroInOCT2HEX() {
        String input = "0";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.OCT,
                NumberSystemBase.HEX);
        assertEquals("0", output);
    }

    @Test
    public void canConvertZeroInBIN2OCT() {
        String input = "0";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.BIN,
                NumberSystemBase.OCT);
        assertEquals("0", output);
    }

    @Test
    public void canSimpleConvertDEC2BIN() {
        String input = "3";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.BIN);
        assertEquals("11", output);
    }

    @Test
    public void canTwoDigitConvertDEC2BIN() {
        String input = "12";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.BIN);
        assertEquals("1100", output);
    }

    @Test
    public void canSimpleConvertDEC2OCT() {
        String input = "7";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.OCT);
        assertEquals("7", output);
    }

    @Test
    public void canConvertDEC2OCT() {
        String input = "42";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.OCT);
        assertEquals("52", output);
    }

    @Test
    public void canSimpleConvertDEC2HEX() {
        String input = "19";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.HEX);
        assertEquals("13", output);
    }

    @Test
    public void canConvertDEC2HEXwithOutHaveLetterA() {
        String input = "42";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.HEX);
        assertEquals("2A", output);
    }

    @Test
    public void canConvertDEC2HEXwithOutHaveAllLetters() {
        String input = "11259375";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.HEX);
        assertEquals("ABCDEF", output);
    }

    @Test
    public void canSimpleConvertBIN2DEC() {
        String input = "01";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.BIN,
                NumberSystemBase.DEC);
        assertEquals("1", output);
    }

    @Test
    public void canTwoBitDepthConvertBIN2DEC() {
        String input = "011";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.BIN,
                NumberSystemBase.DEC);
        assertEquals("3", output);
    }

    @Test
    public void canLongValueConvertBIN2DEC() {
        String input = "0110101";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.BIN,
                NumberSystemBase.DEC);
        assertEquals("53", output);
    }

    @Test
    public void canConvertBIN2OCT() {
        String input = "0110101";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.BIN,
                NumberSystemBase.OCT);
        assertEquals("65", output);
    }

    @Test
    public void canConvertBIN2HEX() {
        String input = "0110101";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.BIN,
                NumberSystemBase.HEX);
        assertEquals("35", output);
    }

    @Test
    public void canSimpleConvertOCT2DEC() {
        String input = "7";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.OCT,
                NumberSystemBase.DEC);
        assertEquals("7", output);
    }

    @Test
    public void canConvertOCT2BIN() {
        String input = "0110101";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.OCT,
                NumberSystemBase.BIN);
        assertEquals("1001000001000001", output);
    }

    @Test
    public void canConvertOCT2BINwhenInputHaveBigValue() {
        String input = "777777";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.OCT,
                NumberSystemBase.BIN);
        assertEquals("111111111111111111", output);
    }

    @Test
    public void canSimpleConvertOCT2BIN() {
        String input = "5";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.OCT,
                NumberSystemBase.BIN);
        assertEquals("101", output);
    }

    @Test
    public void canConvertOCT2DECwhenInHaveThreeDigit() {
        String input = "267";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.OCT,
                NumberSystemBase.DEC);
        assertEquals("183", output);
    }

    @Test
    public void canConvertOCT2HEX() {
        String input = "175";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.OCT,
                NumberSystemBase.HEX);
        assertEquals("7D", output);
    }

    @Test
    public void canSimpleConvertHEX2DEC() {
        String input = "9";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.DEC);
        assertEquals("9", output);
    }

    @Test
    public void canSimpleConvertHEX2BIN() {
        String input = "9";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.BIN);
        assertEquals("1001", output);
    }

    @Test
    public void canSimpleConvertHEX2OCT() {
        String input = "9";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.OCT);
        assertEquals("11", output);
    }

    @Test
    public void canConvertHEX2DECwhenInLetter() {
        String input = "B";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.DEC);
        assertEquals("11", output);
    }

    @Test
    public void canConvertHEX2DECwhenInHaveAllLetters() {
        String input = "ABCDEF";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.DEC);
        assertEquals("11259375", output);
    }

    @Test
    public void canConvertHEX2BIN() {
        String input = "AB8";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.BIN);
        assertEquals("101010111000", output);
    }

    @Test
    public void canConvertHEX2OCT() {
        String input = "AB8";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.OCT);
        assertEquals("5270", output);
    }

    @Test(expected = IllegalArgumentException.class)
    public void conversionBIN2DECThrowsIllegalArgumentExceptionWhenInWrongCalculationSystem() {
        String input = "AB8";
        NumberSystemConverter.convert(input, NumberSystemBase.BIN, NumberSystemBase.DEC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void conversionOCT2DECThrowsIllegalArgumentExceptionWhenInWrongCalculationSystem() {
        String input = "192";
        NumberSystemConverter.convert(input, NumberSystemBase.OCT, NumberSystemBase.DEC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void conversionHEX2DECThrowsIllegalArgumentExceptionWhenInWrongCalculationSystem() {
        String input = "H5UAL";
        NumberSystemConverter.convert(input, NumberSystemBase.HEX, NumberSystemBase.DEC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void conversionDEC2BINThrowsIllegalArgumentExceptionWhenInWrongCalculationSystem() {
        String input = "AB8";
        NumberSystemConverter.convert(input, NumberSystemBase.DEC, NumberSystemBase.BIN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertionDEC2HEXThrowsIllegalArgumentExceptionWhenInWrongCalculationSystem() {
        String input = "AB8";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.HEX);
    }

    @Test
    public void canConvertHEX2DECWhenInputMaxIntegerValue() {
        String input = "7FFFFFFF";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.DEC);
        assertEquals("2147483647", output);
    }

    @Test
    public void canConvertHEX2DECWhenInputIsMaxUnsignedIntegerValue() {
        String input = "FFFFFFFF";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.HEX,
                NumberSystemBase.DEC);
        assertEquals("4294967295", output);
    }

    @Test
    public void canConvertDEC2HEXWhenInputIsMaxUnsignedIntegerValue() {
        String input = "4294967295";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.HEX);
        assertEquals("FFFFFFFF", output);
    }

    @Test
    public void canConvertBIN2HEXWhenInputIsEmpty() {
        String input = "";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.BIN,
                NumberSystemBase.HEX);
        assertTrue(output.isEmpty());
    }

    @Test
    public void canConvertOCT2HEXWhenInputIsEmpty() {
        String input = "";
        String output = NumberSystemConverter.convert(input, NumberSystemBase.OCT,
                NumberSystemBase.HEX);
        assertTrue(output.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void conversionOCT2HEXThrowsIllegalArgumentExceptionWhenInputIsNull() {
        String input = null;
        NumberSystemConverter.convert(input, NumberSystemBase.OCT, NumberSystemBase.HEX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void conversionBIN2HEXThrowsIllegalArgumentExceptionWhenInputIsNull() {
        String input = null;
        NumberSystemConverter.convert(input, NumberSystemBase.BIN, NumberSystemBase.HEX);
    }

    @Test(expected = NumberFormatException.class)
    public void conversionBIN2HEXThrowsInvalidInputExceptionWhenInputIsTrash() {
        String input = "$@&-*€£";
        NumberSystemConverter.convert(input, NumberSystemBase.BIN, NumberSystemBase.HEX);
    }

    @Test(expected = NumberFormatException.class)
    public void conversionDEC2HEXThrowsInvalidInputExceptionWhenInputIsTrash() {
        String input = "$@&-*€£";
        NumberSystemConverter.convert(input, NumberSystemBase.DEC, NumberSystemBase.HEX);
    }

    @Test
    public void canConvertNumberGreaterThenMaxLongFromDecToHex() {
        String input = String.valueOf(Long.MAX_VALUE);
        input = input.concat("1");
        String result = NumberSystemConverter.convert(input, NumberSystemBase.DEC,
                NumberSystemBase.HEX);
        assertEquals("4FFFFFFFFFFFFFFF7", result);
    }

    @Test
    public void canConvertNumberGreaterThenMaxLongFromHexToBin() {
        String result = NumberSystemConverter.convert("4FFFFFFFFFFFFFFF7", NumberSystemBase.HEX,
                NumberSystemBase.BIN);
        assertEquals("1001111111111111111111111111111111111111111111111111111111111110111",
                result);
    }

    @Test
    public void canConvertNumberGreaterThenMaxLongFromBinToOct() {
        String input = "1001111111111111111111111111111111111111111111111111111111111110111";
        String result = NumberSystemConverter.convert(input, NumberSystemBase.BIN,
                NumberSystemBase.OCT);
        assertEquals("11777777777777777777767", result);
    }
}
