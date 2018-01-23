package ru.unn.agile.quadraticequation.model;

public class Complex {

    public Complex(final double real, final double imaginary) {
        this.re = real;
        this.im = imaginary;
    }
    public void setReal(final double real) {
        this.re = real;
    }

    public double getReal() {
        return re;
    }

    public void setImaginary(final double imaginary) {
        this.im = imaginary;
    }

    public double getImaginary() {
        return im;
    }

    private double re;
    private double im;
}

