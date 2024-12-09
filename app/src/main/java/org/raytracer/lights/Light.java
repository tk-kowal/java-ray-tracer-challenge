package org.raytracer.lights;

public abstract class Light {

    protected float[] position, intensity;

    public Light(float[] position, float[] intensity) {
        this.position = position;
        this.intensity = intensity;
    }

    public float[] position() {
        return this.position;
    }

    public float[] intensity() {
        return this.intensity;
    }
}
