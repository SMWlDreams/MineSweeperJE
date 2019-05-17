import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Opens help dialog
 */
public class Help {
    @FXML
    private TextField seeds;

    private Controller controller;

    /**
     * Closes the current window
     * @param actionEvent   Event from javafx
     */
    public void close(ActionEvent actionEvent) {
        controller.closeNewWindow();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public void setSeeds(String seed) {
        seeds.setText(seed);
    }
}
