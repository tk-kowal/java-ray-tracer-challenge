package org.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.raytracer.Point.point;
import static org.raytracer.Color.color;
import org.raytracer.lights.PointLight;
import org.raytracer.shapes.Sphere;
import org.junit.jupiter.api.Test;

public class WorldTest {
    @Test
    public void test_emptyWorld() {
        var w = new World();
        assertTrue(w.objects().isEmpty());
        assertTrue(w.lights().isEmpty());
    }

    @Test
    public void test_defaultWorld() {
        var w = World.defaultWorld();
        var defaultLight = new PointLight(point(-10, 10, -10), color(1, 1, 1));
        var defaultSphere1 = new Sphere(0);
        defaultSphere1.setMaterial(new Material()
                .setColor(color(0.8f, 1.0f, 0.6f))
                .setDiffuse(.7f)
                .setSpecular(.2f));
        var defaultSphere2 = new Sphere(1);
        defaultSphere2.setTransform(Transform.scale(.5f, .5f, .5f));
        assertEquals(w.lights().get(0), defaultLight);
        assertEquals((Sphere) w.objects().get(0), defaultSphere1);
        assertEquals((Sphere) w.objects().get(1), defaultSphere2);
    }

}
