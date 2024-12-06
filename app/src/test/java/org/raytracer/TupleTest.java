/*
 * This source file was generated by the Gradle 'init' task
 */
package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TupleTest {
    @Test
    void test_pointWhenLastElementIsOne() {
        float[] point = { 0, 0, 0, 1 };
        assertTrue(Tuple.isPoint(point));
        assertFalse(Tuple.isVector(point));
    }

    @Test
    void test_vectorWhenLastElementIsZero() {
        float[] point = { 0, 0, 0, 0 };
        assertTrue(Tuple.isVector(point));
        assertFalse(Tuple.isPoint(point));
    }

    @Test
    void test_createPoint() {
        assertTrue(Tuple.isPoint(Tuple.point(1, 2, 3)));
    }

    @Test
    void test_createVector() {
        assertTrue(Tuple.isVector(Tuple.vector(1, 2, 3)));
    }

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
    void test_addingVectorToPoint() {
        var point = Tuple.point(3, -2, 5);
        var vector = Tuple.vector(-2, 3, 1, 0);
        var expected = Tuple.point(1, 1, 6);
        var actual = Tuple.add(point, vector);
        assertTrue(Tuple.isPoint(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_subtractingTwoPoints() {
        var pointA = Tuple.point(3, 2, 1);
        var pointB = Tuple.point(5, 6, 7);
        var expected = Tuple.vector(-2, -4, -6);
        var actual = Tuple.subtract(pointA, pointB);
        assertTrue(Tuple.isVector(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_subtractingVectorFromPoint() {
        var point = Tuple.point(3, 2, 1);
        var vector = Tuple.vector(5, 6, 7);
        var expected = Tuple.point(-2, -4, -6);
        var actual = Tuple.subtract(point, vector);
        assertTrue(Tuple.isPoint(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_subtractingTwoVectors() {
        var vectorA = Tuple.vector(3, 2, 1);
        var vectorB = Tuple.vector(5, 6, 7);
        var expected = Tuple.vector(-2, -4, -6);
        var actual = Tuple.subtract(vectorA, vectorB);
        assertTrue(Tuple.isVector(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_negatingATuple() {
        var vector = Tuple.vector(1, -2, 3);
        var expected = Tuple.vector(-1, 2, -3);
        var actual = Tuple.negate(vector);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_scalarMultiplication() {
        var vector = Tuple.vector(1, -2, 3);
        var scalar = 3.5f;
        var expected = Tuple.vector(3.5f, -7f, 10.5f);
        var actual = Tuple.scale(vector, scalar);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_scalarMultiplicationWithFraction() {
        var vector = Tuple.vector(1, -2, 3);
        var scalar = 0.5f;
        var expected = Tuple.vector(0.5f, -1f, 1.5f);
        var actual = Tuple.scale(vector, scalar);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_scalarDivision() {
        var vector = Tuple.vector(1, -2, 3);
        var scalar = 2;
        var expected = Tuple.vector(0.5f, -1f, 1.5f);
        var actual = Tuple.divide(vector, scalar);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_magnitude() {
        var expected = 1f;
        var magnitudeA = Tuple.magnitude(Tuple.vector(1, 0, 0));
        var magnitudeB = Tuple.magnitude(Tuple.vector(0, 1, 0));
        var magnitudeC = Tuple.magnitude(Tuple.vector(0, 0, 1));
        assertEquals(expected, magnitudeA);
        assertEquals(expected, magnitudeB);
        assertEquals(expected, magnitudeC);
    }

    @Test
    void test_magnitudeComplex() {
        float expected = (float) Math.sqrt(14);
        var magnitudeA = Tuple.magnitude(Tuple.vector(1, 2, 3));
        var magnitudeB = Tuple.magnitude(Tuple.vector(-1, -2, -3));
        assertEquals(expected, magnitudeA);
        assertEquals(expected, magnitudeB);
    }

    @Test
    void test_normalizeSimple() {
        var vector = Tuple.vector(4, 0, 0);
        var expected = Tuple.vector(1, 0, 0);
        var actual = Tuple.normalize(vector);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_normalizeComplex() {
        var vector = Tuple.vector(1, 2, 3);
        var expected = Tuple.vector(0.26726f, 0.53452f, 0.80178f);
        var actual = Tuple.normalize(vector);
        assertTrue(Tuple.areEqual(expected, actual));
        assertTrue(Tuple.isNormalized(actual));
    }

    @Test
    void test_dotProduct() {
        var vectorA = Tuple.vector(1, 2, 3);
        var vectorB = Tuple.vector(2, 3, 4);
        var expected = 20f;
        var actual = Tuple.dot(vectorA, vectorB);
        assertTrue(Scalar.areEqual(expected, actual));
    }

    @Test
    void test_crossProduct() {
        var vectorA = Tuple.vector(1, 2, 3);
        var vectorB = Tuple.vector(2, 3, 4);
        var expectedA = Tuple.vector(-1, 2, -1);
        var expectedB = Tuple.vector(1, -2, 1);
        var actualA = Tuple.cross(vectorA, vectorB);
        var actualB = Tuple.cross(vectorB, vectorA);
        assertTrue(Tuple.areEqual(expectedA, actualA));
        assertTrue(Tuple.areEqual(expectedB, actualB));
    }
}
