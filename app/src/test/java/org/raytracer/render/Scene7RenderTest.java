package org.raytracer.render;

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
import org.raytracer.patterns.Perlin;
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
        private static String filename = "/tmp/render.ppm";

        @Test
        public void testRender() {
                var targetsPattern = new Targets(Color.RED, Color.WHITE, Color.WHITE);
                targetsPattern.setTransform(Transform.scale(.5f, .5f, .5f));

                var floor = new Plane();
                floor.setMaterial(new Material().setColor(color(1, .9f, .9f)).setPattern(targetsPattern));

                var stripesPattern = new StripePattern(Color.WHITE, Color.BLACK);
                stripesPattern.setTransform(
                                Transform.rotateY((float) Math.PI / 2)
                                                .rotateZ((float) Math.PI / 4)
                                                .scale(0.05f, 0.05f, .05f));

                var perlinPattern = new Perlin(stripesPattern, 0.5f);

                var middleSphere = new Sphere();
                middleSphere.setTransform(Transform.translate(-0.5f, 1, 0.5f));
                middleSphere.setMaterial(
                                new Material().setColor(color(0.85f, 0.88f, 0.89f)).setDiffuse(0.2f)
                                                .setSpecular(1f)
                                                .setReflective(1f).setTransparency(0.7f).setRefractIx(1.5f));

                var rightSphere = new Sphere();
                rightSphere.setTransform(Transform.translate(1.5f, 0.5f, -0.5f).scale(0.5f, 0.5f, 0.5f));
                rightSphere.setMaterial(new Material().setColor(Color.WHITE).setDiffuse(.7f).setSpecular(.3f)
                                .setPattern(perlinPattern));

                var intersectingSphere = new Sphere();
                intersectingSphere.setTransform(Transform.translate(-1f, 1.5f, 0f).scale(0.5f, 0.5f, 0.5f));
                intersectingSphere.setMaterial(new Material().setColor(Color.WHITE).setDiffuse(.7f).setSpecular(.3f)
                                .setPattern(perlinPattern));

                var leftSphere = new Sphere();
                leftSphere.setTransform(Transform.translate(-1.5f, 0.33f, -0.75f).scale(0.33f, 0.33f, 0.33f));
                leftSphere.setMaterial(new Material().setColor(Color.WHITE).setDiffuse(.7f).setSpecular(.3f));

                var light = new PointLight(point(0, 10, -10), color(1, 1, 1));

                Camera camera = new Camera(200, 100, (float) (Math.PI / 3));

                if (HIGH_RES) {
                        camera = new Camera(960, 720, (float) (Math.PI / 3));
                }

                camera.setTransform(View.transform(point(2, 4f, -10), point(0, 1, 0), vector(0, 1, 0)));

                var world = new World(List.of(floor, leftSphere, middleSphere, rightSphere, intersectingSphere),
                                List.of(light));

                var renderStart = Instant.now();
                var img = camera.render(world);
                var renderEnd = Instant.now();

                var exportStart = Instant.now();
                PpmExporter.export(img, filename);
                var exportEnd = Instant.now();

                System.out.println("render: " + Duration.between(renderStart, renderEnd).toMillis());
                System.out.println("export: " + Duration.between(exportStart, exportEnd).toMillis());
        }

}
