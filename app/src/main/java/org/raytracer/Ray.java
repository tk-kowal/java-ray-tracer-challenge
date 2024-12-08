package org.raytracer;

import java.util.Arrays;

import org.raytracer.shapes.Shape;
import org.raytracer.shapes.Sphere;

public class Ray {

    public record Intersection(float t, Shape object) {
    }

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

    public static Intersection[] intersect(Sphere s, Ray r) {
        var sphereToRayVector = Tuple.subtract(r.origin(), s.origin());
        var a = Tuple.dot(r.direction(), r.direction());
        var b = 2f * Tuple.dot(r.direction(), sphereToRayVector);
        var c = Tuple.dot(sphereToRayVector, sphereToRayVector) - 1f;

        var discriminant = Math.pow(b, 2) - 4 * a * c;

        if (discriminant < 0) {
            return new Intersection[0];
        } else {
            return new Intersection[] {
                    new Intersection((float) (-1 * b - Math.sqrt(discriminant)) / (2 * a), s),
                    new Intersection((float) (-1 * b + Math.sqrt(discriminant)) / (2 * a), s)
            };
        }

    }

    public static Intersection hit(Intersection[] intersections) {
        Arrays.sort(intersections, (x1, x2) -> Float.compare(x1.t(), x2.t()));
        for (var x : intersections) {
            if (x.t() < 0) {
                continue;
            } else {
                return x;
            }
        }

        return null;
    }

}
