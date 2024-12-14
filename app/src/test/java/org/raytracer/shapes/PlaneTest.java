package org.raytracer.shapes;

import org.junit.jupiter.api.Test;
import static org.raytracer.Vector.vector;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.raytracer.Ray.ray;
import static org.raytracer.Point.point;
import org.raytracer.Tuple;

public class PlaneTest {

    @Test
    public void test_planeNormal() {
        var p = new Plane();
        var sameNormalEverywhere = vector(0, 1, 0);
        assertTrue(Tuple.areEqual(sameNormalEverywhere, p.localNormalAt(point(0, 0, 0))));
        assertTrue(Tuple.areEqual(sameNormalEverywhere, p.localNormalAt(point(10, 0, -10))));
        assertTrue(Tuple.areEqual(sameNormalEverywhere, p.localNormalAt(point(-5, 0, 150))));
    }

    @Test
    public void test_intersectParallelPlane() {
        var p = new Plane();
        var r = ray(point(0, 1, 0), vector(1, 0, 0));
        var xs = p.intersect(r);
        assertTrue(xs.isEmpty());
    }

    @Test
    void test_intersectCoplanarRay() {
        var p = new Plane();
        var r = ray(point(0, 0, 0), vector(1, 0, 0));
        var xs = p.intersect(r);
        assertTrue(xs.isEmpty());
    }

    @Test
    void test_intersectRayAbovePlanePerpendicular() {
        var p = new Plane();
        var r = ray(point(0, 1, 0), vector(0, -1, 0));
        var xs = p.intersect(r);
        assertEquals(1, xs.size());
        assertEquals(1, xs.getFirst().t());
        assertEquals(p, xs.getFirst().object());
    }

    @Test
    void test_intersectRayBelowPlaneAtAngle() {
        var p = new Plane();
        var r = ray(point(0, -1, 0), vector(0, 1, 0));
        var xs = p.intersect(r);
        assertEquals(1, xs.size());
        assertEquals(1, xs.getFirst().t());
        assertEquals(p, xs.getFirst().object());

    }

}
