package org.raytracer.chapter_11;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

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
import org.raytracer.patterns.CheckerPattern;
import org.raytracer.patterns.Perlin;
import org.raytracer.shapes.Plane;
import static org.raytracer.Point.point;
import static org.raytracer.Color.color;
import static org.raytracer.Vector.vector;

@Disabled
public class UnderstandingPerlinTest {

    @Test
    public void test_perlin() {
        var HIGH_RES = true;
        var FILE_A = true;

        var stripes = new CheckerPattern(Color.BLACK, Color.RED);
        stripes.setTransform(Transform.scale(0.5f, 0.5f, 0.5f));
        var perlin = new Perlin(stripes, 0.02f);

        var floorMaterial = new Material()
                .setPattern(perlin);

        var floor = new Plane();

        floor.setMaterial(floorMaterial);

        var light = new PointLight(point(-10, 10, -10), color(1, 1, 1));

        Camera camera = null;

        if (HIGH_RES) {
            camera = new Camera(960, 720, (float) (Math.PI / 3));
        } else {
            camera = new Camera(200, 100, (float) (Math.PI / 3));
        }

        camera.setTransform(View.transform(point(0, 10f, 0), point(0, 0, 0), vector(0, 0, 1)));

        var world = new World(List.of(floor), List.of(light));

        var renderStart = Instant.now();
        var img = camera.render(world);
        var renderEnd = Instant.now();

        var exportStart = Instant.now();
        if (FILE_A) {
            PpmExporter.export(img, "/tmp/noise_a.ppm");
        } else {
            PpmExporter.export(img, "/tmp/noise_b.ppm");
        }
        var exportEnd = Instant.now();

        System.out.println("render: " + Duration.between(renderStart, renderEnd).toMillis());
        System.out.println("export: " + Duration.between(exportStart, exportEnd).toMillis());
    }

}
