package data.storage;

import data.readers.SettingsReader;
import data.writers.SettingsWriter;
import error.ErrorHandler;
import error.exceptions.InvalidXMLException;

import java.io.File;
import java.util.Map;

public class LoadedSettings extends Thread {
    private static Map<String, String> LOADED_HOTKEYS;
    private static Map<String, Object> LAUNCH_SETTINGS;
    private static Map<String, Object> OTHER_SETTINGS;

    public LoadedSettings() {}

    @Override
    public void run() {
        loadAllSettings();
    }

    public static void loadAllSettings() {
        var reader = new SettingsReader(new File(System.getProperty("user.home") +
                "\\AppData\\Roaming\\Minesweeper\\Settings.cfg"));
        try {
            reader.parseFile();
            LOADED_HOTKEYS = reader.getHotkeys();
            LAUNCH_SETTINGS = reader.getLaunchValues();
            OTHER_SETTINGS = reader.getGameSettings();
        } catch (InvalidXMLException e) {
            ErrorHandler.newExpectedExceptionAlert(e, "Invalid File Format", true);
//            SettingsWriter.writeDefaultSettings();
        } catch (Exception e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, true);
            ErrorHandler.forceExit();
        }
    }

    public static Map<String, String> getLoadedHotkeys() {
        return LOADED_HOTKEYS;
    }

    public static Map<String, Object> getLaunchSettings() {
        return LAUNCH_SETTINGS;
    }

    public static Map<String, Object> getOtherSettings() {
        return OTHER_SETTINGS;
    }
}
