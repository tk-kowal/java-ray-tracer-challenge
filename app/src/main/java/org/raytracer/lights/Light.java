package org.raytracer.lights;

import org.raytracer.Tuple;

public abstract class Light {

    protected float[] position, intensity;

    public Light(float[] position, float[] intensity) {
        this.position = position;
        this.intensity = intensity;
    }

    public float[] position() {
        return this.position;
    }

    public Light setPosition(float[] position) {
        this.position = position;
        return this;
    }

    public float[] intensity() {
        return this.intensity;
    }

    public Light setColor(float[] i) {
        this.intensity = i;
        return this;
    }

    public boolean equals(Object other) {
        if (other instanceof Light) {
            return Tuple.areEqual(position, ((Light) other).position)
                    && Tuple.areEqual(intensity, ((Light) other).intensity);
        } else {
            return false;
        }
    }
}
