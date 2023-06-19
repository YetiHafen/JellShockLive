package de.jellshock;

import de.jellshock.game.screen.game.offline.level.Level;
import de.jellshock.game.screen.game.offline.level.impl.FirstLevel;
import de.jellshock.game.screen.game.offline.level.impl.FourthLevel;
import de.jellshock.game.screen.game.offline.level.impl.SecondLevel;
import de.jellshock.game.screen.game.offline.level.impl.ThirdLevel;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String SERVER_URL = "ws://localhost:3000";

    public static final String NEON_SKIN_PATH = "neon/skin/neon-ui.json";
    public static final String JELLY_SKIN_PATH = "jelly/skin/jelly.json";

    // Menu
    public static final String LOGO_PATH = "jellshock.png";
    public static final String MENU_BACKGROUND_PATH = "menu/background.png";
    public static final String PLAY_BUTTON_PATH = "menu/play.png";
    public static final String LEFT_ARROW_PATH = "menu/left-arrow.png";

    // Game
    public static final String BACKGROUND_PATH = "background/sky.png";
    public static final String DIALOG_PATH = "jelly/raw/dialog.png";
    public static final String HEALTH_BAR_PATH = "health-bar.png";

    public static final String FUEL_BUTTON_PATH = "fuel/button-fuel.png";
    public static final String FUEL_POINTER_PATH = "fuel/pointer.png";

    // Tank
    public static final String CHASSIS_PATH = "tank/chassis_round.png";
    public static final String TRACK_PATH = "tank/track_classic.png";
    public static final String GUN_PATH = "tank/gun_dainty.png";

    // Guns
    public static final String SHOT_PATH = "weapon/shot/shot.png";

    public static final List<Class<? extends Level>> LEVELS = Arrays.asList(
            FirstLevel.class,
            SecondLevel.class,
            ThirdLevel.class,
            FourthLevel.class
    );

    public static final List<String> IMAGE_LEVEL_PATHS = Arrays.asList(
            "level/images/level_1.png",
            "level/images/level_2.png",
            "level/images/level_3.png",
            "level/images/level_4.png"
    );

}
