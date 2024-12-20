package org.raytracer.patterns;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Point.point;

import org.junit.jupiter.api.Test;
import org.raytracer.Color;
import org.raytracer.Material;
import org.raytracer.Transform;
import org.raytracer.Tuple;
import org.raytracer.shapes.Sphere;

public class StripePatternTest {

    @Test
    public void test_patternConstantInY() {
        var pattern = new StripePattern(Color.WHITE, Color.BLACK);
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(0, 0, 0))));
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(0, 1, 0))));
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(0, 2, 0))));
    }

    @Test
    public void test_patternIsConstantInZ() {
        var pattern = new StripePattern(Color.WHITE, Color.BLACK);
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(0, 0, 0))));
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(0, 0, 1))));
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(0, 0, 2))));
    }

    @Test
    public void test_patternAlternatesInX() {
        var pattern = new StripePattern(Color.WHITE, Color.BLACK);
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(0, 0, 0))));
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(0.9f, 0, 0))));
        assertTrue(Tuple.areEqual(Color.BLACK, pattern.colorAt(point(1, 0, 0))));
        assertTrue(Tuple.areEqual(Color.BLACK, pattern.colorAt(point(-.1f, 0, 0))));
        assertTrue(Tuple.areEqual(Color.BLACK, pattern.colorAt(point(-1, 0, 0))));
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.colorAt(point(-1.1f, 0, 0))));
    }

    // Transformations

    @Test
    public void test_patternWithObjectTransform() {
        var pattern = new StripePattern(Color.WHITE, Color.BLACK);
        var sphere = new Sphere();
        sphere.setMaterial(new Material().setPattern(pattern));
        sphere.setTransform(Transform.scale(2, 2, 2));
        assertTrue(Tuple.areEqual(Color.WHITE, sphere.colorAt(point(1.5f, 0, 0))));
    }

    @Test
    public void test_patternWithPatternTransform() {
        var pattern = new StripePattern(Color.WHITE, Color.BLACK);
        pattern.setTransform(Transform.scale(2, 2, 2));
        var sphere = new Sphere();
        sphere.setMaterial(new Material().setPattern(pattern));
        assertTrue(Tuple.areEqual(Color.WHITE, sphere.colorAt(point(1.5f, 0, 0))));
    }

    @Test
    public void test_patternWithObjectAndPatternTransform() {
        var pattern = new StripePattern(Color.WHITE, Color.BLACK);
        pattern.setTransform(Transform.translate(0.5f, 0, 0));
        var sphere = new Sphere();
        sphere.setMaterial(new Material().setPattern(pattern));
        sphere.setTransform(Transform.scale(2, 2, 2));
        assertTrue(Tuple.areEqual(Color.WHITE, sphere.colorAt(point(2.5f, 0, 0))));
    }
}
