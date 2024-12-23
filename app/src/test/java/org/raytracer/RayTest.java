package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;
import static org.raytracer.Phong.prepare;

import java.util.Arrays;

import static org.raytracer.Ray.ray;

import org.junit.jupiter.api.Test;
import org.raytracer.shapes.Plane;
import org.raytracer.shapes.Sphere;

public class RayTest {

    @Test
    public void test_rayQuery() {
        var origin = point(1, 2, 3);
        var direction = vector(4, 5, 6);
        var ray = ray(origin, direction);
        assertTrue(Tuple.areEqual(ray.origin(), origin));
        assertTrue(Tuple.areEqual(ray.direction(), direction));
    }

    @Test
    public void test_rayPosition() {
        var ray = ray(point(2, 3, 4), vector(1, 0, 0));
        assertTrue(Tuple.areEqual(point(2, 3, 4), ray.position(0)));
        assertTrue(Tuple.areEqual(point(3, 3, 4), ray.position(1)));
        assertTrue(Tuple.areEqual(point(1, 3, 4), ray.position(-1)));
        assertTrue(Tuple.areEqual(point(4.5f, 3, 4), ray.position(2.5f)));
    }

    @Test
    public void test_intersectionIncludesTimeAndObject() {
        var shape = new Sphere();
        var intersection = new Ray.Intersection(3.5f, shape);
        assertEquals(3.5, intersection.t());
        assertEquals(shape, intersection.object());
    }

    @Test
    public void test_hitReturnsFirstHit() {
        var shape = new Sphere();
        var x1 = new Ray.Intersection(1, shape);
        var x2 = new Ray.Intersection(2, shape);
        var xs = new Ray.Intersection[] { x1, x2 };
        assertEquals(x1.t(), Ray.hit(xs).t());
    }

    @Test
    public void test_hitReturnsFirstNonNegativeHit() {
        var shape = new Sphere();
        var x1 = new Ray.Intersection(-1, shape);
        var x2 = new Ray.Intersection(1, shape);
        var xs = new Ray.Intersection[] { x1, x2 };
        assertEquals(x2.t(), Ray.hit(xs).t());
    }

    @Test
    public void test_hitReturnsNullIfNoHits() {
        var shape = new Sphere();
        var x1 = new Ray.Intersection(-1, shape);
        var x2 = new Ray.Intersection(-2, shape);
        var xs = new Ray.Intersection[] { x1, x2 };
        assertNull(Ray.hit(xs));
    }

    @Test
    public void test_hitSortsIntersections() {
        var shape = new Sphere();
        var x1 = new Ray.Intersection(5, shape);
        var x2 = new Ray.Intersection(7, shape);
        var x3 = new Ray.Intersection(-3, shape);
        var x4 = new Ray.Intersection(2, shape);
        var xs = new Ray.Intersection[] { x1, x2, x3, x4 };
        Arrays.sort(xs, (a, b) -> Float.compare(a.t(), b.t()));
        assertEquals(x4.t(), Ray.hit(xs).t());
    }

    // transforming rays

    @Test
    public void test_translateRay() {
        var ray = ray(point(1, 2, 3), vector(0, 1, 0));
        var transform = Transform.translate(3, 4, 5);
        var newRay = ray.transform(transform);
        assertTrue(Tuple.areEqual(point(4, 6, 8), newRay.origin()));
        assertTrue(Tuple.areEqual(vector(0, 1, 0), newRay.direction()));
    }

    @Test
    public void test_scaleRay() {
        var ray = ray(point(1, 2, 3), vector(0, 1, 0));
        var transform = Transform.scale(2, 3, 4);
        var newRay = ray.transform(transform);
        assertTrue(Tuple.areEqual(point(2, 6, 12), newRay.origin()));
        assertTrue(Tuple.areEqual(vector(0, 3, 0), newRay.direction()));
    }

    @Test
    public void test_rotateRay() {
        var ray = ray(point(0, 0, 0), vector(0, 1, 0));
        var transform = Transform.rotateZ((float) Math.PI / 2);
        var newRay = ray.transform(transform);
        assertTrue(Tuple.areEqual(point(0, 0, 0), newRay.origin()));
        assertTrue(Tuple.areEqual(vector(-1, 0, 0), newRay.direction()));
    }

    // WORLD

    @Test
    public void test_intersectWorld() {
        var world = World.defaultWorld();
        var ray = ray(point(0, 0, -5), vector(0, 0, 1));
        var xs = Ray.intersect(world, ray);
        assertEquals(4, xs.size());
        assertTrue(Scalar.areEqual(4, xs.get(0).t()));
        assertTrue(Scalar.areEqual(4.5f, xs.get(1).t()));
        assertTrue(Scalar.areEqual(5.5f, xs.get(2).t()));
        assertTrue(Scalar.areEqual(6, xs.get(3).t()));
    }
}
