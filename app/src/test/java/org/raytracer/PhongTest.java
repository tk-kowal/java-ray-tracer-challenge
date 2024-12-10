package org.raytracer;

import org.junit.jupiter.api.Test;
import org.raytracer.lights.PointLight;
import org.raytracer.shapes.Sphere;

import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Color.color;
import static org.raytracer.Ray.ray;

public class PhongTest {

    @Test
    public void test_lightWithEyeBetweenLightAndSurface() {
        var material = new Material();
        var position = point(0, 0, 0);
        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, -10), color(1, 1, 1));
        var expected = color(1.9f, 1.9f, 1.9f);
        var actual = Phong.lighting(material, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_lightWithEyeOffset45Degrees() {
        var material = new Material();
        var position = point(0, 0, 0);
        var eyev = vector(0, (float) Math.sqrt(2) / 2, (float) (-1 * Math.sqrt(2) / 2));
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, -10), color(1, 1, 1));
        var expected = color(1f, 1f, 1f);
        var actual = Phong.lighting(material, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_lightWithLightOffset45Degrees() {
        var material = new Material();
        var position = point(0, 0, 0);
        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 10, -10), color(1, 1, 1));
        var expected = color(0.7364f, 0.7364f, 0.7364f);
        var actual = Phong.lighting(material, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_lightWithEyeInPathOfReflectionVector() {
        var material = new Material();
        var position = point(0, 0, 0);
        var eyev = vector(0, (float) (-1 * Math.sqrt(2) / 2), (float) (-1 * Math.sqrt(2) / 2));
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 10, -10), color(1, 1, 1));
        var expected = color(1.6364f, 1.6364f, 1.6364f);
        var actual = Phong.lighting(material, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_lightBehindSurface() {
        var material = new Material();
        var position = point(0, 0, 0);
        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, 10), color(1, 1, 1));
        var expected = color(0.1f, 0.1f, 0.1f);
        var actual = Phong.lighting(material, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    // Prep Vectors

    @Test
    public void test_prepare() {
        var r = ray(point(0, 0, -5), vector(0, 0, 1));
        var s = new Sphere(0);
        var i = new Ray.Intersection(4, s);
        var result = Phong.prepare(i, r);
        assertEquals(i.t(), result.t());
        assertEquals(s, (Sphere) result.s());
        assertTrue(Tuple.areEqual(point(0, 0, -1), result.point()));
        assertTrue(Tuple.areEqual(vector(0, 0, -1), result.eyev()));
        assertTrue(Tuple.areEqual(vector(0, 0, -1), result.normalv()));
    }

}
