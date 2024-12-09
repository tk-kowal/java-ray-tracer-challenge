package org.raytracer.chapter_5;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.raytracer.Canvas;
import org.raytracer.PpmExporter;
import org.raytracer.Ray;
import org.raytracer.Transform;
import org.raytracer.shapes.Sphere;

import static org.raytracer.Point.point;
import static org.raytracer.Color.color;
import static org.raytracer.Vector.vector;

@Disabled
public class ScratchTest {

    @Test
    public void test() {
        var canvasSideLength = 100;
        var canvas = new Canvas(canvasSideLength, canvasSideLength);

        var start = point(0, 0, 0);
        var vector = vector(1, 0, 0);

        var startRender = Instant.now();
        var endRender = Instant.now();
        System.out.println(
                "Rendering finished in " + Duration.between(startRender, endRender).toMillis() + " ms.");

        var startExport = Instant.now();
        PpmExporter.export(canvas, "/tmp/sphere.ppm");
        var endExport = Instant.now();
        System.out.println("Export finished in " + Duration.between(startExport, endExport).toMillis() + " ms.");
    }

}
