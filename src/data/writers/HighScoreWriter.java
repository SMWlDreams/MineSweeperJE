package data.writers;

import error.ErrorHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class HighScoreWriter {
    private static final String[] DEFAULT_NAMES = {"Player 1", "Player 2", "Player 3",
            "Player 4", "Player 5"};
    private static final int[] DEFAULT_SCORES = {500, 400, 300, 200, 100};
    private static final String FOLDER_PATH = System.getProperty("user.home") + "\\appdata" +
            "\\roaming\\minesweeper\\";
    private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String[] START_TAGS = {"<HighScores difficulty=\"", "<Player>", "<Name>",
            "<Score>"};
    private static final String[] END_TAGS = {"</HighScores>", "</Player>", "</Name>", "</Score>"};

    private HighScoreWriter() {}

    public static void writeDefaultScores() throws Exception {
        writeEasy(DEFAULT_NAMES, DEFAULT_SCORES);
        writeIntermediate(DEFAULT_NAMES, DEFAULT_SCORES);
        writeExpert(DEFAULT_NAMES, DEFAULT_SCORES);
    }

    public static void writeEasy(String[] names, int[] scores) throws Exception {
        if (names.length != 5 || scores.length != 5) {
            ErrorHandler.newExpectedExceptionAlert(
                    new IllegalArgumentException("Invalid length for array!"),
                    "High Score Error", true);
            return;
        }
        try (PrintWriter writer = new PrintWriter(new File(FOLDER_PATH + "Easy.mhs"))) {
            writer.println(HEADER);
            writer.println(START_TAGS[0] + "Easy\">");
            for (int i = 0; i < names.length; i++) {
                writer.println(START_TAGS[1]);
                writer.println(START_TAGS[2] + names[i] + END_TAGS[2]);
                writer.println(START_TAGS[3] + scores[i] + END_TAGS[3]);
                writer.println(END_TAGS[1]);
            }
            writer.println(END_TAGS[0]);
        } catch (IOException e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, true);
            throw e;
        }
    }

    public static void writeIntermediate(String[] names, int[] scores) throws Exception {
        if (names.length != 5 || scores.length != 5) {
            ErrorHandler.newExpectedExceptionAlert(
                    new IllegalArgumentException("Invalid length for array!"),
                    "High Score Error", true);
            return;
        }
        try (PrintWriter writer = new PrintWriter(new File(FOLDER_PATH + "Intermediate.mhs"))) {
            writer.println(HEADER);
            writer.println(START_TAGS[0] + "Intermediate\">");
            for (int i = 0; i < names.length; i++) {
                writer.println(START_TAGS[1]);
                writer.println(START_TAGS[2] + names[i] + END_TAGS[2]);
                writer.println(START_TAGS[3] + scores[i] + END_TAGS[3]);
                writer.println(END_TAGS[1]);
            }
            writer.println(END_TAGS[0]);
        } catch (IOException e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, true);
            throw e;
        }
    }

    public static void writeExpert(String[] names, int[] scores) throws Exception {
        if (names.length != 5 || scores.length != 5) {
            ErrorHandler.newExpectedExceptionAlert(
                    new IllegalArgumentException("Invalid length for array!"),
                    "High Score Error", true);
            return;
        }
        try (PrintWriter writer = new PrintWriter(new File(FOLDER_PATH + "Expert.mhs"))) {
            writer.println(HEADER);
            writer.println(START_TAGS[0] + "Expert\">");
            for (int i = 0; i < names.length; i++) {
                writer.println(START_TAGS[1]);
                writer.println(START_TAGS[2] + names[i] + END_TAGS[2]);
                writer.println(START_TAGS[3] + scores[i] + END_TAGS[3]);
                writer.println(END_TAGS[1]);
            }
            writer.println(END_TAGS[0]);
        } catch (IOException e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, true);
            throw e;
        }
    }
}