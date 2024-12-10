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

    public float[] intensity() {
        return this.intensity;
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
