package org.raytracer.shapes;

import static org.raytracer.Point.point;

import org.raytracer.Matrix;

public abstract class Shape {
    private final int id;
    private float[] origin;
    private Matrix transform;

    protected Shape(int id) {
        this.id = id;
        this.origin = point(0, 0, 0);
        this.transform = Matrix.identity();
    }

    public int id() {
        return id;
    }

    public float[] origin() {
        return this.origin;
    }

    public Matrix transform() {
        return this.transform;
    }

    public void setTransform(Matrix newTransform) {
        this.transform = newTransform;
    }
}
