package org.raytracer.patterns;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Point.point;

import org.junit.jupiter.api.Test;
import org.raytracer.Color;
import org.raytracer.Tuple;

public class RingPatternTest {

    @Test
    public void test_ring() {
        var p = new RingPattern(Color.WHITE, Color.BLACK);
        assertTrue(Tuple.areEqual(Color.WHITE, p.colorAt(point(0, 0, 0))));
        assertTrue(Tuple.areEqual(Color.BLACK, p.colorAt(point(1, 0, 0))));
        assertTrue(Tuple.areEqual(Color.BLACK, p.colorAt(point(0, 0, 1))));
        assertTrue(Tuple.areEqual(Color.BLACK, p.colorAt(point(.708f, 0, .708f))));
    }

}
