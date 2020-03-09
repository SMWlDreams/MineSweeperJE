package game;

import error.Logger;
import game.randomizers.SkipRandomizer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Controller {
    @FXML
    private Pane board;
    @FXML
    private ImageView mineDisp;
    @FXML
    private ImageView flagDisp;
    @FXML
    private ImageView timeDisp;

    private Board game;

    public void start() {
         game = new Board(5, 5, 10, 3.0, this.board, new SkipRandomizer(123456789));
    }

    @FXML
    public void onClick(MouseEvent mouseEvent) {
        game.click(mouseEvent);
    }

    public static void cleanup() {
        Logger.close();
        Platform.exit();
    }
}
