package org.raytracer.patterns;

import static org.raytracer.Color.color;

public class TestPattern extends Pattern {

    @Override
    public float[] colorAt(float[] point) {
        return color(point[0], point[1], point[2]);
    }

}
