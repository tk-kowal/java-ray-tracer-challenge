package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class VectorTest {

    @Test
    void test_vectorWhenLastElementIsZero() {
        float[] point = { 0, 0, 0, 0 };
        assertTrue(Vector.isVector(point));
    }

    @Test
    void test_createVector() {
        assertTrue(Vector.isVector(Vector.vector(1, 2, 3)));
    }

    @Test
    void test_subtractingTwoVectors() {
        var vectorA = Vector.vector(3, 2, 1);
        var vectorB = Vector.vector(5, 6, 7);
        var expected = Vector.vector(-2, -4, -6);
        var actual = Tuple.subtract(vectorA, vectorB);
        assertTrue(Vector.isVector(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_magnitude() {
        var expected = 1f;
        var magnitudeA = Vector.magnitude(Vector.vector(1, 0, 0));
        var magnitudeB = Vector.magnitude(Vector.vector(0, 1, 0));
        var magnitudeC = Vector.magnitude(Vector.vector(0, 0, 1));
        assertEquals(expected, magnitudeA);
        assertEquals(expected, magnitudeB);
        assertEquals(expected, magnitudeC);
    }

    @Test
    void test_magnitudeComplex() {
        float expected = (float) Math.sqrt(14);
        var magnitudeA = Vector.magnitude(Vector.vector(1, 2, 3));
        var magnitudeB = Vector.magnitude(Vector.vector(-1, -2, -3));
        assertEquals(expected, magnitudeA);
        assertEquals(expected, magnitudeB);
    }

    @Test
    void test_normalizeSimple() {
        var vector = Vector.vector(4, 0, 0);
        var expected = Vector.vector(1, 0, 0);
        var actual = Vector.normalize(vector);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_normalizeComplex() {
        var vector = Vector.vector(1, 2, 3);
        var expected = Vector.vector(0.26726f, 0.53452f, 0.80178f);
        var actual = Vector.normalize(vector);
        assertTrue(Tuple.areEqual(expected, actual));
        assertTrue(Vector.isNormalized(actual));
    }

    @Test
    void test_dotProduct() {
        var vectorA = Vector.vector(1, 2, 3);
        var vectorB = Vector.vector(2, 3, 4);
        var expected = 20f;
        var actual = Tuple.dot(vectorA, vectorB);
        assertTrue(Scalar.areEqual(expected, actual));
    }

    @Test
    void test_crossProduct() {
        var vectorA = Vector.vector(1, 2, 3);
        var vectorB = Vector.vector(2, 3, 4);
        var expectedA = Vector.vector(-1, 2, -1);
        var expectedB = Vector.vector(1, -2, 1);
        var actualA = Vector.cross(vectorA, vectorB);
        var actualB = Vector.cross(vectorB, vectorA);
        assertTrue(Tuple.areEqual(expectedA, actualA));
        assertTrue(Tuple.areEqual(expectedB, actualB));
    }
}
