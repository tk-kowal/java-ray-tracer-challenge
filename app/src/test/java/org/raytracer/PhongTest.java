package org.raytracer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.raytracer.lights.PointLight;
import org.raytracer.shapes.Plane;
import org.raytracer.shapes.Shape;
import org.raytracer.shapes.Sphere;

import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Color.color;
import static org.raytracer.Ray.ray;

public class PhongTest {

    private Shape plane;

    @BeforeEach
    public void setUp() {
        this.plane = new Plane();
    }

    @Test
    public void test_lightWithEyeBetweenLightAndSurface() {
        var position = point(0, 0, 0);
        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, -10), color(1, 1, 1));
        var expected = color(1.9f, 1.9f, 1.9f);
        var actual = Phong.lighting(plane, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_lightWithEyeOffset45Degrees() {
        var position = point(0, 0, 0);
        var eyev = vector(0, (float) Math.sqrt(2) / 2, (float) (-1 * Math.sqrt(2) / 2));
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, -10), color(1, 1, 1));
        var expected = color(1f, 1f, 1f);
        var actual = Phong.lighting(plane, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_lightWithLightOffset45Degrees() {
        var position = point(0, 0, 0);
        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 10, -10), color(1, 1, 1));
        var expected = color(0.7364f, 0.7364f, 0.7364f);
        var actual = Phong.lighting(plane, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_lightWithEyeInPathOfReflectionVector() {
        var position = point(0, 0, 0);
        var eyev = vector(0, (float) (-1 * Math.sqrt(2) / 2), (float) (-1 * Math.sqrt(2) / 2));
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 10, -10), color(1, 1, 1));
        var expected = color(1.6364f, 1.6364f, 1.6364f);
        var actual = Phong.lighting(plane, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_lightBehindSurface() {
        var position = point(0, 0, 0);
        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, 10), color(1, 1, 1));
        var expected = color(0.1f, 0.1f, 0.1f);
        var actual = Phong.lighting(plane, light, position, eyev, normalv);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    // Prep Vectors

    @Test
    public void test_prepare() {
        var r = ray(point(0, 0, -5), vector(0, 0, 1));
        var s = new Sphere();
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
        var s = new Sphere();
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
        var result = Phong.shadeHit(w, params, 1);
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
        var result = Phong.shadeHit(w, params, 1);
        assertTrue(Tuple.areEqual(color(0.90498f, 0.90498f, 0.90498f), result));
    }

    @Test
    public void test_shadeHitInShadow() {
        var w = World.defaultWorld();
        w.lights().get(0).setPosition(point(0f, 0f, -10f));
        var r = ray(point(0, 0, 5), vector(0, 0, 1));
        var s2 = w.objects().getLast();
        s2.setTransform(Transform.translate(0, 0, 10));
        var i = new Ray.Intersection(4, s2);
        var params = Phong.prepare(i, r);
        var result = Phong.shadeHit(w, params, 1);
        assertTrue(Tuple.areEqual(color(0.1f, 0.1f, 0.1f), result));
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

    // Shadows

    @Test
    public void test_lightingWithSurfaceInShadow() {
        var point = point(0, 0, 0);
        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, -10), color(1, 1, 1));
        var inShadow = true;
        var result = Phong.lighting(plane, light, point, eyev, normalv, inShadow);
        assertTrue(Tuple.areEqual(color(.1f, .1f, .1f), result));
    }

    @Test
    public void test_noShadowWhenNothingBetweenPointAndLight() {
        var w = World.defaultWorld();
        var p = point(0, 10, 0);
        assertFalse(Phong.isShadowed(w, p));
    }

    @Test
    public void test_inShadowWhenObjectBetweenPointAndLight() {
        var w = World.defaultWorld();
        var p = point(10, -10, 10);
        assertTrue(Phong.isShadowed(w, p));
    }

    @Test
    public void test_noShadowWhenObjectBehindLight() {
        var w = World.defaultWorld();
        var p = point(-20, 20, -20);
        assertFalse(Phong.isShadowed(w, p));
    }

    @Test
    public void test_noShadowWhenObjectBehindPoint() {
        var w = World.defaultWorld();
        var p = point(-2, 2, -2);
        assertFalse(Phong.isShadowed(w, p));
    }

    // REFLECTIONS

    @Test
    public void test_reflectRay() {
        var plane = new Plane();
        var ray = ray(point(0, 1, -1), vector(0, (float) (-1 * Math.sqrt(2) / 2), (float) Math.sqrt(2) / 2));
        var i = new Ray.Intersection((float) Math.sqrt(2), plane);
        var expected = vector(0, (float) Math.sqrt(2) / 2, (float) Math.sqrt(2) / 2);
        var actual = Phong.prepare(i, ray).reflectv();
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_nonreflect() {
        var world = World.defaultWorld();
        var ray = ray(point(0, 0, 0), vector(0, 0, 1));
        var shape = world.objects().getLast();
        shape.material().setAmbient(1);
        var i = new Ray.Intersection(1, shape);
        var params = Phong.prepare(i, ray);
        assertTrue(Tuple.areEqual(color(0, 0, 0), Phong.reflectedColorAt(world, params, 1)));
    }

    @Test
    public void test_reflectiveHit() {
        var world = World.defaultWorld();
        var ray = ray(point(0, 0, 0), vector(0, 0, 1));
        var shape = world.objects().getLast();
        shape.material().setAmbient(1).setReflective(0.5f);
        var i = new Ray.Intersection(1, shape);
        var params = Phong.prepare(i, ray);
        assertTrue(Tuple.areEqual(Tuple.multiply(shape.material().color(), 0.5f),
                Phong.reflectedColorAt(world, params, 1)));
    }

    @Test
    public void test_shadeHitReflective() {
        var w = World.defaultWorld();
        var p = new Plane();
        p.setMaterial(new Material().setReflective(0.5f));
        p.setTransform(Transform.translate(0, -1, 0));
        w.objects().add(p);

        var r = ray(point(0, 0, -3), vector(0, (float) (-1 * Math.sqrt(2) / 2), (float) Math.sqrt(2) / 2));
        var i = new Ray.Intersection((float) Math.sqrt(2), p);
        var params = Phong.prepare(i, r);
        var result = Phong.shadeHit(w, params, 1);
        assertTrue(Tuple.areEqual(color(0.87677f, 0.92436f, 0.82918f), result));
    }

}
