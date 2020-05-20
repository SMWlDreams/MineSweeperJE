package windows;

import data.readers.HighScoreReader;
import error.ErrorHandler;

import java.nio.file.Paths;
import java.util.Map;

public class HighScoreWindow extends Thread implements WindowHandler {
    private static final String FILEPATH = System.getProperty("user.home") + "\\AppData\\Roaming" +
            "\\Minesweeper\\";
    private static Map<String, Map<String, Object>> EASY_DATA;
    private static Map<String, Map<String, Object>> INTERMEDIATE_DATA;
    private static Map<String, Map<String, Object>> EXPERT_DATA;

    @Override
    public void run() {
        initializeData();
    }

    public static void initializeData() {
        try {
            var handler = new HighScoreReader(Paths.get(FILEPATH));
            handler.parseScoreFiles();
            EASY_DATA = handler.getEasy();
            INTERMEDIATE_DATA = handler.getIntermediate();
            EXPERT_DATA = handler.getExpert();
        } catch (Exception e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, true);
        }
    }

    @Override
    public void launch(String[] args) {

    }

    @Override
    public void start() {

    }
}
