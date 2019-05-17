import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

public class GUI extends Application {
    private Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("Minesweeper.fxml").openStream());
        Controller controller = loader.getController();
        controller.setGui(this);
        Scene scene = new Scene(root);
        controller.initialGame();
        mainStage.setOnHidden(e -> controller.stopTimeline());
        mainStage.getIcons().add(new Image(new File(System.getProperty("user.dir") + "\\Images\\mine.png").toURI().toURL().toString()));
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.setTitle("Minesweeper");
        mainStage.show();
    }

    /**
     * Sets stage dimensions when drawing a new board
     * @param width     The new width of the stage
     * @param height    The new height of the stage
     */
    public void setDimensions(double width, double height) {
        mainStage.setWidth(width);
        mainStage.setHeight(height);
    }

    /**
     * Closes the main stage
     */
    public void close() {
        mainStage.close();
    }
}
