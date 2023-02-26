package de.jellshock.game.world;

public enum WorldType {

    SAND(100, 100),
    LAVA(200, 200);

    private final int waveLength;
    private final int amplitude;

    WorldType(int waveLength, int amplitude) {
        this.waveLength = waveLength;
        this.amplitude = amplitude;
    }

    public int getWaveLength() {
        return waveLength;
    }

    public int getAmplitude() {
        return amplitude;
    }
}
