package org.raytracer;

import java.util.Iterator;

public class Canvas implements Iterable<float[]> {

    private int width, height;
    private float[][] pixels;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new float[width * height][3];
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public void writePixel(int x, int y, float[] color) {
        var index = coordsToIndex(x, y);
        if (index < 0 || index > pixels.length) {
            System.out.println(String.format("%d, %d is outside of the canvas.", x, y));
            return;
        }
        pixels[coordsToIndex(x, y)] = color;
    }

    public float[] pixelAt(int x, int y) {
        return pixels[coordsToIndex(x, y)];
    }

    private int coordsToIndex(int x, int y) {
        return x + width * y;
    }

    @Override
    public Iterator<float[]> iterator() {
        return new CanvasIterator();
    }

    private class CanvasIterator implements Iterator<float[]> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < pixels.length;
        }

        @Override
        public float[] next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return pixels[currentIndex++];
        }
    }
}
