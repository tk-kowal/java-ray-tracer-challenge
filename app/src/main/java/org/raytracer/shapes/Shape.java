package org.raytracer.shapes;

import static org.raytracer.Point.point;

import java.util.List;

import org.raytracer.Material;
import org.raytracer.Matrix;
import org.raytracer.Ray;
import org.raytracer.Vector;

public abstract class Shape {
    protected Matrix transform;
    protected Material material;
    protected float[] origin = point(0, 0, 0);

    protected Shape() {
        this.transform = Matrix.identity();
        this.material = new Material();
    }

    public float[] colorAt(float[] point) {
        var localPoint = transform.inverse().multiply(point);
        return material.colorAt(localPoint);
    }

    public abstract List<Ray.Intersection> intersect(Ray ray);

    public Material material() {
        return this.material;
    }

    public void setMaterial(Material m) {
        this.material = m;
    }

    public float[] normalAt(float[] point) {
        return normalAt(point[0], point[1], point[2]);
    }

    public float[] normalAt(float x, float y, float z) {
        var localPoint = transform.inverse().multiply(point(x, y, z));
        var localNormal = localNormalAt(localPoint);
        var worldNormal = transform.submatrix(3, 3).inverse().transpose().multiply(localNormal);
        return Vector.normalize(worldNormal);
    }

    public abstract float[] localNormalAt(float[] point);

    public void setTransform(Matrix newTransform) {
        this.transform = newTransform;
    }

    public Matrix transform() {
        return this.transform;
    }

    public abstract boolean equals(Object other);
}
