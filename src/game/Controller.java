package game;

import data.storage.LoadedSettings;
import error.Logger;
import factories.BoardFactory;
import game.randomizers.SkipRandomizer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import windows.HighScoreWindow;

public class Controller {
    @FXML
    private Pane board;

    private enum State {INITIALIZING, WAIT, PAUSE, WIN, LOSE}
    private State state = State.INITIALIZING;

    private Board game;

    public void start() {
        game = BoardFactory.generateInitialBoard(board);
//        game = new Board(10, 10, 10, 3.0, 3.0, board, new SkipRandomizer(123456789));
    }

    @FXML
    public void onClick(MouseEvent mouseEvent) {
        game.click(mouseEvent);
    }

    public static void cleanup() {
        Logger.close();
        Platform.exit();
    }

    public void parseHotkey(KeyEvent keyEvent) {
    }
}
