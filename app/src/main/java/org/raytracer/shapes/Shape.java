package org.raytracer.shapes;

import static org.raytracer.Point.point;

import org.raytracer.Material;
import org.raytracer.Matrix;

public abstract class Shape {
    protected final int id;
    protected Matrix transform;
    protected Material material;

    protected Shape(int id) {
        this.id = id;
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

    public abstract float[] normalAt(float[] point);

    public float[] origin() {
        return point(0, 0, 0);
    }

    public void setTransform(Matrix newTransform) {
        this.transform = newTransform;
    }

    public Matrix transform() {
        return this.transform;
    }

    public abstract boolean equals(Object other);
}
