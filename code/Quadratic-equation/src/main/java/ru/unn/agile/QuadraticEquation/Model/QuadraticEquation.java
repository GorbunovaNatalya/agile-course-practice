package ru.unn.agile.quadraticequation.model;

import javafx.util.Pair;

public class QuadraticEquation {

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public void setA(final double a) {
        this.a = a;
    }

    public void setB(final double b) {
        this.b = b;
    }

    public void setC(final double c) {
        this.c = c;
    }

    public void setABC(final double a, final double b, final double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public QuadraticEquation(final double a, final double b, final double c) {
        if ((a == 0) && (b == 0)) {
            throw new IllegalArgumentException("QuadraticEquation is degenerated.");
        } else {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public double getDiscriminant() {
        if (this.isLinear()) {
            throw new IllegalArgumentException("Discriminant is not exist.");
        } else {
            return b * b - DISCRIMINANT_MULTIPLIER * a * c;
        }
    }

    public int getNumberofRealRoots() {
        if (!this.isLinear()) {
            return numberOfRootsIfIsNotLinear();
        } else {
            return 1;
        }
    }

    public int getNumberofImaginaryRoots() {
        if (this.getNumberofRealRoots() > 0) {
            return 0;
        } else {
            return 2;
        }
    }

    public Pair<Double, Double> solveQuadraticEquationReal() {
        final double delta = 1e-15;
        if (this.isLinear()) {
            return new Pair<Double, Double>((-1) * c / b, null);
        }
        if (this.getDiscriminant() < 0) {
            return new Pair<Double, Double>(null, null);
        }
        if ((this.getDiscriminant() > -delta) && (this.getDiscriminant() < delta)) {
            return new Pair<Double, Double>(-b / 2 / a, null);
        }
        return new Pair<Double, Double>(
                (-b + Math.sqrt(this.getDiscriminant())) / 2 / a,
                (-b - Math.sqrt(this.getDiscriminant())) / 2 / a);
    }

    public Pair<Complex, Complex> solveQuadraticEquationImaginary() {
        if (this.getNumberofImaginaryRoots() == 0) {
            return new Pair<Complex, Complex>(null, null);
        }
        Complex first = new Complex(-b / 2 / a,
                Math.sqrt(-this.getDiscriminant()) / 2);
        Complex second = new Complex(first.getReal(),
                -first.getImaginary());
       return new Pair<Complex, Complex>(first, second);
    }

    private boolean isNumberZero(final double value) {
        return (value > -DELTA) && (value < DELTA);
    }

    private boolean isLinear() {
        return isNumberZero(a);
    }

    private boolean hasZeroDiscriminant() {
        return isNumberZero(this.getDiscriminant());
    }

    private int numberOfRootsIfIsNotLinear() {
        if (this.hasZeroDiscriminant()) {
            return 1;
        } else if (this.getDiscriminant() > 0) {
            return 2;
        } else {
            return 0;
        }
    }
    private double a;
    private double b;
    private double c;
    private static final int DISCRIMINANT_MULTIPLIER = 4;
    private static final double DELTA = 1e-15;
}
