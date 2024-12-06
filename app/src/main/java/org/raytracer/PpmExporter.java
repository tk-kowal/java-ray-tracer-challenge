package org.raytracer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PpmExporter {
    private static String MAGIC_VALUE = "P3";
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
        return MAGIC_VALUE
                + System.lineSeparator()
                + String.valueOf(width) + " " + String.valueOf(height)
                + System.lineSeparator()
                + String.valueOf(MAX_VALUE)
                + System.lineSeparator();
    }

    private static String pixels(Canvas c) {
        String pixelData = "";
        int lineLength = 0;

        for (float[] pixel : c) {
            for (float component : pixel) {
                var componentStr = lineLength == 0 ? "" : " ";
                componentStr += String.valueOf((int) Math.clamp(component * MAX_VALUE, 0, MAX_VALUE));
                lineLength += componentStr.length();
                pixelData += componentStr;
            }
            if (lineLength > 55) {
                pixelData += System.lineSeparator();
                lineLength = 0;
            }
        }

        return pixelData + System.lineSeparator();
    }

}
