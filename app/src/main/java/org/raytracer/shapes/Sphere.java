package org.raytracer.shapes;

import java.util.List;

import org.raytracer.Ray;

public class Sphere extends Shape {

    public Sphere(int id) {
        super(id);
    }

    public List<Ray.Intersection> intersect(Ray ray) {
        return List.of();
    }

    public boolean equals(Object other) {
        if (other instanceof Sphere) {
            var otherSphere = (Sphere) other;
            return transform.equals(otherSphere.transform) && material.equals(otherSphere.material);
        } else {
            return false;
        }
    }

}
