import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Loads about window
 */
public class About implements Controllers {
    @FXML
    private Text version;

    /**
     * Class properties to allow loading of the window
     */
    public static final String[] PROPERTIES = {"About.fxml", "About"};

    /**
     * The current version number of the application
     */
    public static final String VERSION_ID = "1.4.1";

    /**
     * The major revision number of the application
     */
    public static final int MAJOR_REVISION = 1;

    /**
     * The minor revision number of the application
     */
    public static final int MINOR_REVISION = 3;

    /**
     * The sub revision number of the application
     */
    public static final int SUB_REVISION = 2;

    private Controller controller;

    /**
     * Closes about window
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
        version.setText("Version ID: " + string);
    }

    /**
     * Sets the controller
     * @param controller    Controller object
     */
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}