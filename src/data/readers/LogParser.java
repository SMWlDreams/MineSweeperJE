//package data.readers;
//
//import data.storage.Move;
//import error.exceptions.InvalidCoordinateException;
//import error.exceptions.InvalidCountException;
//import error.exceptions.InvalidDimensionException;
//import org.xml.sax.Attributes;
//import org.xml.sax.ContentHandler;
//import org.xml.sax.ErrorHandler;
//import org.xml.sax.Locator;
//import org.xml.sax.SAXException;
//import org.xml.sax.SAXParseException;
//import org.xml.sax.helpers.AttributesImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LogParser extends AbstractParser {
//    private long[] properties = new long[4];
//    private List<Move> moves = new ArrayList<>();
//    /**
//     * The states for this specific state machine
//     */
//    private enum States {INIT, GAME, SEED, WIDTH, HEIGHT, MINES, SELECT, FLAG, X, Y, END}
//    private States states = States.INIT;
//    private States tempStates;
//    private Locator locator;
//    private Move move;
//    /**
//     * Receive an object for locating the origin of SAX document events.
//     *
//     * <p>SAX parsers are strongly encouraged (though not absolutely
//     * required) to supply a locator: if it does so, it must supply
//     * the locator to the application by invoking this method before
//     * invoking any of the other methods in the ContentHandler
//     * interface.</p>
//     *
//     * <p>The locator allows the application to determine the end
//     * position of any document-related event, even if the parser is
//     * not reporting an error.  Typically, the application will
//     * use this information for reporting its own errors (such as
//     * character content that does not match an application's
//     * business rules).  The information returned by the locator
//     * is probably not sufficient for use with a search engine.</p>
//     *
//     * <p>Note that the locator will return correct information only
//     * during the invocation SAX event callbacks after
//     * {@link #startDocument startDocument} returns and before
//     * {@link #endDocument endDocument} is called.  The
//     * application should not attempt to use it at any other time.</p>
//     *
//     * @param locator an object that can return the location of
//     *                any SAX document event
//     * @see Locator
//     */
//    @Override
//    public void setDocumentLocator(Locator locator) {
//        this.locator = locator;
//    }
//
//    /**
//     * Receive notification of the beginning of a document.
//     *
//     * <p>The SAX parser will invoke this method only once, before any
//     * other event callbacks (except for {@link #setDocumentLocator
//     * setDocumentLocator}).</p>
//     *
//     * @see #endDocument
//     */
//    @Override
//    public void startDocument() {
//        System.out.println("Document Parsing Beginning!");
//    }
//
//    /**
//     * Receive notification of the end of a document.
//     *
//     * <p><strong>There is an apparent contradiction between the
//     * documentation for this method and the documentation for {@link
//     * ErrorHandler#fatalError}.  Until this ambiguity is
//     * resolved in a future major release, clients should make no
//     * assumptions about whether endDocument() will or will not be
//     * invoked when the parser has reported a fatalError() or thrown
//     * an exception.</strong></p>
//     *
//     * <p>The SAX parser will invoke this method only once, and it will
//     * be the last method invoked during the parse.  The parser shall
//     * not invoke this method until it has either abandoned parsing
//     * (because of an unrecoverable error) or reached the end of
//     * input.</p>
//     *
//     * @see #startDocument
//     */
//    @Override
//    public void endDocument() {
//        System.out.println("Document Parsing Complete!");
//    }
//
//    /**
//     * Receive notification of the beginning of an element.
//     *
//     * <p>The Parser will invoke this method at the beginning of every
//     * element in the XML document; there will be a corresponding
//     * {@link #endElement endElement} event for every startElement event
//     * (even when the element is empty). All of the element's content will be
//     * reported, in order, before the corresponding endElement
//     * event.</p>
//     *
//     * <p>This event allows up to three name components for each
//     * element:</p>
//     *
//     * <ol>
//     * <li>the Namespace URI;</li>
//     * <li>the local name; and</li>
//     * <li>the qualified (prefixed) name.</li>
//     * </ol>
//     *
//     * <p>Any or all of these may be provided, depending on the
//     * values of the <var>http://xml.org/sax/features/namespaces</var>
//     * and the <var>http://xml.org/sax/features/namespace-prefixes</var>
//     * properties:</p>
//     *
//     * <ul>
//     * <li>the Namespace URI and local name are required when
//     * the namespaces property is <var>true</var> (the default), and are
//     * optional when the namespaces property is <var>false</var> (if one is
//     * specified, both must be);</li>
//     * <li>the qualified name is required when the namespace-prefixes property
//     * is <var>true</var>, and is optional when the namespace-prefixes property
//     * is <var>false</var> (the default).</li>
//     * </ul>
//     *
//     * <p>Note that the attribute list provided will contain only
//     * attributes with explicit values (specified or defaulted):
//     * #IMPLIED attributes will be omitted.  The attribute list
//     * will contain attributes used for Namespace declarations
//     * (xmlns* attributes) only if the
//     * <code>http://xml.org/sax/features/namespace-prefixes</code>
//     * property is true (it is false by default, and support for a
//     * true value is optional).</p>
//     *
//     * <p>Like {@link #characters characters()}, attribute values may have
//     * characters that need more than one <code>char</code> value.  </p>
//     *
//     * @param uri       the Namespace URI, or the empty string if the
//     *                  element has no Namespace URI or if Namespace
//     *                  processing is not being performed
//     * @param localName the local name (without prefix), or the
//     *                  empty string if Namespace processing is not being
//     *                  performed
//     * @param qName     the qualified name (with prefix), or the
//     *                  empty string if qualified names are not available
//     * @param atts      the attributes attached to the element.  If
//     *                  there are no attributes, it shall be an empty
//     *                  Attributes object.  The value of this object after
//     *                  startElement returns is undefined
//     * @throws SAXException any SAX exception, possibly
//     *                      wrapping another exception
//     * @see #endElement
//     * @see Attributes
//     * @see AttributesImpl
//     */
//    @Override
//    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
//        switch (states) {
//            case INIT:
//                if (!localName.equalsIgnoreCase("GAME")) {
//                    throw new SAXException("Expected game element, found " + localName);
//                }
//                states = States.GAME;
//                break;
//            case GAME:
//                switch (localName.toLowerCase()) {
//                    case "seed":
//                        states = States.SEED;
//                        break;
//                    case "diff":
//                        break;
//                    case "width":
//                        states = States.WIDTH;
//                        break;
//                    case "height":
//                        states = States.HEIGHT;
//                        break;
//                    case "mines":
//                        states = States.MINES;
//                        break;
//                    case "select":
//                        states = States.SELECT;
//                        tempStates = States.SELECT;
//                        move = new Move();
//                        move.setState("select");
//                        break;
//                    case "flag":
//                        states = States.FLAG;
//                        tempStates = States.FLAG;
//                        move = new Move();
//                        move.setState("flag");
//                        break;
//                    default:
//                        throw new SAXException("Invalid element! Found " + localName);
//                }
//                break;
//            case SELECT:
//                switch (localName.toLowerCase()) {
//                    case "x":
//                        states = States.X;
//                        break;
//                    case "y":
//                        states = States.Y;
//                        break;
//                    default:
//                        throw new SAXException("Invalid element! Found " + localName);
//                }
//                break;
//            case FLAG:
//                switch (localName.toLowerCase()) {
//                    case "x":
//                        states = States.X;
//                        break;
//                    case "y":
//                        states = States.Y;
//                        break;
//                    default:
//                        throw new SAXException("Invalid element! Found " + localName);
//                }
//                break;
//        }
//    }
//
//    /**
//     * Receive notification of the end of an element.
//     *
//     * <p>The SAX parser will invoke this method at the end of every
//     * element in the XML document; there will be a corresponding
//     * {@link #startElement startElement} event for every endElement
//     * event (even when the element is empty).</p>
//     *
//     * <p>For information on the names, see startElement.</p>
//     *
//     * @param uri       the Namespace URI, or the empty string if the
//     *                  element has no Namespace URI or if Namespace
//     *                  processing is not being performed
//     * @param localName the local name (without prefix), or the
//     *                  empty string if Namespace processing is not being
//     *                  performed
//     * @param qName     the qualified XML name (with prefix), or the
//     *                  empty string if qualified names are not available
//     * @throws SAXException any SAX exception, possibly
//     *                      wrapping another exception
//     */
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        switch (states) {
//            case GAME:
//                if (localName.equalsIgnoreCase("diff")) {
//                    break;
//                } else if (!localName.equalsIgnoreCase("game")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = States.END;
//                break;
//            case SEED:
//                if (!localName.equalsIgnoreCase("seed")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = States.GAME;
//                break;
//            case WIDTH:
//                if (!localName.equalsIgnoreCase("width")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = States.GAME;
//                break;
//            case HEIGHT:
//                if (!localName.equalsIgnoreCase("height")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = States.GAME;
//                break;
//            case MINES:
//                if (!localName.equalsIgnoreCase("mines")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = States.GAME;
//                break;
//            case SELECT:
//                if (!localName.equalsIgnoreCase("select")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = States.GAME;
//                moves.add(move);
//                break;
//            case FLAG:
//                if (!localName.equalsIgnoreCase("flag")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = States.GAME;
//                moves.add(move);
//                break;
//            case X:
//                if (!localName.equalsIgnoreCase("x")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = tempStates;
//                break;
//            case Y:
//                if (!localName.equalsIgnoreCase("y")) {
//                    throw new SAXException("Invalid ending element! Found " + localName);
//                }
//                states = tempStates;
//                break;
//            default:
//                throw new SAXException("Invalid ending element! Found " + localName);
//        }
//    }
//
//    /**
//     * Receive notification of character data.
//     *
//     * <p>The Parser will call this method to report each chunk of
//     * character data.  SAX parsers may return all contiguous character
//     * data in a single chunk, or they may split it into several
//     * chunks; however, all of the characters in any single event
//     * must come from the same external entity so that the Locator
//     * provides useful information.</p>
//     *
//     * <p>The application must not attempt to read from the array
//     * outside of the specified range.</p>
//     *
//     * <p>Individual characters may consist of more than one Java
//     * <code>char</code> value.  There are two important cases where this
//     * happens, because characters can't be represented in just sixteen bits.
//     * In one case, characters are represented in a <em>Surrogate Pair</em>,
//     * using two special Unicode values. Such characters are in the so-called
//     * "Astral Planes", with a code point above U+FFFF.  A second case involves
//     * composite characters, such as a base character combining with one or
//     * more accent characters. </p>
//     *
//     * <p> Your code should not assume that algorithms using
//     * <code>char</code>-at-a-time idioms will be working in character
//     * units; in some cases they will split characters.  This is relevant
//     * wherever XML permits arbitrary characters, such as attribute values,
//     * processing instruction data, and comments as well as in data reported
//     * from this method.  It's also generally relevant whenever Java code
//     * manipulates internationalized text; the issue isn't unique to XML.</p>
//     *
//     * <p>Note that some parsers will report whitespace in element
//     * content using the {@link #ignorableWhitespace ignorableWhitespace}
//     * method rather than this one (validating parsers <em>must</em>
//     * do so).</p>
//     *
//     * @param ch     the characters from the XML document
//     * @param start  the start position in the array
//     * @param length the number of characters to read from the array
//     * @throws SAXException any SAX exception, possibly
//     *                      wrapping another exception
//     * @see #ignorableWhitespace
//     * @see Locator
//     */
//    @Override
//    public void characters(char[] ch, int start, int length) throws SAXException {
//        String s = new String(ch);
//        s = s.substring(start, start + length);
//        s = s.trim();
//        long l;
//        int i;
//        try {
//            switch (states) {
//                case X:
//                    i = Integer.parseInt(s);
//                    if (i >= properties[1] || i < 0) {
//                        throw new InvalidCoordinateException("Invalid X coordinate at line " + locator.getLineNumber() +
//                                ", column " + locator.getColumnNumber() + "!\nValue: " + i);
//                    }
//                    move.setX(i);
//                    break;
//                case Y:
//                    i = Integer.parseInt(s);
//                    if (i >= properties[2] || i < 0) {
//                        throw new InvalidCoordinateException("Invalid Y coordinate at line " + locator.getLineNumber() +
//                                ", column " + locator.getColumnNumber() + "!\nValue: " + i);
//                    }
//                    move.setY(i);
//                    break;
//                case SEED:
//                    properties[0] = Long.parseLong(s);
//                    break;
//                case WIDTH:
//                    l = Long.parseLong(s);
//                    if (l < 5 || l > 30) {
//                        throw new InvalidDimensionException("Invalid log file width at line " + locator.getLineNumber() +
//                                ", column " + locator.getColumnNumber() + "!\nValue: " + l);
//                    }
//                    properties[1] = l;
//                    break;
//                case HEIGHT:
//                    l = Long.parseLong(s);
//                    if (l < 5 || l > 24) {
//                        throw new InvalidDimensionException("Invalid log file height at line " + locator.getLineNumber() +
//                                ", column " + locator.getColumnNumber() + "!\nValue: " + l);
//                    }
//                    properties[2] = l;
//                    break;
//                case MINES:
//                    l = Long.parseLong(s);
//                    if (l == 0 || l >= (properties[1] * properties[2])) {
//                        throw new InvalidCountException("Invalid number of mines at line " + locator.getLineNumber() +
//                                ", column " + locator.getColumnNumber() + "!\nValue: " + l);
//                    }
//                    properties[3] = l;
//                    break;
//            }
//        } catch (NumberFormatException e) {
//            throw new SAXException("Invalid number format! Line " + locator.getLineNumber() + " " +
//                    "Column " + locator.getColumnNumber());
//        }
//    }
//
//    /**
//     * Receive notification of a warning.
//     *
//     * <p>SAX parsers will use this method to report conditions that
//     * are not errors or fatal errors as defined by the XML
//     * recommendation.  The default behaviour is to take no
//     * action.</p>
//     *
//     * <p>The SAX parser must continue to provide normal parsing events
//     * after invoking this method: it should still be possible for the
//     * application to process the document through to the end.</p>
//     *
//     * <p>Filters may use this method to report other, non-XML warnings
//     * as well.</p>
//     *
//     * @param exception The warning information encapsulated in a
//     *                  SAX parse exception.
//     * @see SAXParseException
//     */
//    @Override
//    public void warning(SAXParseException exception) {
//        System.out.println("Warning! Parser threw a warning event!");
//    }
//
//    /**
//     * Receive notification of a recoverable error.
//     *
//     * <p>This corresponds to the definition of "error" in section 1.2
//     * of the W3C XML 1.0 Recommendation.  For example, a validating
//     * parser would use this callback to report the violation of a
//     * validity constraint.  The default behaviour is to take no
//     * action.</p>
//     *
//     * <p>The SAX parser must continue to provide normal parsing
//     * events after invoking this method: it should still be possible
//     * for the application to process the document through to the end.
//     * If the application cannot do so, then the parser should report
//     * a fatal error even if the XML recommendation does not require
//     * it to do so.</p>
//     *
//     * <p>Filters may use this method to report other, non-XML errors
//     * as well.</p>
//     *
//     * @param exception The error information encapsulated in a
//     *                  SAX parse exception.
//     * @see SAXParseException
//     */
//    @Override
//    public void error(SAXParseException exception) {
//        System.out.println("Error! Parser threw an error event!");
//    }
//
//    /**
//     * Receive notification of a non-recoverable error.
//     *
//     * <p><strong>There is an apparent contradiction between the
//     * documentation for this method and the documentation for {@link
//     * ContentHandler#endDocument}.  Until this ambiguity
//     * is resolved in a future major release, clients should make no
//     * assumptions about whether endDocument() will or will not be
//     * invoked when the parser has reported a fatalError() or thrown
//     * an exception.</strong></p>
//     *
//     * <p>This corresponds to the definition of "fatal error" in
//     * section 1.2 of the W3C XML 1.0 Recommendation.  For example, a
//     * parser would use this callback to report the violation of a
//     * well-formedness constraint.</p>
//     *
//     * <p>The application must assume that the document is unusable
//     * after the parser has invoked this method, and should continue
//     * (if at all) only for the sake of collecting additional error
//     * messages: in fact, SAX parsers are free to stop reporting any
//     * other events once this method has been invoked.</p>
//     *
//     * @param exception The error information encapsulated in a
//     *                  SAX parse exception.
//     * @see SAXParseException
//     */
//    @Override
//    public void fatalError(SAXParseException exception) {
//        System.out.println("Fatal Error! Parser threw a fatal error event!");
//    }
//
//    /**
//     * Report an XML comment anywhere in the document.
//     *
//     * <p>This callback will be used for comments inside or outside the
//     * document element, including comments in the external DTD
//     * subset (if read).  Comments in the DTD must be properly
//     * nested inside start/endDTD and start/endEntity events (if
//     * used).</p>
//     *
//     * @param ch     An array holding the characters in the comment.
//     * @param start  The starting position in the array.
//     * @param length The number of characters to use from the array.
//     */
//    @Override
//    public void comment(char[] ch, int start, int length) {
//        String s = new String(ch);
//        s = s.substring(start, start + length);
//        s = s.trim();
//        System.out.println("Comment Found! " + s);
//    }
//
//    /**
//     * Gets the array of values pulled form the header properties
//     * @return Array of longs
//     */
//    public long[] getPropertyValues() {
//        return properties;
//    }
//
//    /**
//     * Gets the list of moves read from the log file
//     * @return List containing all the moves from the log file
//     */
//    public List<Move> getMoves() {
//        return moves;
//    }
//}
