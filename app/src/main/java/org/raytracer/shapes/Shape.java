package org.raytracer.shapes;

import static org.raytracer.Point.point;

import org.raytracer.Matrix;

public abstract class Shape {
    protected final int id;
    protected float[] origin;
    protected Matrix transform;

    protected Shape(int id) {
        this.id = id;
        this.origin = point(0, 0, 0);
        this.transform = Matrix.identity();
    }

    public int id() {
        return id;
    }

    public float[] worldOrigin() {
        return this.origin;
    }

    public float[] objectOrigin() {
        return point(0, 0, 0);
    }

    public Matrix transform() {
        return this.transform;
    }

    public void setTransform(Matrix newTransform) {
        this.origin = newTransform.multiply(origin);
        this.transform = newTransform;
    }

    public abstract float[] normalAt(float x, float y, float z);
}
