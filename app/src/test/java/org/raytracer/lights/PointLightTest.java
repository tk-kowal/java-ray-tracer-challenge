package org.raytracer.lights;

import org.junit.jupiter.api.Test;
import org.raytracer.Tuple;

import static org.raytracer.Point.point;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Color.color;

public class PointLightTest {

    @Test
    public void test_lightHasPositionAndIntensity() {
        var position = point(0, 0, 0);
        var intensity = color(1, 1, 1);
        var light = new PointLight(position, intensity);
        assertTrue(Tuple.areEqual(position, light.position()));
        assertTrue(Tuple.areEqual(intensity, light.intensity()));
    }
}
