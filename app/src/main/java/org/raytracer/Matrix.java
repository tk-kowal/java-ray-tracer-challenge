package org.raytracer;

public class Matrix {

    public int height, width;
    protected float[][] values;
    private boolean immutable = false;

    public static final Matrix IDENTITY_MATRIX = matrix(true,
            new float[] { 1, 0, 0, 0 },
            new float[] { 0, 1, 0, 0 },
            new float[] { 0, 0, 1, 0 },
            new float[] { 0, 0, 0, 1 });

    protected Matrix(int rows, int columns) {
        this(rows, columns, false);
    }

    protected Matrix(int rows, int columns, boolean immutable) {
        this.values = new float[rows][columns];
        this.height = rows;
        this.width = columns;
        this.immutable = immutable;
    }

    public static Matrix matrix(float[]... values) {
        return matrix(false, values);
    }

    public static Matrix matrix(boolean immutable, float[]... values) {
        var matrix = new Matrix(values.length, values[0].length, immutable);
        matrix.values = values;
        return matrix;
    }

    public float cofactor(int row, int col) {
        var minor = minor(row, col);
        return ((row + col) % 2 != 0) ? minor * -1 : minor;
    }

    public float determinant() {
        float result = 0f;

        if (height == 2 && width == 2) {
            result = values[0][0] * values[1][1] - values[0][1] * values[1][0];
        } else {
            for (var col = 0; col < width; col++) {
                result += values[0][col] * cofactor(0, col);
            }
        }

        return result;
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

    public Matrix inverse() {
        var determinant = determinant();
        var result = new Matrix(height, width);

        for (var row = 0; row < height; row++) {
            for (var col = 0; col < width; col++) {
                var cofactor = cofactor(row, col);
                result.set(col, row, cofactor / determinant);
            }
        }

        return result;
    }

    public boolean isInvertible() {
        return determinant() != 0;
    }

    public float minor(int row, int col) {
        return submatrix(row, col).determinant();
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

    public Matrix set(int row, int index, float value) {
        if (this.immutable) {
            System.out.println("Illegally modifying an immutable matrix.");
        }
        values[row][index] = value;
        return this;
    }

    public Matrix submatrix(int rowToRemove, int colToRemove) {
        var submatrix = new Matrix(height - 1, width - 1);
        var submatrixRow = 0;
        var submatrixCol = 0;

        for (int row = 0; row < height; row++) {
            if (row == rowToRemove)
                continue;
            for (int col = 0; col < width; col++) {
                if (col == colToRemove)
                    continue;
                if (submatrixCol >= submatrix.width) {
                    submatrixRow++;
                    submatrixCol = 0;
                }
                submatrix.set(submatrixRow, submatrixCol, values[row][col]);
                submatrixCol++;
            }
        }

        return submatrix;
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

    public float[] multiply(float[] tuple) {
        var result = new float[tuple.length];

        for (int row = 0; row < height; row++) {
            result[row] = Tuple.dot(values[row], tuple);
        }

        return result;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        for (var row : values) {
            for (var col : row) {
                str.append(String.valueOf(col));
                str.append(", ");
            }
            str.append(System.lineSeparator());
        }

        return str.toString();
    }

}
