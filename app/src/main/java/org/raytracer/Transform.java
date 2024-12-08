package org.raytracer;

public class Transform {

    public static Matrix translation(int x, int y, int z) {
        return Matrix.matrix(
                new float[] { 1, 0, 0, x },
                new float[] { 0, 1, 0, y },
                new float[] { 0, 0, 1, z },
                new float[] { 0, 0, 0, 1 });
    }

    public static Matrix scaling(int x, int y, int z) {
        return Matrix.matrix(
                new float[] { x, 0, 0, 0 },
                new float[] { 0, y, 0, 0 },
                new float[] { 0, 0, z, 0 },
                new float[] { 0, 0, 0, 1 });
    }

    public static Matrix rotateX(float radians) {
        return Matrix.matrix(
                new float[] { 1, 0, 0, 0 },
                new float[] { 0, (float) Math.cos(radians), (float) (-1 * Math.sin(radians)), 0 },
                new float[] { 0, (float) Math.sin(radians), (float) Math.cos(radians), 0 },
                new float[] { 0, 0, 0, 1 });
    }

    public static Matrix rotateY(float radians) {
        return Matrix.matrix(
                new float[] { (float) Math.cos(radians), 0, (float) Math.sin(radians), 0 },
                new float[] { 0, 1, 0, 0 },
                new float[] { (float) (-1 * Math.sin(radians)), 0, (float) Math.cos(radians), 0 },
                new float[] { 0, 0, 0, 1 });
    }

    public static Matrix rotateZ(float radians) {
        return Matrix.matrix(
                new float[] { (float) Math.cos(radians), (float) (-1 * Math.sin(radians)), 0, 0 },
                new float[] { (float) Math.sin(radians), (float) Math.cos(radians), 0, 0 },
                new float[] { 0, 0, 1, 0 },
                new float[] { 0, 0, 0, 1 });
    }

    public static Matrix shear(int xy, int xz, int yx, int yz, int zx, int zy) {
        return Matrix.matrix(
                new float[] { 1, xy, xz, 0 },
                new float[] { yx, 1, yz, 0 },
                new float[] { zx, zy, 1, 0 },
                new float[] { 0, 0, 0, 1 });
    }

}
