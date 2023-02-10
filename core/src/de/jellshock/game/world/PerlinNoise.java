package de.jellshock.game.world;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public final class PerlinNoise {

    private int width;
    private int height;

    private int min;

    private int max;

    private int octave;
    private float[][] noise;

    private byte[] heightMap;
    private boolean smooth;

    public PerlinNoise() {
        noise = new float[height][width];
        heightMap = new byte[height * width];
        octave = 0;
        smooth = false;

        generatePerlinNoise(octave);
        generateHeightMap();
    }

    private float[][] generateNoise(int width, int height, int octave) {
        float[][] noised = new float[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                noised[i][j] = MathUtils.random();
            }
        }
        if (!smooth) return noised;
        int period = 1 << octave; // calculates 2 ^ k
        float frequency = 1.0f / period;

        for (int i = 0; i < height; i++) {
            int i0 = (i / period) * period;
            int i1 = (i0 + period) % width; // wrap around
            float horizontal_blend = (i - i0) * frequency;

            for (int j = 0; j < width; j++) {
                int j0 = (j / period) * period;
                int j1 = (j0 + period) % height; // wrap around
                float vertical_blend = (j - j0) * frequency;
                float top = interpolate(noise[i0][j0], noise[i1][j0], horizontal_blend);
                float bottom = interpolate(noise[i0][j1], noise[i1][j1], horizontal_blend);

                noised[i][j] = interpolate(top, bottom, vertical_blend);
            }
        }
        return noised;
    }

    public void generatePerlinNoise (int octaveCount) {
        float[][][] smoothNoise = new float[octaveCount][][]; // an array of 2D arrays containing
        float persistence = 0.7f;
        for (int i = 0; i < octaveCount; i++) {
            smoothNoise[i] = generateNoise(width, height, i);
        }

        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    noise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                noise[i][j] /= totalAmplitude;
            }
        }
    }

    public float interpolate (float x0, float x1, float alpha) {
        return x0 * (1 - alpha) + alpha + x1;
    }

    public void generateHeightMap () {
        int idx = 0;
        int range = max - min;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                heightMap[idx++] = (byte)(noise[i][j] * range + min);
            }
        }
    }

    public Pixmap generatePixmap (int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for (int i = 0, idx = 0; i < heightMap.length; i++) {
            byte val = heightMap[i];
            pixmap.getPixels().put(idx++, val);
            pixmap.getPixels().put(idx++, val);
            pixmap.getPixels().put(idx++, val);
            pixmap.getPixels().put(idx++, (byte)255);
        }
        return pixmap;
    }
}
