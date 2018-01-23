package ru.unn.agile.ConvertNumeral.Model;

import java.security.InvalidParameterException;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConvertNumeralTest {
    @Test
    public void convert1toI() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(1);
        assertEquals("I", romanNumber);
    }

    @Test
    public void convert2toII() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(2);
        assertEquals("II", romanNumber);
    }

    @Test
    public void convert5toV() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(5);
        assertEquals("V", romanNumber);
    }

    @Test
    public void convert10toX() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(10);
        assertEquals("X", romanNumber);
    }

    @Test
    public void convert50toL() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(50);
        assertEquals("L", romanNumber);
    }

    @Test
    public void convert6toVI() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(6);
        assertEquals("VI", romanNumber);
    }

    @Test
    public void convert7toVII() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(7);
        assertEquals("VII", romanNumber);
    }

    @Test
    public void convert4toIV() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(4);
        assertEquals("IV", romanNumber);
    }

    @Test
    public void convert9toIX() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(9);
        assertEquals("IX", romanNumber);
    }

    @Test
    public void convert14toXIV() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(14);
        assertEquals("XIV", romanNumber);
    }

    @Test
    public void convert44toXLIV() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(44);
        assertEquals("XLIV", romanNumber);
    }

    @Test
    public void convert40toXIV() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(40);
        assertEquals("XL", romanNumber);
    }

    @Test
    public void convert139toCXXXIX() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(139);
        assertEquals("CXXXIX", romanNumber);
    }

    @Test
    public void convert458toCDLVIII() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(458);
        assertEquals("CDLVIII", romanNumber);
    }

    @Test
    public void convert3999toMMMCMXCIX() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(3999);
        assertEquals("MMMCMXCIX", romanNumber);
    }

    @Test
    public void convert0toEmptyString() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(0);
        assertEquals("", romanNumber);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvert4000() {
        ConvertNumeral converter = new ConvertNumeral();
        String romanNumber = converter.convert(4000);
    }

    @Test
    public void convertIto1() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("I");
        assertEquals(1, arabicNumber);
    }

    @Test
    public void convertIVto4() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("IV");
        assertEquals(4, arabicNumber);
    }

    @Test
    public void convertIIto2() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("II");
        assertEquals(2, arabicNumber);
    }

    @Test
    public void convertIIIto3() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("III");
        assertEquals(3, arabicNumber);
    }

    @Test
    public void convertXIVto14() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("XIV");
        assertEquals(14, arabicNumber);
    }

    @Test
    public void convertVIIIto8() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("VIII");
        assertEquals(8, arabicNumber);
    }

    @Test
    public void convertIXto9() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("IX");
        assertEquals(9, arabicNumber);
    }

    @Test
    public void convertXIXto19() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("XIX");
        assertEquals(19, arabicNumber);
    }

    @Test
    public void convertCXXXVIIIto138() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("CXXXVIII");
        assertEquals(138, arabicNumber);
    }

    @Test
    public void convertCMLXXXIXto989() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("CMLXXXIX");
        assertEquals(989, arabicNumber);
    }

    @Test
    public void convertMMMCMXCIXto3999() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("MMMCMXCIX");
        assertEquals(3999, arabicNumber);
    }

    @Test
    public void convertEmptyStringTo0() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("");
        assertEquals(0, arabicNumber);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertVXI() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("VXI");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertVCI() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("VCI");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertXXXX() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("XXXX");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertXMI() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("XMI");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertCCXMCC() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("CCXMCC");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertLL() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("LL");
    }

    @Test
    public void convertMCMto1900() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("MCM");
        assertEquals(1900, arabicNumber);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertXCX() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("XCX");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertIVI() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("IVI");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotConvertMCDC() {
        ConvertNumeral converter = new ConvertNumeral();
        int arabicNumber = converter.convert("MCDC");
    }
}
