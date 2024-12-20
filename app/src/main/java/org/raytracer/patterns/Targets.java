package org.raytracer.patterns;

public class Targets extends Pattern {

    private float[] a, b, c;

    public Targets(float[] a, float[] b, float[] c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public float[] colorAt(float[] point) {
        var lp = transform.inverse().multiply(point);
        var floored_x = Math.floor(lp[0]);
        var floored_z = Math.floor(lp[2]);
        var x = (floored_x + floored_z) % 2 == 0;

        if (x) {
            var targetCenter_x = floored_x + 0.5f;
            var targetCenter_z = floored_z + 0.5f;
            var distanceFromTargetCenter = Math
                    .floor(10 * Math.sqrt(Math.pow(targetCenter_x - lp[0], 2) + Math.pow(targetCenter_z - lp[2], 2)));

            if (distanceFromTargetCenter % 2 == 0) {
                return a;
            } else {
                return b;
            }

        } else {
            return c;
        }
    }

}
