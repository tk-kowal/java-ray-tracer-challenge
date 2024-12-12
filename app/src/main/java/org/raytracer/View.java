package org.raytracer;

import static org.raytracer.Tuple.subtract;
import static org.raytracer.Vector.normalize;

public class View {
    public static Matrix transform(float[] from, float[] to, float[] up) {
        var forwardv = normalize(subtract(to, from));
        var leftv = Vector.cross(forwardv, normalize(up));
        var trueUpv = Vector.cross(leftv, forwardv);
        var orientation = Matrix.matrix(
                new float[] { leftv[0], leftv[1], leftv[2], 0 },
                new float[] { trueUpv[0], trueUpv[1], trueUpv[2], 0 },
                new float[] { -1 * forwardv[0], -1 * forwardv[1], -1 * forwardv[2], 0 },
                new float[] { 0, 0, 0, 1 });
        return orientation.multiply(Transform.translate(-1.0f * from[0], -1.0f * from[1], -1.0f * from[2]));
    }
}
