package org.raytracer.chapter_7;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.raytracer.Camera;
import org.raytracer.Color;
import org.raytracer.Material;
import org.raytracer.PpmExporter;
import org.raytracer.Transform;
import org.raytracer.View;
import org.raytracer.World;
import org.raytracer.lights.PointLight;
import org.raytracer.patterns.StripePattern;
import org.raytracer.shapes.Plane;
import org.raytracer.shapes.Sphere;

import static org.raytracer.Color.color;
import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

//@Disabled
public class Scene7RenderTest {

    @Test
    public void testScene7Render() {
        // var floor = new Sphere();
        // floor.setTransform(Transform.scale(10, 0.01f, 10));
        // floor.setMaterial(new Material().setColor(color(1, .9f,
        // .9f)).setSpecular(0));

        var floor = new Plane();
        floor.setMaterial(new Material().setColor(color(1, .9f, .9f)).setSpecular(0)
                .setPattern(new StripePattern(Color.WHITE, Color.BLUE)));

        var wall = new Plane();
        wall.setMaterial(new Material().setColor(color(0.4f, .6f, 1f)).setSpecular(0));
        wall.setTransform(Transform.rotateX((float) (-1f * Math.PI / 2)).translate(0, -100, 200));

        // var leftWall = new Sphere();
        // leftWall.setTransform(Transform
        // .translate(0, 0, 5)
        // .rotateY((float) (-1 * Math.PI / 4))
        // .rotateX((float) Math.PI / 2)
        // .scale(10, 0.5f, 10));
        // leftWall.setMaterial(floor.material());

        // var rightWall = new Sphere();
        // rightWall.setTransform(Transform
        // .translate(0, 0, 5)
        // .rotateY((float) (Math.PI / 4))
        // .rotateX((float) Math.PI / 2)
        // .scale(10, 0.5f, 10));
        // rightWall.setMaterial(floor.material());

        var middlePattern = new StripePattern(Color.RED, Color.GREEN);
        middlePattern.setTransform(Transform.rotateY((float) Math.PI / 4).scale(0.1f, 1, 1));

        var middle = new Sphere();
        middle.setTransform(Transform.translate(-0.5f, 1, 0.5f));
        middle.setMaterial(new Material().setColor(color(0.1f, 1, 0.5f)).setDiffuse(.7f).setSpecular(.3f)
                .setPattern(middlePattern));

        var right = new Sphere();
        right.setTransform(Transform.translate(1.5f, 0.5f, -0.5f).scale(0.5f, 0.5f, 0.5f));
        right.setMaterial(new Material().setColor(color(0.5f, 1f, 0.1f)).setDiffuse(.7f).setSpecular(.3f));

        var left = new Sphere();
        left.setTransform(Transform.translate(-1.5f, 0.33f, -0.75f).scale(0.33f, 0.33f, 0.33f));
        left.setMaterial(new Material().setColor(color(1, 0.8f, 0.1f)).setDiffuse(.7f).setSpecular(.3f));

        var light = new PointLight(point(-10, 10, -10), color(1, .8f, .5f));
        var camera = new Camera(960, 720, (float) (Math.PI / 3));
        camera.setTransform(View.transform(point(0, 1.5f, -10), point(0, 1, 0), vector(0, 1, 0)));

        var world = new World(List.of(floor, wall, left, middle, right), List.of(light));

        var renderStart = Instant.now();
        var img = camera.render(world);
        var renderEnd = Instant.now();

        var exportStart = Instant.now();
        PpmExporter.export(img, "/tmp/scene7.ppm");
        var exportEnd = Instant.now();

        System.out.println("render: " + Duration.between(renderStart, renderEnd).toMillis());
        System.out.println("export: " + Duration.between(exportStart, exportEnd).toMillis());
    }

}
