package windows;

import data.readers.HighScoreParser;
import data.readers.ParserHandler;
import data.writers.HighScoreWriter;
import data.writers.SettingsWriter;
import error.ErrorHandler;
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
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResourceAsStream("/fxml/Game.fxml"));
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        Tile.loadImages("/tilesets/defaultset/");
        stage.getIcons().add(Tile.getImage(Tile.MINE_IMAGE_INDEX));
//        scene.setOnKeyPressed();
        scene.setOnMouseClicked(controller::onClick);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Minesweeper " + AboutWindow.VERSION_ID);
        stage.setOnShown(e -> controller.start());
        stage.setOnCloseRequest(e -> Controller.cleanup());
        var x = new ParserHandler();
        try {
            x.parse(new HighScoreParser(), "Settings.cfg");
        } catch (Exception e) {
            ErrorHandler.newUnexpectedExceptionAlert(e, true);
        }
//        HighScoreWriter.writeDefaultScores();
//        SettingsWriter.writeDefaultSettings();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
