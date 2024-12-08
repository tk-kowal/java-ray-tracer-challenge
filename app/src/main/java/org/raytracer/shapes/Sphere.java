package org.raytracer.shapes;

import static org.raytracer.Point.point;

public class Sphere {

    private final int id;
    private float[] origin;

    public Sphere(int id) {
        this.id = id;
        this.origin = point(0, 0, 0);
    }

    public float[] origin() {
        return this.origin;
    }

}
