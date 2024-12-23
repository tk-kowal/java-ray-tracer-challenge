package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Vector.vector;

import org.junit.jupiter.api.Test;

public class VectorTest {

    @Test
    void test_vectorWhenLastElementIsZero() {
        float[] tuple = { 0, 0, 0, 0 };
        assertTrue(Vector.isVector(tuple));
    }

    @Test
    void test_createVector() {
        assertTrue(Vector.isVector(vector(1, 2, 3)));
    }

    @Test
    void test_subtractingTwoVectors() {
        var vectorA = vector(3, 2, 1);
        var vectorB = vector(5, 6, 7);
        var expected = vector(-2, -4, -6);
        var actual = Tuple.subtract(vectorA, vectorB);
        assertTrue(Vector.isVector(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_magnitude() {
        var expected = 1f;
        var magnitudeA = Vector.magnitude(vector(1, 0, 0));
        var magnitudeB = Vector.magnitude(vector(0, 1, 0));
        var magnitudeC = Vector.magnitude(vector(0, 0, 1));
        assertEquals(expected, magnitudeA);
        assertEquals(expected, magnitudeB);
        assertEquals(expected, magnitudeC);
    }

    @Test
    void test_magnitudeComplex() {
        float expected = (float) Math.sqrt(14);
        var magnitudeA = Vector.magnitude(vector(1, 2, 3));
        var magnitudeB = Vector.magnitude(vector(-1, -2, -3));
        assertEquals(expected, magnitudeA);
        assertEquals(expected, magnitudeB);
    }

    @Test
    void test_normalizeSimple() {
        var vector = vector(4, 0, 0);
        var expected = vector(1, 0, 0);
        var actual = Vector.normalize(vector);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_normalizeComplex() {
        var vector = vector(1, 2, 3);
        var expected = vector(0.26726f, 0.53452f, 0.80178f);
        var actual = Vector.normalize(vector);
        assertTrue(Tuple.areEqual(expected, actual));
        assertTrue(Vector.isNormalized(actual));
    }

    @Test
    void test_dotProduct() {
        var vectorA = vector(1, 2, 3);
        var vectorB = vector(2, 3, 4);
        var expected = 20f;
        var actual = Tuple.dot(vectorA, vectorB);
        assertTrue(Scalar.areEqual(expected, actual));
    }

    @Test
    void test_crossProduct() {
        var vectorA = vector(1, 2, 3);
        var vectorB = vector(2, 3, 4);
        var expectedA = vector(-1, 2, -1);
        var expectedB = vector(1, -2, 1);
        var actualA = Vector.cross(vectorA, vectorB);
        var actualB = Vector.cross(vectorB, vectorA);
        assertTrue(Tuple.areEqual(expectedA, actualA));
        assertTrue(Tuple.areEqual(expectedB, actualB));
    }

    // REFLECTION

    @Test
    void test_reflectAroundNormalAt45Degrees() {
        var trajectory = vector(1, -1, 0);
        var normal = vector(0, 1, 0);
        var expected = vector(1, 1, 0);
        var actual = Vector.reflect(trajectory, normal);
        assertTrue(Tuple.areEqual(expected, actual));
    }
}
