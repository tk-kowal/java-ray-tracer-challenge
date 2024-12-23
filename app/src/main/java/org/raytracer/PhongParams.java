package org.raytracer;

import org.raytracer.shapes.Shape;

public class PhongParams {
    public float t;
    public Shape s;
    public float[] point;
    public float[] overpoint;
    public float[] underpoint;
    public float[] eyev;
    public float[] normalv;
    public float[] reflectv;
    public float n1;
    public float n2;
    public boolean isInside;

    public PhongParams(float t, Shape s, float[] point, float[] overpoint, float[] underpoint, float[] eyev,
            float[] normalv,
            float[] reflectv, float n1, float n2, boolean isInside) {
        this.t = t;
        this.s = s;
        this.point = point;
        this.overpoint = overpoint;
        this.underpoint = underpoint;
        this.eyev = eyev;
        this.normalv = normalv;
        this.reflectv = reflectv;
        this.n1 = n1;
        this.n2 = n2;
        this.isInside = isInside;
    }
}