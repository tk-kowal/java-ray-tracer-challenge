package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Point.point;
import static org.raytracer.Vector.vector;
import static org.raytracer.Color.color;

import org.junit.jupiter.api.Test;

public class CameraTest {

    @Test
    public void test_camera() {
        var hsize = 160f;
        var vsize = 120f;
        var fov = (float) Math.PI / 2;
        var c = new Camera(hsize, vsize, fov);
        assertTrue(Scalar.areEqual(hsize, c.hsize()));
        assertTrue(Scalar.areEqual(vsize, c.vsize()));
        assertTrue(Scalar.areEqual(fov, c.fov()));
        assertEquals(Matrix.identity(), c.transform());
    }

    @Test
    public void test_cameraKnowsPixelSizeHorizontalAspect() {
        var hsize = 200;
        var vsize = 125;
        var fov = (float) Math.PI / 2;
        var c = new Camera(hsize, vsize, fov);
        assertTrue(Scalar.areEqual(.01f, c.pixelSize()));
    }

    @Test
    public void test_cameraKnowsPixelSizeVerticalAspect() {
        var hsize = 125;
        var vsize = 200;
        var fov = (float) Math.PI / 2;
        var c = new Camera(hsize, vsize, fov);
        assertTrue(Scalar.areEqual(.01f, c.pixelSize()));
    }

    @Test
    public void test_rayForPixelThroughCenter() {
        var c = new Camera(201, 101, (float) Math.PI / 2);
        var expected = Ray.ray(point(0, 0, 0), vector(0, 0, -1));
        var actual = c.rayForPixel(100, 50);
        assertEquals(expected, actual);
    }

    @Test
    public void test_rayForPixelThroughCorner() {
        var c = new Camera(201, 101, (float) Math.PI / 2);
        var expected = Ray.ray(point(0, 0, 0), vector(.66519f, .33259f, -.66851f));
        var actual = c.rayForPixel(0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_rayForPixelWhenCamTransformed() {
        var c = new Camera(201, 101, (float) Math.PI / 2);
        c.setTransform(Transform.rotateY((float) Math.PI / 4).translate(0, -2, 5));
        var expected = Ray.ray(point(0, 2, -5), vector((float) (Math.sqrt(2) / 2), 0, (float) (-1 * Math.sqrt(2) / 2)));
        var actual = c.rayForPixel(100, 50);
        assertEquals(expected, actual);
    }

    @Test
    public void test_render() {
        var w = World.defaultWorld();
        var c = new Camera(11, 11, (float) Math.PI / 2);
        var view = View.transform(point(0, 0, -5), point(0, 0, 0), vector(0, 1, 0));
        c.setTransform(view);
        var img = c.render(w);
        var pixel = img.pixelAt(5, 5);
        assertTrue(Tuple.areEqual(color(.38066f, .47583f, .2855f), pixel));
    }

}
