package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CanvasTest {

    @Test
    public void test_initializeBlankCanvas() {
        var width = 10;
        var height = 20;
        var canvas = new Canvas(width, height);
        assertEquals(width, canvas.width());
        assertEquals(height, canvas.height());
        for (float[] pixel : canvas) {
            assertTrue(Tuple.areEqual(pixel, Color.color(0, 0, 0)));
        }
    }

    @Test
    public void test_writeToCanvas() {
        var width = 10;
        var height = 20;
        var canvas = new Canvas(width, height);
        var red = Color.color(1, 0, 0);
        canvas.writePixel(2, 3, red);
        assertTrue(Tuple.areEqual(red, canvas.pixelAt(2, 3)));
    }

}
