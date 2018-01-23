package ru.unn.agile.quadraticequation.model;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class QuadraticEquationTest {

    @Before
    public void createEquation() {
        equation = new QuadraticEquation(1, 1, 1);
    }

    @After
    public void deleteEquation() {
        equation = null;
    }

    @Test
    public void canCreateQuadraticEquation() {
        equation.setABC(1, 2, 3);
        assertNotNull(equation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void willThrowExceptionIfDegenerateEquation() {
        new QuadraticEquation(0, 0, 2);
    }

    @Test
    public void canGetSimpleDiscriminant() {
        equation.setABC(1, 0, 0);
        double discriminant = equation.getDiscriminant();
        assertEquals(0, discriminant, 1e-15);
    }

    @Test
    public void canGetPositiveDiscriminant() {
        equation.setABC(1, 0, -1);
        double discriminant = equation.getDiscriminant();
        assertEquals(4, discriminant, 1e-15);
    }

    @Test
    public void canGetNegativeDiscriminant() {
        equation.setABC(1, 2, 3);
        double discriminant = equation.getDiscriminant();
        assertEquals(-8, discriminant, 1e-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void willThrowExceptionIfDiscriminantNotExist() {
        equation.setABC(0, 1, 0);
        double discriminant = equation.getDiscriminant();
    }

    @Test
    public void canGetNumberofRealRootsSimple() {
        equation.setABC(1, 0, 0);
        int number = equation.getNumberofRealRoots();
        assertEquals(1, number);
    }

    @Test
    public void canGetNumberofRealRootsTwo() {
        equation.setABC(1, 2, -1);
        int number = equation.getNumberofRealRoots();
        assertEquals(2, number);
    }

    @Test
    public void canGetNumberofRealRootsNull() {
        equation.setABC(1, 2, 2);
        int number = equation.getNumberofRealRoots();
        assertEquals(0, number);
    }

    @Test
    public void canGetNumberofRealRootsLinearEquation() {
        equation.setABC(0, 2, 2);
        int number = equation.getNumberofRealRoots();
        assertEquals(1, number);
    }

    @Test
    public void canGetNumberofImaginaryRootsSimple() {
        equation.setABC(1, 0, 0);
        int number = equation.getNumberofImaginaryRoots();
        assertEquals(0, number);
    }

    @Test
    public void canGetNumberofImaginaryRoots() {
        equation.setABC(1, 4, 5);
        int number = equation.getNumberofImaginaryRoots();
        assertEquals(2, number);
    }

    @Test
    public void canFindFirstRootOfSimpleQuadraticEquation() {
        equation.setABC(4, 0, 0);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals(0.0, solve.getKey(), 1e-15);
    }

    @Test
    public void canFindSecondRootOfSimpleQuadraticEquation() {
        equation.setABC(4, 0, 0);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals(null, solve.getValue());
    }

    @Test
    public void canFindFirstRootOfQuadraticEquationWithOneRealRoot() {
        equation.setABC(1, 4, 4);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals(-2.0, solve.getKey(), 1e-15);
    }

    @Test
    public void canFindFirstRootOfLinearEquation() {
        equation.setABC(0, 6, -2);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals(1.0 / 3, solve.getKey(), 1e-15);
    }

    @Test
    public void canFindFirstRootOfQuadraticEquationWithNoRealRoots() {
        equation.setABC(6, -4, 3.3);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals(null, solve.getKey());
    }

    @Test
    public void canFindSecondRootOfQuadraticEquationWithNoRealRoots() {
        equation.setABC(6, -4, 3.3);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals(null, solve.getKey());
    }

    @Test
    public void canFindFirstRootOfQuadraticEquationWithTwoRealRoots() {
        equation.setABC(-2, 3, 1);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals(0.75 - Math.sqrt(17) / 4, solve.getKey(), 1e-15);
    }

    @Test
    public void canFindSecondRootOfQuadraticEquationWithTwoRealRoots() {
        equation.setABC(-2, 3, 1);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals(0.75 + Math.sqrt(17) / 4, solve.getValue(), 1e-15);
    }

    @Test
    public void canFindFirstRootOfQuadraticEquationWithDoubleCoefficients() {
        equation.setABC(-0.4, 0.3, 0.2);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals((-0.3 + Math.sqrt(0.41)) / (-0.8), solve.getKey(), 1e-15);
    }

    @Test
    public void canFindSecondRootOfQuadraticEquationWithDoubleCoefficients() {
        equation.setABC(-0.4, 0.3, 0.2);
        Pair<Double, Double> solve = equation.solveQuadraticEquationReal();
        assertEquals((-0.3 - Math.sqrt(0.41)) / (-0.8), solve.getValue(), 1e-15);
    }

    @Test
    public void canFindFirstRootOfQuadraticEquationWithNoImaginaryRoots() {
        equation.setABC(-1, -4, 3.3);
        Pair<Complex, Complex> solve = equation.solveQuadraticEquationImaginary();
        assertEquals(null, solve.getKey());
    }

    @Test
    public void canFindSecondRootOfQuadraticEquationWithNoImaginaryRoots() {
        equation.setABC(-1, -4, 3.3);
        Pair<Complex, Complex> solve = equation.solveQuadraticEquationImaginary();
        assertEquals(null, solve.getValue());
    }

    @Test
    public void canFindRePartOfFirstRootOfQuadraticEquationWithTwoImaginaryRoots() {
        equation.setABC(3, -4, 3);
        Pair<Complex, Complex> solve = equation.solveQuadraticEquationImaginary();
        assertEquals(2.0 / 3, solve.getKey().getReal(), 1e-15);
    }

    @Test
    public void canFindImPartOfFirstRootOfQuadraticEquationWithTwoImaginaryRoots() {
        equation.setABC(3, -4, 3);
        Pair<Complex, Complex> solve = equation.solveQuadraticEquationImaginary();
        assertEquals(Math.sqrt(20) / 2, solve.getKey().getImaginary(), 1e-15);
    }

    @Test
    public void canFindRePartOfSecondRootOfQuadraticEquationWithTwoImaginaryRoots() {
        equation.setABC(3, -4, 3);
        Pair<Complex, Complex> solve = equation.solveQuadraticEquationImaginary();
        assertEquals(2.0 / 3, solve.getValue().getReal(), 1e-15);
    }

    @Test
    public void canFindImPartOfSecondRootOfQuadraticEquationWithTwoImaginaryRoots() {
        equation.setABC(3, -4, 3);
        Pair<Complex, Complex> solve = equation.solveQuadraticEquationImaginary();
        assertEquals((-1) * Math.sqrt(20) / 2, solve.getValue().getImaginary(), 1e-15);
    }

    private QuadraticEquation equation;
}
