package org.raytracer;

import org.raytracer.lights.Light;
import org.raytracer.shapes.Shape;

import static org.raytracer.Color.color;
import static org.raytracer.Vector.*;
import static org.raytracer.Ray.ray;

public class Phong {

    public record PhongParams(float t, Shape s, float[] point, float[] overpoint, float[] eyev, float[] normalv,
            float[] reflectv,
            boolean isInside) {
    }

    public static float[] colorAt(World w, Ray r) {
        return colorAt(w, r, 5);
    }

    public static float[] colorAt(World w, Ray r, int ttl) {
        var xs = Ray.intersect(w, r);
        if (xs.isEmpty() || Ray.hit(xs) == null) {
            return color(0, 0, 0);
        }
        var params = prepare(Ray.hit(xs), r);
        return shadeHit(w, params, ttl);
    }

    public static float[] reflectedColorAt(World w, PhongParams p, int ttl) {
        if (ttl == 0 || p.s.material().reflective() == 0) {
            return Color.BLACK;
        }

        var reflectedRay = ray(p.overpoint(), p.reflectv());
        var reflectedColor = colorAt(w, reflectedRay, ttl - 1);

        return Tuple.multiply(reflectedColor, p.s.material().reflective());
    }

    public static boolean isShadowed(World w, float[] p) {
        var lp = w.lights().getFirst().position();
        var v = Tuple.subtract(lp, p);
        var distance = magnitude(v);
        var r = ray(p, normalize(v));
        var hit = Ray.hit(Ray.intersect(w, r));
        return hit != null && hit.t() < distance;
    }

    public static float[] lighting(Shape s, Light l, float[] point, float[] eyev, float[] normalv) {
        return lighting(s, l, point, eyev, normalv, false);
    }

    public static float[] lighting(Shape s, Light l, float[] point, float[] eyev, float[] normalv,
            boolean isInShadow) {
        var effectiveColor = Tuple.multiply(s.colorAt(point), l.intensity());
        var lightv = normalize(Tuple.subtract(l.position(), point));
        var ambient = Tuple.multiply(effectiveColor, s.ambient());
        var diffuse = color(0, 0, 0);
        var specular = color(0, 0, 0);
        var lightDotNormal = dot(lightv, normalv);

        if (!isInShadow && lightDotNormal >= 0) {
            diffuse = Tuple.multiply(Tuple.multiply(effectiveColor, s.diffuse()), lightDotNormal);
            var lightReflectV = reflect(multiply(lightv, -1f), normalv);
            var reflectDotEye = dot(lightReflectV, eyev);

            if (reflectDotEye > 0) {
                var factor = (float) Math.pow(reflectDotEye, s.shininess());
                specular = multiply(multiply(l.intensity(), s.specular()), factor);
            }
        }

        return add(add(ambient, diffuse), specular);
    }

    public static float[] shadeHit(World w, PhongParams p, int ttl) {
        var shadowed = isShadowed(w, p.overpoint());
        var surface = lighting(
                p.s(),
                w.lights().getFirst(),
                p.overpoint(),
                p.eyev(),
                p.normalv(),
                shadowed);
        var reflected = reflectedColorAt(w, p, ttl);
        return Tuple.add(surface, reflected);
    }

    public static PhongParams prepare(Ray.Intersection i, Ray r) {
        var object = i.object();
        var point = r.position(i.t());
        var normalv = object.normalAt(point);
        var reflectv = reflect(r.direction(), normalv);
        var eyev = multiply(r.direction(), -1);
        var isInside = false;
        if (Vector.dot(normalv, eyev) < 0) {
            isInside = true;
            normalv = multiply(normalv, -1);
        }
        var overpoint = Tuple.add(point, Tuple.multiply(normalv, Constants.EPSILON));
        return new PhongParams(i.t(), i.object(), point, overpoint, eyev, normalv, reflectv, isInside);
    }

}
