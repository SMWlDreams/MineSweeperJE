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

    public Board(int columns, int rows, int mines, double scale, Pane pane, Randomizer randomizer) {
        this.randomizer = randomizer;
        this.columns = columns;
        this.rows = rows;
        this.mines = mines;
        this.scale = scale;
        moves = new ArrayList<>();
        generateBoard(pane);
    }

    public void click(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            clickTile(mouseEvent);
        } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            flagTile(mouseEvent);
        }
    }

    private void flagTile(MouseEvent mouseEvent) {
        int x = (int)(mouseEvent.getX() / (scale * Tile.DEFAULT_TILE_SIZE));
        int y = (int)(mouseEvent.getY() / (scale * Tile.DEFAULT_TILE_SIZE));
        Tile t = board.get(y).get(x);
        if (!t.isClicked()) {
            t.flag();
            moves.add(new Move(x, y, "flag"));
        }
    }

    private void clickTile(MouseEvent mouseEvent) {
        int x = (int)(mouseEvent.getX() / (scale * Tile.DEFAULT_TILE_SIZE));
        int y = (int)(mouseEvent.getY() / (scale * Tile.DEFAULT_TILE_SIZE));
        Tile t = board.get(y).get(x);
        if (!t.isClicked() && !t.isFlagged()) {
            t.onClick();
            moves.add(new Move(x, y, "select"));
        }
    }

    public void restart(Pane pane) {
        if (LogWriter.writeMoves(new String[]{"" + columns, "" + rows, "" + randomizer.getSeed(),
                        randomizer.getClass().getName()}, moves)) {
            moves = new ArrayList<>();
            clearBoard(pane);
            generateBoard(pane);
        }
    }

    private void clearBoard(Pane pane) {
        for (List<Tile> tiles : board) {
            pane.getChildren().removeAll(tiles);
        }
    }

    private void generateBoard(Pane pane) {
        randomizer.generateBoard(new double[]{columns, rows, scale, mines});
        board = randomizer.getBoard();
        for (List<Tile> tiles : board) {
            pane.getChildren().addAll(tiles);
        }
    }
}
