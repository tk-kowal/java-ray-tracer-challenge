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

    public void setColor(float[] color) {
        this.color = color;
    }

}
