package ru.unn.agile.MetricsDistance.Model;

import java.util.Arrays;

public final class MetricsDistance {

    public static float calculateDistanceChebyshev(final float[] vector1, final float[] vector2) {
        validateInputArgs(vector1, vector2);

        if (isVectorEquals(vector1, vector2)) {
            return 0;
        }

        float distanceBetweenVectors = Math.abs(vector1[0] - vector2[0]);
        for (int i = 1; i < vector1.length; i++) {
            distanceBetweenVectors = Math.max(distanceBetweenVectors,
                    Math.abs(vector1[i] - vector2[i]));
        }

        if (Float.isInfinite(distanceBetweenVectors)) {
            throw new ArithmeticException("Переполнение float переменной!");
        }

        return distanceBetweenVectors;
    }

    public static float calculateDistanceMinkowski(final float[] vector1, final float[] vector2,
                                                   final int p) {
        validateInputArgs(vector1, vector2);

        if (isVectorEquals(vector1, vector2)) {
            return 0;
        }

        float distanceBetweenVectors = 0.0f;
        for (int i = 0; i < vector1.length; i++) {
            distanceBetweenVectors += Math.pow(Math.abs(vector1[i] - vector2[i]), (double) p);
        }
        distanceBetweenVectors = (float) Math.pow(distanceBetweenVectors, 1.0 / (double) p);

        if (Float.isInfinite(distanceBetweenVectors)) {
            throw new ArithmeticException("Переполнение float переменной!");
        }

        return  distanceBetweenVectors;
    }

    private static void validateInputArgs(final float[] vector1, final float[] vector2) {
        if (isVectorsNull(vector1, vector2)) {
            throw new IllegalArgumentException("Вектор не инициализирован!");
        }
        if (isVectorsZeroLength(vector1, vector2)) {
            throw new IllegalArgumentException("Вектор имеет длину 0!");
        }
        if (!isLengthEquals(vector1, vector2)) {
            throw new IllegalArgumentException("Векторы разных размеров!");
        }
    }

    private static boolean isVectorsNull(final float[] vector1, final float[] vector2) {
        return (vector1 == null) || (vector2 == null);
    }

    private static boolean isVectorsZeroLength(final float[] vector1, final float[] vector2) {
        return (vector1.length == 0) || (vector2.length == 0);
    }

    private static boolean isLengthEquals(final float[] vector1, final float[] vector2) {
        return vector1.length == vector2.length;
    }

    private static boolean isVectorEquals(final float[] vector1, final float[] vector2) {
        return Arrays.equals(vector1, vector2);
    }

    private MetricsDistance() {
    }
}
