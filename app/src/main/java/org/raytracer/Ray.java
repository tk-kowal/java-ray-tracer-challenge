package org.raytracer;

import org.raytracer.shapes.Sphere;

public class Ray {

    private float[] origin, direction;

    protected Ray(float[] origin, float[] direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public float[] origin() {
        return this.origin;
    }

    public float[] direction() {
        return this.direction;
    }

    public float[] position(float time) {
        return Tuple.add(origin, Vector.multiply(direction, time));
    }

    // static

    public static Ray ray(float[] origin, float[] direction) {
        return new Ray(origin, direction);
    }

    public static float[] intersect(Sphere s, Ray r) {
        var sphereToRayVector = Tuple.subtract(r.origin(), s.origin());
        var a = Tuple.dot(r.direction(), r.direction());
        var b = 2f * Tuple.dot(r.direction(), sphereToRayVector);
        var c = Tuple.dot(sphereToRayVector, sphereToRayVector) - 1f;

        var discriminant = Math.pow(b, 2) - 4 * a * c;

        if (discriminant < 0) {
            return new float[0];
        } else {
            return new float[] {
                    (float) (-1 * b - Math.sqrt(discriminant)) / (2 * a),
                    (float) (-1 * b + Math.sqrt(discriminant)) / (2 * a)
            };
        }

    }

}
