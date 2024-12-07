package org.raytracer;

public class Matrix {

    public int height, width;
    protected float[][] values;

    public static final Matrix IDENTITY_MATRIX = matrix(
            new float[] { 1, 0, 0, 0 },
            new float[] { 0, 1, 0, 0 },
            new float[] { 0, 0, 1, 0 },
            new float[] { 0, 0, 0, 1 });

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

    public float[] getCol(int col) {
        var result = new float[height];
        for (int row = 0; row < height; row++) {
            result[row] = values[row][col];
        }
        return result;
    }

    public float[] getRow(int row) {
        return values[row];
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

    public void set(int row, int index, float value) {
        values[row][index] = value;
    }

    public Matrix transpose() {
        var result = new Matrix(height, width);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result.set(col, row, values[row][col]);
            }
        }

        return result;
    }

    public float[] tupleMultiply(float[] tuple) {
        var result = new float[tuple.length];

        for (int row = 0; row < height; row++) {
            result[row] = Tuple.dot(values[row], tuple);
        }

        return result;
    }

}
