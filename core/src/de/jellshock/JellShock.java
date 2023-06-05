package de.jellshock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import de.jellshock.game.screen.AbstractScreen;
import de.jellshock.game.screen.game.offline.OfflineScreen;
import de.jellshock.game.screen.game.online.OnlineScreen;
import de.jellshock.game.screen.menu.*;
import de.jellshock.game.weapon.WeaponManager;
import de.jellshock.user.User;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.*;

@Getter
public class JellShock extends Game {

	private static JellShock instance;

	private AssetManager assetManager;
	private WeaponManager weaponManager;

	private final Map<Class<? extends AbstractScreen>, Screen> screens;

	private MenuScreen menuScreen;
	private LevelSelectScreen levelSelectScreen;
	private CreateAccountMenu createAccountMenu;
	private ServerSelectMenu serverSelectMenu;
	private SettingsScreen settingsScreen;

	private OfflineScreen offlineScreen;
	private OnlineScreen onlineScreen;

	private User user;

	public JellShock() {
		instance = this;
		screens = new HashMap<>();
		screens.put(LevelSelectScreen.class, levelSelectScreen);
		screens.put(CreateAccountMenu.class, createAccountMenu);
		screens.put(ServerSelectMenu.class, serverSelectMenu);
		screens.put(SettingsScreen.class, settingsScreen);
		screens.put(OfflineScreen.class, offlineScreen);
		screens.put(OnlineScreen.class, onlineScreen);
	}

	@Override
	public void create() {
		assetManager = new AssetManager();
		weaponManager = new WeaponManager();

		menuScreen = new MenuScreen();
		setScreen(menuScreen);
	}

	public void setScreen(Class<? extends AbstractScreen> screenClazz) {
		Screen screen = screens.get(screenClazz);
		if (screen == null) {
			Field[] fields = getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equalsIgnoreCase(screenClazz.getSimpleName())) {
					try {
						AbstractScreen abstractScreen = screenClazz.getDeclaredConstructor().newInstance();
						field.set(this, abstractScreen);
						screens.put(screenClazz, abstractScreen);
					} catch (Exception e) {
						Gdx.app.error("Screen", "Can't create an instance of " + screenClazz.getSimpleName(), e);
					}
				}
			}
			screen = screens.get(screenClazz);
		}

		setScreen(screen);
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		menuScreen.dispose();
		screens.values().stream()
				.filter(Objects::nonNull)
				.forEach(Screen::dispose);
	}

	public static JellShock getInstance() {
		return instance;
	}
}
