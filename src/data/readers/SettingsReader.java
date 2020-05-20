package data.readers;

import game.Tile;
import org.json.JSONObject;
import validation.Verifier;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class SettingsReader {
    private File path;
    private int majorID;
    private int minorID;
    private int revision;
    private Map<String, Object> launchValues;
    private Map<String, String> hotkeys;
    private Map<String, Object> gameSettings;

    public SettingsReader(File path) {
        this.path = path;
    }

    public void parseFile() throws IOException {
        var in = new Scanner(path);
        var file = new StringBuilder();
        while (in.hasNextLine()) {
            file.append(in.nextLine());
        }
        var object = new JSONObject(file.toString());
        var settings = object.getJSONObject("settings");
        readVersion(settings);
        readLaunchSettings(settings);
        readHotkeys(settings);
        readSettings(settings);
    }

    public Map<String, Object> getLaunchValues() {
        return launchValues;
    }

    public Map<String, Object> getGameSettings() {
        return gameSettings;
    }

    public Map<String, String> getHotkeys() {
        return hotkeys;
    }

    public int getMajorID() {
        return majorID;
    }

    public int getMinorID() {
        return minorID;
    }

    public int getRevision() {
        return revision;
    }

    private void readSettings(JSONObject settings) {
        var localsettings = settings.getJSONObject("settings");
        var tileset = localsettings.getString("tileset");
        Tile.loadImages("/tilesets/" + tileset.toLowerCase() + "set/");
        gameSettings = new Hashtable<>(5);
        gameSettings.put("tileset", tileset);
        gameSettings.put("randomizer", localsettings.getString("randomizer"));
        gameSettings.put("savelogmax", localsettings.getInt("savelogmax"));
        gameSettings.put("logpath", localsettings.getString("logpath"));
    }

    private void readHotkeys(JSONObject settings) {
        var hotkey = settings.getJSONObject("hotkeys");
        var restart = hotkey.getString("restart").toUpperCase();
        var pause = hotkey.getString("pause").toUpperCase();
        var newgame = hotkey.getString("newgame").toUpperCase();
        var help = hotkey.getString("help").toUpperCase();
        var highscore = hotkey.getString("highscore").toUpperCase();
        var about = hotkey.getString("about").toUpperCase();
        Verifier.getInstance().validateHotkeys(restart, pause, newgame, help, highscore, about);
        hotkeys = new Hashtable<>(8);
        hotkeys.put("restart", restart);
        hotkeys.put("pause", pause);
        hotkeys.put("newgame", newgame);
        hotkeys.put("help", help);
        hotkeys.put("highscore", highscore);
        hotkeys.put("about", about);
    }

    private void readLaunchSettings(JSONObject settings) {
        var launch = settings.getJSONObject("launch");
        var width = launch.getInt("width");
        var height = launch.getInt("height");
        var mines = launch.getInt("mines");
        Verifier.getInstance().validateDimensions(width, height, mines);
        launchValues = new Hashtable<>(8);
        launchValues.put("setseed", launch.getBoolean("setseed"));
        launchValues.put("seed", launch.getLong("seed"));
        launchValues.put("width", width);
        launchValues.put("height", height);
        launchValues.put("mines", mines);
        launchValues.put("createlog", launch.getBoolean("createlog"));
    }

    private void readVersion(JSONObject settings) {
        var version = settings.getJSONObject("version");
        majorID = version.getInt("major");
        minorID = version.getInt("minor");
        revision = version.getInt("revision");
    }
}
