package ru.unn.agile.LeftSidedHeap.viewmodel.legacy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.LeftSidedHeap.viewmodel.legacy.ViewModel.LogMessages;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewModelTests {
    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new ListLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canConstructWithDefaultLogger() {
        viewModel = new ViewModel();
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getTextAdd());
        assertEquals("", viewModel.getTextRemove());
        assertFields("size: 0", "min: -", "remove: -");
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingInTheBeginning() {
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenAddWithEmptyFields() {
        viewModel.add();

        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenRemoveWithEmptyFields() {
        viewModel.remove();

        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAddAreFillSimple() {
        viewModel.setTextAdd("1_2.0");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsRemoveAreFillSimple() {
        viewModel.setTextRemove("1");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAddAreFillComplicated() {
        viewModel.setTextAdd("1_2.0;3_4;1_5.8");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAddContainsNegativeData() {
        viewModel.setTextAdd("1_-2.0;");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsRemoveAreFillComplicated() {
        viewModel.setTextRemove("1;2;76");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAddAreFillComplicatedWithSpace() {
        viewModel.setTextAdd("1_2; 3_4 ; 1_5");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAddAreFillComplicatedWithSpaceWithDouble() {
        viewModel.setTextAdd("1_2.0; 3_4 ; 1_5.8");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsRemoveAreFillComplicatedWithSpace() {
        viewModel.setTextRemove("1 ; 2; 76");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatInAdd() {
        viewModel.setTextAdd("some words");

        viewModel.processKeyInTextField();

        assertEquals(Status.BAD_FORMAT_IN_ADD, viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatInRemove() {
        viewModel.setTextRemove("some words");

        viewModel.processKeyInTextField();

        assertEquals(Status.BAD_FORMAT_IN_REMOVE, viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatInAllField() {
        viewModel.setTextAdd("some words");
        viewModel.setTextRemove("some words");

        viewModel.processKeyInTextField();

        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatRemoveInAdd() {
        viewModel.setTextAdd("1; 2; 3");

        viewModel.processKeyInTextField();

        assertEquals(Status.BAD_FORMAT_IN_ADD, viewModel.getStatus());
    }

    @Test
    public void canReportBadFormatAddInRemove() {
        viewModel.setTextRemove("1 _ 2; 3_4");

        viewModel.processKeyInTextField();

        assertEquals(Status.BAD_FORMAT_IN_REMOVE, viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfParseAddIsOK() {
        viewModel.setTextAdd("a");
        viewModel.processKeyInTextField();
        viewModel.setTextAdd("1_3");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfParseRemoveIsOK() {
        viewModel.setTextRemove("a");
        viewModel.processKeyInTextField();
        viewModel.setTextRemove("1");

        viewModel.processKeyInTextField();

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isButtonsDisabledInitially() {
        assertEquals(false, viewModel.isButtonAddEnabled());
        assertEquals(false, viewModel.isButtonRemoveEnabled());
    }

    @Test
    public void isButtonAddDisabledWhenFormatAddIsBad() {
        viewModel.setTextAdd("1_3");
        viewModel.processKeyInTextField();

        assertEquals(true, viewModel.isButtonAddEnabled());

        viewModel.setTextAdd("some words");

        viewModel.processKeyInTextField();

        assertEquals(false, viewModel.isButtonAddEnabled());
    }

    @Test
    public void isButtonRemoveDisabledWhenFormatRemoveIsBad() {
        viewModel.setTextRemove("1");
        viewModel.processKeyInTextField();

        assertEquals(true, viewModel.isButtonRemoveEnabled());

        viewModel.setTextRemove("some words");

        viewModel.processKeyInTextField();

        assertEquals(false, viewModel.isButtonRemoveEnabled());
    }

    @Test
    public void isButtonRemoveDisabledWhenFormatAddIsBadFormatRemoveIsGood() {
        viewModel.setTextRemove("1");
        viewModel.processKeyInTextField();

        assertEquals(true, viewModel.isButtonRemoveEnabled());

        viewModel.setTextAdd("some words");

        viewModel.processKeyInTextField();

        assertEquals(false, viewModel.isButtonRemoveEnabled());
    }

    @Test
    public void isButtonRemoveDisabledWhenFormatAddIsBadFormatAndDeleteRemove() {
        viewModel.setTextRemove("1231");
        viewModel.processKeyInTextField();

        assertEquals(true, viewModel.isButtonRemoveEnabled());

        viewModel.setTextAdd("some words");
        viewModel.processKeyInTextField();
        viewModel.setTextRemove("");

        viewModel.processKeyInTextField();

        assertEquals(false, viewModel.isButtonRemoveEnabled());
    }

    @Test
    public void isButtonAddDisabledWhenFormatRemoveIsBadFormatAndDeleteAdd() {
        viewModel.setTextAdd("1_1");
        viewModel.processKeyInTextField();

        assertEquals(true, viewModel.isButtonAddEnabled());

        viewModel.setTextRemove("some words");
        viewModel.processKeyInTextField();
        viewModel.setTextAdd("");

        viewModel.processKeyInTextField();

        assertEquals(false, viewModel.isButtonAddEnabled());
    }

    @Test
    public void isButtonAddDisabledWhenFormatRemoveIsBadFormatAddIsGood() {
        viewModel.setTextAdd("1_3");
        viewModel.processKeyInTextField();

        assertEquals(true, viewModel.isButtonAddEnabled());

        viewModel.setTextRemove("some words");

        viewModel.processKeyInTextField();

        assertEquals(false, viewModel.isButtonAddEnabled());
    }

    @Test
    public void isButtonAddDisabledWithIncompleteInput() {
        viewModel.setTextAdd("1_");

        viewModel.processKeyInTextField();

        assertEquals(false, viewModel.isButtonAddEnabled());
    }

    @Test
    public void isButtonsEnabledWithCorrectInput() {
        viewModel.setTextAdd("122_31233;31_321");
        viewModel.setTextRemove("122;31233");

        viewModel.processKeyInTextField();

        assertEquals(true, viewModel.isButtonAddEnabled());
        assertEquals(true, viewModel.isButtonRemoveEnabled());
    }

    @Test
    public void canChangeFieldsWhenAddOneElementInEmptyHeap() {
        viewModel.setTextAdd("1_0");

        viewModel.add();

        assertFields("size: 1", "min: 1=0.0", "remove: -");
    }

    @Test
    public void canChangeFieldsWhenAddOneNegativeElementInEmptyHeap() {
        viewModel.setTextAdd("1_-10.8");

        viewModel.add();

        assertFields("size: 1", "min: 1=-10.8", "remove: -");
    }

    @Test
    public void canChangeFieldsAddOneElementWithNegativeKeyInEmptyHeap() {
        viewModel.setTextAdd("-11_18");

        viewModel.add();

        assertFields("size: 1", "min: -11=18.0", "remove: -");
    }

    @Test
    public void canChangeFieldsWhenAddTwoElementInEmptyHeap() {
        viewModel.setTextAdd("1_0; 2_1");

        viewModel.add();

        assertFields("size: 2", "min: 1=0.0", "remove: -");
    }

    @Test
    public void canChangeFieldsWhenAddOneElementInNoEmptyHeap() {
        viewModel.setTextAdd("10_3");
        viewModel.add();

        viewModel.setTextAdd("1_0");

        viewModel.add();

        assertFields("size: 2", "min: 1=0.0", "remove: -");
    }

    @Test
    public void canChangeFieldsWhenAddTwoElementInNoEmptyHeap() {
        viewModel.setTextAdd("10_3");
        viewModel.add();

        viewModel.setTextAdd("1_0;2_23");

        viewModel.add();

        assertFields("size: 3", "min: 1=0.0", "remove: -");
    }

    @Test
    public void canSetSuccessMessageAfterAdd() {
        viewModel.setTextAdd("21312_23132");

        viewModel.add();

        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canSetBadFormatMessageInAdd() {
        viewModel.setTextAdd("some words");

        viewModel.add();

        assertEquals(Status.BAD_FORMAT_IN_ADD, viewModel.getStatus());
    }

    @Test
    public void cantChangeFieldsWhenRemoveFromEmptyHeap() {
        viewModel.setTextRemove("21");

        viewModel.remove();

        assertFields("size: 0", "min: -", "remove: -");
    }

    @Test
    public void canChangeFieldsWhenRemoveOneElementFromHeapWithOneElement() {
        viewModel.setTextAdd("10_3");
        viewModel.add();
        viewModel.setTextRemove("10");

        viewModel.remove();

        assertFields("size: 0", "min: -", "remove: 10=3.0");
    }

    @Test
    public void canChangeFieldsRemoveOneElementFromHeapWithMoreElements() {
        viewModel.setTextAdd("10_3;120_2;1_56");
        viewModel.add();
        viewModel.setTextRemove("10");

        viewModel.remove();

        assertFields("size: 2", "min: 1=56.0", "remove: 10=3.0");
    }

    @Test
    public void canChangeFieldsRemoveTwoElementFromHeapWithOneElements() {
        viewModel.setTextAdd("10_3");
        viewModel.add();
        viewModel.setTextRemove("10;2");

        viewModel.remove();

        assertFields("size: 0", "min: -", "remove: 10=3.0");
    }

    @Test
    public void canChangeFieldsRemoveTwoElementFromHeapWithTwoElements() {
        viewModel.setTextAdd("10_3;2_21");
        viewModel.add();
        viewModel.setTextRemove("10;2");

        viewModel.remove();

        assertFields("size: 0", "min: -", "remove: 10=3.0 2=21.0");
    }

    @Test
    public void canChangeFieldsRemoveTwoElementWithOneKeyFromHeap() {
        viewModel.setTextAdd("1_3;1_3435");
        viewModel.add();
        viewModel.setTextRemove("1");

        viewModel.remove();

        assertFields("size: 0", "min: -", "remove: 1=3.0 1=3435.0");
    }

    @Test
    public void canRemoveTwoElementFromHeapWithMoreElements() {
        viewModel.setTextAdd("10_3;2_21;32_123");
        viewModel.add();
        viewModel.setTextRemove("10;32");

        viewModel.remove();

        assertFields("size: 1", "min: 2=21.0", "remove: 10=3.0 32=123.0");
    }

    @Test
    public void canSetSuccessMessageAfterRemove() {
        viewModel.setTextAdd("21312_23132");
        viewModel.add();
        viewModel.setTextRemove("21312");

        viewModel.remove();

        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canSetBadFormatMessageInRemove() {
        viewModel.setTextRemove("some words");

        viewModel.remove();

        assertEquals(Status.BAD_FORMAT_IN_REMOVE, viewModel.getStatus());
    }

    private void assertFields(final String sizeField, final String minField,
                              final String removeField) {
        assertEquals(sizeField, viewModel.getTextSizeHeap());
        assertEquals(minField, viewModel.getTextMinInHeap());
        assertEquals(removeField, viewModel.getTextRemoveFromHeap());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        new ViewModel(null);
    }

    @Test
    public void logIsEmptyAfterConstruction() {
        List<String> log = viewModel.getFullLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsCorrectMessageAfterAddToHeap() {
        doAdd();

        String message = viewModel.getFullLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.ADD_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsCorrectMessageAfterRemoveFromHeap() {
        doAdd();
        doRemove();

        String message = viewModel.getFullLog().get(1);

        assertTrue(message.matches(".*" + LogMessages.REMOVE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputArgumentsAfterAddToHeap() {
        viewModel.setTextAdd("10_3;2_21;32_19");
        String pattern = ".*" + "Arguments: " + viewModel.getTextAdd() + ".*";
        viewModel.add();

        String message = viewModel.getFullLog().get(0);
        assertTrue(message.matches(pattern));
    }

    @Test
    public void logContainsInputArgumentsAfterRemoveFromHeap() {
        doAdd();
        viewModel.setTextAdd("10_3;2_21;32_198");
        String pattern = ".*" + "Arguments: " + viewModel.getTextAdd() + ".*";
        viewModel.add();

        String message = viewModel.getFullLog().get(1);
        assertTrue(message.matches(pattern));
    }

    @Test
    public void canPutSeveralLogMessages() {
        doAdd();
        doAdd();
        doAdd();

        assertEquals(3, viewModel.getFullLog().size());
    }

    @Test
    public void isEditingFinishLogged() {
        viewModel.setTextAdd("10_3");

        viewModel.focusLost();

        String message = viewModel.getFullLog().get(0);
        assertTrue(message.matches(".*" + ViewModel.LogMessages.EDITING_HAPPENED + ".*"));
    }

    @Test
    public void areArgumentsCorrectlyLoggedOnEditingAddFieldFinish() {
        viewModel.setTextAdd("10_3");
        viewModel.focusLost();

        String message = viewModel.getFullLog().get(0);
        assertTrue(message.matches(".*" + ViewModel.LogMessages.EDITING_HAPPENED + " "
                + "\"Add\" field changed to: " + viewModel.getTextAdd()));
    }

    @Test
    public void areArgumentsCorrectlyLoggedOnEditingRemoveFieldFinish() {
        viewModel.setTextRemove("13");
        viewModel.focusLost();

        String message = viewModel.getFullLog().get(0);
        assertTrue(message.matches(".*" + ViewModel.LogMessages.EDITING_HAPPENED + " "
                + "\"Remove\" field changed to: " + viewModel.getTextRemove()));
    }

    @Test
    public void isLogInputsCalledOnChange() {
        viewModel.setTextAdd("10_3");

        viewModel.processKeyInAddField();

        String message = viewModel.getFullLog().get(0);
        assertTrue(message.matches(".*" + ViewModel.LogMessages.EDITING_HAPPENED + ".*"));
    }

    @Test
    public void doNotLogSameParametersTwice() {
        viewModel.setTextAdd("10_3;2_21;32_123");
        viewModel.setTextAdd("10_3;2_21;32_123");

        viewModel.focusLost();
        viewModel.focusLost();

        assertEquals(1, viewModel.getFullLog().size());
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.setTextAdd("12");
        viewModel.setTextAdd("12");
        viewModel.setTextAdd("12");

        viewModel.focusLost();
        viewModel.focusLost();
        viewModel.focusLost();

        assertEquals(1, viewModel.getFullLog().size());
    }

    @Test
    public void doNotLogSameParametersTwiceWithRemovePartialInput() {
        viewModel.setTextRemove("a");
        viewModel.setTextRemove("a");

        viewModel.focusLost();
        viewModel.focusLost();

        assertEquals(1, viewModel.getFullLog().size());
    }

    private void doAdd() {
        viewModel.setTextAdd("10_3;2_21;32_12");
        viewModel.add();
    }

    private void doRemove() {
        viewModel.setTextRemove("10;32");
        viewModel.remove();
    }
    private ViewModel viewModel;
}
