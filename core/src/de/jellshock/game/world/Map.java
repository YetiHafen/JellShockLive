package de.jellshock.game.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;
import lombok.Getter;

import java.util.Random;

@Getter
public class Map implements IRenderConsumer<SpriteBatch>, Disposable {

    private final Pixmap pixmap;
    private Texture mapTexture;
    private final Random random;
    private MapType mapType;
    private final int[] worldMap;
    private final Color color;

    private final int mapWidth;
    private final int mapHeight;
    private final int waveLength;
    private final float frequency;
    private final int amplitude;

    private boolean mapChanged = false;

    public Map(int mapWidth, MapType mapType) {
        this(mapWidth, mapType.getWaveLength(), mapType.getAmplitude());
        this.mapType = mapType;
    }

    public Map(int mapWidth, int waveLength, int amplitude) {
        this.mapWidth = mapWidth;
        this.waveLength = waveLength;
        this.amplitude = amplitude;
        this.mapHeight = amplitude * 2;
        this.frequency = 1.0F / waveLength;
        this.mapType = MapType.CUSTOM;
        pixmap = new Pixmap(mapWidth, mapHeight, Pixmap.Format.RGBA8888);
        random = new Random();
        worldMap = new int[mapWidth];
        color = new Color();
    }

    public void generateMap() {
        mapChanged = true;
        float a = random.nextFloat();
        float b = random.nextFloat();

        for (int x = 0; x < mapWidth; x++) {
            float y;
            if (!(x % waveLength == 0)) {
                y = interpolate(a, b, (x % waveLength) * 1.0f / (float) waveLength) * amplitude;
            } else {
                a = b;
                b = random.nextFloat();
                y = a * amplitude;
            }
            worldMap[x] = (int) (mapHeight - y);
        }
    }

    private void renderMap() {
        mapChanged = false;
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                int mapValue = mapHeight - worldMap[x];

                if (y > mapValue) {
                    pixmap.setBlending(Pixmap.Blending.SourceOver);
                    Color mapColor = Color.CYAN.cpy();
                    mapColor.a = 1F - ((float) (y - mapValue) / (float) (mapHeight - mapValue));
                    pixmap.setColor(mapColor);
                    pixmap.drawPixel(x, y);
                } else {
                    pixmap.setBlending(Pixmap.Blending.None);
                    pixmap.drawPixel(x, y);
                }
            }
        }
        if (mapTexture == null) {
            mapTexture = new Texture(pixmap);
        }
        mapTexture.draw(pixmap, 0,0);
    }

    public void setMapHeight(int x, int height) {
        if (height < 20) height = 20;
        mapChanged = true;
        assert height <= this.mapHeight;
        worldMap[x] = height;
    }

    public int getMapHeight(int x) {
        return worldMap[x];
    }

    public void addCircleDamage(int position, int depth, int radius) {
        int centerHeight = getMapHeight(position) - depth;

        for(int i = -radius; i < radius; i++) {
            int currentX = position + i;
            if(!isXinBounds(currentX)) continue;
            int circleHeight = (int) (Math.sin(Math.acos(i/(double)radius)) * radius);

            int newHeight = centerHeight - circleHeight;
            if(newHeight < getMapHeight(currentX)) {
                setMapHeight(currentX, newHeight);
            }
        }
    }

    public boolean isXinBounds(int xPos) {
        return xPos >= 0 && xPos < worldMap.length;
    }

    public void render(SpriteBatch batch) {
        if (mapChanged) renderMap();
        mapTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch.draw(mapTexture, 0, 0, mapWidth, mapHeight);
    }

    private float interpolate(float aPosition, float bPosition, float relativeDistance) {
        // calc the weight of the two texture values (aPosition, bPosition)
        float scaledValue = relativeDistance * (float) Math.PI;
        // weight of the two texture values of the relative position
        float weight = (1 - (float) Math.cos(scaledValue)) * 0.5f;
        return aPosition * (1 - weight) + bPosition * weight;
    }

    @Override
    public void dispose() {
        mapTexture.dispose();
        pixmap.dispose();
    }
}
