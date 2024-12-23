package org.raytracer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.raytracer.lights.PointLight;
import org.raytracer.patterns.TestPattern;
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
        assertEquals(i.t(), result.t);
        assertEquals(s, (Sphere) result.s);
        assertTrue(Tuple.areEqual(point(0, 0, -1), result.point));
        assertTrue(Tuple.areEqual(vector(0, 0, -1), result.eyev));
        assertTrue(Tuple.areEqual(vector(0, 0, -1), result.normalv));
    }

    @Test
    public void test_prepareHitInsideShape() {
        var r = ray(point(0, 0, 0), vector(0, 0, 1));
        var s = new Sphere();
        var i = new Ray.Intersection(1, s);
        var result = Phong.prepare(i, r);
        assertTrue(result.isInside);
        assertTrue(Tuple.areEqual(vector(0, 0, -1), result.normalv));
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
        var actual = Phong.prepare(i, ray).reflectv;
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

    @Test
    public void test_refractix() {
        var a = Sphere.glassSphere();
        a.setTransform(Transform.scale(2, 2, 2));

        var b = Sphere.glassSphere();
        b.setTransform(Transform.translate(0, 0, -.25f));
        b.material().setRefractIx(2f);

        var c = Sphere.glassSphere();
        c.setTransform(Transform.translate(0, 0, .25f));
        c.material().setRefractIx(2.5f);

        var w = new World();
        w.objects().addAll(List.of(a, b, c));

        var r = ray(point(0, 0, -4), vector(0, 0, 1));

        var xs = Ray.intersect(w, r);
        var expectedN1s = List.of(1.0f, 1.5f, 2.0f, 2.5f, 2.5f, 1.5f);
        var expectedN2s = List.of(1.5f, 2.0f, 2.5f, 2.5f, 1.5f, 1.0f);

        for (var i = 0; i < 6; i++) {
            var params = Phong.prepare(xs.get(i), r, xs);
            assertTrue(Scalar.areEqual(expectedN1s.get(i), params.n1));
            assertTrue(Scalar.areEqual(expectedN2s.get(i), params.n2));
        }
    }

    @Test
    public void test_refractedColorAtOpaque() {
        var w = new World();
        w.objects().add(new Sphere());

        var r = ray(point(0, 0, -5), vector(0, 0, 1));
        var xs = Ray.intersect(w, r);
        var params = Phong.prepare(Ray.hit(xs), r, xs);

        var result = Phong.refractedColorAt(w, params, 5);

        assertTrue(Tuple.areEqual(Color.BLACK, result));
    }

    @Test
    public void test_refractedColorAtMaxRecursion() {
        var w = new World();
        w.objects().add(Sphere.glassSphere());

        var r = ray(point(0, 0, -5), vector(0, 0, 1));
        var xs = Ray.intersect(w, r);
        var params = Phong.prepare(Ray.hit(xs), r, xs);

        var result = Phong.refractedColorAt(w, params, 0);

        assertTrue(Tuple.areEqual(Color.BLACK, result));
    }

    @Test
    public void test_refractedTotalInternalReflection() {
        var w = new World();
        var s = Sphere.glassSphere();
        w.objects().add(s);

        var r = ray(point(0.8f, 0.3f, 0), vector(0.1f, -1, 0));

        var xs = Ray.intersect(w, r);
        var params = Phong.prepare(Ray.hit(xs), r, xs);
        var result = Phong.refractedColorAt(w, params, 5);

        assertTrue(Tuple.areEqual(Color.BLACK, result));
    }

    @Test
    public void test_refractedColor() {
        var w = World.defaultWorld();
        var a = w.objects().getFirst();
        a.setMaterial(new Material().setAmbient(1).setPattern(new TestPattern()));

        var b = w.objects().get(1);
        b.setMaterial(new Material().setTransparency(1).setRefractIx(1.5f));

        var r = ray(point(0, 0, 0.1f), vector(0, 1, 0));
        var xs = Ray.intersect(w, r);
        var params = Phong.prepare(Ray.hit(xs), r, xs);
        var result = Phong.refractedColorAt(w, params, 5);
        assertTrue(Tuple.areEqual(color(0, 0.99888f, 0.04725f), result));
    }
}
