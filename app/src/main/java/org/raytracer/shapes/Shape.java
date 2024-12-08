package org.raytracer.shapes;

import static org.raytracer.Point.point;

public abstract class Shape {
    private final int id;
    private float[] origin;

    protected Shape(int id) {
        this.id = id;
        this.origin = point(0, 0, 0);
    }

    public int id() {
        return id;
    }

    public float[] origin() {
        return this.origin;
    }
}
