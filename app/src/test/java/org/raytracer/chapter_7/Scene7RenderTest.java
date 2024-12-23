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
import org.raytracer.patterns.Gradient;
import org.raytracer.patterns.Perlin;
import org.raytracer.patterns.SolidPattern;
import org.raytracer.patterns.StripePattern;
import org.raytracer.patterns.Targets;
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

        private static boolean HIGH_RES = true;

        @Test
        public void testScene7Render() {

                var targets = new Targets(Color.RED, Color.WHITE, Color.WHITE);
                targets.setTransform(Transform.scale(.5f, .5f, .5f));

                var floor = new Plane();
                floor.setMaterial(
                                new Material().setColor(color(1, .9f, .9f)).setPattern(targets));

                var wallPattern = new SolidPattern(Color.BLACK);
                wallPattern.setTransform(Transform.rotateY((float) (-1 * Math.PI / 2)).scale(100, 1, 1));

                var wall = new Plane();
                wall.setMaterial(new Material().setSpecular(0).setPattern(wallPattern));
                wall.setTransform(Transform.rotateX((float) (-1f * Math.PI / 2)).translate(0, -100, 0));

                var middlePattern = new StripePattern(Color.WHITE, Color.BLACK);
                middlePattern
                                .setTransform(
                                                Transform.rotateY((float) Math.PI / 2).rotateZ((float) Math.PI / 4)
                                                                .scale(0.05f, 0.05f, .05f));

                var perlinPattern = new Perlin(middlePattern, 0.5f);

                var middle = new Sphere();
                middle.setTransform(Transform.translate(-0.5f, 1, 0.5f));
                middle.setMaterial(new Material().setColor(color(0f, 0.75f, 0.75f)).setDiffuse(.7f).setSpecular(.3f)
                                .setReflective(.15f));

                var right = new Sphere();
                right.setTransform(Transform.translate(1.5f, 0.5f, -0.5f).scale(0.5f, 0.5f, 0.5f));
                right.setMaterial(new Material().setColor(Color.WHITE).setDiffuse(.7f).setSpecular(.3f)
                                .setPattern(perlinPattern));

                var left = new Sphere();
                left.setTransform(Transform.translate(-1.5f, 0.33f, -0.75f).scale(0.33f, 0.33f, 0.33f));
                left.setMaterial(new Material().setColor(Color.WHITE).setDiffuse(.7f).setSpecular(.3f));

                var giant = new Sphere();
                giant.setTransform(Transform.translate(3.5f, 3f, 5).scale(3f, 3f, 3f));
                giant.setMaterial(new Material().setColor(Color.WHITE).setSpecular(0.9f).setReflective(1f));

                var light = new PointLight(point(-10, 10, -10), color(1, 1, 1));

                Camera camera = null;

                if (HIGH_RES) {
                        camera = new Camera(960, 720, (float) (Math.PI / 3));
                } else {
                        camera = new Camera(200, 100, (float) (Math.PI / 3));
                }

                camera.setTransform(View.transform(point(2, 4f, -10), point(0, 1, 0), vector(0, 1, 0)));

                var world = new World(List.of(floor, wall, giant, left, middle, right), List.of(light));

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
