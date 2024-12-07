package org.raytracer;

public class Matrix {

    public int height, width;
    protected float[][] values;

    protected Matrix(int rows, int columns) {
        this.values = new float[rows][columns];
        this.height = rows;
        this.width = columns;
    }

    public static Matrix matrix(float[]... values) {
        var matrix = new Matrix(values.length, values[0].length);
        matrix.values = values;
        return matrix;
    }

    public boolean equals(Matrix other) {
        for (var i = 0; i < values.length; i++) {
            if (Tuple.notEqual(values[i], other.getRow(i)))
                return false;
        }
        return true;
    }

    public float get(int row, int col) {
        return values[row][col];
    }

    public void set(int row, int index, float value) {
        values[row][index] = value;
    }

    public float[] getRow(int row) {
        return values[row];
    }

    public float[] getCol(int col) {
        var result = new float[height];
        for (int row = 0; row < height; row++) {
            result[row] = values[row][col];
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        var result = new Matrix(height, width);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result.set(row, col, Tuple.dot(values[row], other.getCol(col)));
            }
        }

        return result;
    }

}
