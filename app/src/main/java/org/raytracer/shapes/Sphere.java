package org.raytracer.shapes;

import static org.raytracer.Point.point;

import org.raytracer.Tuple;
import org.raytracer.Vector;

public class Sphere extends Shape {

    public Sphere(int id) {
        super(id);
    }

    public float[] normalAt(float x, float y, float z) {
        var objectNormal = Tuple.subtract(transform.inverse().multiply(point(x, y, z)), objectOrigin());
        var worldNormal = transform.inverse().transpose().multiply(objectNormal);
        worldNormal[3] = 0;
        return Vector.normalize(worldNormal);
    }

}
