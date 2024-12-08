package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;
import static org.raytracer.Ray.ray;

import org.junit.jupiter.api.Test;
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
    public void test_rayIntersectsSphereAtTwoPoints() {
        var ray = ray(point(0, 0, -5), vector(0, 0, 1));
        var sphere = new Sphere(0);
        var intersections = Ray.intersect(sphere, ray);
        assertEquals(2, intersections.length);
        assertEquals(4f, intersections[0]);
        assertEquals(6f, intersections[1]);
    }

    @Test
    public void test_raySphereTangent() {
        var ray = ray(point(0, 1, -5), vector(0, 0, 1));
        var sphere = new Sphere(0);
        var intersections = Ray.intersect(sphere, ray);
        assertEquals(2, intersections.length);
        assertEquals(5f, intersections[0]);
        assertEquals(5f, intersections[1]);
    }

    @Test
    public void test_raySphereMiss() {
        var ray = ray(point(0, 2, -5), vector(0, 0, 1));
        var sphere = new Sphere(0);
        var intersections = Ray.intersect(sphere, ray);
        assertEquals(0, intersections.length);
    }

    @Test
    public void test_rayFromInsideSphereIntersectsInFrontAndBehind() {
        var ray = ray(point(0, 0, 0), vector(0, 0, 1));
        var sphere = new Sphere(0);
        var intersections = Ray.intersect(sphere, ray);
        assertEquals(2, intersections.length);
        assertEquals(-1.0f, intersections[0]);
        assertEquals(1f, intersections[1]);
    }

    @Test
    public void test_rayFromBeyondSphereIntersectsInBehind() {
        var ray = ray(point(0, 0, 5), vector(0, 0, 1));
        var sphere = new Sphere(0);
        var intersections = Ray.intersect(sphere, ray);
        assertEquals(2, intersections.length);
        assertEquals(-6.0f, intersections[0]);
        assertEquals(-4.0f, intersections[1]);
    }

}
