import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.IllegalFormatException;

public class Playback {

    /**
     * Creates a new game playback from the specified log file and verifies the integrity of the
     * input file
     * @param file  Log file to play back
     * @throws NullPointerException     if the file specified is null
     * @throws FileNotFoundException    if the file does not exist
     * @throws IllegalFormatException   if the file format is invalid
     */
    public Playback(File file) throws NullPointerException, FileNotFoundException,
            IllegalFormatException {
    }

    /**
     * Generates a new board based on the supplied log files seed and hash information
     * @param pane  The pane to draw tiles to
     */
    public void generate(Pane pane) {
    }

    public String toStart() {
        return "";
    }

    public String stepBack() {
        return "";
    }

    public String stepForward() {
        return "";
    }

    public String toEnd() {
        return "";
    }
}
