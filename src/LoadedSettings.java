import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public final class LoadedSettings {
    private static String[] hotkeys;
    private static String[] launchSettings;
    private static String[] logSettings;
    private static int errorLevel;

    /**
     * Loads the contents of the Settings.cfg file and stores them in their individual arrays
     * @throws IOException              If the file is unable to be found
     * @throws InvalidSettingsException If any part of the settings file is invalid
     */
    public static void load() throws IOException, InvalidSettingsException {
        Scanner in = new Scanner(Paths.get("Settings.cfg"));
        if (!GenerateSettings.verifyXML()) {
            errorLevel = GenerateSettings.getErrorLevel();
            if (errorLevel == 1) {
                while (in.hasNextLine()) {
                    if (in.nextLine().equalsIgnoreCase("    <Hotkeys>")) {
                        loadHotkeySettings(in);
                        throw new InvalidSettingsException("Settings file is invalid");
                    }
                }
            } else if (errorLevel == 2) {
                loadLaunchSettings(in);
                throw new InvalidSettingsException("Settings file is invalid");
            } else if (errorLevel == 3) {
                throw new InvalidSettingsException("Settings file is invalid");
            }
        }
        loadHotkeySettings(loadLaunchSettings(in));
    }

    public static void generateNewFile() {
    }

    public static int getErrorLevel() {
        return errorLevel;
    }

    public static String[] getLogSettings() {
        return logSettings;
    }

    public static String[] getHotkeys() {
        return hotkeys;
    }

    public static String[] getLaunchSettings() {
        return launchSettings;
    }

    private static Scanner loadLaunchSettings(Scanner in) {
        launchSettings = new String[5];
        in.nextLine();
        in.nextLine();
        in.nextLine();
        String s = in.next();
        launchSettings[0] = s.substring(8, s.length() - 9);
        in.nextLine();
        s = in.next();
        launchSettings[1] = s.substring(9, s.length() - 10);
        in.nextLine();
        s = in.next();
        launchSettings[2] = s.substring(6, s.length() - 7);
        in.nextLine();
        s = in.next();
        launchSettings[3] = s.substring(6, s.length() - 7);
        in.nextLine();
        s = in.next();
        launchSettings[4] = s.substring(6, s.length() - 7);
        in.nextLine();
        in.nextLine();
        in.nextLine();
        return in;
    }

    private static void loadHotkeySettings(Scanner in) {
        hotkeys = new String[6];
        hotkeys[0] = Character.toString(in.next().toUpperCase().charAt(9));
        in.nextLine();
        hotkeys[1] = Character.toString(in.next().toUpperCase().charAt(7));
        in.nextLine();
        hotkeys[2] = Character.toString(in.next().toUpperCase().charAt(9));
        in.nextLine();
        hotkeys[3] = Character.toString(in.next().toUpperCase().charAt(6));
        in.nextLine();
        hotkeys[4] = Character.toString(in.next().toUpperCase().charAt(12));
        in.nextLine();
        hotkeys[5] = Character.toString(in.next().toUpperCase().charAt(7));
    }
}
