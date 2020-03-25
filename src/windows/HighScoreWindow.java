package windows;

import data.readers.HighScoreParser;
import data.readers.ParserHandler;
import error.ErrorHandler;

public class HighScoreWindow implements WindowHandler {
    private static final String FILEPATH = System.getProperty("user.home") + "\\AppData\\Roaming" +
            "\\Minesweeper\\";
    private static String[][] EASY_DATA;
    private static String[][] INTERMEDIATE_DATA;
    private static String[][] EXPERT_DATA;

    public static void initializeData() {
        try {
            ParserHandler handler = new ParserHandler();
            HighScoreParser parser = new HighScoreParser();
            handler.parse(parser, FILEPATH + "Easy.mhs");
            EASY_DATA = new String[][]{parser.getNames(), parser.getScores()};
            handler.parse(parser, FILEPATH + "Intermediate.mhs");
            INTERMEDIATE_DATA = new String[][]{parser.getNames(), parser.getScores()};
            handler.parse(parser, FILEPATH + "Expert.mhs");
            EXPERT_DATA = new String[][]{parser.getNames(), parser.getScores()};
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
