package game.randomizers;

import game.Tile;

import java.util.List;
import java.util.Random;

public class SkipRandomizer extends Randomizer {
    private Random random;

    public SkipRandomizer() {
        super();
    }

    public SkipRandomizer(long seed) {
        this.seed = seed;
    }

    /**
     * Generates a board from the given arguments
     * @param columns   Number of columns to place on the board
     * @param rows      Number of rows to place on the board
     * @param scaleX    The vertical tile size multiplication scale
     * @param scaleY    The horizontal tile size multiplication scale
     */
    public void generateBoard(int columns, int rows, double scaleX, double scaleY) {
        random = new Random(seed);
        super.generateBoard(columns, rows, scaleX, scaleY);
    }

    @Override
    public void placeMines(int columns, int rows, int mines, List<List<Tile>> board) {
        placeMines(columns, rows, mines, board, this);
    }

    @Override
    public void placeMines(int columns, int rows, int mines, List<List<Tile>> board, int[] safeTile) {
        placeMines(columns, rows, mines, board, this, safeTile);
    }

    @Override
    protected int[] getCoordinatePair(int x, int y) {
        int[] i = new int[2];
        i[0] = random.nextInt(x);
        random.nextInt(x);
        i[1] = random.nextInt(y);
        random.nextInt(y);
        return i;
    }
}