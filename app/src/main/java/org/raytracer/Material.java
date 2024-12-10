package org.raytracer;

public class Material {

    private float ambient, diffuse, specular, shininess;
    private float[] color;

    public Material() {
        this.color = Color.color(1f, 1f, 1f);
        this.ambient = 0.1f;
        this.diffuse = 0.9f;
        this.specular = 0.9f;
        this.shininess = 200.0f;
    }

    public float ambient() {
        return this.ambient;
    }

    public float diffuse() {
        return this.diffuse;
    }

    public float specular() {
        return this.specular;
    }

    public float shininess() {
        return this.shininess;
    }

    public float[] color() {
        return this.color;
    }

    public Material setColor(float[] color) {
        this.color = color;
        return this;
    }

    public Material setShininess(float s) {
        this.shininess = s;
        return this;
    }

    public Material setAmbient(float a) {
        this.ambient = a;
        return this;
    }

    public Material setDiffuse(float d) {
        this.diffuse = d;
        return this;
    }

    public Material setSpecular(float s) {
        this.specular = s;
        return this;
    }

    public boolean equals(Object other) {
        if (other instanceof Material) {
            var otherMaterial = (Material) other;
            return Tuple.areEqual(color, otherMaterial.color)
                    && Scalar.areEqual(specular, otherMaterial.specular)
                    && Scalar.areEqual(diffuse, otherMaterial.diffuse)
                    && Scalar.areEqual(ambient, otherMaterial.ambient)
                    && Scalar.areEqual(shininess, otherMaterial.shininess);
        } else {
            return false;
        }
    }
}
