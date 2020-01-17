package tilesets;

import javafx.scene.image.Image;

public class TilesetRequestHandler {
    private static Image[] imageSet;

    private TilesetRequestHandler() {}

    public static Image[] getImageSet() {
        return imageSet;
    }

    public static void loadImageSet(String pathname) {
        Image[] images = new Image[12];
        images[0] = new Image(pathname + "zero.png");
        images[1] = new Image(pathname + "one.png");
        images[2] = new Image(pathname + "two.png");
        images[3] = new Image(pathname + "three.png");
        images[4] = new Image(pathname + "four.png");
        images[5] = new Image(pathname + "five.png");
        images[6] = new Image(pathname + "six.png");
        images[7] = new Image(pathname + "seven.png");
        images[8] = new Image(pathname + "eight.png");
        images[9] = new Image(pathname + "default.png");
        images[10] = new Image(pathname + "flag.png");
        images[11] = new Image(pathname + "mine.png");
        imageSet = images;
    }
}
