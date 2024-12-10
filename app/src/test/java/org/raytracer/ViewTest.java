package org.raytracer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;

public class ViewTest {

    @Test
    public void test_transform() {
        var from = point(0, 0, 0);
        var to = point(0, 0, -1);
        var up = vector(0, 1, 0);
        var result = View.transform(from, to, up);
        assertTrue(Matrix.identity().equals(result));
    }

    @Test
    public void test_lookingInPositiveZ() {
        var from = point(0, 0, 0);
        var to = point(0, 0, 1);
        var up = vector(0, 1, 0);
        var result = View.transform(from, to, up);
        assertTrue(Transform.scale(-1, 1, -1).equals(result));
    }

    @Test
    public void test_viewTransformMovesWorld() {
        var from = point(0, 0, 8);
        var to = point(0, 0, 0);
        var up = vector(0, 1, 0);
        var result = View.transform(from, to, up);
        assertTrue(Transform.translate(0, 0, -8).equals(result));
    }

    @Test
    public void test_arbitraryViewTransform() {
        var from = point(1, 3, 2);
        var to = point(4, -2, 8);
        var up = vector(1, 1, 0);
        var result = View.transform(from, to, up);
        assertTrue(Matrix.matrix(
                new float[] { -0.50709f, 0.50709f, 0.67612f, -2.36643f },
                new float[] { .76772f, .60609f, .12122f, -2.82843f },
                new float[] { -.35857f, .59761f, -.71714f, 0f },
                new float[] { 0, 0, 0, 1 }).equals(result));
    }
}
