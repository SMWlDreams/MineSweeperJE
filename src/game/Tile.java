package game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import tilesets.TilesetRequestHandler;

import java.util.List;

public class Tile extends Rectangle {
    private static Image[] images;
    public static final int MINE_IMAGE_INDEX = 11;
    public static final int DEFAULT_TILE_SIZE = 20;
    private static final int ZERO_IMAGE_INDEX = 0;
    private static final int ONE_IMAGE_INDEX = 1;
    private static final int TWO_IMAGE_INDEX = 2;
    private static final int THREE_IMAGE_INDEX = 3;
    private static final int FOUR_IMAGE_INDEX = 4;
    private static final int FIVE_IMAGE_INDEX = 5;
    private static final int SIX_IMAGE_INDEX = 6;
    private static final int SEVEN_IMAGE_INDEX = 7;
    private static final int EIGHT_IMAGE_INDEX = 8;
    private static final int DEFAULT_IMAGE_INDEX = 9;
    private static final int FLAG_IMAGE_INDEX = 10;

    private Image onClick;
    private int neighborMines = 0;
    private boolean mine = false;
    private boolean clicked = false;
    private boolean flagged = false;

    public Tile(double x, double y, double scaleX, double scaleY) {
        setX(x * scaleX * DEFAULT_TILE_SIZE);
        setY(y * scaleY * DEFAULT_TILE_SIZE);
        setHeight(scaleX * DEFAULT_TILE_SIZE);
        setWidth(scaleY * DEFAULT_TILE_SIZE);
        setFill(new ImagePattern(images[DEFAULT_IMAGE_INDEX]));
    }

    public static void loadImages(String pathname) {
        TilesetRequestHandler.loadImageSet(pathname);
        images = TilesetRequestHandler.getImageSet();
    }

    public static Image getImage(int index) {
        if (index < images.length) {
            return images[index];
        }
        return null;
    }

    public void setMine() {
        mine = true;
        onClick = images[MINE_IMAGE_INDEX];
    }

    public void reset() {
        setFill(new ImagePattern(images[DEFAULT_IMAGE_INDEX]));
        clicked = false;
        flagged = false;
    }

    public boolean isMine() {
        return mine;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean onClick() {
        setFill(new ImagePattern(onClick));
        clicked = true;
        return !mine && neighborMines == 0;
    }

    public void flag() {
        if (flagged) {
            setFill(new ImagePattern(images[DEFAULT_IMAGE_INDEX]));
        } else {
            setFill(new ImagePattern(images[FLAG_IMAGE_INDEX]));
        }
        flagged = !flagged;
    }

    public void determineNeighbors(List<List<Tile>> tiles, int columns, int rows, int x, int y) {
        if (y - 1 >= 0 && x - 1 >= 0 && tiles.get(y - 1).get(x - 1).isMine()) neighborMines++;
        if (y - 1 >= 0 && tiles.get(y - 1).get(x).isMine()) neighborMines++;
        if (y - 1 >= 0 && x + 1 < columns && tiles.get(y - 1).get(x + 1).isMine()) neighborMines++;
        if (x - 1 >= 0 && tiles.get(y).get(x - 1).isMine()) neighborMines++;
        if (x + 1 < columns && tiles.get(y).get(x + 1).isMine()) neighborMines++;
        if (y + 1 < rows && x - 1 >= 0 && tiles.get(y + 1).get(x - 1).isMine()) neighborMines++;
        if (y + 1 < rows && tiles.get(y + 1).get(x).isMine()) neighborMines++;
        if (y + 1 < rows && x + 1 < columns && tiles.get(y + 1).get(x + 1).isMine()) neighborMines++;
        setOnClickImage();
    }

    private void setOnClickImage() {
        switch (neighborMines) {
            case 0:
                onClick = images[ZERO_IMAGE_INDEX];
                break;
            case 1:
                onClick = images[ONE_IMAGE_INDEX];
                break;
            case 2:
                onClick = images[TWO_IMAGE_INDEX];
                break;
            case 3:
                onClick = images[THREE_IMAGE_INDEX];
                break;
            case 4:
                onClick = images[FOUR_IMAGE_INDEX];
                break;
            case 5:
                onClick = images[FIVE_IMAGE_INDEX];
                break;
            case 6:
                onClick = images[SIX_IMAGE_INDEX];
                break;
            case 7:
                onClick = images[SEVEN_IMAGE_INDEX];
                break;
            case 8:
                onClick = images[EIGHT_IMAGE_INDEX];
                break;
        }
    }
}
