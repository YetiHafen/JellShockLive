package de.jellshock.game.screen.game;

import lombok.Getter;

public enum GameState {
    LOBBY(0, "Lobby"),
    START(1, "Starting..."),
    INGAME(2, "Ingame"),
    ENDING(3, "End");

    private final int id;
    private final String name;

    GameState(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static GameState getById(int id) {
        for(GameState state : values()) {
            if(state.id == id) return state;
        }
        return null;
    }
}