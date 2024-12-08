package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TransformTest {

    // TRANSLATION

    @Test
    public void test_translation() {
        var transform = Transform.translation(5, -3, 2);
        var point = Point.point(-3, 4, 5);
        var expected = Point.point(2, 1, 7);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_inverseTranslation() {
        var transform = Transform.translation(5, -3, 2).inverse();
        var point = Point.point(-3, 4, 5);
        var expected = Point.point(-8, 7, 3);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_translateDoesNotAffectVectors() {
        var transform = Transform.translation(5, -3, 2);
        var vector = Vector.vector(-3, 4, 5);
        var actual = transform.tupleMultiply(vector);
        assertTrue(Tuple.areEqual(vector, actual));
    }

    // SCALING

    @Test
    public void test_scalingPoint() {
        var transform = Transform.scaling(2, 3, 4);
        var point = Point.point(-4, 6, 8);
        var expected = Point.point(-8, 18, 32);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_scalingVector() {
        var transform = Transform.scaling(2, 3, 4);
        var vector = Vector.vector(-4, 6, 8);
        var expected = Vector.vector(-8, 18, 32);
        var actual = transform.tupleMultiply(vector);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_scalingInverse() {
        var transform = Transform.scaling(2, 3, 4).inverse();
        var vector = Vector.vector(-4, 6, 8);
        var expected = Vector.vector(-2, 2, 2);
        var actual = transform.tupleMultiply(vector);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    // REFLECTION

    @Test
    public void test_reflectAcrossX() {
        var transform = Transform.scaling(-1, 1, 1);
        var point = Point.point(2, 3, 4);
        var expected = Point.point(-2, 3, 4);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    // ROTATION

    @Test
    public void test_rotateAroundX() {
        var point = Point.point(0, 1, 0);
        var halfQuarterRotation = Transform.rotateX((float) (Math.PI / 4));
        var quarterRotation = Transform.rotateX((float) (Math.PI / 2));
        var expectedHalfQuarter = Point.point(0, (float) (Math.sqrt(2) / 2), (float) (Math.sqrt(2) / 2));
        var expectedQuarter = Point.point(0, 0, 1);
        var actualHalfQuarter = halfQuarterRotation.tupleMultiply(point);
        var actualQuarter = quarterRotation.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expectedHalfQuarter, actualHalfQuarter));
        assertTrue(Tuple.areEqual(expectedQuarter, actualQuarter));

    }
}
