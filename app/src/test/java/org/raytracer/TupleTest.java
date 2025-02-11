/*
 * This source file was generated by the Gradle 'init' task
 */
package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class TupleTest {

    @Test
    void test_areEqual() {
        float[] a = { 1.0f };
        float[] b = { 1.0f + Constants.EPSILON / 2 };
        assertTrue(Tuple.areEqual(a, b));
    }

    @Test
    void test_areNotEqual() {
        float[] a = { 1.0f };
        float[] b = { 1.0f + Constants.EPSILON + Constants.EPSILON / 2 };
        assertFalse(Tuple.areEqual(a, b));
    }

    @Test
    void test_negatingATuple() {
        var vector = Vector.vector(1, -2, 3);
        var expected = Vector.vector(-1, 2, -3);
        var actual = Tuple.negate(vector);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_scalarMultiplication() {
        var vector = Vector.vector(1, -2, 3);
        var scalar = 3.5f;
        var expected = Vector.vector(3.5f, -7f, 10.5f);
        var actual = Tuple.multiply(vector, scalar);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_scalarMultiplicationWithFraction() {
        var vector = Vector.vector(1, -2, 3);
        var scalar = 0.5f;
        var expected = Vector.vector(0.5f, -1f, 1.5f);
        var actual = Tuple.multiply(vector, scalar);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_scalarDivision() {
        var vector = Vector.vector(1, -2, 3);
        var scalar = 2;
        var expected = Vector.vector(0.5f, -1f, 1.5f);
        var actual = Tuple.divide(vector, scalar);
        assertTrue(Tuple.areEqual(expected, actual));
    }
}
