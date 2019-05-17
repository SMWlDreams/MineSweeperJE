import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The tiles to make the board
 */
public class Tile extends Rectangle {
    /**
     * The default scale for each of the image
     */
    public static final int SCALE = 20;

    private int scaleMultiplier;
    private final Image beforeFlagged;
    private final Image flag;
    private Image afterClicked;
    private final int xcoord;
    private final int ycoord;
    private boolean mine = false;
    private int neighborMines = 0;
    private boolean flagged = false;
    private boolean selected;
    private Board board;

    /**
     * Creates a new tile with specified coordinates
     * @param x             X Coordinate of the tile
     * @param y             Y Coordinate of the tile
     * @param board         The board the tiles are placed in
     * @param multiplier    The multiplier for the scale based on board size
     */
    public Tile(int x, int y, Board board, int multiplier) throws IOException {
        beforeFlagged = new Image(new File(System.getProperty("user.dir") + "\\Images\\default" +
                ".png").toURI().toURL().toString());
        flag =
                new Image(new File(System.getProperty("user.dir") + "\\Images\\flag.png").toURI().toURL().toString());
        scaleMultiplier = multiplier;
        selected = false;
        this.board = board;
        xcoord = x;
        ycoord = y;
        this.setLayoutX(x * SCALE * scaleMultiplier);
        this.setLayoutY(y * SCALE * scaleMultiplier);
        this.setWidth(SCALE * scaleMultiplier);
        this.setHeight(SCALE * scaleMultiplier);
        this.setFill(new ImagePattern(beforeFlagged, 0, 0, SCALE * scaleMultiplier,
                SCALE * scaleMultiplier, false));
    }

    public void setMine() throws IOException {
        mine = true;
        afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString());
    }

    public boolean isMine() {
        return mine;
    }

    public int getXcoord() {
        return xcoord;
    }

    /**
     * Marks the tile as a flag if it is not already selected, un-flags it if it is already flagged
     */
    public void flag() {
        if (!selected) {
            if (flagged) {
                this.setFill(new ImagePattern(beforeFlagged, 0, 0, SCALE * scaleMultiplier,
                        SCALE * scaleMultiplier, false));
                flagged = false;
            } else {
                this.setFill(new ImagePattern(flag, 0, 0, SCALE * scaleMultiplier,
                        SCALE * scaleMultiplier, false));
                flagged = true;
            }
        }
    }

    /**
     * Selects the tile and updates the image for the tile
     * @return  -1 if it is a mine, otherwise the number of adjacent mines
     */
    public int onClick() {
        if (!selected) {
            updateImage();
            selected = true;
            if (mine) {
                return -1;
            } else {
                return neighborMines;
            }
        } else {
            return 1;
        }
    }

    public int getYcoord() {
        return ycoord;
    }

    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Determines how many mines are around this specific tile
     * @param tiles The list of tiles for this board
     */
    public void determineNeighbors(List<List<Tile>> tiles) throws IOException {
        neighborMines = 0;
        determineNeighbors(tiles, xcoord, ycoord);
        switch (neighborMines) {
            case 0:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\zero.png").toURI().toURL().toString());
                break;
            case 1:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\one.png").toURI().toURL().toString());
                break;
            case 2:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\two.png").toURI().toURL().toString());
                break;
            case 3:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\three.png").toURI().toURL().toString());
                break;
            case 4:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\four.png").toURI().toURL().toString());
                break;
            case 5:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\five.png").toURI().toURL().toString());
                break;
            case 6:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\six.png").toURI().toURL().toString());
                break;
            case 7:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\seven.png").toURI().toURL().toString());
                break;
            case 8:
                afterClicked = new Image(new File(System.getProperty("user.dir") + "\\Images\\eight.png").toURI().toURL().toString());
                break;
        }
    }

    private void determineNeighbors(List<List<Tile>> tiles, int xcoord, int ycoord) {
        if (ycoord + 1 < board.getRows()) {
            if (tiles.get(xcoord).get(ycoord + 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord + 1 < board.getRows() && xcoord - 1 >= 0) {
            if (tiles.get(xcoord - 1).get(ycoord + 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord + 1 < board.getRows() && xcoord + 1 < board.getColumns()) {
            if (tiles.get(xcoord + 1).get(ycoord + 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord - 1 >= 0) {
            if (tiles.get(xcoord).get(ycoord - 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord - 1 >= 0 && xcoord - 1 >= 0) {
            if (tiles.get(xcoord - 1).get(ycoord - 1).isMine()) {
                neighborMines++;
            }
        }
        if (ycoord - 1 >= 0 && xcoord + 1 < board.getColumns()) {
            if (tiles.get(xcoord + 1).get(ycoord - 1).isMine()) {
                neighborMines++;
            }
        }
        if (xcoord - 1 >= 0) {
            if (tiles.get(xcoord - 1).get(ycoord).isMine()) {
                neighborMines++;
            }
        }
        if (xcoord + 1 < board.getColumns()) {
            if (tiles.get(xcoord + 1).get(ycoord).isMine()) {
                neighborMines++;
            }
        }
    }

    public void updateImage() {
        this.setFill(new ImagePattern(afterClicked, 0, 0, SCALE * scaleMultiplier,
                SCALE * scaleMultiplier, false));
    }

    public boolean isSelected() {
        return selected;
    }
}