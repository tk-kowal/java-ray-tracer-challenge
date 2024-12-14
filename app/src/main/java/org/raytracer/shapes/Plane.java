package org.raytracer.shapes;

import java.util.List;

import org.raytracer.Constants;
import org.raytracer.Ray;
import org.raytracer.Ray.Intersection;

import static org.raytracer.Vector.vector;

public class Plane extends Shape {

    public Plane() {
        super();
    }

    @Override
    public List<Intersection> intersect(Ray ray) {
        var localRay = ray.transform(transform.inverse());
        var d = localRay.direction();
        var o = localRay.origin();
        if (Math.abs(d[1]) < Constants.EPSILON) {
            return List.of();
        } else {
            var t = (-1f * o[1]) / d[1];
            return List.of(new Ray.Intersection(t, this));
        }
    }

    @Override
    public float[] localNormalAt(float[] localPoint) {
        return vector(0, 1, 0);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Plane) {
            return ((Plane) other).transform().equals(transform);
        }
        return false;
    }
}
