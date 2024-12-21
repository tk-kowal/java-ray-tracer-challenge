package org.raytracer.patterns;

import static org.raytracer.Point.point;

public class Perlin extends Pattern {

        private Pattern a;
        private float scale;

        public Perlin(Pattern a, float scale) {
                this.a = a;
                this.scale = scale;
        }

        @Override
        public float[] colorAt(float[] point) {
                var lp = transform.inverse().multiply(point);
                var nX = noise(lp[0] + 7, lp[1], lp[2]) * scale;
                var nY = noise(lp[0], lp[1] + 17, lp[2]) * scale;
                var nZ = noise(lp[0], lp[1], lp[2] + 31) * scale;

                return a.colorAt(point(lp[0] + nX, lp[1] + nY, lp[2] + nZ));
        }

        static public float noise(float x, float y, float z) {
                int X = (int) Math.floor(x) & 255;
                int Y = (int) Math.floor(y) & 255;
                int Z = (int) Math.floor(z) & 255;

                var dx = x - (float) Math.floor(x);
                var dy = y - (float) Math.floor(y);
                var dz = z - (float) Math.floor(z);

                float u = fade(dx);
                float v = fade(dy);
                float w = fade(dz);

                float w000 = grad(X, Y, Z, dx, dy, dz);
                float w100 = grad(X + 1, Y, Z, dx - 1, dy, dz);
                float w010 = grad(X, Y + 1, Z, dx, dy - 1, dz);
                float w001 = grad(X, Y, Z + 1, dx, dy, dz - 1);
                float w110 = grad(X + 1, Y + 1, Z, dx - 1, dy - 1, dz);
                float w101 = grad(X + 1, Y, Z + 1, dx - 1, dy, dz - 1);
                float w011 = grad(X, Y + 1, Z + 1, dx, dy - 1, dz - 1);
                float w111 = grad(X + 1, Y + 1, Z + 1, dx - 1, dy - 1, dz - 1);

                float x00 = lerp(u, w000, w100);
                float x10 = lerp(u, w010, w110);
                float x01 = lerp(u, w001, w101);
                float x11 = lerp(u, w011, w111);
                float y0 = lerp(v, x00, x10);
                float y1 = lerp(v, x01, x11);

                return lerp(w, y0, y1);
        }

        static float fade(float t) {
                // 6t^5 - 15t^4 + 10t^3
                return t * t * t * (t * (t * 6 - 15) + 10);
        }

        static float lerp(float t, float a, float b) {
                return a + t * (b - a);
        }

        static float grad(int ix, int iy, int iz, float dx, float dy, float dz) {
                int h = p[p[p[ix] + iy] + iz];
                h &= 15;
                float u = h < 8 || h == 12 || h == 13 ? dx : dy;
                float v = h < 4 || h == 12 || h == 13 ? dy : dz;
                return ((h & 1) == 1 ? -u : u) + ((h & 2) == 1 ? -v : v);
        }

        static final int p[] = new int[512], permutation[] = { 151, 160, 137, 91, 90, 15,
                        131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10,
                        23,
                        190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
                        88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
                        77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
                        102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
                        135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
                        5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28,
                        42,
                        223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
                        129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
                        251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
                        49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
                        138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
        };
        static {
                for (int i = 0; i < 256; i++)
                        p[256 + i] = p[i] = permutation[i];
        }
}
