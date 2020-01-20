package game.randomizers;

import game.Tile;

import java.util.ArrayList;
import java.util.List;

public abstract class Randomizer {
    protected List<List<Tile>> board;
    protected long seed;

    protected Randomizer() {
        seed = System.nanoTime();
    }

    public List<List<Tile>> getBoard() {
        return board;
    }

    /**
     * Generates a board from the given arguments
     * @param args  4 arguments as double: Number of columns, number of rows, tile dimension scale,
     *             number of mines
     */
    public abstract void generateBoard(double[] args);

    /**
     * Generates a board from the given arguments
     * @param args  4 arguments as doubles: Number of columns, number of rows, tile dimension scale,
     *             number of mines
     * @param randomizer    A reference to the underlying randomizer
     */
    protected void generateBoard(double[] args, Randomizer randomizer) {
        for (int i = 0; i < args[0]; i++) {
            List<Tile> tiles = new ArrayList<>();
            for (int j = 0; j < args[1]; j++) {
                tiles.add(new Tile(i, j, args[2]));
            }
            board.add(tiles);
        }
        int i = 0;
        int x = (int)args[0];
        int y = (int)args[1];
        while (i < args[3]) {
            int[] coords = randomizer.getCoordinatePair(x, y);
            Tile t = board.get(coords[0]).get(coords[1]);
            if (!t.isMine()) {
                t.setMine();
                i++;
            }
        }
    }

    public long getSeed() {
        return seed;
    }

    protected abstract int[] getCoordinatePair(int x, int y);

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
