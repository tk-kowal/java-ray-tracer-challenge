package org.raytracer.shapes;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Vector.vector;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.raytracer.Matrix;
import org.raytracer.Scalar;
import org.raytracer.Transform;
import org.raytracer.Tuple;
import org.raytracer.Material;

public class SphereTest {

    @Test
    public void test_sphereHasDefaultTransform() {
        var sphere = new Sphere(0);
        assertTrue(sphere.transform().equals(Matrix.identity()));
    }

    @Test
    public void test_setSphereTransform() {
        var sphere = new Sphere(0);
        var newTransform = Transform.translate(2, 3, 4);
        sphere.setTransform(newTransform);
        assertTrue(sphere.transform().equals(newTransform));
    }

    @Test
    public void test_normalAtXAxis() {
        var sphere = new Sphere(0);
        var expected = vector(1, 0, 0);
        var actual = sphere.normalAt(1, 0, 0);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalAtYAxis() {
        var sphere = new Sphere(0);
        var expected = vector(0, 1, 0);
        var actual = sphere.normalAt(0, 1, 0);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalAtZAxis() {
        var sphere = new Sphere(0);
        var expected = vector(0, 0, 1);
        var actual = sphere.normalAt(0, 0, 1);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalNonAxial() {
        var sphere = new Sphere(0);
        sphere.setTransform(Transform.rotateY((float) Math.PI / 4));
        var expected = vector((float) Math.sqrt(3) / 3, (float) Math.sqrt(3) / 3, (float) Math.sqrt(3) / 3);
        var actual = sphere.normalAt((float) Math.sqrt(3) / 3, (float) Math.sqrt(3) / 3, (float) Math.sqrt(3) / 3);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalOnTranslatedSphere() {
        var sphere = new Sphere(0);
        sphere.setTransform(Transform.translate(0, 1, 0));
        var expected = vector(0, 0.70711f, -0.70711f);
        var actual = sphere.normalAt(0, 1.70711f, -0.70711f);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    public void test_normalOnTransformedSphere() {
        var sphere = new Sphere(0);
        sphere.setTransform(Transform.scale(1, 0.5f, 1).rotateZ((float) (Math.PI / 5)));
        var expected = vector(0, 0.97014f, -0.24254f);
        var actual = sphere.normalAt(0, (float) (Math.sqrt(2) / 2), (float) (-1 * (Math.sqrt(2) / 2)));
        assertTrue(Tuple.areEqual(expected, actual));
    }

    // Material

    @Test
    public void test_sphereHasDefaultMaterial() {
        var sphere = new Sphere(0);
        var defaultMaterial = new Material();
        var sphereMaterial = sphere.material();
        assertTrue(Scalar.areEqual(sphereMaterial.ambient(), defaultMaterial.ambient()));
        assertTrue(Scalar.areEqual(sphereMaterial.diffuse(), defaultMaterial.diffuse()));
        assertTrue(Scalar.areEqual(sphereMaterial.specular(), defaultMaterial.specular()));
        assertTrue(Scalar.areEqual(sphereMaterial.shininess(), defaultMaterial.shininess()));
        assertTrue(Tuple.areEqual(sphereMaterial.color(), defaultMaterial.color()));
    }

}
