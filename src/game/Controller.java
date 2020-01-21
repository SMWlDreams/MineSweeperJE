package game;

import game.randomizers.DefaultRandomizer;
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
         game = new Board(9, 9, 10, 3.0, this.board, new DefaultRandomizer());
    }

    @FXML
    public void onClick(MouseEvent mouseEvent) {
        game.click(mouseEvent);
    }
}
