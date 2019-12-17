import javafx.scene.layout.Pane;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Loads the playback file data and allows stepping
 */
public class Playback {
    private boolean loaded;
    private long[] properties;
    private List<Move> moves;
    private int index = 0;
    private PlaybackBoard board;
    private Controller controller;
    private boolean displayFlags;

    /**
     * Creates a new game playback from the specified log file and verifies the integrity of the
     * input file
     * @param file  Log file to play back
     * @throws NullPointerException     if the file specified is null
     * @throws IOException              if the file does not exist
     * @throws InvalidLogException      if the log file format is invalid
     */
    public Playback(File file) throws NullPointerException, IOException,
            InvalidLogException {
        XMLReader reader;
        LogParser parser = new LogParser();
        System.getProperty("javax.xml.parsers.SAXParserFactory");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
            reader.setContentHandler(parser);
            reader.setErrorHandler(parser);
            reader.setProperty("http://xml.org/sax/properties/lexical-handler", parser);
            String URI = file.toURI().toString();
            reader.parse(URI);
            properties = parser.getPropertyValues();
            moves = parser.getMoves();
            loaded = true;
        } catch (ParserConfigurationException | SAXException e) {
            throw new InvalidLogException("Log file formatting is incorrect!");
        }
    }

    /**
     * Generates a new board based on the supplied log files seed and hash information
     * @param pane  The pane to draw tiles to
     * @throws IllegalStateException    if no file was loaded
     */
    public void generate(Pane pane) throws IllegalStateException {
        if (!loaded) {
            throw new IllegalStateException("No file loaded");
        }
        int multiplier;
        if (properties[1] <= 13 || properties[2] <= 10) {
            multiplier = 3;
        } else if (properties[1] <= 22 || properties[2] <= 16) {
            multiplier = 2;
        } else {
            multiplier = 1;
        }
        controller.setVars((int)properties[1], (int)properties[2], multiplier);
        board = new PlaybackBoard((int)properties[1], (int)properties[2], (int)properties[3], pane,
                multiplier, properties[0]);
    }

    /**
     * Jumps to before the first move in the log file
     */
    public void toStart() {
        board.resetBoard();
        index = 0;
    }

    /**
     * Clears all tiles off of the pane
     * @param pane  Pane containing the tiles
     */
    public void clear(Pane pane) {
        board.clear(pane);
    }

    /**
     * Steps backwards one move if the playback isn't currently at move 0
     * @return  String with the format of "(x,y) move#"
     */
    public String stepBack() {
        if (index != 0) {
            Move m = moves.get(--index);
            String s = m.getMove();
            int i = getIndex(s);
            int x = Integer.parseInt(s.substring(0, i));
            int y = Integer.parseInt(s.substring(i + 1));
            switch (m.getProperty()) {
                case "select":
                    board.deSelect(x, y, index);
                    break;
                case "flag":
                    if (displayFlags) {
                        board.flagTile(x, y);
                    } else {
                        board.flagUndisplayedTile(x, y);
                    }
                    break;
            }
            return ("(" + x + "," + y + ") " + index);
        }
        return "";
    }

    /**
     * Tells this playback whether or not it should display flagged mines or not
     * @param bool  True if it should display flags, false if otherwise
     */
    public void setDisplayFlags(boolean bool) {
        displayFlags = bool;
    }

    /**
     * Steps forward one move in the log file assuming the playback is not already at the end of
     * the file
     * @return  String with the format of "movetype (x,y) move#"
     */
    public String stepForward() {
        if (index < moves.size()) {
            Move m = moves.get(index);
            String s = m.getMove();
            int i = getIndex(s);
            int x = Integer.parseInt(s.substring(0, i));
            int y = Integer.parseInt(s.substring(i + 1));
            switch (m.getProperty()) {
                case "select":
                    board.selectTile(x, y, index++);
                    return ("Select (" + x + "," + y + ") " + index);
                case "flag":
                    if (displayFlags) {
                        board.flagTile(x, y);
                    }
                    index++;
                    return ("Flag (" + x + "," + y + ") " + index);
            }
        }
        return "";
    }

    /**
     * Jumps to the end of the playback file
     * @return  See @link #stepForward stepForward for return information
     */
    public String toEnd() {
        while (index < moves.size() - 1) {
            stepForward();
        }
        return stepForward();
    }

    /**
     * Gets the total number of moves in this playback
     * @return  Number of moves as an int
     */
    public int getNumberOfMoves() {
        return moves.size();
    }

    /**
     * Sets a reference to the main controller
     * @param controller    The main controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    private int getIndex(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                return i;
            }
        }
        return -1;
    }
}
