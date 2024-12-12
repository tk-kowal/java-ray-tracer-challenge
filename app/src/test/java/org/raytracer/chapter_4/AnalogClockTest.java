package org.raytracer.chapter_4;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.raytracer.Canvas;
import org.raytracer.PpmExporter;
import org.raytracer.Transform;

import static org.raytracer.Point.point;
import static org.raytracer.Color.color;

public class AnalogClockTest {

    @Test
    public void test_renderAnalogClock() {
        var canvas = new Canvas(50, 50);
        var color = color(1, 1, 1f);
        var numPoints = 20;
        var arcLengthBetweenPoints = (float) (2 * Math.PI / numPoints);

        for (var i = 0; i < numPoints; i++) {
            var point = Transform
                    .translate(25, 25, 0)
                    .rotateZ((float) (i * arcLengthBetweenPoints))
                    .multiply(point(0, 20, 0));
            canvas.writePixel(point, color);
        }

        PpmExporter.export(canvas, "/tmp/clock.ppm");
    }
}
