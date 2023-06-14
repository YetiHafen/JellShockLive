package de.jellshock.game.player;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public enum Team {
    DEFAULT(Color.WHITE, -1),
    CYAN(Color.CYAN, 0),
    RED(Color.RED, 1);

    private static final Random random = new Random();
    private final Color color;
    private final int packageValue;

    Team(Color color, int packageValue) {
        this.color = color;
        this.packageValue = packageValue;
    }

    public Color getColor() {
        return color;
    }

    public int getPackageValue() {
        return packageValue;
    }

    public static Team getTeamByPackage(int value) {
        for (Team team : values()) {
            if (team.packageValue == value) return team;
        }
        return null;
    }

    public static Team randomTeam() {
        Team[] teams = values();
        return teams[random.nextInt(teams.length)];
    }
}
