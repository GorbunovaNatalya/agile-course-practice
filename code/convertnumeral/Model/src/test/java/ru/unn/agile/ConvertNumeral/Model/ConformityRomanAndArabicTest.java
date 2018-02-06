package ru.unn.agile.ConvertNumeral.Model;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class ConformityRomanAndArabicTest {
    @Test
    public void match100AndC() {
        assertEquals(ConformityRomanAndArabic.getRomanNumber(100), "C");
    }

    @Test
    public void match1000AndM() {
        assertEquals(ConformityRomanAndArabic.getRomanNumber(1000), "M");
    }

    @Test
    public void match500AndD() {
        assertEquals(ConformityRomanAndArabic.getArabicNumber("D"), (Integer) 500);
    }

    @Test
    public void match400AndCD() {
        assertEquals(ConformityRomanAndArabic.getArabicNumber("CD"), (Integer) 400);
    }

    @Test
    public void gettingIteratorOfRoman() {
        Iterator<String> iterator = ConformityRomanAndArabic.getRomanIterator();
        assertEquals(iterator.next(), "M");
        assertEquals(iterator.next(), "CM");
    }

    @Test
    public void gettingIteratorOfArabic() {
        Iterator<Integer> iterator = ConformityRomanAndArabic.getArabicIterator();
        assertEquals(iterator.next(), (Integer) 1000);
        assertEquals(iterator.next(), (Integer) 900);
    }
}
