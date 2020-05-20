package data.writers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class HighScoreWriter {
    private static final String PATH = System.getProperty("user.home") + "\\appdata\\roaming" +
            "\\minesweeper\\";
    private static final String[] DEFAULT_NAMES = {"Player 1", "Player 2", "Player 3", "Player 4",
            "Player 5"};
    private static final int[] DEFAULT_SCORES = {500, 400, 300, 200, 100};

    public static void writeDefaults() {
        writeArray("Easy", DEFAULT_NAMES, DEFAULT_SCORES);
        writeArray("Intermediate", DEFAULT_NAMES, DEFAULT_SCORES);
        writeArray("Expert", DEFAULT_NAMES, DEFAULT_SCORES);
    }

    public static boolean writeScores(String difficulty, Map<String, Map<String, Object>> players) {
        writeMap(difficulty, players);
        return true;
    }

    public static boolean writeScores(String difficulty, String[] names, int[] scores) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                writeArray("Easy", names, scores);
                break;
            case "intermediate":
                writeArray("Intermediate", names, scores);
                break;
            case "expert":
                writeArray("Expert", names, scores);
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty argument!");
        }
        return true;
    }

    private static void writeArray(String difficulty, String[] names, int[] scores) {
        var array = new JSONArray();
        for (int i = 0; i < 5; i++) {
            var person = new JSONObject();
            person.put("id", names[i]);
            person.put("score", scores[i]);
            array.put(person);
        }
        var arrayWrapper = new JSONObject();
        arrayWrapper.put("players", array);
        arrayWrapper.put("difficulty", difficulty);
        var ending = new JSONObject();
        ending.put("highscores", arrayWrapper);
        try (FileWriter writer = new FileWriter(PATH + difficulty + ".mhs")) {
            writer.write(ending.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMap(String difficulty, Map<String, Map<String, Object>> players) {
        var array = new JSONArray();
//        for (var i = 0; i < 5)
    }
}
