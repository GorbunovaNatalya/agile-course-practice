package ru.unn.agile.ConverterTemperature.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ViewModelTests {
    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultInputTemperatureValues() {
        assertEquals("", viewModel.inputTemperatureProperty().get());
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.inputTemperatureProperty().get());
        assertEquals(NameSystem.CELSIUS, viewModel.inputTypeProperty().get());
        assertEquals(NameSystem.FAHRENHEIT, viewModel.outputTypeProperty().get());
        assertEquals("", viewModel.resultProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canConvertDefaultWhereInputSetZero() {
        viewModel.inputTemperatureProperty().set("0.0");

        viewModel.convert();

        assertEquals("32.00", viewModel.resultProperty().get());
    }

    @Test
    public void canConvertReturnCorrectValueWhenSystemIsEqual() {
        viewModel.inputTemperatureProperty().set("0.0");
        viewModel.inputTypeProperty().set(NameSystem.CELSIUS);
        viewModel.outputTypeProperty().set(NameSystem.CELSIUS);

        viewModel.convert();

        assertEquals("0.0", viewModel.resultProperty().get());
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenInputIsEmpty() {
        viewModel.convert();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenInputIsFill() {
        viewModel.inputTemperatureProperty().set("1.0");
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.inputTemperatureProperty().set("a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetInputConversionOperation() {
        viewModel.inputTypeProperty().set(NameSystem.FAHRENHEIT);
        assertEquals(NameSystem.FAHRENHEIT, viewModel.inputTypeProperty().get());
    }

    @Test
    public void canSetInputAndOutputConversionsOperation() {
        viewModel.inputTypeProperty().set(NameSystem.FAHRENHEIT);
        viewModel.outputTypeProperty().set(NameSystem.CELSIUS);

        assertEquals(NameSystem.FAHRENHEIT, viewModel.inputTypeProperty().get());
        assertEquals(NameSystem.CELSIUS, viewModel.outputTypeProperty().get());
    }

    @Test
    public void canSetTrashInput() {
        viewModel.inputTemperatureProperty().set("trash");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsUnphysicalWhneInputLessThanAbsoluteZero() {
        viewModel.inputTemperatureProperty().set("-1000.0");

        viewModel.convert();

        assertEquals(Status.IMPOSSIBLE.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenInputIsEmpty() {
        viewModel.inputTemperatureProperty().set("");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void calculateButtonIsEnabledWhenInputIsSet() {
        viewModel.inputTemperatureProperty().set("1");
        assertFalse(viewModel.isCalculationDisabled());
    }

    @Test
    public void canSetSuccessMessage() {
        viewModel.inputTemperatureProperty().set("1");

        viewModel.convert();

        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convertMinus103FahrenheitsFrom198Point15KelvinsHasCorrectResult() {
        viewModel.inputTypeProperty().set(NameSystem.KELVIN);
        viewModel.outputTypeProperty().set(NameSystem.FAHRENHEIT);
        viewModel.inputTemperatureProperty().set("198.15");

        viewModel.convert();

        assertEquals("-103.00", viewModel.resultProperty().get());
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convertCelsiusToKelvinHasCorrectResult() {
        viewModel.inputTypeProperty().set(NameSystem.CELSIUS);
        viewModel.outputTypeProperty().set(NameSystem.KELVIN);
        viewModel.inputTemperatureProperty().set("0");

        viewModel.convert();

        assertEquals("273.15", viewModel.resultProperty().get());
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convertNewtonToFahrenheitHasCorrectResult() {
        viewModel.inputTypeProperty().set(NameSystem.NEWTON);
        viewModel.outputTypeProperty().set(NameSystem.FAHRENHEIT);
        viewModel.inputTemperatureProperty().set("0");

        viewModel.convert();

        assertEquals("32.00", viewModel.resultProperty().get());
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canConvertCorrectlyRoundsValueWhenTypeIsDifferent() {
        viewModel.inputTypeProperty().set(NameSystem.NEWTON);
        viewModel.outputTypeProperty().set(NameSystem.FAHRENHEIT);
        viewModel.inputTemperatureProperty().set("100.00");

        viewModel.convert();

        assertEquals("577.45", viewModel.resultProperty().get());
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canConvertCorrectlyRetranslateValueWhenTypeIsSame() {
        viewModel.inputTypeProperty().set(NameSystem.CELSIUS);
        viewModel.outputTypeProperty().set(NameSystem.CELSIUS);
        viewModel.inputTemperatureProperty().set("100.0");

        viewModel.convert();

        assertEquals("100.0", viewModel.resultProperty().get());
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    private ViewModel viewModel;
}
