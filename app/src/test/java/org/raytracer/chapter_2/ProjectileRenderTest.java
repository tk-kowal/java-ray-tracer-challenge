package org.raytracer.chapter_2;

import org.junit.jupiter.api.Test;
import org.raytracer.Canvas;
import org.raytracer.Color;
import org.raytracer.PpmExporter;

import static org.raytracer.Tuple.*;
import static org.raytracer.Vector.*;

import java.time.Duration;
import java.time.Instant;

import static org.raytracer.Point.*;

public class ProjectileRenderTest {

    private record Projectile(float[] position, float[] velocity) {
    }

    private record Environment(float[] gravity, float[] wind) {
    }

    private int[] toCoords(float[] position, Canvas c) {
        int x = (int) position[0];
        int y = (int) (c.height() - position[1]);
        return new int[] { x, y };
    }

    @Test
    public void test_Tick() {
        var canvas = new Canvas(900, 550);
        var projectile = new Projectile(point(0, 1, 0), multiply(normalize(vector(1, 1.8f, 0)), 15.25f));
        var environment = new Environment(vector(0, -0.1f, 0), vector(-0.01f, 0, 0));
        var red = Color.color(1, 1, 1);

        do {
            var coords = toCoords(projectile.position, canvas);
            System.out.println(String.format("Marking %d, %d position on canvas.", coords[0], coords[1]));
            canvas.writePixel(coords[0], coords[1], red);

            var newPosition = add(projectile.position, projectile.velocity);
            var newVelocity = add(add(projectile.velocity, environment.gravity), environment.wind);
            projectile = new Projectile(newPosition, newVelocity);

        } while (projectile.position[1] > 0f);
        System.out.println("Exporting Canvas");

        var start = Instant.now();
        PpmExporter.export(canvas, "/tmp/canvas.ppm");
        var end = Instant.now();
        System.out.println(String.format("Done. Took %d seconds", Duration.between(start, end).getSeconds()));
    }
}