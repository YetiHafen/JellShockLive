package de.jellshock.game.world;

public enum WorldType {

    MOUNTAIN(100, 100),
    CAVE(100, 100),
    CUSTOM(-1, -1);

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
