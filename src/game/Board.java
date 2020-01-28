package game;

import data.storage.Move;
import data.writers.LogWriter;
import game.randomizers.Randomizer;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Randomizer randomizer;
    private int columns;
    private int rows;
    private int mines;
    private double scale;
    private List<List<Tile>> board;
    private List<Move> moves;
    private boolean firstClick;

    /**
     * Creates a new game from the specifications provided and using the mine placement
     * randomizer given to this board
     * @param columns       Number of columns to hold in the board
     * @param rows          Number of rows to hold in the board
     * @param mines         The number of mines to place in the board
     * @param scale         The dimension multiplication scale for the tiles
     * @param pane          The pane to draw the tiles to
     * @param randomizer    The randomizer used to place mines on the board
     */
    public Board(int columns, int rows, int mines, double scale, Pane pane, Randomizer randomizer) {
        this.randomizer = randomizer;
        this.columns = columns;
        this.rows = rows;
        this.mines = mines;
        this.scale = scale;
        firstClick = false;
        moves = new ArrayList<>();
        generateBoard(pane);
    }

    /**
     * Parses clicks and sends them to their proper helped method to do what the user wanted
     * @param mouseEvent    The event sent by javafx containing information about where the mouse
     *                     clicked
     */
    public void click(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            clickTile(mouseEvent);
        } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            flagTile(mouseEvent);
        }
    }

    /**
     * Restarts the game with the same provided seed and dimensions as the constructor
     * @param pane  The pane to draw the tiles to
     */
    public void restart(Pane pane) {
        if (LogWriter.writeMoves(new String[]{"" + columns, "" + rows, "" + randomizer.getSeed(),
                randomizer.getClass().getName()}, moves)) {
            moves = new ArrayList<>();
            clearBoard(pane);
            generateBoard(pane);
            firstClick = false;
        }
    }

    /**
     * Clears all the tiles from this board off the pane
     * @param pane  The pane to clear the tiles from
     */
    public void clearBoard(Pane pane) {
        for (List<Tile> tiles : board) {
            pane.getChildren().removeAll(tiles);
        }
    }

    private void flagTile(MouseEvent mouseEvent) {
        int x = (int)(mouseEvent.getX() / (scale * Tile.DEFAULT_TILE_SIZE));
        int y = (int)(mouseEvent.getY() / (scale * Tile.DEFAULT_TILE_SIZE));
        if (!firstClick) {
            randomizer.placeMines(columns, rows, mines, board);
            firstClick = true;
        }
        Tile t = board.get(y).get(x);
        if (!t.isClicked()) {
            t.flag();
            moves.add(new Move(x, y, "flag"));
        }
    }

    private void clickTile(MouseEvent mouseEvent) {
        int x = (int)(mouseEvent.getX() / (scale * Tile.DEFAULT_TILE_SIZE));
        int y = (int)(mouseEvent.getY() / (scale * Tile.DEFAULT_TILE_SIZE));
        if (!firstClick) {
            randomizer.placeMines(columns, rows, mines, board, new int[] {x, y});
            firstClick = true;
        }
        Tile t = board.get(y).get(x);
        if (!t.isClicked() && !t.isFlagged()) {
            moves.add(new Move(x, y, "select"));
            if (t.onClick()) {
                clearAdjacentTiles(x, y);
            } else if (t.isMine()) {
                end();
            }
        }
    }

    private void clearAdjacentTiles(int x, int y) {
        if (y - 1 >= 0 && x - 1 >= 0 && !board.get(y - 1).get(x - 1).isFlagged()) {
            if (board.get(y - 1).get(x - 1).onClick()) clearAdjacentTiles(x - 1, y - 1);
        }
        if (y - 1 >= 0 && !board.get(y - 1).get(x).isFlagged()) {
            if (board.get(y - 1).get(x).onClick()) clearAdjacentTiles(x, y - 1);
        }
        if (y - 1 >= 0 && x + 1 < columns && !board.get(y - 1).get(x + 1).isFlagged()) {
            if (board.get(y - 1).get(x + 1).onClick()) clearAdjacentTiles(x + 1, y - 1);
        }
        if (x - 1 >= 0 && !board.get(y).get(x - 1).isFlagged()) {
            if (board.get(y).get(x - 1).onClick()) clearAdjacentTiles(x - 1, y);
        }
        if (x + 1 < columns && !board.get(y).get(x + 1).isFlagged()) {
            if (board.get(y).get(x + 1).onClick()) clearAdjacentTiles(x + 1, y);
        }
        if (y + 1 < rows && x - 1 >= 0 && !board.get(y + 1).get(x - 1).isFlagged()) {
            if (board.get(y + 1).get(x - 1).onClick()) clearAdjacentTiles(x - 1, y + 1);
        }
        if (y + 1 < rows && !board.get(y + 1).get(x).isFlagged()) {
            if (board.get(y + 1).get(x).onClick()) clearAdjacentTiles(x, y + 1);
        }
        if (y + 1 < rows && x + 1 < columns && !board.get(y + 1).get(x + 1).isFlagged()) {
            if (board.get(y + 1).get(x + 1).onClick()) clearAdjacentTiles(x + 1, y + 1);
        }
    }

    private void generateBoard(Pane pane) {
        randomizer.generateBoard(columns, rows, scale);
        board = randomizer.getBoard();
        for (List<Tile> tiles : board) {
            pane.getChildren().addAll(tiles);
        }
    }

    private void end() {
    }
}
