package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatrixTest {
        @Test
        public void test_creatingAMatrix() {
                var matrix = Matrix.matrix(
                                new float[] { 1f, 2f, 3f, 4f },
                                new float[] { 5.5f, 6.5f, 7.5f, 8.5f },
                                new float[] { 9, 10, 11, 12 },
                                new float[] { 13.5f, 14.5f, 15.5f, 16.5f });
                assertEquals(1f, matrix.get(0, 0));
                assertEquals(5.5f, matrix.get(1, 0));
                assertEquals(7.5f, matrix.get(1, 2));
                assertEquals(11f, matrix.get(2, 2));
                assertEquals(13.5, matrix.get(3, 0));
                assertEquals(15.5, matrix.get(3, 2));
        }

        @Test
        public void test_2x2Matrix() {
                var matrix = Matrix.matrix(
                                new float[] { -3, 5 },
                                new float[] { 1, -2 });
                assertEquals(-3, matrix.get(0, 0));
        }

        @Test
        public void test_areEqual() {
                var matrixA = Matrix.matrix(
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 5, 6, 7.02f, 8 },
                                new float[] { 9.4f, 10.002f, 7.5f, 11 });
                var matrixB = Matrix.matrix(
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 5, 6, 7.02f, 8 },
                                new float[] { 9.4f, 10.002f, 7.5f, 11 });

                assertTrue(matrixA.equals(matrixB));
        }

        @Test
        public void test_areNotEqual() {
                var matrixA = Matrix.matrix(
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 5, 6, 7.02f, 8 },
                                new float[] { 9.4f, 10.002f, 7.5f, 11 });
                var matrixB = Matrix.matrix(
                                new float[] { 5, 6, 7.02f, 8 },
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 9.4f, 10.002f, 7.5f, 11 });

                assertFalse(matrixA.equals(matrixB));
        }

        @Test
        public void test_getCol() {
                var matrix = Matrix.matrix(
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 5, 6, 7, 8 },
                                new float[] { 9, 8, 7, 6 },
                                new float[] { 5, 4, 3, 2 });

                assertTrue(Tuple.areEqual(new float[] { 1, 5, 9, 5 }, matrix.getCol(0)));
                assertTrue(Tuple.areEqual(new float[] { 2, 6, 8, 4 }, matrix.getCol(1)));
        }

        @Test
        public void test_multiply() {
                var matrixA = Matrix.matrix(
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 5, 6, 7, 8 },
                                new float[] { 9, 8, 7, 6 },
                                new float[] { 5, 4, 3, 2 });

                var matrixB = Matrix.matrix(
                                new float[] { -2, 1, 2, 3 },
                                new float[] { 3, 2, 1, -1 },
                                new float[] { 4, 3, 6, 5 },
                                new float[] { 1, 2, 7, 8 });

                var expected = Matrix.matrix(
                                new float[] { 20, 22, 50, 48 },
                                new float[] { 44, 54, 114, 108 },
                                new float[] { 40, 58, 110, 102 },
                                new float[] { 16, 26, 46, 42 });

                var actual = matrixA.multiply(matrixB);

                assertTrue(expected.equals(actual));
        }

        @Test
        public void test_multiplyTuple() {
                var matrix = Matrix.matrix(
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 2, 4, 4, 2 },
                                new float[] { 8, 6, 4, 1 },
                                new float[] { 0, 0, 0, 1 });
                var tuple = new float[] { 1, 2, 3, 1 };
                var expected = new float[] { 18, 24, 33, 1 };
                var actual = matrix.tupleMultiply(tuple);

                assertTrue(Tuple.areEqual(expected, actual));
        }

        @Test
        public void test_identityMultiply() {
                var matrix = Matrix.matrix(
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 2, 4, 4, 2 },
                                new float[] { 8, 6, 4, 1 },
                                new float[] { 0, 0, 0, 1 });
                var expected = Matrix.matrix(
                                new float[] { 1, 2, 3, 4 },
                                new float[] { 2, 4, 4, 2 },
                                new float[] { 8, 6, 4, 1 },
                                new float[] { 0, 0, 0, 1 });
                var actual = matrix.multiply(Matrix.IDENTITY_MATRIX);

                assertTrue(expected.equals(actual));
        }

        @Test
        public void test_identityTupleMultiply() {
                var tuple = new float[] { 1, 2, 3, 4 };
                var expected = new float[] { 1, 2, 3, 4 };
                var actual = Matrix.IDENTITY_MATRIX.tupleMultiply(tuple);

                assertTrue(Tuple.areEqual(expected, actual));
        }

        @Test
        public void test_transpose() {
                var matrix = Matrix.matrix(
                                new float[] { 0, 9, 3, 0 },
                                new float[] { 9, 8, 0, 8 },
                                new float[] { 1, 8, 5, 3 },
                                new float[] { 0, 0, 5, 8 });

                var expected = Matrix.matrix(
                                new float[] { 0, 9, 1, 0 },
                                new float[] { 9, 8, 8, 0 },
                                new float[] { 3, 0, 5, 5 },
                                new float[] { 0, 8, 3, 8 });

                var actual = matrix.transpose();

                assertTrue(expected.equals(actual));
        }

        @Test
        public void test_transposeIdentity() {
                assertTrue(Matrix.IDENTITY_MATRIX.equals(Matrix.IDENTITY_MATRIX.transpose()));
        }

        @Test
        public void test_determinant2x2() {
                var matrix = Matrix.matrix(
                                new float[] { 1, 5 },
                                new float[] { -3, 2 });
                assertEquals(17, matrix.determinant());
        }

        @Test
        public void test_submatrix3x3() {
                var rowToRemove = 0;
                var colToRemove = 2;
                var matrix = Matrix.matrix(
                                new float[] { 1, 5, 0 },
                                new float[] { -3, 2, 7 },
                                new float[] { 0, 6, -3 });

                var expected = Matrix.matrix(
                                new float[] { -3, 2 },
                                new float[] { 0, 6 });

                var actual = matrix.submatrix(rowToRemove, colToRemove);

                assertTrue(expected.equals(actual));
        }

        @Test
        public void test_submatrix4x4() {
                var rowToRemove = 2;
                var colToRemove = 1;
                var matrix = Matrix.matrix(
                                new float[] { -6, 1, 1, 6 },
                                new float[] { -8, 5, 8, 6 },
                                new float[] { -1, 0, 8, 2 },
                                new float[] { -7, 1, -1, 1 });

                var expected = Matrix.matrix(
                                new float[] { -6, 1, 6 },
                                new float[] { -8, 8, 6 },
                                new float[] { -7, -1, 1 });

                var actual = matrix.submatrix(rowToRemove, colToRemove);

                assertTrue(expected.equals(actual));
        }

        @Test
        public void test_minor3x3() {
                var matrix = Matrix.matrix(
                                new float[] { 3, 5, 0 },
                                new float[] { 2, -1, -7 },
                                new float[] { 6, -1, 5 });
                var submatrix = matrix.submatrix(1, 0);
                assertEquals(25, submatrix.determinant());
                assertEquals(25, matrix.minor(1, 0));
        }

        @Test
        public void test_cofactor3x3() {
                var matrix = Matrix.matrix(
                                new float[] { 3, 5, 0 },
                                new float[] { 2, -1, -7 },
                                new float[] { 6, -1, 5 });
                assertEquals(-12, matrix.minor(0, 0));
                assertEquals(-12, matrix.cofactor(0, 0));
                assertEquals(25, matrix.minor(1, 0));
                assertEquals(-25, matrix.cofactor(1, 0));
        }

        @Test
        public void test_determinant3x3() {
                var matrix = Matrix.matrix(
                                new float[] { 1, 2, 6 },
                                new float[] { -5, 8, -4 },
                                new float[] { 2, 6, 4 });
                assertEquals(56, matrix.cofactor(0, 0));
                assertEquals(12, matrix.cofactor(0, 1));
                assertEquals(-46, matrix.cofactor(0, 2));
                assertEquals(-196, matrix.determinant());
        }

        @Test
        public void test_determinant4x4() {
                var matrix = Matrix.matrix(
                                new float[] { -2, -8, 3, 5 },
                                new float[] { -3, 1, 7, 3 },
                                new float[] { 1, 2, -9, 6 },
                                new float[] { -6, 7, 7, -9 });
                assertEquals(690, matrix.cofactor(0, 0));
                assertEquals(447, matrix.cofactor(0, 1));
                assertEquals(210, matrix.cofactor(0, 2));
                assertEquals(51, matrix.cofactor(0, 3));
                assertEquals(-4071, matrix.determinant());
        }

        @Test
        public void test_isInvertible() {
                var matrix = Matrix.matrix(
                                new float[] { 6, 4, 4, 4 },
                                new float[] { 5, 5, 7, 6 },
                                new float[] { 4, -9, 3, -7 },
                                new float[] { 9, 1, 7, -6 });
                var matrixB = Matrix.matrix(
                                new float[] { -4, 2, -2, -3 },
                                new float[] { 9, 6, 2, 6 },
                                new float[] { 0, -5, 1, -5 },
                                new float[] { 0, 0, 0, 0 });
                assertTrue(matrix.isInvertible());
                assertFalse(matrixB.isInvertible());
        }

        @Test
        public void test_inversion() {
                var matrix = Matrix.matrix(
                                new float[] { -5, 2, 6, -8 },
                                new float[] { 1, -5, 1, 8 },
                                new float[] { 7, 7, -6, -7 },
                                new float[] { 1, -3, 7, 4 });
                var expected = Matrix.matrix(
                                new float[] { 0.21805f, 0.45113f, 0.24060f, -0.04511f },
                                new float[] { -0.80827f, -1.45677f, -0.44361f, 0.52068f },
                                new float[] { -0.07895f, -0.22368f, -0.05263f, 0.19737f },
                                new float[] { -0.52256f, -0.81391f, -0.30075f, 0.30639f });
                var actual = matrix.inverse();
                assertTrue(expected.equals(actual));
        }

        @Test
        public void test_undoMultiplication() {
                var matrixA = Matrix.matrix(
                                new float[] { 3, -9, 7, 3 },
                                new float[] { 3, -8, 2, -9 },
                                new float[] { -4, 4, 4, 1 },
                                new float[] { -6, 5, -1, 1 });

                var matrixB = Matrix.matrix(
                                new float[] { 8, 2, 2, 2 },
                                new float[] { 3, -1, 7, 0 },
                                new float[] { 7, 0, 5, 4 },
                                new float[] { 6, -2, 0, 5 });

                var matrixC = matrixA.multiply(matrixB);
                assertTrue(matrixC.multiply(matrixB.inverse()).equals(matrixA));
        }
}
