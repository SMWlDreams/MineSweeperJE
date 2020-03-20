package data.readers;

import data.writers.HighScoreWriter;
import data.writers.SettingsWriter;
import error.ErrorHandler;
import error.exceptions.InvalidXMLException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParserHandler {
    public void parse(AbstractParser parser, String URI) throws FileNotFoundException,
            InvalidXMLException {
        XMLReader reader;
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
            reader.parse(URI);
        } catch (ParserConfigurationException | SAXException e) {
            throw new InvalidXMLException("XML file formatting is incorrect!");
        } catch (IOException e) {
            File f = new File(URI);
            if (f.mkdirs()) {
                if (parser instanceof SettingsParser) {
                    SettingsWriter.writeDefaultSettings();
                } else if (parser instanceof HighScoreParser) {
                    try {
                        HighScoreWriter.writeDefaultScores();
                    } catch (Exception e1) {
                        ErrorHandler.newUnexpectedExceptionAlert(e, true);
                        ErrorHandler.forceExit();
                    }
                }
                parse(parser, URI);
            } else {
                throw new FileNotFoundException("Unable to find or create required file:\n" + URI);
            }
        }
    }
}
