public class Settings implements Controllers {
    public static final String[] PROPERTIES = {"Settings.fxml", "Settings"};

    private Controller controller;

    /**
     * Method that handles the launch settings for each new window
     *
     * @param string Any argument string that must be passed to the implemented method
     */
    @Override
    public void launch(String string) {

    }

    /**
     * Gives a reference to the implementing class that allows it to access the close method in
     * the main controller
     *
     * @param controller The copy of the main GUI controller
     */
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
