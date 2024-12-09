package org.raytracer.shapes;

import static org.raytracer.Point.point;

import org.raytracer.Material;
import org.raytracer.Matrix;

public abstract class Shape {
    protected final int id;
    protected float[] origin;
    protected Matrix transform;
    protected Material material;

    protected Shape(int id) {
        this.id = id;
        this.origin = point(0, 0, 0);
        this.transform = Matrix.identity();
        this.material = new Material();
    }

    public int id() {
        return id;
    }

    public Material material() {
        return this.material;
    }

    public void setMaterial(Material m) {
        this.material = m;
    }

    public abstract float[] normalAt(float x, float y, float z);

    public float[] objectOrigin() {
        return point(0, 0, 0);
    }

    public void setTransform(Matrix newTransform) {
        this.origin = newTransform.multiply(origin);
        this.transform = newTransform;
    }

    public Matrix transform() {
        return this.transform;
    }

    public float[] worldOrigin() {
        return this.origin;
    }
}
