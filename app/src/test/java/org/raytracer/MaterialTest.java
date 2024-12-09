package org.raytracer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Color.color;

public class MaterialTest {

    @Test
    public void test_Material() {
        var material = new Material();
        assertTrue(Tuple.areEqual(material.color(), color(1, 1, 1)));
        assertTrue(Scalar.areEqual(0.1f, material.ambient()));
        assertTrue(Scalar.areEqual(0.9f, material.diffuse()));
        assertTrue(Scalar.areEqual(0.9f, material.specular()));
        assertTrue(Scalar.areEqual(200.0f, material.shininess()));
    }

}
