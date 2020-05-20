//package data.writers;
//
//import error.ErrorHandler;
//import windows.AboutWindow;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//
//public class SettingsWriter {
//    private static final String FILENAME = System.getProperty("user.home") + "\\AppData\\Roaming" +
//            "\\Minesweeper\\";
//    private static final String[] DEFAULT_LAUNCH_SETTINGS = {"False", "Null", "10", "10", "10",
//            "False"};
//    private static final String[] DEFAULT_HOTKEYS = {"R", "P", "N", "H", "S", "A"};
//    private static final String[] DEFAULT_SETTINGS = {"Default", "Default", "0", "default"};
//    private static final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//    private static final String[] START_TAGS = {"<Settings version=\"" + AboutWindow.VERSION_ID + "\">",
//            "<Launch>", "<SetSeed>", "<Seed>", "<Width>", "<Height>", "<Mine>", "<Log>", "<Hotkeys>",
//            "<Restart>", "<Pause>", "<NewGame>", "<Help>", "<HighScore>", "<About>",
//            "<GameSettings>", "<Tileset>", "<Randomizer>", "<NumLogs>", "<LogPath>"};
//    private static final String[] END_TAGS = {"</Settings>", "</Launch>", "</SetSeed>", "</Seed>",
//            "</Width>", "</Height>", "</Mine>", "</Log>", "</Hotkeys>", "</Restart>", "</Pause>",
//            "</NewGame>", "</Help>", "</HighScore>", "</About>", "</GameSettings>", "</Tileset>",
//            "</Randomizer>", "</NumLogs>", "</LogPath>"};
//
//    private SettingsWriter() {}
//
//    public static void writeSettings(String[] launchSettings, String[] hotkeys, String[] settings) {
//        writeAllSettings(launchSettings, hotkeys, settings);
//    }
//
//    public static void writeDefaultSettings() {
//        writeAllSettings(DEFAULT_LAUNCH_SETTINGS, DEFAULT_HOTKEYS, DEFAULT_SETTINGS);
//    }
//
//    private static void writeAllSettings(String[] launchSettings, String[] hotkeys, String[] settings) {
//        try (PrintWriter writer = new PrintWriter(new File(FILENAME + "Settings.cfg"))) {
//            writer.println(HEAD);
//            writer.println(START_TAGS[0]);
//            writer.println(START_TAGS[1]);
//            for (int i = 2; i <= 7; i++) {
//                writer.print(START_TAGS[i]);
//                writer.print(launchSettings[i - 2]);
//                writer.println(END_TAGS[i]);
//            }
//            writer.println(END_TAGS[1]);
//            writer.println(START_TAGS[8]);
//            for (int i = 9; i <= 14; i++) {
//                writer.print(START_TAGS[i]);
//                writer.print(hotkeys[i - 9]);
//                writer.println(END_TAGS[i]);
//            }
//            writer.println(END_TAGS[8]);
//            writer.println(START_TAGS[15]);
//            for (int i = 16; i <= 19; i++) {
//                writer.print(START_TAGS[i]);
//                writer.print(settings[i - 16]);
//                writer.println(END_TAGS[i]);
//            }
//            writer.println(END_TAGS[15]);
//            writer.println(END_TAGS[0]);
//        } catch (FileNotFoundException e) {
//            File f = new File(FILENAME);
//            if (f.mkdirs()) {
//                writeAllSettings(launchSettings, hotkeys, settings);
//            } else {
//                ErrorHandler.newExpectedExceptionAlert(new FileNotFoundException("Unable to write to " +
//                        "file\n" + FILENAME), "File Write Error!", true);
//            }
//        }
//    }
//}
