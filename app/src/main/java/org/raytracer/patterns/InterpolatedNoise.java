package org.raytracer.patterns;

import static org.raytracer.Color.color;

public class InterpolatedNoise extends Pattern {

    private float[] a, b;

    public InterpolatedNoise(float[] colorA, float[] colorB) {
        this.a = colorA;
        this.b = colorB;
    }

    @Override
    public float[] colorAt(float[] point) {
        var lp = transform.inverse().multiply(point);
        var X = (int) lp[0] & 99;
        var Z = (int) lp[2] & 99;
        var x = (float) (lp[0] - Math.floor(lp[0]));
        var z = (float) (lp[2] - Math.floor(lp[2]));

        var noiseXA = p[X] + Z;
        var noiseXB = p[X + 1] + Z;
        var noiseZA = p[Z] + X;
        var noiseZB = p[Z + 1] + X;

        var noise = lerp(lerp(lerp(noiseXA, noiseXB, x), noiseZA, z), noiseZB, z);

        var value = noise / 100f;

        return color(value, value, value);
    }

    private float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    static final int p[] = new int[200], permutations[] = {
            93, 22, 4, 37, 2, 32, 80, 44, 13, 74, 86, 8, 79, 85, 50, 28, 53, 55, 21, 65, 56, 52, 39, 97, 61, 73, 34, 17,
            33, 57, 89, 72, 7, 35, 76, 27, 63, 25, 66, 91, 19, 59, 64, 99, 47, 95, 94, 82, 38, 92, 67, 90, 1, 46, 45,
            49, 42, 36, 96, 77, 48, 40, 71, 15, 10, 51, 18, 6, 24, 98, 41, 75, 54, 0, 84, 58, 5, 87, 11, 83, 60, 31, 78,
            12, 30, 43, 14, 69, 62, 70, 3, 16, 20, 26, 81, 68, 9, 23, 29, 88
    };

    static {
        for (int i = 0; i < 100; i++) {
            p[100 + i] = p[i] = permutations[i];
        }
    }
}
