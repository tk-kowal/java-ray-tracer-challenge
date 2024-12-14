package org.raytracer.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Vector.vector;
import static org.raytracer.Point.point;
import static org.raytracer.Ray.ray;

import org.junit.jupiter.api.Test;
import org.raytracer.Matrix;
import org.raytracer.Scalar;
import org.raytracer.Transform;
import org.raytracer.Tuple;
import org.raytracer.Material;

public class SphereTest {

    @Test
    public void test_sphereHasDefaultTransform() {
        var sphere = new Sphere();
        assertTrue(sphere.transform().equals(Matrix.identity()));
    }

    @Test
    public void test_setSphereTransform() {
        var sphere = new Sphere();
        var newTransform = Transform.translate(2, 3, 4);
        sphere.setTransform(newTransform);
        assertTrue(sphere.transform().equals(newTransform));
    }

    @Test
    public void test_normalAtXAxis() {
        var sphere = new Sphere();
        var expected = vector(1, 0, 0);
        var actual = sphere.normalAt(1, 0, 0);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalAtYAxis() {
        var sphere = new Sphere();
        var expected = vector(0, 1, 0);
        var actual = sphere.normalAt(0, 1, 0);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalAtZAxis() {
        var sphere = new Sphere();
        var expected = vector(0, 0, 1);
        var actual = sphere.normalAt(0, 0, 1);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalNonAxial() {
        var sphere = new Sphere();
        sphere.setTransform(Transform.rotateY((float) Math.PI / 4));
        var expected = vector((float) Math.sqrt(3) / 3, (float) Math.sqrt(3) / 3, (float) Math.sqrt(3) / 3);
        var actual = sphere.normalAt((float) Math.sqrt(3) / 3, (float) Math.sqrt(3) / 3, (float) Math.sqrt(3) / 3);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalOnTranslatedSphere() {
        var sphere = new Sphere();
        sphere.setTransform(Transform.translate(0, 1, 0));
        var expected = vector(0, 0.70711f, -0.70711f);
        var actual = sphere.normalAt(0, 1.70711f, -0.70711f);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalOnTransformedSphere() {
        var sphere = new Sphere();
        sphere.setTransform(Transform.scale(1, 0.5f, 1).rotateZ((float) (Math.PI / 5)));
        var expected = vector(0, 0.97014f, -0.24254f);
        var actual = sphere.normalAt(0, (float) (Math.sqrt(2) / 2), (float) (-1 * (Math.sqrt(2) / 2)));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    // Material

    @Test
    public void test_sphereHasDefaultMaterial() {
        var sphere = new Sphere();
        var defaultMaterial = new Material();
        var sphereMaterial = sphere.material();
        assertTrue(Scalar.areEqual(sphereMaterial.ambient(), defaultMaterial.ambient()));
        assertTrue(Scalar.areEqual(sphereMaterial.diffuse(), defaultMaterial.diffuse()));
        assertTrue(Scalar.areEqual(sphereMaterial.specular(), defaultMaterial.specular()));
        assertTrue(Scalar.areEqual(sphereMaterial.shininess(), defaultMaterial.shininess()));
        assertTrue(Tuple.areEqual(sphereMaterial.color(), defaultMaterial.color()));
    }

    // Intersections

    @Test
    public void test_intersectScaledSphere() {
        var ray = ray(point(0, 0, -5), vector(0, 0, 1));
        var sphere = new Sphere();
        sphere.setTransform(Transform.scale(2, 2, 2));
        var xs = sphere.intersect(ray);
        assertEquals(2, xs.size());
        assertEquals(3f, xs.getFirst().t());
        assertEquals(7f, xs.getLast().t());
    }

    @Test
    public void test_intersectTranslatedSphere() {
        var ray = ray(point(0, 0, -5), vector(0, 0, 1));
        var sphere = new Sphere();
        sphere.setTransform(Transform.translate(5, 0, 0));
        var xs = sphere.intersect(ray);
        assertEquals(0, xs.size());
    }

    @Test
    public void test_rayIntersectsSphereAtTwoPoints() {
        var ray = ray(point(0, 0, -5), vector(0, 0, 1));
        var sphere = new Sphere();
        var xs = sphere.intersect(ray);
        assertEquals(2, xs.size());
        assertEquals(4f, xs.getFirst().t());
        assertEquals(6f, xs.getLast().t());
        assertEquals(sphere, xs.getFirst().object());
        assertEquals(sphere, xs.getLast().object());
    }

    @Test
    public void test_raySphereTangent() {
        var ray = ray(point(0, 1, -5), vector(0, 0, 1));
        var sphere = new Sphere();
        var xs = sphere.intersect(ray);
        assertEquals(2, xs.size());
        assertEquals(5f, xs.getFirst().t());
        assertEquals(5f, xs.getLast().t());
    }

    @Test
    public void test_raySphereMiss() {
        var ray = ray(point(0, 2, -5), vector(0, 0, 1));
        var sphere = new Sphere();
        var xs = sphere.intersect(ray);
        assertEquals(0, xs.size());
    }

    @Test
    public void test_rayFromInsideSphereIntersectsInFrontAndBehind() {
        var ray = ray(point(0, 0, 0), vector(0, 0, 1));
        var sphere = new Sphere();
        var xs = sphere.intersect(ray);
        assertEquals(2, xs.size());
        assertEquals(-1.0f, xs.getFirst().t());
        assertEquals(1f, xs.getLast().t());
    }

    @Test
    public void test_rayFromBeyondSphereIntersectsInBehind() {
        var ray = ray(point(0, 0, 5), vector(0, 0, 1));
        var sphere = new Sphere();
        var xs = sphere.intersect(ray);
        assertEquals(2, xs.size());
        assertEquals(-6.0f, xs.getFirst().t());
        assertEquals(-4.0f, xs.getLast().t());
    }

}
