package org.raytracer;

import org.raytracer.Ray.Intersection;
import org.raytracer.lights.Light;
import org.raytracer.shapes.Shape;

import static org.raytracer.Color.color;
import static org.raytracer.Vector.*;
import static org.raytracer.Tuple.add;
import static org.raytracer.Tuple.dot;
import static org.raytracer.Tuple.multiply;
import static org.raytracer.Tuple.subtract;

import java.util.List;
import java.util.Stack;

import static org.raytracer.Ray.ray;

public class Phong {

    public static float[] colorAt(World w, Ray r) {
        return colorAt(w, r, 5);
    }

    public static float[] colorAt(World w, Ray r, int ttl) {
        var xs = Ray.intersect(w, r);
        if (xs.isEmpty() || Ray.hit(xs) == null) {
            return color(0, 0, 0);
        }
        var params = prepare(Ray.hit(xs), r, xs);
        return shadeHit(w, params, ttl);
    }

    public static float[] reflectedColorAt(World w, PhongParams p, int ttl) {
        if (ttl == 0 || p.s.reflective() == 0) {
            return Color.BLACK;
        }

        var reflectedRay = ray(p.overpoint, p.reflectv);
        var reflectedColor = colorAt(w, reflectedRay, ttl - 1);

        return Tuple.multiply(reflectedColor, p.s.material().reflective);
    }

    public static float[] refractedColorAt(World w, PhongParams p, int ttl) {
        if (ttl == 0 || p.s.transparency() == 0) {
            return Color.BLACK;
        }

        var nratio = p.n1 / p.n2;
        var cosi = Vector.dot(p.eyev, p.normalv);
        var sin2t = nratio * nratio * (1 - cosi * cosi);

        if (sin2t > 1) {
            return Color.BLACK;
        }

        var cost = Math.sqrt(1.0f - sin2t);
        var direction = subtract(multiply(p.normalv, (float) (nratio * cosi - cost)), multiply(p.eyev, nratio));
        var ray = ray(p.underpoint, direction);

        return multiply(colorAt(w, ray, ttl - 1), p.s.transparency());
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
        var shadowed = isShadowed(w, p.overpoint);
        var surface = lighting(
                p.s,
                w.lights().getFirst(),
                p.overpoint,
                p.eyev,
                p.normalv,
                shadowed);
        var reflected = reflectedColorAt(w, p, ttl);
        var refracted = refractedColorAt(w, p, ttl);

        if (p.s.reflective() > 0 && p.s.transparency() > 0) {
            var reflectance = schlick(p);
            var r0 = multiply(reflected, reflectance);
            var r1 = multiply(refracted, (1 - reflectance));

            return add(add(surface, r0), r1);
        }
        return add(add(surface, reflected), refracted);
    }

    public static float schlick(PhongParams p) {
        var cos = dot(p.eyev, p.normalv);
        if (p.n1 > p.n2) {
            var n = p.n1 / p.n2;
            var sin2t = n * n * (1.0f - cos * cos);

            if (sin2t > 1.0f)
                return 1.0f;

            var cost = (float) Math.sqrt(1.0 - sin2t);
            cos = cost;
        }

        var r0 = ((p.n1 - p.n2) / (p.n1 + p.n2)) * ((p.n1 - p.n2) / (p.n1 + p.n2));
        return r0 + (1 - r0) * (1 - cos) * (1 - cos) * (1 - cos) * (1 - cos) * (1 - cos);
    }

    public static PhongParams prepare(Intersection i, Ray r) {
        return prepare(i, r, List.of(i));
    }

    public static PhongParams prepare(Intersection i, Ray r, List<Intersection> xs) {
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
        var underpoint = Tuple.subtract(point, Tuple.multiply(normalv, Constants.EPSILON));
        var containers = new Stack<Shape>();
        float n1, n2;
        n1 = n2 = -99f;

        for (var x : xs) {
            if (i == x) {
                if (containers.isEmpty()) {
                    n1 = 1f;
                } else {
                    n1 = containers.peek().refractix();
                }
            }

            if (containers.contains(x.object())) {
                containers.remove(x.object());
            } else {
                containers.add(x.object());
            }

            if (i == x) {
                if (containers.isEmpty()) {
                    n2 = 1f;
                } else {
                    n2 = containers.peek().refractix();
                }
                break;
            }

        }

        return new PhongParams(i.t(), i.object(), point, overpoint, underpoint, eyev, normalv, reflectv, n1, n2,
                isInside);
    }

}
