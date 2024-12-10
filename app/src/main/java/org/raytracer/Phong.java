package org.raytracer;

import org.raytracer.lights.Light;
import org.raytracer.shapes.Shape;

import static org.raytracer.Color.color;
import static org.raytracer.Vector.*;

public class Phong {

    public record PhongParams(float t, Shape s, float[] point, float[] eyev, float[] normalv, boolean isInside) {
    }

    public static float[] colorAt(World w, Ray r) {
        var xs = Ray.intersect(w, r);
        if (xs.isEmpty()) {
            return color(0, 0, 0);
        }
        var params = prepare(Ray.hit(xs), r);
        return shadeHit(w, params);
    }

    public static float[] lighting(Material m, Light l, float[] point, float[] eyev, float[] normalv) {
        var effectiveColor = Tuple.multiply(m.color(), l.intensity());
        var lightv = normalize(Tuple.subtract(l.position(), point));
        var ambient = Tuple.multiply(effectiveColor, m.ambient());
        var diffuse = color(0, 0, 0);
        var specular = color(0, 0, 0);
        var lightDotNormal = dot(lightv, normalv);

        if (lightDotNormal >= 0) {
            diffuse = Tuple.multiply(Tuple.multiply(effectiveColor, m.diffuse()), lightDotNormal);
            var reflectv = reflect(multiply(lightv, -1f), normalv);
            var reflectDotEye = dot(reflectv, eyev);

            if (reflectDotEye > 0) {
                var factor = (float) Math.pow(reflectDotEye, m.shininess());
                specular = multiply(multiply(l.intensity(), m.specular()), factor);
            }
        }

        return add(add(ambient, diffuse), specular);
    }

    public static float[] shadeHit(World w, PhongParams p) {
        return lighting(
                p.s().material(),
                w.lights().getFirst(),
                p.point(),
                p.eyev(),
                p.normalv());
    }

    public static PhongParams prepare(Ray.Intersection i, Ray r) {
        var object = i.object();
        var point = r.position(i.t());
        var normalv = object.normalAt(point);
        var eyev = multiply(r.direction(), -1);
        var isInside = false;
        if (Vector.dot(normalv, eyev) < 0) {
            isInside = true;
            normalv = multiply(normalv, -1);
        }
        return new PhongParams(i.t(), i.object(), point, eyev, normalv, isInside);
    }

}
