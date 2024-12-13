package org.raytracer;

import java.util.ArrayList;
import java.util.List;

import org.raytracer.shapes.Shape;

public class Ray {

    public record Intersection(float t, Shape object) {
    }

    private float[] origin, direction;

    protected Ray(float[] origin, float[] direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public float[] direction() {
        return this.direction;
    }

    public boolean equals(Object other) {
        if (other instanceof Ray) {
            var otherRay = (Ray) other;
            return Tuple.areEqual(this.origin, otherRay.origin) && Tuple.areEqual(this.direction, otherRay.direction);
        } else {
            return false;
        }
    }

    public float[] origin() {
        return this.origin;
    }

    public float[] position(float time) {
        return Tuple.add(origin, Vector.multiply(direction, time));
    }

    public Ray transform(Matrix transform) {
        return new Ray(transform.multiply(origin), transform.multiply(direction));
    }

    public String toString() {
        return "Origin: " + Tuple.pretty(origin) + " Direction: " + Tuple.pretty(direction) + System.lineSeparator();
    }

    // static

    public static Ray ray(float[] origin, float[] direction) {
        return new Ray(origin, direction);
    }

    public static List<Intersection> intersect(World w, Ray r) {
        List<Intersection> xs = new ArrayList<>();
        for (var o : w.objects()) {
            xs.addAll(o.intersect(r));
        }
        xs.sort((x1, x2) -> Float.compare(x1.t(), x2.t()));
        return xs;
    }

    public static Intersection hit(Intersection[] xs) {
        return hit(List.of(xs));
    }

    public static Intersection hit(List<Intersection> xs) {
        for (var x : xs) {
            if (x.t() < 0) {
                continue;
            } else {
                return x;
            }
        }

        return null;
    }

}
