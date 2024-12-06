package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ColorTest {
    @Test
    void test_colorsHaveComponents() {
        float red = -0.5f;
        float green = 0.4f;
        float blue = 1.7f;
        float[] color = Color.color(red, green, blue);
        assertEquals(red, Color.red(color));
        assertEquals(green, Color.green(color));
        assertEquals(blue, Color.blue(color));
    }

    @Test
    void test_addingColors() {
        float[] colorA = Color.color(0.9f, 0.6f, 0.75f);
        float[] colorB = Color.color(0.7f, 0.1f, 0.25f);
        float[] expected = Color.color(1.6f, 0.7f, 1f);
        float[] actual = Tuple.add(colorA, colorB);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_subtractingColors() {
        float[] colorA = Color.color(0.9f, 0.6f, 0.75f);
        float[] colorB = Color.color(0.7f, 0.1f, 0.25f);
        float[] expected = Color.color(0.2f, 0.5f, 0.5f);
        float[] actual = Tuple.subtract(colorA, colorB);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_multiplyingColorByScalar() {
        float[] colorA = Color.color(0.2f, 0.3f, 0.4f);
        float scalar = 2f;
        float[] expected = Color.color(0.4f, 0.6f, 0.8f);
        float[] actual = Tuple.multiply(colorA, scalar);
        assertTrue(Tuple.areEqual(expected, actual));
    }

    @Test
    void test_multiplyingColorByColor() {
        float[] colorA = Color.color(1f, 0.2f, 0.4f);
        float[] colorB = Color.color(0.9f, 1f, 0.1f);
        float[] expected = Color.color(0.9f, 0.2f, 0.04f);
        float[] actual = Color.multiply(colorA, colorB);
        assertTrue(Tuple.areEqual(expected, actual));
    }

}
