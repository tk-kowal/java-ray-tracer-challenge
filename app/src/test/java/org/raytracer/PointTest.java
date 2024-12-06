package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PointTest {
    @Test
    void test_pointWhenLastElementIsOne() {
        float[] point = { 0, 0, 0, 1 };
        assertTrue(Point.isPoint(point));
    }

    @Test
    void test_createPoint() {
        assertTrue(Point.isPoint(Point.point(1, 2, 3)));
    }

    @Test
    void test_addingVectorToPoint() {
        var point = Point.point(3, -2, 5);
        var vector = Vector.vector(-2, 3, 1, 0);
        var expected = Point.point(1, 1, 6);
        var actual = Tuple.add(point, vector);
        assertTrue(Point.isPoint(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_subtractingTwoPoints() {
        var pointA = Point.point(3, 2, 1);
        var pointB = Point.point(5, 6, 7);
        var expected = Vector.vector(-2, -4, -6);
        var actual = Tuple.subtract(pointA, pointB);
        assertTrue(Vector.isVector(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_subtractingVectorFromPoint() {
        var point = Point.point(3, 2, 1);
        var vector = Vector.vector(5, 6, 7);
        var expected = Point.point(-2, -4, -6);
        var actual = Tuple.subtract(point, vector);
        assertTrue(Point.isPoint(actual));
        assertTrue(Tuple.areEqual(expected, actual));
    }
}
