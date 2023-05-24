package de.jellshock.game.world;

public enum MapType {

    MOUNTAIN(100, 400),
    HILLS(200, 300),
    CUSTOM(-1, -1);

    private final int waveLength;
    private final int amplitude;

    MapType(int waveLength, int amplitude) {
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
