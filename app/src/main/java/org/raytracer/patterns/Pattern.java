package org.raytracer.patterns;

import org.raytracer.Matrix;

public abstract class Pattern {

    protected Matrix transform;

    public Pattern() {
        this.transform = Matrix.identity();
    }

    public Matrix transform() {
        return transform;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    public abstract float[] colorAt(float[] point);
}
