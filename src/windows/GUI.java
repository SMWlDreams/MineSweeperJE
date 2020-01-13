package windows;

import game.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResourceAsStream("fxml/Game.fxml"));
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
//        scene.setOnKeyPressed();
//        scene.setOnMouseClicked();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Minesweeper version " + AboutWindow.VERSION_ID);
//        stage.setOnShown(e -> controller.start());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
