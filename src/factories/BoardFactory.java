package factories;

import data.storage.LoadedSettings;
import data.writers.SettingsWriter;
import error.ErrorHandler;
import error.exceptions.InvalidDimensionException;
import game.Board;
import game.Tile;
import game.randomizers.Randomizer;
import javafx.scene.layout.Pane;

public class BoardFactory {
    private static String DIFFICULTY;

    private BoardFactory() {}

    public static Board generateInitialBoard(Pane pane) {
        String[] settings = LoadedSettings.getLaunchSettings();
        String[] otherSettings = LoadedSettings.getOtherSettings();
        long seed;
        int width = Integer.parseInt(settings[2]);
        int height = Integer.parseInt(settings[3]);
        int mines = Integer.parseInt(settings[4]);
        if (!verifyDimensions(width, height, mines)) {
            ErrorHandler.newExpectedExceptionAlert(new InvalidDimensionException("Inavlid " +
                    "Dimensions for Starting Board!"), "Settings Error", true);
            SettingsWriter.writeDefaultSettings();
            LoadedSettings.loadAllSettings();
            generateInitialBoard(pane);
        }
        if (!settings[0].equalsIgnoreCase("False")) {
            seed = Long.parseLong(settings[1]);
            DIFFICULTY = "Custom";
        } else {
            seed = System.nanoTime();
            if (width == 9 && height == 9 && mines == 10) {
                DIFFICULTY = "Easy";
            } else if (width == 16 && height == 16 && mines == 40) {
                DIFFICULTY = "Intermediate";
            } else if (width == 24 && height == 24 && mines == 99) {
                DIFFICULTY = "Expert";
            } else {
                DIFFICULTY = "Custom";
            }
        }
        double[] scale = determineScale(width, height, pane);
        Tile.loadImages("\\tilesets\\" + settings[0] + "set\\");
        Randomizer randomizer = RandomizerFactory.getRandomizer(otherSettings[1], seed);
        Board board = new Board(width, height, mines, scale[1], scale[0], pane, randomizer);

    }

    private static double[] determineScale(int width, int height, Pane pane) {
        double boardDimension = pane.getWidth();
        double relativeWidth = boardDimension/(width * Tile.DEFAULT_TILE_SIZE);
        double relativeHeight = boardDimension/(height * Tile.DEFAULT_TILE_SIZE);
        return new double[] {relativeWidth, relativeHeight};
    }

    private static boolean verifyDimensions(int width, int height, int mines) {
        if (width >= 31 || width <= 5) {
            return false;
        }
        if (height >= 25 || height <= 5) {
            return false;
        }
        return mines > 0 && mines < height * width;
    }

    public static String getDifficulty() {
        return DIFFICULTY;
    }
}
