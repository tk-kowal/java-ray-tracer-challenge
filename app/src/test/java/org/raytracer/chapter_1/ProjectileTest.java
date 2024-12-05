package org.raytracer.chapter_1;

import org.junit.jupiter.api.Test;
import static org.raytracer.Tuple.*;
import static org.raytracer.Scalar.*;

public class ProjectileTest {
    private record Projectile(float[] position, float[] velocity) {
    }

    private record Environment(float[] gravity, float[] wind) {
    }

    @Test
    public void test_Tick() {
        var projectile = new Projectile(point(0, 10, 0), normalize(vector(1, 1, 0)));
        var environment = new Environment(vector(0, -0.1f, 0), vector(-0.01f, 0, 0));
        var ticks = 0;

        do {
            System.out.println("tick: " + ticks);
            System.out.println("position: " + pretty(projectile.position));
            System.out.println("velocity: " + pretty(projectile.velocity));
            var newPosition = add(projectile.position, projectile.velocity);
            var newVelocity = add(add(projectile.velocity, environment.gravity), environment.wind);
            projectile = new Projectile(newPosition, newVelocity);
            ticks++;
        } while (projectile.position[1] > 0f);

        System.out.println(String.format("Bullet hit the ground after %d ticks", ticks));
        System.out.println(String.format("Bullet traveled %f meters", projectile.position[0]));
    }

}
