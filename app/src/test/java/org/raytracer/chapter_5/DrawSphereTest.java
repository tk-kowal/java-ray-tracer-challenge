package org.raytracer.chapter_5;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.raytracer.Canvas;
import org.raytracer.Lighting;
import org.raytracer.Material;
import org.raytracer.PpmExporter;
import org.raytracer.Ray;
import org.raytracer.Transform;
import org.raytracer.Tuple;
import org.raytracer.lights.PointLight;
import org.raytracer.shapes.Sphere;

import static org.raytracer.Color.color;
import static org.raytracer.Point.point;
import static org.raytracer.Vector.normalize;

import java.time.Duration;
import java.time.Instant;

import static org.raytracer.Ray.ray;
import static org.raytracer.Tuple.subtract;

@Disabled
public class DrawSphereTest {

    @Test
    public void test_drawSphere() {
        var canvasSideLength = 100;
        var canvas = new Canvas(canvasSideLength, canvasSideLength);
        var sphere = new Sphere(0);
        sphere.material().setColor(color(0f, 0.2f, 1f));
        sphere.material().setShininess(50f);
        sphere.setTransform(Transform.rotateZ((float) (-1 * Math.PI / 4)).scale(0.5f, 1, 1));

        var lightPosition = point(10, 10, -5);
        var lightColor = color(1, 1, 1);
        var light = new PointLight(lightPosition, lightColor);

        var wallZ = 10;
        var wallSize = 7f;

        var pixelSize = wallSize / canvasSideLength;
        var half = wallSize / 2;
        var rayOrigin = point(0, 0, -5);

        var startRender = Instant.now();
        for (var y = 0; y < canvasSideLength; y++) {
            var worldY = half - pixelSize * y;

            for (var x = 0; x < canvasSideLength; x++) {
                var worldX = -1 * half + pixelSize * x;

                var position = point(worldX, worldY, wallZ);
                var ray = ray(rayOrigin, normalize(subtract(position, rayOrigin)));
                var xs = Ray.intersect(sphere, ray);
                var hit = Ray.hit(xs);
                if (hit != null) {
                    var point = ray.position(hit.t());
                    var normal = sphere.normalAt(point[0], point[1], point[2]);
                    var eye = Tuple.multiply(ray.direction(), -1f);
                    canvas.writePixel(x, y, Lighting.lighting(sphere.material(), light, point, eye, normal));
                }
            }
        }
        var endRender = Instant.now();
        System.out.println(
                "Rendering finished in " + Duration.between(startRender, endRender).toMillis() + " ms.");

        var startExport = Instant.now();
        PpmExporter.export(canvas, "/tmp/sphere.ppm");
        var endExport = Instant.now();
        System.out.println("Export finished in " + Duration.between(startExport, endExport).toMillis() + " ms.");
    }

}
