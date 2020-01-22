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
     * @param columns   Number of columns to place on the board
     * @param rows      Number of rows to place on the board
     * @param scale     The tile size multiplication scale
     */
    public void generateBoard(int columns, int rows, double scale) {
        board = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Tile> tiles = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                tiles.add(new Tile(j, i, scale));
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

    public abstract void placeMines(int columns, int rows, int mines, List<List<Tile>> board);

    public abstract void placeMines(int columns, int rows, int mines, List<List<Tile>> board,
                                    int[] safeTile);

    protected void placeMines(int columns, int rows, int mines, List<List<Tile>> board, Randomizer randomizer) {
        placeMines(columns, rows, mines, board, randomizer, new int[] {32, 32});
    }

    protected void placeMines(int columns, int rows, int mines, List<List<Tile>> board, Randomizer randomizer, int[] safeTile) {
        int i = 0;
        while (i < mines) {
            int[] coords = randomizer.getCoordinatePair(columns, rows);
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
