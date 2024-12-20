package org.raytracer.patterns;

/*
 * This is Step 1 in my experiment with building up to Perlin Noise.
 */
public class WhiteNoise extends Pattern {

    private float[] a, b;

    public WhiteNoise(float[] colorA, float[] colorB) {
        this.a = colorA;
        this.b = colorB;
    }

    @Override
    public float[] colorAt(float[] point) {
        var rand = Math.round(Math.random());

        return rand == 0 ? a : b;
    }

}
