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
import org.raytracer.patterns.InterpolatedNoise;
import org.raytracer.patterns.Perlin;
import org.raytracer.patterns.StructuredNoise;
import org.raytracer.patterns.WhiteNoise;
import org.raytracer.shapes.Plane;
import static org.raytracer.Point.point;
import static org.raytracer.Color.color;
import static org.raytracer.Vector.vector;

//@Disabled
public class UnderstandingPerlinTest {

    @Test
    public void test_perlin() {
        var HIGH_RES = true;
        var NOISE_ON = true;
        var FILE_A = true;

        var floorMaterial = new Material()
                .setPattern(new CheckerPattern(Color.WHITE, Color.BLACK));

        var noisyFloorMaterial = new Material()
                .setPattern(new Perlin(new CheckerPattern(Color.WHITE, Color.BLACK)));

        var myNoise = new InterpolatedNoise(Color.WHITE, Color.BLACK);
        myNoise.setTransform(Transform.scale(0.01f, 0.01f, 0.01f));
        var myNoiseFloor = new Material().setPattern(myNoise);

        var floor = new Plane();

        if (NOISE_ON) {
            // floor.setMaterial(noisyFloorMaterial);
            floor.setMaterial(myNoiseFloor);
        } else {
            floor.setMaterial(floorMaterial);
        }

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
