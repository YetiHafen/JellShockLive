package de.jellshock.game.screen.game;

import lombok.Getter;

@Getter
public enum GameState {

    START("start"),
    WEAPON_SELECT("select"),
    SHOOT("shoot"),
    END("end");

    private final String packageName;

    GameState(String packageName) {
        this.packageName = packageName;
    }
}
