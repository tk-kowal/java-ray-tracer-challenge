package org.raytracer;

import org.junit.jupiter.api.Test;
import org.raytracer.lights.PointLight;

import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Color.color;

public class LightingTest {

    @Test
    public void test_lightWithEyeBetweenLightAndSurface() {
        var material = new Material();
        var position = point(0, 0, 0);
        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, -10), color(1, 1, 1));
        var expected = color(1.9f, 1.9f, 1.9f);
        var actual = Lighting.lighting(material, light, position, eyev, normalv);
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
        var actual = Lighting.lighting(material, light, position, eyev, normalv);
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
        var actual = Lighting.lighting(material, light, position, eyev, normalv);
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
        var actual = Lighting.lighting(material, light, position, eyev, normalv);
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
        var actual = Lighting.lighting(material, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

}
