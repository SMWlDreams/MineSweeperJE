import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public final class GenerateSettings {

    /**
     * The default settings strings in the event they need to be reset
     */
    public static final String[] DEFAULT_SETTINGS = {"Easy", "False", "Null", "Null", "False"};

    private static int ERROR_LEVEL = 0;
    private static final String[] FORMAT = {"<Launch>", "<SetSeed>", "<Seed>", "<Hash>", "<Logs>",
            "</Launch>", "</SetSeed>", "</Seed>", "</Hash>", "</Logs>"};
    private static final String[] HKFORMAT = {"<Restart>", "<Pause>", "<NewGame>", "<Help>",
            "<HighScores>", "<About>", "</Restart>", "</Pause>", "</NewGame>", "</Help>",
            "</HighScores>", "</About>"};
    private static final String[] DIFFICULTIES = {"Easy", "Intermediate", "Expert", "Custom"};

    /**
     * Generates a completely new file in the event it is missing or corrupted
     */
    public static void xmlGeneration() {
        try {
            PrintWriter writer = new PrintWriter("LaunchSettings.cfg");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
            writer.write("<LaunchSettings version=\"" + About.VERSION_ID + "\">\r\n");
            writer.write("    <LaunchSettings>\r\n");
            writer.write("        <Launch>" + DEFAULT_SETTINGS[0] + "</Launch>\r\n");
            writer.write("        <SetSeed>" + DEFAULT_SETTINGS[1] + "</SetSeed>\r\n");
            writer.write("        <Seed>" + DEFAULT_SETTINGS[2] + "</Seed>\r\n");
            writer.write("        <Hash>" + DEFAULT_SETTINGS[3] + "</Hash>\r\n");
            writer.write("        <Logs>" + DEFAULT_SETTINGS[4] + "</Logs>\r\n");
            writer.write("    </LaunchSettings>\r\n");
            writer.write("    <Hotkeys>\r\n");
            writer.write("        <Restart>" + Hotkeys.DEFAULT_HOTKEYS[0] + "</Restart>\r\n");
            writer.write("        <Pause>" + Hotkeys.DEFAULT_HOTKEYS[1] + "</Pause>\r\n");
            writer.write("        <NewGame>" + Hotkeys.DEFAULT_HOTKEYS[2] + "</NewGame>\r\n");
            writer.write("        <Help>" + Hotkeys.DEFAULT_HOTKEYS[3] + "</Help>\r\n");
            writer.write("        <HighScores>" + Hotkeys.DEFAULT_HOTKEYS[4] + "</HighScores>\r\n");
            writer.write("        <About>" + Hotkeys.DEFAULT_HOTKEYS[5] + "</About>\r\n");
            writer.write("    </Hotkeys>\r\n");
            writer.write("</LaunchSettings>");
            writer.close();
            ERROR_LEVEL = 0;
            LoadedSettings.load();
        } catch (IOException e) {
            System.out.println("Oops");
        }
    }

    /**
     * Updates the settings and hotkey in the config file
     * @param settings  The settings to be written to the file
     * @param hotkeys   The hotkeys to be written to the file
     */
    public static void updateSettings(String[] settings, String[] hotkeys) {
        try {
            PrintWriter writer = new PrintWriter("LaunchSettings.cfg");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
            writer.write("<LaunchSettings version=\"" + About.VERSION_ID + "\">\r\n");
            writer.write("    <LaunchSettings>\r\n");
            writer.write("        <Launch>" + settings[0] + "</Launch>\r\n");
            writer.write("        <SetSeed>" + settings[1] + "</SetSeed>\r\n");
            writer.write("        <Seed>" + settings[2] + "</Seed>\r\n");
            writer.write("        <Hash>" + settings[3] + "</Hash>\r\n");
            writer.write("        <Logs>" + settings[4] + "</Logs>\r\n");
            writer.write("    </LaunchSettings>\r\n");
            writer.write("    <Hotkeys>\r\n");
            writer.write("        <Restart>" + hotkeys[0] + "</Restart>\r\n");
            writer.write("        <Pause>" + hotkeys[1] + "</Pause>\r\n");
            writer.write("        <NewGame>" + hotkeys[2] + "</NewGame>\r\n");
            writer.write("        <Help>" + hotkeys[3] + "</Help>\r\n");
            writer.write("        <HighScores>" + hotkeys[4] + "</HighScores>\r\n");
            writer.write("        <About>" + hotkeys[5] + "</About>\r\n");
            writer.write("    </Hotkeys>\r\n");
            writer.write("</LaunchSettings>");
            writer.close();
            ERROR_LEVEL = 0;
            LoadedSettings.load();
        } catch (IOException e) {
            System.out.println("Oops");
        }
    }

    /**
     * Verifies the integrity of the LaunchSettings.cfg file. This method updates the ERROR_LEVEL based
     * on the result of the integrity check: 0 for pass, 1 for invalid settings, 2 for invalid
     * hotkeys, 3 for both or the header being invalid.
     * @return  True if and only if the file passes the integrity check
     */
    public static boolean verifyXML() {
        ERROR_LEVEL = 0;
        try (Scanner in = new Scanner(Paths.get("LaunchSettings.cfg"))) {
            String[] hk = new String[6];
            if (in.hasNextLine()) {
                if (in.nextLine().equalsIgnoreCase("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
                    if (in.hasNextLine()) {
                        if (!(in.nextLine().equalsIgnoreCase("<LaunchSettings version=\"" + About.VERSION_ID + "\">"))) {
                            if (rewriteFile()) {
                                return verifyXML();
                            } else {
                                ERROR_LEVEL = 3;
                                return false;
                            }
                        }
                    } else {
                        ERROR_LEVEL = 3;
                        return false;
                    }
                } else {
                    ERROR_LEVEL = 3;
                    return false;
                }
            } else {
                ERROR_LEVEL = 3;
                return false;
            }
            if (in.hasNextLine()) {
                if (in.nextLine().equalsIgnoreCase("    <LaunchSettings>")) {
                    int i = 0;
                    String s;
                    String dif = "";
                    String set = "";
                    String seed = "";
                    while (in.hasNextLine() && i < FORMAT.length/2) {
                        s = in.next();
                        if (s.substring(0, FORMAT[i].length()).equalsIgnoreCase(FORMAT[i]) &&
                        s.substring(s.length() - FORMAT[i + FORMAT.length/2].length()).equalsIgnoreCase(FORMAT[i + FORMAT.length/2])) {
                            if (i == 0) {
                                String c = s.substring(FORMAT[i].length(),
                                        s.length() - FORMAT[i + FORMAT.length/2].length());
                                boolean bool = false;
                                for (String v : DIFFICULTIES) {
                                    if (c.equalsIgnoreCase(v)) {
                                        bool = true;
                                        break;
                                    }
                                }
                                if (!bool) {
                                    ERROR_LEVEL = 1;
                                    i = FORMAT.length/2;
                                } else {
                                    dif = c;
                                }
                            } else if (i == 1 || i == 4) {
                                String c = s.substring(FORMAT[i].length(),
                                        s.length() - FORMAT[i + FORMAT.length/2].length());
                                if (!(c.equalsIgnoreCase("True")) && !(c.equalsIgnoreCase("False"))) {
                                    ERROR_LEVEL = 1;
                                    i = FORMAT.length/2;
                                }
                                if (i == 1) {
                                    if (c.equalsIgnoreCase("True")) {
                                        if (!(dif.equalsIgnoreCase(DIFFICULTIES[3]))) {
                                            ERROR_LEVEL = 1;
                                            i = FORMAT.length/2;
                                        } else {
                                            set = "True";
                                        }
                                    }
                                }
                            } else {
                                String c = s.substring(FORMAT[i].length(),
                                        s.length() - FORMAT[i + FORMAT.length/2].length());
                                if (i == 2) {
                                    if (!(c.equalsIgnoreCase("Null"))) {
                                        if (!(set.equalsIgnoreCase("True"))) {
                                            ERROR_LEVEL = 1;
                                            i = FORMAT.length/2;
                                        } else if (c.equalsIgnoreCase("0")) {
                                            ERROR_LEVEL = 1;
                                            i = FORMAT.length/2;
                                        }
                                    }
                                    seed = c;
                                } else {
                                    if (!(c.equalsIgnoreCase("Null"))) {
                                        if (set.equalsIgnoreCase("True")) {
                                            try {
                                                verify(seed + c);
                                            } catch (InvalidSeedException e) {
                                                ERROR_LEVEL = 1;
                                                i = FORMAT.length/2;
                                            }
                                        } else {
                                            try {
                                                verify("1" + c);
                                            } catch (InvalidSeedException e) {
                                                ERROR_LEVEL = 1;
                                                i = FORMAT.length/2;
                                            }
                                        }
                                    } else {
                                        if (set.equalsIgnoreCase("True")) {
                                            ERROR_LEVEL = 1;
                                            i = FORMAT.length/2;
                                        }
                                    }
                                }
                            }
                            i++;
                            in.nextLine();
                        } else {
                            ERROR_LEVEL = 1;
                            i = (FORMAT.length/2) + 1;
                        }
                    }
                    if (in.hasNextLine() && i <= FORMAT.length/2) {
                        if (!(in.nextLine().equalsIgnoreCase("    </LaunchSettings>"))) {
                            ERROR_LEVEL = 1;
                        }
                    }
                } else {
                    ERROR_LEVEL = 1;
                }
            } else {
                ERROR_LEVEL = 3;
                return false;
            }
            if (in.hasNextLine()) {
                boolean found = false;
                while (in.hasNextLine()) {
                    if (in.nextLine().equalsIgnoreCase("    <Hotkeys>")) {
                        found = true;
                        int i = 0;
                        String s;
                        while (in.hasNextLine() && i < HKFORMAT.length / 2) {
                            s = in.next();
                            if (s.substring(0, HKFORMAT[i].length()).equalsIgnoreCase(HKFORMAT[i]) &&
                                    s.substring(HKFORMAT[i].length() + 1).equalsIgnoreCase(HKFORMAT[i + HKFORMAT.length / 2])) {
                                hk[i] = s.substring(HKFORMAT[i].length(), HKFORMAT[i].length() + 1);
                                i++;
                                in.nextLine();
                            } else {
                                if (ERROR_LEVEL == 1) {
                                    ERROR_LEVEL = 3;
                                    return false;
                                } else {
                                    ERROR_LEVEL = 2;
                                    i = (FORMAT.length / 2) + 1;
                                }
                            }
                        }
                        if (in.hasNextLine() && i <= HKFORMAT.length / 2) {
                            if (!(in.nextLine().equalsIgnoreCase("    </Hotkeys>"))) {
                                if (ERROR_LEVEL == 1) {
                                    ERROR_LEVEL = 3;
                                    return false;
                                } else {
                                    ERROR_LEVEL = 2;
                                }
                            }
                            break;
                        }
                    }
                }
                if (!found) {
                    if (ERROR_LEVEL == 1) {
                        ERROR_LEVEL = 3;
                        return false;
                    } else {
                        ERROR_LEVEL = 2;
                    }
                }
            } else if (ERROR_LEVEL == 1) {
                ERROR_LEVEL = 3;
                return false;
            } else {
                ERROR_LEVEL = 2;
            }
            boolean bool = false;
            while (in.hasNextLine()) {
                if (in.nextLine().equalsIgnoreCase("</LaunchSettings>")) {
                    bool = true;
                    break;
                }
            }
            if (!bool) {
                ERROR_LEVEL = 3;
                return false;
            }
            verify(hk);
            if (ERROR_LEVEL != 0) {
                return false;
            }
        } catch (IOException e) {
            ERROR_LEVEL = 3;
            return false;
        } catch (StringIndexOutOfBoundsException e) {
            if (ERROR_LEVEL == 1) {
                ERROR_LEVEL = 3;
                return false;
            } else {
                ERROR_LEVEL = 2;
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the current error level set by the verify() method
     * @return  ERROR_LEVEL
     */
    public static int getErrorLevel() {
        return ERROR_LEVEL;
    }

    private static boolean rewriteFile() {
        try {
            Scanner in = new Scanner(Paths.get("LaunchSettings.cfg"));
            String[] lines = new String[17];
            lines[0] = in.nextLine() + "\r\n";
            String s = in.nextLine();
            s = s.substring(19, s.length() - 2);
            if (!(Integer.parseInt(s.substring(0, 1)) <= About.MAJOR_REVISION)) {
                return false;
            }
            if (!(Integer.parseInt(s.substring(2, 3)) <= About.MINOR_REVISION)) {
                return false;
            }
            if (!(Integer.parseInt(s.substring(4)) <= About.SUB_REVISION)) {
                return false;
            }
            for (int i = 1; i < lines.length; i++) {
                lines[i] = in.nextLine() + "\r\n";
            }
            in.close();
            PrintWriter writer = new PrintWriter("LaunchSettings.cfg");
            writer.write(lines[0]);
            writer.write("<LaunchSettings version=\"" + About.VERSION_ID + "\">\r\n");
            for (int i = 1; i < lines.length; i++) {
                writer.write(lines[i]);
            }
            writer.close();
            ERROR_LEVEL = 0;
            return true;
        } catch (IOException e) {
            System.out.println("Oops");
            return false;
        }
    }

    private static void verify(String seed) {
        String hash = "";
        for (int i = 0; i < seed.length(); i++) {
            if (seed.charAt(i) == 't') {
                if (i == 0) {
                    throw new InvalidSeedException("No RNG seed provided to populate the board");
                }
                hash = seed.substring(i);
                break;
            }
        }
        if (hash.length() != 9) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
        String start = hash.substring(0, 2);
        String height = hash.substring(2, 4);
        String width = hash.substring(4, 6);
        String mines = hash.substring(6, 9);
        if (!(start.equalsIgnoreCase("t4"))) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
        if (!(Integer.parseInt(height) >= 5) || !(Integer.parseInt(height) <= 24)) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
        if (!(Integer.parseInt(width) >= 5) || !(Integer.parseInt(width) <= 30)) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
        if (!(Integer.parseInt(mines) >= 0) ||
                !(Integer.parseInt(mines) <= (Integer.parseInt(height) * Integer.parseInt(width) - 1))) {
            throw new InvalidSeedException("Invalid seed ending hash " + hash + ".");
        }
    }

    private static void verify(String[] hotkeys) {
        if (!(verify(hotkeys, 0))) {
            throw new StringIndexOutOfBoundsException("Invalid hotkey");
        }
    }

    private static boolean verify(String[] hotkeys, int searchIndex) {
        String s = hotkeys[searchIndex];
        if (s.equalsIgnoreCase(" ")) {
            return false;
        }
        switch (searchIndex) {
            case 0:
                if (s.equalsIgnoreCase(hotkeys[1])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[2])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[3])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[4])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 1:
                if (s.equalsIgnoreCase(hotkeys[2])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[3])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[4])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 2:
                if (s.equalsIgnoreCase(hotkeys[3])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[4])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 3:
                if (s.equalsIgnoreCase(hotkeys[4])) {
                    return false;
                } else if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 4:
                if (s.equalsIgnoreCase(hotkeys[5])) {
                    return false;
                }
                break;
            case 5:
                return true;
            default:
                return false;
        }
        return (verify(hotkeys, ++searchIndex));
    }
}
