package data.writers;

import error.ErrorHandler;
import windows.AboutWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SettingsWriter {
    private static String filename;
    private static final String[] DEFAULT_LAUNCH_SETTINGS = {"False", "Null", "10", "10", "False"};
    private static final String[] DEFAULT_HOTKEYS = {"R", "P", "N", "H", "S", "A"};
    private static final String[] DEFAULT_SETTINGS = {"Default", "Default", "0", "default"};
    private static final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String[] START_TAGS = {"<Settings version=\"" + AboutWindow.VERSION_ID + "\">",
            "<Launch>", "<SetSeed>", "<Seed>", "<Width>", "<Height>", "<Log>", "<Hotkeys>",
            "<Restart>", "<Pause>", "<NewGame>", "<Help>", "<HighScore>", "<About>",
            "<GameSettings>", "<Tileset>", "<Randomizer>", "<NumLogs>", "<LogPath>"};
    private static final String[] END_TAGS = {"</Settings>", "</Launch>", "</SetSeed>", "</Seed>",
            "</Width>", "</Height>", "</Log>", "</Hotkeys>", "</Restart>", "</Pause>", "</NewGame>",
            "</Help>", "</HighScore>", "</About>", "</GameSettings>", "</Tileset>", "</Randomizer>",
            "</NumLogs>", "</LogPath>"};

    private SettingsWriter() {}

    public static void setFilename(String file) {
        filename = file;
    }

    public static void writeSettings(String[] launchSettings, String[] hotkeys, String[] settings) {
        if (filename == null) {
            throw new IllegalStateException("Must set filename before invoking this method");
        }
        writeAllSettings(launchSettings, hotkeys, settings);
    }

    public static void updateSettings() {

    }

    public static void writeDefaultSettings() {

    }

    private static void writeAllSettings(String[] launchSettings, String[] hotkeys, String[] settings) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println(HEAD);
            writer.println(START_TAGS[0]);
            writer.println(START_TAGS[1]);
            writer.print(START_TAGS[2]);
            writer.print(launchSettings[0]);
            writer.println(END_TAGS[2]);
            writer.print(START_TAGS[3]);
            writer.print(launchSettings[1]);
            writer.println(END_TAGS[3]);
            writer.print(START_TAGS[4]);
            writer.print(launchSettings[2]);
            writer.println(END_TAGS[4]);
            writer.print(START_TAGS[5]);
            writer.print(launchSettings[3]);
            writer.println(END_TAGS[5]);
            writer.print(START_TAGS[6]);
            writer.print(launchSettings[4]);
            writer.println(END_TAGS[6]);
            writer.println(END_TAGS[1]);
            writer.println(START_TAGS[7]);
            writer.print(START_TAGS[8]);
            writer.print(hotkeys[0]);
            writer.println(END_TAGS[8]);
            writer.print(START_TAGS[9]);
            writer.print(hotkeys[1]);
            writer.println(END_TAGS[9]);
            writer.print(START_TAGS[10]);
            writer.print(hotkeys[2]);
            writer.println(END_TAGS[10]);
            writer.print(START_TAGS[11]);
            writer.print(hotkeys[3]);
            writer.println(END_TAGS[11]);
            writer.print(START_TAGS[12]);
            writer.print(hotkeys[4]);
            writer.println(END_TAGS[12]);
            writer.print(START_TAGS[13]);
            writer.print(hotkeys[5]);
            writer.println(END_TAGS[13]);
            writer.println(END_TAGS[7]);
            writer.println(START_TAGS[14]);
            writer.print(START_TAGS[15]);
            writer.print(settings[0]);
            writer.println(END_TAGS[15]);
            writer.print(START_TAGS[16]);
            writer.print(settings[1]);
            writer.println(END_TAGS[16]);
            writer.print(START_TAGS[17]);
            writer.print(settings[2]);
            writer.println(END_TAGS[17]);
            writer.print(START_TAGS[18]);
            writer.print(settings[3]);
            writer.println(END_TAGS[18]);
            writer.println(END_TAGS[14]);
            writer.println(END_TAGS[0]);
        } catch (FileNotFoundException e) {
            File f = new File(filename);
            if (f.mkdirs()) {
                writeAllSettings(launchSettings, hotkeys, settings);
            } else {
                ErrorHandler.newExpectedExceptionAlert(new FileNotFoundException("Unable to write to " +
                        "file\n" + filename), "File Write Error!");
            }
        }
    }
}
