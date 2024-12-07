package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatrixTest {
    @Test
    public void test_creatingAMatrix() {
        var matrix = Matrix.matrix(
                new float[] { 1f, 2f, 3f, 4f },
                new float[] { 5.5f, 6.5f, 7.5f, 8.5f },
                new float[] { 9, 10, 11, 12 },
                new float[] { 13.5f, 14.5f, 15.5f, 16.5f });
        assertEquals(1f, matrix.get(0, 0));
        assertEquals(5.5f, matrix.get(1, 0));
        assertEquals(7.5f, matrix.get(1, 2));
        assertEquals(11f, matrix.get(2, 2));
        assertEquals(13.5, matrix.get(3, 0));
        assertEquals(15.5, matrix.get(3, 2));
    }

    @Test
    public void test_2x2Matrix() {
        var matrix = Matrix.matrix(
                new float[] { -3, 5 },
                new float[] { 1, -2 });
        assertEquals(-3, matrix.get(0, 0));
    }

    @Test
    public void test_areEqual() {
        var matrixA = Matrix.matrix(
                new float[] { 1, 2, 3, 4 },
                new float[] { 5, 6, 7.02f, 8 },
                new float[] { 9.4f, 10.002f, 7.5f, 11 });
        var matrixB = Matrix.matrix(
                new float[] { 1, 2, 3, 4 },
                new float[] { 5, 6, 7.02f, 8 },
                new float[] { 9.4f, 10.002f, 7.5f, 11 });

        assertTrue(matrixA.equals(matrixB));
    }

    @Test
    public void test_areNotEqual() {
        var matrixA = Matrix.matrix(
                new float[] { 1, 2, 3, 4 },
                new float[] { 5, 6, 7.02f, 8 },
                new float[] { 9.4f, 10.002f, 7.5f, 11 });
        var matrixB = Matrix.matrix(
                new float[] { 5, 6, 7.02f, 8 },
                new float[] { 1, 2, 3, 4 },
                new float[] { 9.4f, 10.002f, 7.5f, 11 });

        assertFalse(matrixA.equals(matrixB));
    }

    @Test
    public void test_getCol() {
        var matrix = Matrix.matrix(
                new float[] { 1, 2, 3, 4 },
                new float[] { 5, 6, 7, 8 },
                new float[] { 9, 8, 7, 6 },
                new float[] { 5, 4, 3, 2 });

        assertTrue(Tuple.areEqual(new float[] { 1, 5, 9, 5 }, matrix.getCol(0)));
        assertTrue(Tuple.areEqual(new float[] { 2, 6, 8, 4 }, matrix.getCol(1)));
    }

    @Test
    public void test_multiply() {
        var matrixA = Matrix.matrix(
                new float[] { 1, 2, 3, 4 },
                new float[] { 5, 6, 7, 8 },
                new float[] { 9, 8, 7, 6 },
                new float[] { 5, 4, 3, 2 });

        var matrixB = Matrix.matrix(
                new float[] { -2, 1, 2, 3 },
                new float[] { 3, 2, 1, -1 },
                new float[] { 4, 3, 6, 5 },
                new float[] { 1, 2, 7, 8 });

        var expected = Matrix.matrix(
                new float[] { 20, 22, 50, 48 },
                new float[] { 44, 54, 114, 108 },
                new float[] { 40, 58, 110, 102 },
                new float[] { 16, 26, 46, 42 });

        var actual = matrixA.multiply(matrixB);

        assertTrue(expected.equals(actual));
    }
}
