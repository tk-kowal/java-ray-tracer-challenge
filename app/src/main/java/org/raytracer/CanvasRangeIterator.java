package org.raytracer;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CanvasRangeIterator implements Iterator<float[]> {
    private float[][][] pixels;
    private int currentLine, x, width, totalPixels, count;

    public CanvasRangeIterator(Canvas c, int startLine, int lastLine) {
        this.pixels = c.pixels();
        this.width = c.width();
        this.currentLine = startLine;
        this.x = 0;
        this.totalPixels = (Math.min(lastLine, c.height()) - startLine + 1) * width;
    }

    @Override
    public boolean hasNext() {
        return count < totalPixels;
    }

    @Override
    public float[] next() {
        if (x == width) {
            x = 0;
            currentLine++;
        }
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        count++;
        return pixels[currentLine][x++];
    }

}
