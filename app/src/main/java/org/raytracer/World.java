package org.raytracer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.raytracer.lights.Light;
import org.raytracer.lights.PointLight;
import org.raytracer.shapes.Shape;
import org.raytracer.shapes.Sphere;

import static org.raytracer.Point.point;
import static org.raytracer.Color.color;

public class World {

    private List<Shape> objects;
    private List<Light> lights;

    public World() {
        this.objects = new ArrayList<Shape>();
        this.lights = new ArrayList<Light>();
    }

    public World(List<Shape> objects, List<Light> lights) {
        this.objects = objects;
        this.lights = lights;
    }

    public static World defaultWorld() {
        var light = new PointLight(point(-10, 10, -10), color(1, 1, 1));
        var s1 = new Sphere();
        s1.setMaterial(new Material()
                .setColor(color(0.8f, 1.0f, 0.6f))
                .setDiffuse(0.7f)
                .setSpecular(0.2f));
        var s2 = new Sphere();
        s2.setTransform(Transform.scale(.5f, .5f, .5f));
        return new World(new ArrayList<Shape>(Arrays.asList(s1, s2)), new ArrayList<Light>(Arrays.asList(light)));

    }

    public List<Shape> objects() {
        return objects;
    }

    public List<Light> lights() {
        return lights;
    }

}
