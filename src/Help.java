import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Opens help dialog
 */
public class Help implements Controllers {

    /**
     * Class properties to allow loading of the window
     */
    public static final String[] PROPERTIES = {"Help.fxml", "Help"};

    @FXML
    private Text seeds;

    private Controller controller;

    /**
     * Closes the current window
     * @param actionEvent   Event from javafx
     */
    public void close(ActionEvent actionEvent) {
        controller.closeNewWindow();
    }

    /**
     * Method that handles the launch settings for each new window
     * @param string Any argument string that must be passed to the implemented method
     */
    @Override
    public void launch(String string) {
        seeds.setText(string);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
