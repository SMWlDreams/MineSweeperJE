package game.randomizers;

import game.Tile;

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

    public abstract void generateBoard(int[] args);

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
