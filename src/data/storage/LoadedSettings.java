package data.storage;

import data.readers.ParserHandler;
import data.readers.SettingsParser;
import error.ErrorHandler;
import error.exceptions.InvalidXMLException;

import java.io.FileNotFoundException;

public class LoadedSettings {
    private static String[] LOADED_HOTKEYS;
    private static String[] LAUNCH_SETTINGS;
    private static String[] OTHER_SETTINGS;

    private LoadedSettings() {}

    public static void loadAllSettings() {
        SettingsParser settingsParser = new SettingsParser();
        ParserHandler handler = new ParserHandler();
        try {
            handler.parse(settingsParser, System.getProperty("user.home") + "\\AppData\\Roaming" +
                    "\\Minesweeper\\Settings.cfg");
            LOADED_HOTKEYS = settingsParser.getHotkeys();
            LAUNCH_SETTINGS = settingsParser.getLaunchSettings();
            OTHER_SETTINGS = settingsParser.getSettings();
        } catch (FileNotFoundException e) {
            ErrorHandler.newExpectedExceptionAlert(e, "Critical Error!", true);
            ErrorHandler.forceExit();
        } catch (InvalidXMLException e) {
            ErrorHandler.newExpectedExceptionAlert(e, "Invalid File Format", true);
            ErrorHandler.forceExit();
        } catch (Exception e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, true);
            ErrorHandler.forceExit();
        }
    }

    public static String[] getLoadedHotkeys() {
        return LOADED_HOTKEYS;
    }

    public static String[] getLaunchSettings() {
        return LAUNCH_SETTINGS;
    }

    public static String[] getOtherSettings() {
        return OTHER_SETTINGS;
    }
}
