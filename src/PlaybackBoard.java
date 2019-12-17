import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Board object for log file playback
 */
public class PlaybackBoard extends Board {
    private List<List<Tile>> tiles = new ArrayList<>();
    private int rows;
    private int columns;
    private int scaleMultiplier;

    /**
     * Creates a new playback board from the specified dimensions and seed
     * @param width         Width of the board
     * @param height        Height of the board
     * @param mines         Number of mine on the board
     * @param pane          Pane to draw tiles to
     * @param multiplier    The scale multiplier for the tiles
     * @param seed          The RNG seed to populate this board with
     */
    public PlaybackBoard(int width, int height, int mines, Pane pane, int multiplier, long seed) {
        scaleMultiplier = multiplier;
        createBoard(height, width);
        rows = height;
        columns = width;
        initMines(mines, seed);
        setNeighbors();
        for (List<Tile> l: tiles) {
            for (Tile t: l) {
                pane.getChildren().add(t);
            }
        }
    }

    /**
     * Clears the tiles off of this board
     * @param pane  The pane the mines are drawn to
     */
    @Override
    public void clear(Pane pane) {
        for (List<Tile> tile : tiles) {
            pane.getChildren().removeAll(tile);
        }
    }

    /**
     * Gets the number of rows in this board
     * @return  Number of rows as an int
     */
    @Override
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns in this board
     * @return  Number of columns as an int
     */
    @Override
    public int getColumns() {
        return columns;
    }

    /**
     * Selects the tile and, if the tile has no adjacent mines, reveals all tiles around the
     * current tile
     * @param x             X coordinate of the tile
     * @param y             Y coordinate of the tile
     * @param moveSelected  The move this tile was selected on, used for deselection
     */
    public void selectTile(int x, int y, int moveSelected) {
        Tile t = tiles.get(y).get(x);
        if (t.isFlagged() || t.isSelected()) {
            return;
        }
        t.setMoveSelected(moveSelected);
        if (t.onClick() == 0) {
            if (x - 1 >= 0 && y - 1 >= 0) {
                selectTile(x - 1, y - 1, moveSelected);
            }
            if (x - 1 >= 0) {
                selectTile(x - 1, y, moveSelected);
            }
            if (x - 1 >= 0 && y + 1 < rows) {
                selectTile(x - 1, y + 1, moveSelected);
            }
            if (y - 1 >= 0) {
                selectTile(x, y - 1, moveSelected);
            }
            if (y + 1 < rows) {
                selectTile(x, y + 1,moveSelected);
            }
            if (x + 1 < columns && y - 1 >= 0) {
                selectTile(x + 1, y - 1, moveSelected);
            }
            if (x + 1 < columns) {
                selectTile(x + 1, y, moveSelected);
            }
            if (x + 1 < columns && y + 1 < rows) {
                selectTile(x + 1, y + 1, moveSelected);
            }
        }
    }

    /**
     * Puts this board into its default state
     */
    public void resetBoard() {
        for (List<Tile> list : tiles) {
            for (Tile t : list) {
                t.reset();
            }
        }
    }

    /**
     * Deselects the specified tile and, if the tile has no adjacent mines, deselects all
     * adjacent tiles who were selected in the SANE move as the initial tile
     * @param x             X coordinate of the tile
     * @param y             Y coordinate of the tile
     * @param moveSelected  The move the BASE TILE was selected
     */
    public void deSelect(int x, int y, int moveSelected) {
        Tile t = tiles.get(y).get(x);
        if (!t.isSelected() || t.getMoveSelected() != moveSelected) {
            return;
        }
        t.reset();
        if (t.getNeighborMines() == 0) {
            if (x - 1 >= 0 && y - 1 >= 0) {
                deSelect(x - 1, y - 1, moveSelected);
            }
            if (x - 1 >= 0) {
                deSelect(x - 1, y, moveSelected);
            }
            if (x - 1 >= 0 && y + 1 < rows) {
                deSelect(x - 1, y + 1, moveSelected);
            }
            if (y - 1 >= 0) {
                deSelect(x, y - 1, moveSelected);
            }
            if (y + 1 < rows) {
                deSelect(x, y + 1, moveSelected);
            }
            if (x + 1 < columns && y - 1 >= 0) {
                deSelect(x + 1, y - 1, moveSelected);
            }
            if (x + 1 < columns) {
                deSelect(x + 1, y, moveSelected);
            }
            if (x + 1 < columns && y + 1 < rows) {
                deSelect(x + 1, y + 1, moveSelected);
            }
        }
    }

    /**
     * Flags or unflags the specified tile
     * @param x X coordinate of the tile
     * @param y Y coordinate of the tile
     */
    public void flagTile(int x, int y) {
        Tile t = tiles.get(y).get(x);
        t.flag();
    }

    public void flagUndisplayedTile(int x, int y) {
        Tile t = tiles.get(y).get(x);
        t.unshownFlag();
    }

    private void createBoard(int col, int row) {
        for (int i = 0; i < col; i++) {
            List<Tile> temp = new ArrayList<>();
            for (int j = 0; j < row; j++) {
                temp.add(new Tile(i, j, this, scaleMultiplier));
            }
            tiles.add(temp);
        }
    }

    private void initMines(int mines, long seed) {
        int i = 0;
        Random rand = new Random(seed);
        int rowSize = rows;
        int colSize = columns;
        while (i < mines) {
            int y = rand.nextInt(rowSize);
            int x = rand.nextInt(colSize);
            Tile tile = tiles.get(y).get(x);
            if (!tile.isMine()) {
                tile.setMine();
                i++;
            }
        }
    }

    private void setNeighbors() {
        for (int i = 0; i < tiles.size(); i++) {
            List<Tile> temp = tiles.get(i);
            for (Tile tile : temp) {
                if (!tile.isMine()) {
                    tile.determineNeighbors(tiles);
                }
            }
        }
    }
}
