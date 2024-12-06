package org.raytracer;

import java.util.Iterator;

public class Canvas implements Iterable<float[]> {

    private int width, height;
    private float[][][] pixels;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new float[height][width][3];
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public float[][][] pixels() {
        return this.pixels;
    }

    public void writePixel(int x, int y, float[] color) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            System.out.println(String.format("%d, %d is outside of the canvas.", x, y));
            return;
        }
        pixels[y][x] = color;
    }

    public float[] pixelAt(int x, int y) {
        return pixels[y][x];
    }

    @Override
    public Iterator<float[]> iterator() {
        return new CanvasIterator();
    }

    private class CanvasIterator implements Iterator<float[]> {
        private int currentX = 0;
        private int currentY = 0;
        private int index = 0;
        private int maxIndex = width * height;

        @Override
        public boolean hasNext() {
            return index < maxIndex;
        }

        @Override
        public float[] next() {
            if (currentX == width) {
                currentX = 0;
                currentY++;
            }
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            index++;
            return pixels[currentY][currentX++];
        }
    }
}
