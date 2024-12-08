package org.raytracer.chapter_5;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.raytracer.Canvas;
import org.raytracer.PpmExporter;
import org.raytracer.Ray;
import org.raytracer.Transform;
import org.raytracer.shapes.Sphere;

import static org.raytracer.Color.color;
import static org.raytracer.Point.point;
import static org.raytracer.Vector.normalize;
import static org.raytracer.Ray.ray;
import static org.raytracer.Tuple.subtract;

public class DrawSphereTest {

    @Test
    public void test_drawSphere() {
        var canvasSideLength = 100;
        var canvas = new Canvas(canvasSideLength, canvasSideLength);
        var color = color(1, 0, 0);
        var sphere = new Sphere(0);
        sphere.setTransform(Transform.rotateZ((float) Math.PI / 4).scale(1, 0.5f, 1));

        var wallZ = 10;
        var wallSize = 7f;

        var pixelSize = wallSize / canvasSideLength;
        var half = wallSize / 2;
        var rayOrigin = point(0, 0, -5);

        for (var y = 0; y < canvasSideLength; y++) {
            var worldY = half - pixelSize * y;

            for (var x = 0; x < canvasSideLength; x++) {
                var worldX = -1 * half + pixelSize * x;

                var position = point(worldX, worldY, wallZ);
                var ray = ray(rayOrigin, normalize(subtract(position, rayOrigin)));
                var xs = Ray.intersect(sphere, ray);
                if (Ray.hit(xs) != null) {
                    canvas.writePixel(x, y, color);
                }
            }
        }

        PpmExporter.export(canvas, "/tmp/sphere.ppm");
    }

}
