package data.readers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HighScoreReader {
    private Path path;
    private Map<String, Map<String, Object>> easy;
    private Map<String, Map<String, Object>> intermediate;
    private Map<String, Map<String, Object>> expert;

    public HighScoreReader(Path folderPath) {
        path = folderPath;
    }

    public void parseScoreFiles() throws IOException {
        parseEasyFile();
        parseIntermediateFile();
        parseExpertFile();
    }

    public Map<String, Map<String, Object>> getEasy() {
        return easy;
    }

    public Map<String, Map<String, Object>> getIntermediate() {
        return intermediate;
    }

    public Map<String, Map<String, Object>> getExpert() {
        return expert;
    }

    private void parseExpertFile() throws IOException {
        var in = new Scanner(Paths.get(path + "\\Expert.mhs"));
        var rawJSON = new StringBuilder();
        while (in.hasNextLine()) {
            rawJSON.append(in.nextLine());
        }
        expert = new HashMap<>(7);
        var file = new JSONObject(rawJSON.toString());
        var scores = file.getJSONObject("highscores");
        var players = scores.getJSONArray("players");
        addPlayersToMap(players, expert);
    }

    private void parseIntermediateFile() throws IOException {
        var in = new Scanner(Paths.get(path + "\\Intermediate.mhs"));
        var rawJSON = new StringBuilder();
        while (in.hasNextLine()) {
            rawJSON.append(in.nextLine());
        }
        intermediate = new HashMap<>(7);
        var file = new JSONObject(rawJSON.toString());
        var scores = file.getJSONObject("highscores");
        var players = scores.getJSONArray("players");
        addPlayersToMap(players, intermediate);
    }

    private void parseEasyFile() throws IOException {
        var in = new Scanner(Paths.get(path + "\\Easy.mhs"));
        var rawJSON = new StringBuilder();
        while (in.hasNextLine()) {
            rawJSON.append(in.nextLine());
        }
        easy = new HashMap<>(7);
        var file = new JSONObject(rawJSON.toString());
        System.out.println(file.toString());
        var scores = file.getJSONObject("highscores");
        var players = scores.getJSONArray("players");
        addPlayersToMap(players, easy);
    }

    private static void addPlayersToMap(JSONArray players, Map<String, Map<String, Object>> map) {
        for (var i = 0; i < players.length(); ++i) {
            map.put("Player" + i, players.getJSONObject(i).toMap());
        }
    }
}
