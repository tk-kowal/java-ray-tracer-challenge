package org.raytracer.patterns;

import org.raytracer.Transform;

public class Patterns {

    public static Pattern ringsInCheckers(float[] ringColorA, float[] ringColorB, float[] checkerColor) {
        var ringp = new RingPattern(ringColorA, ringColorB);
        ringp.setTransform(Transform.scale(1f, 1, 1f));
        return new CheckerPattern(ringp, new SolidPattern(checkerColor));
    }

}
