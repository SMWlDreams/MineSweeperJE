package factories;

import data.storage.LoadedSettings;
import game.Board;
import game.Tile;
import game.randomizers.Randomizer;
import javafx.scene.layout.Pane;

public class BoardFactory {
    private static String DIFFICULTY;

    private BoardFactory() {}

    public static Board generateInitialBoard(Pane pane) {
        var settings = LoadedSettings.getLaunchSettings();
        var otherSettings = LoadedSettings.getOtherSettings();
        long seed;
        int width = (Integer)settings.get("width");
        int height = (Integer)settings.get("height");
        int mines = (Integer)settings.get("mines");
        if ((Boolean)settings.get("setseed")) {
            seed = (Long)settings.get("seed");
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
        Randomizer randomizer = RandomizerFactory.getRandomizer((String)otherSettings.get(
                "randomizer"), seed);
        Board board = new Board(width, height, mines, scale[1], scale[0], pane, randomizer);
        return board;
    }

    private static double[] determineScale(int width, int height, Pane pane) {
        double boardDimension = pane.getWidth();
        double relativeWidth = boardDimension/(width * Tile.DEFAULT_TILE_SIZE);
        double relativeHeight = boardDimension/(height * Tile.DEFAULT_TILE_SIZE);
        return new double[] {relativeWidth, relativeHeight};
    }

    public static String getDifficulty() {
        return DIFFICULTY;
    }
}
