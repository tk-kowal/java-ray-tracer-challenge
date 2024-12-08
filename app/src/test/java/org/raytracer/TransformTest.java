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

    @Test
    public void test_inverseRotateX() {
        var point = Point.point(0, 1, 0);
        var halfQuarterRotation = Transform.rotateX((float) (Math.PI / 4)).inverse();
        var expectedHalfQuarter = Point.point(0, (float) (Math.sqrt(2) / 2), (float) (-1 * Math.sqrt(2) / 2));
        var actualHalfQuarter = halfQuarterRotation.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expectedHalfQuarter, actualHalfQuarter));
    }

    @Test
    public void test_rotateY() {
        var point = Point.point(0, 0, 1);
        var halfQuarterRotation = Transform.rotateY((float) (Math.PI / 4));
        var quarterRotation = Transform.rotateY((float) (Math.PI / 2));
        var expectedHalfQuarter = Point.point((float) (Math.sqrt(2) / 2), 0, (float) (Math.sqrt(2) / 2));
        var expectedQuarter = Point.point(1, 0, 0);
        var actualHalfQuarter = halfQuarterRotation.tupleMultiply(point);
        var actualQuarter = quarterRotation.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expectedHalfQuarter, actualHalfQuarter));
        assertTrue(Tuple.areEqual(expectedQuarter, actualQuarter));
    }

    @Test
    public void test_rotateZ() {
        var point = Point.point(0, 1, 0);
        var halfQuarterRotation = Transform.rotateZ((float) (Math.PI / 4));
        var quarterRotation = Transform.rotateZ((float) (Math.PI / 2));
        var expectedHalfQuarter = Point.point((float) (-1 * Math.sqrt(2) / 2), (float) (Math.sqrt(2) / 2), 0);
        var expectedQuarter = Point.point(-1, 0, 0);
        var actualHalfQuarter = halfQuarterRotation.tupleMultiply(point);
        var actualQuarter = quarterRotation.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expectedHalfQuarter, actualHalfQuarter));
        assertTrue(Tuple.areEqual(expectedQuarter, actualQuarter));
    }

    // SHEARING

    @Test
    public void test_shearXY() {
        var point = Point.point(2, 3, 4);
        var transform = Transform.shear(1, 0, 0, 0, 0, 0);
        var expected = Point.point(5, 3, 4);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_shearXZ() {
        var point = Point.point(2, 3, 4);
        var transform = Transform.shear(0, 1, 0, 0, 0, 0);
        var expected = Point.point(6, 3, 4);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_shearYX() {
        var point = Point.point(2, 3, 4);
        var transform = Transform.shear(0, 0, 1, 0, 0, 0);
        var expected = Point.point(2, 5, 4);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_shearYZ() {
        var point = Point.point(2, 3, 4);
        var transform = Transform.shear(0, 0, 0, 1, 0, 0);
        var expected = Point.point(2, 7, 4);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_shearZX() {
        var point = Point.point(2, 3, 4);
        var transform = Transform.shear(0, 0, 0, 0, 1, 0);
        var expected = Point.point(2, 3, 6);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_shearZY() {
        var point = Point.point(2, 3, 4);
        var transform = Transform.shear(0, 0, 0, 0, 0, 1);
        var expected = Point.point(2, 3, 7);
        var actual = transform.tupleMultiply(point);
        assertTrue(Tuple.areEqual(expected, actual));
    }
}
