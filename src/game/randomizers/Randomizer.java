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
     * @param args  4 arguments as doubles: Number of columns, number of rows, tile dimension scale,
     *             number of mines
     */
    protected void generateBoard(double[] args) {
        board = new ArrayList<>();
        for (int i = 0; i < args[1]; i++) {
            List<Tile> tiles = new ArrayList<>();
            for (int j = 0; j < args[0]; j++) {
                tiles.add(new Tile(j, i, args[2]));
            }
            board.add(tiles);
        }
    }

    private void determineTimeNeighbors() {
        for (int i = 0; i < board.size(); i++) {
            List<Tile> tiles = board.get(i);
            for (int j = 0; j < tiles.size(); j++) {
                Tile t = tiles.get(j);
                if (!t.isMine()) {
                    t.determineNeighbors(board, tiles.size(), board.size(), j, i);
                }
            }
        }
    }

    public abstract void placeMines(double[] args);

    protected void placeMines(double[] args, Randomizer randomizer) {
        placeMines(args, randomizer, new int[] {32, 32});
    }

    public void placeMines(double[] args, Randomizer randomizer, int[] safeTile) {
        int i = 0;
        int x = (int)args[0];
        int y = (int)args[1];
        while (i < args[3]) {
            int[] coords = randomizer.getCoordinatePair(x, y);
            if (coords[0] != safeTile[0] || coords[1] != safeTile[1]) {
                Tile t = board.get(coords[1]).get(coords[0]);
                if (!t.isMine()) {
                    t.setMine();
                    i++;
                }
            }
        }
        determineTimeNeighbors();
    }

    public long getSeed() {
        return seed;
    }

    protected abstract int[] getCoordinatePair(int x, int y);

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
