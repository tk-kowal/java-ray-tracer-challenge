package org.raytracer.chapter_7;

import org.junit.jupiter.api.Test;
import org.raytracer.Camera;
import org.raytracer.Material;
import org.raytracer.PpmExporter;
import org.raytracer.Transform;
import org.raytracer.View;
import org.raytracer.World;
import org.raytracer.lights.PointLight;
import org.raytracer.shapes.Sphere;

import static org.raytracer.Color.color;
import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;

import java.util.List;

public class Scene7RenderTest {

    @Test
    public void testScene7Render() {
        var floor = new Sphere(0);
        floor.setTransform(Transform.scale(10, 0.01f, 10));
        floor.setMaterial(new Material().setColor(color(1, .9f, .9f)).setSpecular(0));

        var leftWall = new Sphere(1);
        leftWall.setTransform(Transform
                .translate(0, 0, 5)
                .rotateY((float) (-1 * Math.PI / 4))
                .rotateX((float) Math.PI / 2)
                .scale(10, 0.1f, 10));
        leftWall.setMaterial(floor.material());

        var rightWall = new Sphere(2);
        rightWall.setTransform(Transform
                .translate(0, 0, 5)
                .rotateY((float) (Math.PI / 4))
                .rotateX((float) Math.PI / 2)
                .scale(10, 0.1f, 10));
        rightWall.setMaterial(floor.material());

        var middle = new Sphere(3);
        middle.setTransform(Transform.translate(-0.5f, 1, 0.5f));
        middle.setMaterial(new Material().setColor(color(0.1f, 1, 0.5f)).setDiffuse(.7f).setSpecular(.3f));

        var right = new Sphere(4);
        right.setTransform(Transform.translate(1.5f, 0.5f, -0.5f).scale(0.5f, 0.5f, 0.5f));
        right.setMaterial(new Material().setColor(color(0.5f, 1f, 0.1f)).setDiffuse(.7f).setSpecular(.3f));

        var left = new Sphere(5);
        left.setTransform(Transform.translate(-1.5f, 0.33f, -0.75f).scale(0.33f, 0.33f, 0.33f));
        left.setMaterial(new Material().setColor(color(1, 0.8f, 0.1f)).setDiffuse(.7f).setSpecular(.3f));

        var light = new PointLight(point(-10, 10, -10), color(1, 1, 1));
        var camera = new Camera(960, 720, (float) (Math.PI / 3));
        camera.setTransform(View.transform(point(0, 1.5f, -5), point(0, 1, 0), vector(0, 1, 0)));

        var world = new World(List.of(floor, leftWall, rightWall, left, middle, right), List.of(light));

        var img = camera.render(world);

        PpmExporter.export(img, "/tmp/scene7.ppm");
    }

}
