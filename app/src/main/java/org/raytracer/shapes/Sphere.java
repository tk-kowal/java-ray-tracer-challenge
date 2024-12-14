package org.raytracer.shapes;

import java.util.List;

import org.raytracer.Ray;
import org.raytracer.Tuple;

import static org.raytracer.Tuple.subtract;
import static org.raytracer.Tuple.dot;

public class Sphere extends Shape {

    public Sphere() {
        super();
    }

    public boolean equals(Object other) {
        if (other instanceof Sphere) {
            var otherSphere = (Sphere) other;
            return transform.equals(otherSphere.transform) && material.equals(otherSphere.material);
        } else {
            return false;
        }
    }

    public List<Ray.Intersection> intersect(Ray r) {
        var transformedRay = r.transform(transform.inverse());
        var sphereToRayVector = subtract(transformedRay.origin(), this.origin);
        var a = dot(transformedRay.direction(), transformedRay.direction());
        var b = 2f * dot(transformedRay.direction(), sphereToRayVector);
        var c = dot(sphereToRayVector, sphereToRayVector) - 1f;

        var discriminant = Math.pow(b, 2) - 4 * a * c;

        if (discriminant < 0) {
            return List.of();
        } else {
            return List.of(
                    new Ray.Intersection((float) (-1 * b - Math.sqrt(discriminant)) / (2 * a), this),
                    new Ray.Intersection((float) (-1 * b + Math.sqrt(discriminant)) / (2 * a), this));
        }
    }

    public float[] localNormalAt(float[] point) {
        return Tuple.subtract(point, origin);
    }

}
