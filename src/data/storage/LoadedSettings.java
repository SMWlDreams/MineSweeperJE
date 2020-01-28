package data.storage;

import data.readers.SettingsParser;

public class LoadedSettings {
    private static String[] LOADED_HOTKEYS;
    private static String[] LAUNCH_SETTINGS;
    private static String[] OTHER_SETTINGS;

    public static void loadAllSettings() {
        SettingsParser settingsParser = new SettingsParser();
    }
}
