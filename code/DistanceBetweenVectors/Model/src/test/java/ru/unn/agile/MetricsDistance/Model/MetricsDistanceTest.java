package ru.unn.agile.MetricsDistance.Model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MetricsDistanceTest {

    private float calculateDistanceChebyshev(final float[] vector1, final float[] vector2) {
        return MetricsDistance.calculateDistanceChebyshev(vector1, vector2);
    }

    private float calculateDistanceMinkowski(final float[] vector1,
                                             final float[] vector2,
                                             final int p) {
        return MetricsDistance.calculateDistanceMinkowski(vector1, vector2, p);
    }

    @Test
    public void canCalculateDistanceChebyshevBetweenEqualVectors() {
        float distanceBetweenVectors = calculateDistanceChebyshev(new float[]{1.0f, 1.0f, 1.0f},
                new float[]{1.0f, 1.0f, 1.0f});

        assertEquals(0.0f, distanceBetweenVectors, delta);
    }

    @Test
    public void canCalculateDistanceChebyshevBetweenZeroVectorsAndOnesVectors() {
        float distanceBetweenVectors = calculateDistanceChebyshev(new float[]{0.0f, 0.0f, 0.0f},
                new float[]{1.0f, 1.0f, 1.0f});

        assertEquals(1.0f, distanceBetweenVectors, delta);
    }


    @Test
    public void canCalculateDistanceChebyshevBetweenFloatVectors() {
        float distanceBetweenVectors = calculateDistanceChebyshev(new float[]{1.0f, 1.0f, 1.0f},
                new float[]{5.0f, 2.0f, 1.0f});

        assertEquals(4.0f, distanceBetweenVectors, delta);
    }

    @Test (expected = ArithmeticException.class)
    public void calculateDistanceChebyshevBetweenVectorsWithOverflowThrowsArithmeticException() {
        float distanceBetweenVectors = calculateDistanceChebyshev(new float[]{3.4e+38f, 1.0f, 1.0f},
                new float[]{-3e+35f, 2.0f, 7.0f});
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceChebyshevBetweenVectorsDiffSizeThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceChebyshev(new float[]{1.0f, 1.0f, 1.0f},
                new float[]{3.0f, 2.0f, 1.0f, 7.0f});
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceChebyshevWhereOneVectorZeroLenThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceChebyshev(new float[]{},
                new float[]{1.0f, 5.5f});
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceChebyshevWhereTwoVectorsZeroLenThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceChebyshev(new float[]{},
                new float[]{});
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceChebyshevWhereOneVectorNullThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceChebyshev(new float[]{1.1f, 9.0f},
                null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceChebyshevWhereTwoVectorsNullThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceChebyshev(null, null);
    }

    @Test
    public void canCalculateDistanceMinkowskiL1BetweenEqualVectors() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{5.0f, 5.0f, 5.0f},
                new float[]{5.0f, 5.0f, 5.0f}, 1);

        assertEquals(0.0f, distanceBetweenVectors, delta);
    }

    @Test
    public void canCalculateDistanceMinkowskiL1BetweenFloatVectors() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{-7.0f, 3.5f, 6.0f},
                new float[]{4.93f, 4.0f, 2.0f}, 1);

        assertEquals(16.43f, distanceBetweenVectors, delta);
    }

    @Test
    public void canCalculateDistanceMinkowskiL2BetweenFloatVectors() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{-1.0f, 3.0f, 2.0f},
                new float[]{0.0f, 0.5f, 1.0f}, 2);

        assertEquals((float) Math.sqrt(8.25f), distanceBetweenVectors, delta);
    }

    @Test
    public void canCalculateDistanceMinkowskiL3BetweenFloatVectors() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{1.0f, 2.0f, 3.0f},
                new float[]{3.0f, 2.0f, 1.0f}, 3);

        assertEquals((float) Math.cbrt(16), distanceBetweenVectors, delta);
    }

    @Test
    public void canCalculateDistanceMinkowskiL4BetweenFloatVectors() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{5.0f, 3.0f, 1.0f},
                new float[]{1.0f, 2.0f, 3.0f}, 4);

        assertEquals((float) Math.pow(273, 1.0 / 4.0), distanceBetweenVectors, delta);
    }

    @Test (expected = ArithmeticException.class)
    public void calculateDistanceMinkowskiL1BetweenVectorsWithOverflowThrowsArithmeticException() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{3.4e+38f, 1.0f, 1.0f},
                new float[]{-3e+35f, -5.0f, 10.0f}, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceMinkowskiL1BetweenVectorsDiffSizeThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{2.0f, 1.0f, 5.0f},
                new float[]{3.0f}, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceMinkowskiL2WhereOneVectorZeroLenThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{},
                new float[]{1.0f, 5.5f}, 2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceMinkowskiL2WhereTwoVectorsZeroLenThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{},
                new float[]{}, 2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceMinkowskiL3WhereOneVectorNullThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceMinkowski(new float[]{1.1f, 9.0f},
                null, 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void calculateDistanceinkowskiL4WhereTwoVectorsNullThrowsIllegalArgumentException() {
        float distanceBetweenVectors = calculateDistanceMinkowski(null, null, 4);
    }

    private final double delta = 1e-6;
}
