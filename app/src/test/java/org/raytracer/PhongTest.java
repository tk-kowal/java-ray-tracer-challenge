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

    @Test
    public void test_prepareHitInsideShape() {
        var r = ray(point(0, 0, 0), vector(0, 0, 1));
        var s = new Sphere(0);
        var i = new Ray.Intersection(1, s);
        var result = Phong.prepare(i, r);
        assertTrue(result.isInside());
        assertTrue(Tuple.areEqual(vector(0, 0, -1), result.normalv()));
    }

    // World

    @Test
    public void test_shadeHit() {
        var w = World.defaultWorld();
        var r = ray(point(0, 0, -5), vector(0, 0, 1));
        var s = w.objects().getFirst();
        var i = new Ray.Intersection(4, s);
        var params = Phong.prepare(i, r);
        var result = Phong.shadeHit(w, params);
        assertTrue(Tuple.areEqual(color(0.38066f, 0.47583f, 0.2855f), result));
    }

    @Test
    public void test_shadeHitInsideShape() {
        var w = World.defaultWorld();
        w.lights().get(0).setPosition(point(0f, 0.25f, 0f));
        var r = ray(point(0, 0, 0), vector(0, 0, 1));
        var s = w.objects().getLast();
        var i = new Ray.Intersection(0.5f, s);
        var params = Phong.prepare(i, r);
        var result = Phong.shadeHit(w, params);
        assertTrue(Tuple.areEqual(color(0.90498f, 0.90498f, 0.90498f), result));
    }

    @Test
    public void test_colorAtMiss() {
        var w = World.defaultWorld();
        var r = ray(point(0, 0, -5), vector(0, 1, 0));
        var result = Phong.colorAt(w, r);
        assertTrue(Tuple.areEqual(color(0, 0, 0), result));
    }

    @Test
    public void test_colorAtHit() {
        var w = World.defaultWorld();
        var r = ray(point(0, 0, -5), vector(0, 0, 1));
        var result = Phong.colorAt(w, r);
        assertTrue(Tuple.areEqual(color(.38066f, .47583f, .2855f), result));
    }

    @Test
    public void test_colorWithIntersectionBehindRay() {
        var w = World.defaultWorld();
        w.objects().getFirst().material().setAmbient(1f); // outer sphere
        w.objects().getLast().material().setAmbient(1f); // inner sphere
        var r = ray(point(0, 0, 0.75f), vector(0, 0, -1));
        var expected = w.objects().getLast().material().color();
        var actual = Phong.colorAt(w, r);
        assertTrue(Tuple.areEqual(expected, actual));
    }

}
