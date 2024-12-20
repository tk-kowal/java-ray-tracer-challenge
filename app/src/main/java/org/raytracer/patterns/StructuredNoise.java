package org.raytracer.patterns;

/*
 * This is Step 2 in my experiment with building up to Perlin noise from white noise.
 */
public class StructuredNoise extends Pattern {

    private float[] a, b;

    public StructuredNoise(float[] colorA, float[] colorB) {
        this.a = colorA;
        this.b = colorB;
    }

    @Override
    public float[] colorAt(float[] point) {
        var lp = transform.inverse().multiply(point);
        var X = (int) lp[0] & 99;
        var Z = (int) lp[2] & 99;

        var noiseAtPoint = p[(p[X] + Z) % p.length];

        return noiseAtPoint % 2 == 0 ? a : b;

    }

    static final int p[] = {
            93, 22, 4, 37, 2, 32, 80, 44, 13, 74, 86, 8, 79, 85, 50, 28, 53, 55, 21, 65, 56, 52, 39, 97, 61, 73, 34, 17,
            33, 57, 89, 72, 7, 35, 76, 27, 63, 25, 66, 91, 19, 59, 64, 99, 47, 95, 94, 82, 38, 92, 67, 90, 1, 46, 45,
            49, 42, 36, 96, 77, 48, 40, 71, 15, 10, 51, 18, 6, 24, 98, 41, 75, 54, 0, 84, 58, 5, 87, 11, 83, 60, 31, 78,
            12, 30, 43, 14, 69, 62, 70, 3, 16, 20, 26, 81, 68, 9, 23, 29, 88
    };

}
