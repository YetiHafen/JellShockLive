package de.jellshock.network.game;

public enum Package {
    // Client to Server

    // Server to Client
    TEAM("team"),
    GAMESTATE("gamestate"),
    JOIN_DATA("joindata"),
    LEFT("left"),

    // Both
    ERROR("err"),
    ROTATE_GUN("rotation"),
    MOVEMENT("movement"),
    JOIN("join");


    private String value;

    Package(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
