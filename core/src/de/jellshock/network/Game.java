package de.jellshock.network;

import de.jellshock.game.screen.game.GameState;
import lombok.Getter;

public class Game {
    @Getter
    private String gameId;
    @Getter
    private String name;
    @Getter
    private String password;
    @Getter
    private String map;
    @Getter
    private int playerCount;
    private int gameState;

    @Getter
    private static final String[] labels = {
            "Server ID",
            "Server Name",
            "Map",
            "Players",
            "GameState"
    };

    public GameState getGameState() {
        return GameState.getById(gameState);
    }
}