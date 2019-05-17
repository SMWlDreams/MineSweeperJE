import javafx.event.ActionEvent;

/**
 * Loads about window
 */
public class About {
    private Controller controller;

    /**
     * Closes about window
     * @param actionEvent   Event from javafx
     */
    public void close(ActionEvent actionEvent) {
        controller.closeNewWindow();
    }

    /**
     * Sets the controller
     * @param controller    Controller object
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
}