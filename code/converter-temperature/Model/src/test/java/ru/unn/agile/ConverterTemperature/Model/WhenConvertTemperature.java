package ru.unn.agile.ConverterTemperature.Model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WhenConvertTemperature {
    @Test(expected = IllegalArgumentException.class)
    public void fromCelsiusLessThanAbsoluteZeroToFahrenheitIllegalArgumentExceptionIsThrown() {
        double celsiuses = -300.0;
        ConverterTemperature.convert(celsiuses, Conversion.CELSIUS_TO_FAHRENHEIT);
    }

    @Test
    public void getMinus148FahrenheitsFromMinus100Celsiuses() {
        double celsiuses = -100.0;
        double fahrenheits = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_FAHRENHEIT);
        assertEquals(-148.0, fahrenheits, delta);
    }

    @Test
    public void get0FahrenheitsFromMinus160Div9Celsiuses() {
        double celsiuses = -160.0 / 9.0;
        double fahrenheits = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_FAHRENHEIT);
        assertEquals(0.0, fahrenheits, delta);
    }

    @Test
    public void get41FahrenheitsFrom5Celsiuses() {
        double celsiuses = 5.0;
        double fahrenheits = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_FAHRENHEIT);
        assertEquals(41.0, fahrenheits, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromCelsiusLessThanAbsoluteZeroToKelvinIllegalArgumentExceptionIsThrown() {
        double celsiuses = -300.0;
        ConverterTemperature.convert(celsiuses, Conversion.CELSIUS_TO_KELVIN);
    }

    @Test
    public void get0KelvinsFromMinus273Point15Celsiuses() {
        double celsiuses = -273.15;
        double kelvins = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_KELVIN);
        assertEquals(0.0, kelvins, delta);
    }

    @Test
    public void get299Point15KelvinsFrom26Celsiuses() {
        double celsiuses = 26.0;
        double kelvins = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_KELVIN);
        assertEquals(299.15, kelvins, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromCelsiusLessThanAbsoluteZeroToNewtonIllegalArgumentExceptionIsThrown() {
        double celsiuses = -300.0;
        ConverterTemperature.convert(celsiuses, Conversion.CELSIUS_TO_NEWTON);
    }

    @Test
    public void getMinus8Point58NewtonsFromMinus26Celsiuses() {
        double celsiuses = -26.0;
        double newtons = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_NEWTON);
        assertEquals(-8.58, newtons, delta);
    }

    @Test
    public void get0NewtonsFrom0Celsiuses() {
        double celsiuses = 0.0;
        double newtons = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_NEWTON);
        assertEquals(0.0, newtons, delta);
    }

    @Test
    public void get33NewtonsFrom100Celsiuses() {
        double celsiuses = 100.0;
        double newtons = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_NEWTON);
        assertEquals(33.0, newtons, delta);

    }

    @Test(expected = IllegalArgumentException.class)
    public void fromFahrenheitLessThanAbsoluteZeroToCelsiusIllegalArgumentExceptionIsThrown() {
        double fahrenheits = -500.0;
        ConverterTemperature.convert(fahrenheits, Conversion.FAHRENHEIT_TO_CELSIUS);
    }

    @Test
    public void getMinus20CelsiusesFromMinusMinus4Fahrenheits() {
        double fahrenheits = -4.0;
        double celsiuses = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_CELSIUS);
        assertEquals(-20.0, celsiuses, delta);
    }

    @Test
    public void get0CelsiusesFrom32Fahrenheits() {
        double fahrenheits = 32.0;
        double celsiuses = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_CELSIUS);
        assertEquals(0.0, celsiuses, delta);
    }

    @Test
    public void get5CelsiusesFrom41Fahrenheits() {
        double fahrenheits = 41.0;
        double celsiuses = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_CELSIUS);
        assertEquals(5.0, celsiuses, delta);
    }

    @Test
    public void getTheSameCelsiusesAfterConversionToFahrenheitAndBack() {
        double celsiuses = 26.0;
        double fahrenheits = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_FAHRENHEIT);

        double celsiusesAfterConversion = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_CELSIUS);

        assertEquals(celsiuses, celsiusesAfterConversion, delta);
    }

    @Test
    public void getTheSameFahrenheitsAfterConversionToCelsiusAndBack() {
        double fahrenheits = 71.0;
        double celsiuses = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_CELSIUS);

        double fahrenheitsAfterConversion = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_FAHRENHEIT);

        assertEquals(fahrenheits, fahrenheitsAfterConversion, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromFahrenheitLessThanAbsoluteZeroToKelvinIllegalArgumentExceptionIsThrown() {
        double fahrenheits = -500.0;
        ConverterTemperature.convert(fahrenheits, Conversion.FAHRENHEIT_TO_KELVIN);
    }

    @Test
    public void get0KelvinsFromMinus459Point67Fahrenheits() {
        double fahrenheits = -459.67;
        double kelvins = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_KELVIN);
        assertEquals(0.0, kelvins, delta);
    }

    @Test
    public void get300KelvinsFromMinus80Point33Fahrenheits() {
        double fahrenheits = 80.33;
        double kelvins = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_KELVIN);
        assertEquals(300.0, kelvins, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromFahrenheitLessThanAbsoluteZeroToNewtonIllegalArgumentExceptionIsThrown() {
        double fahrenheits = -500.0;
        ConverterTemperature.convert(fahrenheits, Conversion.FAHRENHEIT_TO_NEWTON);
    }

    @Test
    public void getMinus33NewtonsFromMinus148Fahrenheits() {
        double fahrenheits = -148.0;
        double newtons = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_NEWTON);
        assertEquals(-33.0, newtons, delta);
    }

    @Test
    public void getMinus0NewtonsFrom32Fahrenheits() {
        double fahrenheits = 32.0;
        double newtons = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_NEWTON);
        assertEquals(0.0, newtons, delta);
    }

    @Test
    public void get49Point5NewtonsFrom302Fahrenheits() {
        double fahrenheits = 302.0;
        double newtons = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_NEWTON);
        assertEquals(49.5, newtons, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromKelvinLessThanAbsoluteZeroToCelsiusIllegalArgumentExceptionIsThrown() {
        double kelvins = -1.0;
        ConverterTemperature.convert(kelvins, Conversion.KELVIN_TO_CELSIUS);
    }

    @Test
    public void getMinus26CelsiusesFrom247Point15Kelvins() {
        double kelvins = 247.15;
        double celsiuses = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_CELSIUS);
        assertEquals(-26.0, celsiuses, delta);
    }

    @Test
    public void get0CelsiusesFrom273Point15Kelvins() {
        double kelvins = 273.15;
        double celsiuses = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_CELSIUS);
        assertEquals(0.0, celsiuses, delta);
    }

    @Test
    public void get17CelsiusesFrom290Point15Kelvins() {
        double kelvins = 290.15;
        double celsiuses = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_CELSIUS);
        assertEquals(17.0, celsiuses, delta);
    }

    @Test
    public void getTheSameCelsiusesAfterConversionToKelvinAndBack() {
        double celsiuses = 26.0;
        double kelvins = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_KELVIN);

        double celsiusAfterConversion = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_CELSIUS);

        assertEquals(celsiuses, celsiusAfterConversion, delta);
    }

    @Test
    public void getTheSameKelvinsAfterConversionToCelsiusAndBack() {
        double kelvins = 71.0;
        double celsiuses = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_CELSIUS);

        double kelvinsAfterConversion = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_KELVIN);

        assertEquals(kelvins, kelvinsAfterConversion, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromKelvinLessThanAbsoluteZeroToFahrenheitIllegalArgumentExceptionIsThrown() {
        double kelvins = -1.0;
        ConverterTemperature.convert(kelvins, Conversion.KELVIN_TO_FAHRENHEIT);
    }

    @Test
    public void getMinus103FahrenheitsFrom198Point15Kelvins() {
        double kelvins = 198.15;
        double fahrenheits = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_FAHRENHEIT);
        assertEquals(-103.0, fahrenheits, delta);
    }

    @Test
    public void get0FahrenheitsFrom255Point372222Kelvins() {
        double kelvins = 255.372222;
        double fahrenheits = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_FAHRENHEIT);
        assertEquals(0, fahrenheits, delta);
    }

    @Test
    public void get246FahrenheitsFrom392Point0388888Kelvins() {
        double kelvins = 392.0388888;
        double fahrenheits = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_FAHRENHEIT);
        assertEquals(246, fahrenheits, delta);
    }

    @Test
    public void getTheSameFahrenheitsAfterConversionToKelvinAndBack() {
        double fahrenheits = 26.0;
        double kelvins = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_KELVIN);

        double fahrenheitsAfterConversion = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_FAHRENHEIT);

        assertEquals(fahrenheits, fahrenheitsAfterConversion, delta);
    }

    @Test
    public void getTheSameKelvinsAfterConversionToFahrenheitAndBack() {
        double kelvins = 71.0;
        double fahrenheits = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_FAHRENHEIT);

        double kelvinsAfterConversion = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_KELVIN);

        assertEquals(kelvins, kelvinsAfterConversion, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromKelvinLessThanAbsoluteZeroToNewtonIllegalArgumentExceptionIsThrown() {
        double kelvins = -1.0;
        ConverterTemperature.convert(kelvins, Conversion.KELVIN_TO_NEWTON);
    }

    @Test
    public void getMinus33NewtonsFrom173Point15Kelvins() {
        double kelvins = 173.15;
        double newtons = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_NEWTON);
        assertEquals(-33.0, newtons, delta);
    }

    @Test
    public void get0NewtonsFrom273Point15Kelvins() {
        double kelvins = 273.15;
        double newtons = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_NEWTON);
        assertEquals(0.0, newtons, delta);
    }

    @Test
    public void get16Point5NewtonsFrom323Point15Kelvins() {
        double kelvins = 323.15;
        double newtons = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_NEWTON);
        assertEquals(16.5, newtons, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromNewtonLessThanAbsoluteZeroToCelsiusIllegalArgumentExceptionIsThrown() {
        double newtons = -100.0;
        ConverterTemperature.convert(newtons, Conversion.NEWTON_TO_CELSIUS);
    }

    @Test
    public void getMinus100CelsiusesFromMinus33Newtons() {
        double newtons = -33.0;
        double celsiuses = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_CELSIUS);
        assertEquals(-100.0, celsiuses, delta);
    }

    @Test
    public void getMinus0CelsiusesFrom0Newtons() {
        double newtons = 0.0;
        double celsiuses = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_CELSIUS);
        assertEquals(0.0, celsiuses, delta);
    }

    @Test
    public void get200CelsiusesFrom66Newtons() {
        double newtons = 66.0;
        double celsiuses = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_CELSIUS);
        assertEquals(200.0, celsiuses, delta);
    }

    @Test
    public void getTheSameCelsiusesAfterConversionToNewtonAndBack() {
        double celsiuses = 26.0;
        double newtons = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_NEWTON);

        double celsiusesAfterConversion = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_CELSIUS);

        assertEquals(celsiuses, celsiusesAfterConversion, delta);
    }

    @Test
    public void getTheSameNewtonsAfterConversionToCelsiusAndBack() {
        double newtons = 71.0;
        double celsiuses = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_CELSIUS);

        double newtonsAfterConversion = ConverterTemperature.convert(celsiuses,
            Conversion.CELSIUS_TO_NEWTON);

        assertEquals(newtons, newtonsAfterConversion, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromNewtonLessThanAbsoluteZeroToFahrenheitIllegalArgumentExceptionIsThrown() {
        double newtons = -100.0;
        ConverterTemperature.convert(newtons, Conversion.NEWTON_TO_FAHRENHEIT);
    }

    @Test
    public void getMinus148FahrenheitsFromMinus66Newtons() {
        double newtons = -66.0;
        double fahrenheits = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_FAHRENHEIT);
        assertEquals(-328.0, fahrenheits, delta);
    }

    @Test
    public void get0FahrenheitsFromMinus5Point86666666Newtons() {
        double newtons = -5.86666666;
        double fahrenheits = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_FAHRENHEIT);
        assertEquals(0.0, fahrenheits, delta);
    }

    @Test
    public void get212FahrenheitsFrom33Newtons() {
        double newtons = 33.0;
        double fahrenheits = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_FAHRENHEIT);
        assertEquals(212.0, fahrenheits, delta);
    }

    @Test
    public void getTheSameFahrenheitsAfterConversionToNewtonAndBack() {
        double fahrenheits = 26.0;
        double newtons = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_NEWTON);

        double fahrenheitsAfterConversion = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_FAHRENHEIT);

        assertEquals(fahrenheits, fahrenheitsAfterConversion, delta);
    }

    @Test
    public void getTheSameNewtonsAfterConversionToFahrenheitAndBack() {
        double newtons = 71.0;
        double fahrenheits = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_FAHRENHEIT);

        double newtonsAfterConversion = ConverterTemperature.convert(fahrenheits,
            Conversion.FAHRENHEIT_TO_NEWTON);

        assertEquals(newtons, newtonsAfterConversion, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromNewtonLessThanAbsoluteZeroToKelvinIllegalArgumentExceptionIsThrown() {
        double newtons = -100.0;
        ConverterTemperature.convert(newtons, Conversion.NEWTON_TO_KELVIN);
    }

    @Test
    public void get0KelvinsFromMinus90Point1395Newtons() {
        double newtons = -90.1395;
        double kelvins = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_KELVIN);
        assertEquals(0.0, kelvins, delta);
    }

    @Test
    public void get423Point15KelvinsFrom49Point5Newtons() {
        double newtons = 49.5;
        double kelvins = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_KELVIN);
        assertEquals(423.15, kelvins, delta);
    }

    @Test
    public void getTheSameKelvinsAfterConversionToNewtonAndBack() {
        double kelvins = 26.0;
        double newtons = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_NEWTON);

        double kelvinsAfterConversion = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_KELVIN);

        assertEquals(kelvins, kelvinsAfterConversion, delta);
    }

    @Test
    public void getTheSameNewtonsAfterConversionToKelvinAndBack() {
        double newtons = 71.0;
        double kelvins = ConverterTemperature.convert(newtons,
            Conversion.NEWTON_TO_KELVIN);

        double newtonsAfterConversion = ConverterTemperature.convert(kelvins,
            Conversion.KELVIN_TO_NEWTON);

        assertEquals(newtons, newtonsAfterConversion, delta);
    }

    private final double delta = 1e-6;
}
