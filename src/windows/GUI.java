package windows;

import game.Controller;
import game.Tile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tilesets.TilesetRequestHandler;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResourceAsStream("/fxml/Game.fxml"));
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        Tile.loadImages("/tilesets/windowsset/");
        stage.getIcons().add(Tile.getImage(Tile.MINE_IMAGE_INDEX));
//        scene.setOnKeyPressed();
//        scene.setOnMouseClicked();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Minesweeper " + AboutWindow.VERSION_ID);
//        stage.setOnShown(e -> controller.start());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
