package org.raytracer.shapes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.raytracer.Matrix;
import org.raytracer.Transform;

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
}
