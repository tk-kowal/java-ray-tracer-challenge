package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PpmExporterTest {

    @Test
    public void test_header() {
        var c = new Canvas(5, 3);
        var output = PpmExporter.toString(c);
        assertEquals("""
                P3
                5 3
                255\n""", output);
    }
}
