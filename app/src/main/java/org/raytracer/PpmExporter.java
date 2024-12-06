package org.raytracer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PpmExporter {
    private static int MAX_VALUE = 255;

    public static String toString(Canvas c) {
        return header(c.width(), c.height()) + pixels(c);
    }

    public static void export(Canvas c, String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write(toString(c));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String header(int width, int height) {
        return String.format("P3\n%d %d\n%d\n", width, height, MAX_VALUE);
    }

    private static String pixels(Canvas c) {
        String pixelData = "";
        int lineLength = 0;

        for (float[] pixel : c) {
            for (float component : pixel) {
                var pixelString = String.format("%d ", (int) Math.clamp(component * MAX_VALUE, 0, MAX_VALUE));
                lineLength += pixelString.length();
                pixelData += pixelString;
            }
            if (lineLength > 70) {
                pixelData += "\n";
                lineLength = 0;
            }
        }

        return pixelData + "\n";
    }

}
