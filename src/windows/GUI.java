package windows;

import data.storage.LoadedSettings;
import data.writers.HighScoreWriter;
import game.Controller;
import game.Tile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        HighScoreWriter.writeDefaults();
        var settings = new LoadedSettings();
        settings.start();
        var scores = new HighScoreWindow();
        scores.run();
//        LoadedSettings.loadAllSettings();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResourceAsStream("/fxml/Game.fxml"));
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
//        Tile.loadImages("/tilesets/defaultset/");
//        stage.getIcons().add(Tile.getImage(Tile.MINE_IMAGE_INDEX));
        scene.setOnKeyPressed(controller::parseHotkey);
        scene.setOnMouseClicked(controller::onClick);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Minesweeper " + AboutWindow.VERSION_ID);
        stage.setOnShown(e -> controller.start());
        stage.setOnCloseRequest(e -> Controller.cleanup());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
