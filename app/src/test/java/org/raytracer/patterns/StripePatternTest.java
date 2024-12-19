package org.raytracer.patterns;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Point.point;

import org.junit.jupiter.api.Test;
import org.raytracer.Color;
import org.raytracer.Tuple;

public class StripePatternTest {

    @Test
    public void test_stripePattern() {
        var pattern = new StripePattern(Color.WHITE, Color.BLACK);
        assertTrue(Tuple.areEqual(Color.WHITE, pattern.a()));
        assertTrue(Tuple.areEqual(Color.BLACK, pattern.b()));
    }

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
}
