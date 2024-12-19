package org.raytracer;

import org.junit.jupiter.api.Test;
import org.raytracer.patterns.StripePattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Color.color;
import static org.raytracer.Vector.vector;
import static org.raytracer.Point.point;
import static org.raytracer.Phong.lighting;
import org.raytracer.lights.PointLight;

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

    @Test
    public void test_pattern() {
        var material = new Material()
                .setAmbient(1)
                .setDiffuse(0)
                .setSpecular(0)
                .setPattern(new StripePattern(Color.WHITE, Color.BLACK));

        var eyev = vector(0, 0, -1);
        var normalv = vector(0, 0, -1);
        var light = new PointLight(point(0, 0, -10), Color.WHITE);
        var colorA = lighting(material, light, point(0.9f, 0, 0), eyev, normalv, false);
        var colorB = lighting(material, light, point(1.1f, 0, 0), eyev, normalv, false);

        assertTrue(Tuple.areEqual(Color.WHITE, colorA));
        assertTrue(Tuple.areEqual(Color.BLACK, colorB));
    }

}
