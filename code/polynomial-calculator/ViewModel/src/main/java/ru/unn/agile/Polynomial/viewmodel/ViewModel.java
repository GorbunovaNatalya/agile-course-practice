package ru.unn.agile.Polynomial.viewmodel;

import java.util.List;

import ru.unn.agile.Polynomial.model.Polynomial;

public class ViewModel {
    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }

        this.logger = logger;
        isCalculateButtonEnabled = false;
        firstPolynomial = "";
        secondPolynomial = "";
        operation = Operation.ADD;
        result = "";
        status = Status.WAITING;
        isFirstInputChanged = false;
        isSecondInputChanged = false;
    }

    public boolean isButtonCalculateEnabled() {
        return isCalculateButtonEnabled;
    }

    public String getResult() {
        return result;
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(final Operation operation) {
        if (this.operation != operation) {
            this.operation = operation;
            logger.addInfo(operationLogMessage());
        }
    }

    public String getFirstPolynomial() {
        return firstPolynomial;
    }

    public void setFirstPolynomial(final String txt) {
        if (txt.equals(this.firstPolynomial)) {
            return;
        }

        firstPolynomial = txt;
        isFirstInputChanged = true;
    }

    public String getSecondPolynomial() {
        return secondPolynomial;
    }

    public void setSecondPolynomial(final String txt) {
        if (txt.equals(this.secondPolynomial)) {
            return;
        }

        secondPolynomial = txt;
        isSecondInputChanged = true;
    }

    public void processTextChanged() {
        parseInput();
        logInputParams();
    }

    public void focusLost() {
        logInputParams();
    }

    public void calculate() {
        logger.addInfo(calculateLogMessage());

        if (parseInput()) {
            Polynomial firstOperand = Parser.getPolynomial(firstPolynomial);
            Polynomial secondOperand = Parser.getPolynomial(secondPolynomial);
            Polynomial resultOfOperation;
            switch (operation) {
                case ADD:
                    resultOfOperation = firstOperand.add(secondOperand);
                    break;
                case SUB:
                    resultOfOperation = firstOperand.sub(secondOperand);
                    break;
                case MULTIPLY:
                    resultOfOperation = firstOperand.multiply(secondOperand);
                    break;
                default:
                    throw new IllegalStateException("No such operation!");
            }
            result = resultOfOperation.toString();
            status = Status.SUCCESS;
        }
    }

    private boolean isInputAvailable() {
        return !firstPolynomial.isEmpty() && !secondPolynomial.isEmpty();
    }

    private boolean parseInput() {
        isCalculateButtonEnabled = false;
        if (!isInputAvailable()) {
            status = Status.WAITING;
        } else {
            if (Parser.getPolynomial(firstPolynomial) != null
                    && Parser.getPolynomial(secondPolynomial) != null) {
                isCalculateButtonEnabled = true;
                status = Status.READY;
            } else {
                status = Status.BAD_FORMAT;
            }
        }
        return isCalculateButtonEnabled;
    }

    private void logInputParams() {
        if (isFirstInputChanged) {
            logger.addInfo(editingFirstPolynomialLogMessage());
            isFirstInputChanged = false;
        }
        if (isSecondInputChanged) {
            logger.addInfo(editingSecondPolynomialLogMessage());
            isSecondInputChanged = false;
        }
    }

    private String calculateLogMessage() {
        return LogMessages.CALCULATE_WAS_PRESSED
                + String.format("Arguments: [%s | %s]. Operation: %s.",
                firstPolynomial, secondPolynomial, operation.toString());
    }

    private String operationLogMessage() {
        return LogMessages.OPERATION_WAS_CHANGED + operation.toString() + ".";
    }

    private String editingFirstPolynomialLogMessage() {
        return LogMessages.EDITING_HAPPENED
                + String.format("First polynomial: [%s].", firstPolynomial);
    }

    private String editingSecondPolynomialLogMessage() {
        return LogMessages.EDITING_HAPPENED
                + String.format("Second polynomial: [%s].", secondPolynomial);
    }

    private boolean isFirstInputChanged;
    private boolean isSecondInputChanged;
    private boolean isCalculateButtonEnabled;
    private String firstPolynomial;
    private String secondPolynomial;
    private Operation operation;
    private Status status;
    private String result;
    private ILogger logger;
}
