package org.raytracer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PpmExporter {
    private static String MAGIC_VALUE = "P3";
    private static int MAX_VALUE = 255;
    private static int THREAD_COUNT = 10;

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
        int pixelRowsPerThread = c.height() / THREAD_COUNT;
        ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<String>> results = new ArrayList<>();

        for (int i = 0; i < c.height(); i += pixelRowsPerThread) {
            int start = i;
            int end = i + pixelRowsPerThread - 1;
            System.out
                    .println("Starting thread for lines: " + String.valueOf(start) + " through " + String.valueOf(end));
            results.add(es.submit(() -> {
                var iterator = new CanvasRangeIterator(c, start, end);
                String pixelData = "";
                int lineLength = 0;
                while (iterator.hasNext()) {
                    var pixel = iterator.next();
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
            }));
        }

        es.shutdown();

        StringBuilder result = new StringBuilder();
        for (var r : results) {
            try {
                result.append(r.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

}
