package org.raytracer;

import org.raytracer.patterns.Pattern;

public class Material {

    public float ambient, diffuse, specular, shininess, reflective, transparency, refractix;
    public float[] color;

    public Pattern pattern;

    public Material() {
        this.color = Color.color(1f, 1f, 1f);
        this.ambient = 0.1f;
        this.diffuse = 0.9f;
        this.specular = 0.9f;
        this.shininess = 200.0f;
        this.reflective = 0f;
        this.transparency = 0f;
        this.refractix = 1f;
        this.pattern = null;
    }

    public float[] color() {
        return this.color;
    }

    public float[] colorAt(float[] point) {
        if (pattern == null) {
            return this.color;
        } else {
            return pattern.colorAt(point);
        }
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

    public Material setPattern(Pattern p) {
        this.pattern = p;
        return this;
    }

    public Material setReflective(float r) {
        this.reflective = r;
        return this;
    }

    public Material setTransparency(float t) {
        this.transparency = t;
        return this;
    }

    public Material setRefractIx(float ri) {
        this.refractix = ri;
        return this;
    }

    public boolean equals(Object other) {
        if (other instanceof Material) {
            var otherMaterial = (Material) other;
            return Tuple.areEqual(color, otherMaterial.color)
                    && Scalar.areEqual(specular, otherMaterial.specular)
                    && Scalar.areEqual(diffuse, otherMaterial.diffuse)
                    && Scalar.areEqual(ambient, otherMaterial.ambient)
                    && Scalar.areEqual(shininess, otherMaterial.shininess)
                    && Scalar.areEqual(reflective, otherMaterial.reflective)
                    && Scalar.areEqual(transparency, otherMaterial.transparency)
                    && Scalar.areEqual(refractix, otherMaterial.refractix)
                    && pattern == otherMaterial.pattern;
        } else {
            return false;
        }
    }
}
