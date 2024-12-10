package org.raytracer.shapes;

import static org.raytracer.Point.point;

import org.raytracer.Tuple;
import org.raytracer.Vector;

public class Sphere extends Shape {

    public Sphere(int id) {
        super(id);
    }

    public float[] normalAt(float[] point) {
        return normalAt(point[0], point[1], point[2]);
    }

    public float[] normalAt(float x, float y, float z) {
        var objectNormal = Tuple.subtract(transform.inverse().multiply(point(x, y, z)), objectOrigin());
        var worldNormal = transform.submatrix(3, 3).inverse().transpose().multiply(objectNormal);
        return Vector.normalize(worldNormal);
    }

    public boolean equals(Object other) {
        if (other instanceof Sphere) {
            var otherSphere = (Sphere) other;
            return Tuple.areEqual(origin, otherSphere.origin)
                    && transform.equals(otherSphere.transform)
                    && material.equals(otherSphere.material);
        } else {
            return false;
        }
    }

}
