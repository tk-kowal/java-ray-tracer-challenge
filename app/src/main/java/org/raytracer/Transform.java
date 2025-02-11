package org.raytracer;

public class Transform {
    public static Matrix translate(float x, float y, float z) {
        return Matrix.identity().translate(x, y, z);
    }

    public static Matrix scale(float x, float y, float z) {
        return Matrix.identity().scale(x, y, z);
    }

    public static Matrix rotateX(float radians) {
        return Matrix.identity().rotateX(radians);
    }

    public static Matrix rotateY(float radians) {
        return Matrix.identity().rotateY(radians);
    }

    public static Matrix rotateZ(float radians) {
        return Matrix.identity().rotateZ(radians);
    }

    public static Matrix shear(float xy, float xz, float yx, float yz, float zx, float zy) {
        return Matrix.identity().shear(xy, xz, yx, yz, zx, zy);
    }

}
