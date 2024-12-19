package org.raytracer;

import static org.raytracer.Point.point;
import static org.raytracer.Ray.ray;
import static org.raytracer.Vector.normalize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Camera {

    // horizontal & vertical size in pixels
    private float hsize, vsize;
    // half height & width in units
    private float halfWidth, halfHeight;
    private float fov;
    private float aspect;
    private float pixelSize;
    private Matrix transform;
    private final int THREAD_COUNT = 10;

    public Camera(float hsize, float vsize, float fov) {
        this.hsize = hsize;
        this.vsize = vsize;
        this.fov = fov;
        this.transform = Matrix.identity();
        computeParams();
    }

    public float fov() {
        return this.fov;
    }

    public float halfHeight() {
        return this.halfHeight;
    }

    public float halfWidth() {
        return this.halfWidth;
    }

    public float hsize() {
        return this.hsize;
    }

    public float pixelSize() {
        return this.pixelSize;
    }

    public Ray rayForPixel(float x, float y) {
        // we add .5f to the pixelSize because we want to compute the ray to the center
        // of the pixel
        var point = point(halfWidth - (x + .5f) * pixelSize, halfHeight - (y + .5f) * pixelSize, -1);
        // the transform says how to move the world relative to the camera, so we use
        // the inverse here to actually move the points how the camera would move
        var worldPoint = transform.inverse().multiply(point);
        var worldOrigin = transform.inverse().multiply(point(0, 0, 0));
        var worldDirection = normalize(Tuple.subtract(worldPoint, worldOrigin));
        return ray(worldOrigin, worldDirection);
    }

    public Canvas render(World w) {
        int columnsPerThread = (int) hsize / THREAD_COUNT;
        var es = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<List<PixelData>>> results = new ArrayList<>();
        var c = new Canvas((int) hsize, (int) vsize);

        for (int i = 0; i < hsize; i += columnsPerThread) {
            int start = i;
            int end = i + columnsPerThread;
            System.out.println(
                    "Starting render thread for columns: " + String.valueOf(start) + " through "
                            + String.valueOf(end - 1));
            results.add(es.submit(() -> {
                var data = new ArrayList<PixelData>();
                for (var y = 0; y < vsize; y++) {
                    for (var x = start; x < end; x++) {
                        var r = rayForPixel(x, y);
                        data.add(new PixelData(x, y, Phong.colorAt(w, r)));
                    }
                }
                return data;
            }));
        }
        for (var r : results) {
            try {
                var data = r.get();
                for (var p : data) {
                    c.writePixel(p.x, p.y, p.color);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    public Matrix transform() {
        return this.transform;
    }

    public float vsize() {
        return this.vsize;
    }

    private void computeParams() {
        this.aspect = hsize / vsize;

        // this assumes FOV always refers to the widest angle
        // (so vertical when aspect < 1 and horizontal when aspect > 1)
        var half_view = (float) Math.tan(fov / 2);
        this.halfWidth = aspect >= 1 ? half_view : half_view * aspect;
        this.halfHeight = aspect >= 1 ? half_view / aspect : half_view;
        this.pixelSize = (halfWidth * 2) / hsize;
    }

    private record PixelData(int x, int y, float[] color) {
    }
}
