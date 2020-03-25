package game.randomizers;

import game.Tile;

import java.util.List;
import java.util.Random;

public class DoubleSeededRandomizer extends Randomizer {
    private Random seed1;
    private Random seed2;
    private long secondSeed;

    public DoubleSeededRandomizer() {
        super();
        secondSeed = seed % Integer.MAX_VALUE;
        if (secondSeed == 0) {
            secondSeed = Integer.MAX_VALUE;
        }
        while (seed == secondSeed) {
            secondSeed %= (secondSeed/2);
        }
    }

    @Override
    public void placeMines(int columns, int rows, int mines, List<List<Tile>> board) {
        placeMines(columns, rows, mines, board, this);
    }

    @Override
    public void placeMines(int columns, int rows, int mines, List<List<Tile>> board, int[] safeTile) {
        placeMines(columns, rows, mines, board, this, safeTile);
    }

    public DoubleSeededRandomizer(long seed) {
        this.seed = seed;
        secondSeed = seed % Integer.MAX_VALUE;
        if (secondSeed == 0) {
            secondSeed = Integer.MAX_VALUE;
        }
        while (seed == secondSeed) {
            secondSeed %= (secondSeed/2);
        }
    }

    /**
     * Generates a board from the given arguments
     * @param columns   Number of columns to place on the board
     * @param rows      Number of rows to place on the board
     * @param scaleX    The vertical tile size multiplication scale
     * @param scaleY    The horizontal tile size multiplication scale
     */
    public void generateBoard(int columns, int rows, double scaleX, double scaleY) {
        seed1 = new Random(seed);
        seed2 = new Random(secondSeed);
        super.generateBoard(columns, rows, scaleX, scaleY);
    }

    @Override
    protected int[] getCoordinatePair(int x, int y) {
        return new int[]{seed1.nextInt(x), seed2.nextInt(y)};
    }
}
