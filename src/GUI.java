import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {
    private Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean promptSaveLog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Log");
        alert.setHeaderText("Save Log File?");
        alert.setContentText("Would you like to save your partial game?");
        alert.showAndWait();
        return alert.getResult().equals(ButtonType.OK);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("Minesweeper.fxml").openStream());
        Controller controller = loader.getController();
        controller.setGui(this);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::parseInput);
        controller.initialGame();
        mainStage.setOnHidden(e -> controller.stopTimeline());
        mainStage.getIcons().add(Tile.MINE_IMAGE);
        mainStage.setScene(scene);
        mainStage.setTitle("Minesweeper " + About.VERSION_ID);
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

    /**
     * Enables or disables manual resizing of the window
     */
    public void setResizable() {
        if (mainStage.isResizable()) {
            mainStage.setResizable(false);
        } else {
            mainStage.setResizable(true);
        }
    }
}