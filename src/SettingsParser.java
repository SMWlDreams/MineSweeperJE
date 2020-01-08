import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;

public class SettingsParser implements ErrorHandler, ContentHandler, LexicalHandler {
    private enum States {INIT, SETTINGS, LAUNCHSETTINGS, LAUNCH, SETSEED, SEED, HASH, LOGS, HOTKEYS,
        RESTART, PAUSE, NEWGAME, HELP, HIGHSCORE, ABOUT, OTHERSETTINGS, LINES}
    private States state = States.INIT;
    private int errorLevel = 0;
    private Locator locator;
    private String version;

    /**
     * Receive an object for locating the origin of SAX document events.
     *
     * <p>SAX parsers are strongly encouraged (though not absolutely
     * required) to supply a locator: if it does so, it must supply
     * the locator to the application by invoking this method before
     * invoking any of the other methods in the ContentHandler
     * interface.</p>
     *
     * <p>The locator allows the application to determine the end
     * position of any document-related event, even if the parser is
     * not reporting an error.  Typically, the application will
     * use this information for reporting its own errors (such as
     * character content that does not match an application's
     * business rules).  The information returned by the locator
     * is probably not sufficient for use with a search engine.</p>
     *
     * <p>Note that the locator will return correct information only
     * during the invocation SAX event callbacks after
     * {@link #startDocument startDocument} returns and before
     * {@link #endDocument endDocument} is called.  The
     * application should not attempt to use it at any other time.</p>
     *
     * @param locator an object that can return the location of
     *                any SAX document event
     * @see Locator
     */
    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    /**
     * Receive notification of the beginning of a document.
     *
     * <p>The SAX parser will invoke this method only once, before any
     * other event callbacks (except for {@link #setDocumentLocator
     * setDocumentLocator}).</p>
     *
     * @throws SAXException any SAX exception, possibly
     *                      wrapping another exception
     * @see #endDocument
     */
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Document Parsing Beginning!");
    }

    /**
     * Receive notification of the end of a document.
     *
     * <p><strong>There is an apparent contradiction between the
     * documentation for this method and the documentation for {@link
     * ErrorHandler#fatalError}.  Until this ambiguity is
     * resolved in a future major release, clients should make no
     * assumptions about whether endDocument() will or will not be
     * invoked when the parser has reported a fatalError() or thrown
     * an exception.</strong></p>
     *
     * <p>The SAX parser will invoke this method only once, and it will
     * be the last method invoked during the parse.  The parser shall
     * not invoke this method until it has either abandoned parsing
     * (because of an unrecoverable error) or reached the end of
     * input.</p>
     *
     * @throws SAXException any SAX exception, possibly
     *                      wrapping another exception
     * @see #startDocument
     */
    @Override
    public void endDocument() throws SAXException {
        System.out.println("Document Parsing Complete!");
    }

    /**
     * Begin the scope of a prefix-URI Namespace mapping.
     *
     * <p>The information from this event is not necessary for
     * normal Namespace processing: the SAX XML reader will
     * automatically replace prefixes for element and attribute
     * names when the <code>http://xml.org/sax/features/namespaces</code>
     * feature is <var>true</var> (the default).</p>
     *
     * <p>There are cases, however, when applications need to
     * use prefixes in character data or in attribute values,
     * where they cannot safely be expanded automatically; the
     * start/endPrefixMapping event supplies the information
     * to the application to expand prefixes in those contexts
     * itself, if necessary.</p>
     *
     * <p>Note that start/endPrefixMapping events are not
     * guaranteed to be properly nested relative to each other:
     * all startPrefixMapping events will occur immediately before the
     * corresponding {@link #startElement startElement} event,
     * and all {@link #endPrefixMapping endPrefixMapping}
     * events will occur immediately after the corresponding
     * {@link #endElement endElement} event,
     * but their order is not otherwise
     * guaranteed.</p>
     *
     * <p>There should never be start/endPrefixMapping events for the
     * "xml" prefix, since it is predeclared and immutable.</p>
     *
     * @param prefix the Namespace prefix being declared.
     *               An empty string is used for the default element namespace,
     *               which has no prefix.
     * @param uri    the Namespace URI the prefix is mapped to
     * @throws SAXException the client may throw
     *                      an exception during processing
     * @see #endPrefixMapping
     * @see #startElement
     */
    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }

    /**
     * End the scope of a prefix-URI mapping.
     *
     * <p>See {@link #startPrefixMapping startPrefixMapping} for
     * details.  These events will always occur immediately after the
     * corresponding {@link #endElement endElement} event, but the order of
     * {@link #endPrefixMapping endPrefixMapping} events is not otherwise
     * guaranteed.</p>
     *
     * @param prefix the prefix that was being mapped.
     *               This is the empty string when a default mapping scope ends.
     * @throws SAXException the client may throw
     *                      an exception during processing
     * @see #startPrefixMapping
     * @see #endElement
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

    }

    /**
     * Receive notification of the beginning of an element.
     *
     * <p>The Parser will invoke this method at the beginning of every
     * element in the XML document; there will be a corresponding
     * {@link #endElement endElement} event for every startElement event
     * (even when the element is empty). All of the element's content will be
     * reported, in order, before the corresponding endElement
     * event.</p>
     *
     * <p>This event allows up to three name components for each
     * element:</p>
     *
     * <ol>
     * <li>the Namespace URI;</li>
     * <li>the local name; and</li>
     * <li>the qualified (prefixed) name.</li>
     * </ol>
     *
     * <p>Any or all of these may be provided, depending on the
     * values of the <var>http://xml.org/sax/features/namespaces</var>
     * and the <var>http://xml.org/sax/features/namespace-prefixes</var>
     * properties:</p>
     *
     * <ul>
     * <li>the Namespace URI and local name are required when
     * the namespaces property is <var>true</var> (the default), and are
     * optional when the namespaces property is <var>false</var> (if one is
     * specified, both must be);</li>
     * <li>the qualified name is required when the namespace-prefixes property
     * is <var>true</var>, and is optional when the namespace-prefixes property
     * is <var>false</var> (the default).</li>
     * </ul>
     *
     * <p>Note that the attribute list provided will contain only
     * attributes with explicit values (specified or defaulted):
     * #IMPLIED attributes will be omitted.  The attribute list
     * will contain attributes used for Namespace declarations
     * (xmlns* attributes) only if the
     * <code>http://xml.org/sax/features/namespace-prefixes</code>
     * property is true (it is false by default, and support for a
     * true value is optional).</p>
     *
     * <p>Like {@link #characters characters()}, attribute values may have
     * characters that need more than one <code>char</code> value.  </p>
     *
     * @param uri       the Namespace URI, or the empty string if the
     *                  element has no Namespace URI or if Namespace
     *                  processing is not being performed
     * @param localName the local name (without prefix), or the
     *                  empty string if Namespace processing is not being
     *                  performed
     * @param qName     the qualified name (with prefix), or the
     *                  empty string if qualified names are not available
     * @param atts      the attributes attached to the element.  If
     *                  there are no attributes, it shall be an empty
     *                  Attributes object.  The value of this object after
     *                  startElement returns is undefined
     * @throws SAXException any SAX exception, possibly
     *                      wrapping another exception
     * @see #endElement
     * @see Attributes
     * @see AttributesImpl
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        switch (state) {
            case INIT:
                if (!localName.equalsIgnoreCase("Settings")) {
                    throw new SAXException("Expected Settings, Found " + localName);
                }
                state = States.SETTINGS;
                version = atts.getValue("version");
                break;
            case SETTINGS:
                switch (localName.toLowerCase()) {
                    case "launchsettings":
                        state = States.LAUNCHSETTINGS;
                        break;
                    case "hotkeys":
                        state = States.HOTKEYS;
                        break;
                    case "othersettings":
                        state = States.OTHERSETTINGS;
                        break;
                    default:
                        throw new SAXException("Invalid tag following settings, found " + localName);
                }
                break;
            case LAUNCHSETTINGS:
                switch (localName.toLowerCase()) {
                    case "launch":
                        state = States.LAUNCH;
                        break;
                    case "setseed":
                        state = States.SETSEED;
                        break;
                    case "seed":
                        state = States.SEED;
                        break;
                    case "hash":
                        state = States.HASH;
                        break;
                    case "logs":
                        state = States.LOGS;
                        break;
                    default:
                        throw new SAXException("Invalid tag following LaunchSettings, found " + localName);
                }
                break;
            case HOTKEYS:
                switch (localName.toLowerCase()) {
                    case "restart":
                        state = States.RESTART;
                        break;
                    case "pause":
                        state = States.PAUSE;
                        break;
                    case "newgame":
                        state = States.NEWGAME;
                        break;
                    case "help":
                        state = States.HELP;
                        break;
                    case "highscores":
                        state = States.HIGHSCORE;
                        break;
                    case "about":
                        state = States.ABOUT;
                        break;
                    default:
                        throw new SAXException("Invalid tag following hotkeys, found " + localName);
                }
                break;
            case OTHERSETTINGS:
                switch (localName.toLowerCase()) {
                    case "lines":
                        state = States.LINES;
                        break;
                    default:
                        throw new SAXException("Invalid tag following OtherSettings, found " + localName);
                }
                break;
        }
    }

    /**
     * Receive notification of the end of an element.
     *
     * <p>The SAX parser will invoke this method at the end of every
     * element in the XML document; there will be a corresponding
     * {@link #startElement startElement} event for every endElement
     * event (even when the element is empty).</p>
     *
     * <p>For information on the names, see startElement.</p>
     *
     * @param uri       the Namespace URI, or the empty string if the
     *                  element has no Namespace URI or if Namespace
     *                  processing is not being performed
     * @param localName the local name (without prefix), or the
     *                  empty string if Namespace processing is not being
     *                  performed
     * @param qName     the qualified XML name (with prefix), or the
     *                  empty string if qualified names are not available
     * @throws SAXException any SAX exception, possibly
     *                      wrapping another exception
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (state) {
            case INIT:
                throw new SAXException("Invalid ending tag, found " + localName);
            case SETTINGS:
                switch (localName.toLowerCase()) {
                    case "launchsettings":
                        state = States.LAUNCHSETTINGS;
                        break;
                    case "hotkeys":
                        state = States.HOTKEYS;
                        break;
                    case "othersettings":
                        state = States.OTHERSETTINGS;
                        break;
                    default:
                        throw new SAXException("Invalid tag following settings, found " + localName);
                }
                break;
            case LAUNCHSETTINGS:
                switch (localName.toLowerCase()) {
                    case "launch":
                        state = States.LAUNCH;
                        break;
                    case "setseed":
                        state = States.SETSEED;
                        break;
                    case "seed":
                        state = States.SEED;
                        break;
                    case "hash":
                        state = States.HASH;
                        break;
                    case "logs":
                        state = States.LOGS;
                        break;
                    default:
                        throw new SAXException("Invalid tag following LaunchSettings, found " + localName);
                }
                break;
            case HOTKEYS:
                switch (localName.toLowerCase()) {
                    case "restart":
                        state = States.RESTART;
                        break;
                    case "pause":
                        state = States.PAUSE;
                        break;
                    case "newgame":
                        state = States.NEWGAME;
                        break;
                    case "help":
                        state = States.HELP;
                        break;
                    case "highscores":
                        state = States.HIGHSCORE;
                        break;
                    case "about":
                        state = States.ABOUT;
                        break;
                    default:
                        throw new SAXException("Invalid tag following hotkeys, found " + localName);
                }
                break;
            case OTHERSETTINGS:
                switch (localName.toLowerCase()) {
                    case "lines":
                        state = States.LINES;
                        break;
                    default:
                        throw new SAXException("Invalid tag following OtherSettings, found " + localName);
                }
                break;
        }
    }

    /**
     * Receive notification of character data.
     *
     * <p>The Parser will call this method to report each chunk of
     * character data.  SAX parsers may return all contiguous character
     * data in a single chunk, or they may split it into several
     * chunks; however, all of the characters in any single event
     * must come from the same external entity so that the Locator
     * provides useful information.</p>
     *
     * <p>The application must not attempt to read from the array
     * outside of the specified range.</p>
     *
     * <p>Individual characters may consist of more than one Java
     * <code>char</code> value.  There are two important cases where this
     * happens, because characters can't be represented in just sixteen bits.
     * In one case, characters are represented in a <em>Surrogate Pair</em>,
     * using two special Unicode values. Such characters are in the so-called
     * "Astral Planes", with a code point above U+FFFF.  A second case involves
     * composite characters, such as a base character combining with one or
     * more accent characters. </p>
     *
     * <p> Your code should not assume that algorithms using
     * <code>char</code>-at-a-time idioms will be working in character
     * units; in some cases they will split characters.  This is relevant
     * wherever XML permits arbitrary characters, such as attribute values,
     * processing instruction data, and comments as well as in data reported
     * from this method.  It's also generally relevant whenever Java code
     * manipulates internationalized text; the issue isn't unique to XML.</p>
     *
     * <p>Note that some parsers will report whitespace in element
     * content using the {@link #ignorableWhitespace ignorableWhitespace}
     * method rather than this one (validating parsers <em>must</em>
     * do so).</p>
     *
     * @param ch     the characters from the XML document
     * @param start  the start position in the array
     * @param length the number of characters to read from the array
     * @throws SAXException any SAX exception, possibly
     *                      wrapping another exception
     * @see #ignorableWhitespace
     * @see Locator
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }

    /**
     * Receive notification of ignorable whitespace in element content.
     *
     * <p>Validating Parsers must use this method to report each chunk
     * of whitespace in element content (see the W3C XML 1.0
     * recommendation, section 2.10): non-validating parsers may also
     * use this method if they are capable of parsing and using
     * content models.</p>
     *
     * <p>SAX parsers may return all contiguous whitespace in a single
     * chunk, or they may split it into several chunks; however, all of
     * the characters in any single event must come from the same
     * external entity, so that the Locator provides useful
     * information.</p>
     *
     * <p>The application must not attempt to read from the array
     * outside of the specified range.</p>
     *
     * @param ch     the characters from the XML document
     * @param start  the start position in the array
     * @param length the number of characters to read from the array
     * @throws SAXException any SAX exception, possibly
     *                      wrapping another exception
     * @see #characters
     */
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    /**
     * Receive notification of a processing instruction.
     *
     * <p>The Parser will invoke this method once for each processing
     * instruction found: note that processing instructions may occur
     * before or after the main document element.</p>
     *
     * <p>A SAX parser must never report an XML declaration (XML 1.0,
     * section 2.8) or a text declaration (XML 1.0, section 4.3.1)
     * using this method.</p>
     *
     * <p>Like {@link #characters characters()}, processing instruction
     * data may have characters that need more than one <code>char</code>
     * value. </p>
     *
     * @param target the processing instruction target
     * @param data   the processing instruction data, or null if
     *               none was supplied.  The data does not include any
     *               whitespace separating it from the target
     * @throws SAXException any SAX exception, possibly
     *                      wrapping another exception
     */
    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    /**
     * Receive notification of a skipped entity.
     * This is not called for entity references within markup constructs
     * such as element start tags or markup declarations.  (The XML
     * recommendation requires reporting skipped external entities.
     * SAX also reports internal entity expansion/non-expansion, except
     * within markup constructs.)
     *
     * <p>The Parser will invoke this method each time the entity is
     * skipped.  Non-validating processors may skip entities if they
     * have not seen the declarations (because, for example, the
     * entity was declared in an external DTD subset).  All processors
     * may skip external entities, depending on the values of the
     * <code>http://xml.org/sax/features/external-general-entities</code>
     * and the
     * <code>http://xml.org/sax/features/external-parameter-entities</code>
     * properties.</p>
     *
     * @param name the name of the skipped entity.  If it is a
     *             parameter entity, the name will begin with '%', and if
     *             it is the external DTD subset, it will be the string
     *             "[dtd]"
     * @throws SAXException any SAX exception, possibly
     *                      wrapping another exception
     */
    @Override
    public void skippedEntity(String name) throws SAXException {

    }

    /**
     * Receive notification of a warning.
     *
     * <p>SAX parsers will use this method to report conditions that
     * are not errors or fatal errors as defined by the XML
     * recommendation.  The default behaviour is to take no
     * action.</p>
     *
     * <p>The SAX parser must continue to provide normal parsing events
     * after invoking this method: it should still be possible for the
     * application to process the document through to the end.</p>
     *
     * <p>Filters may use this method to report other, non-XML warnings
     * as well.</p>
     *
     * @param exception The warning information encapsulated in a
     *                  SAX parse exception.
     * @throws SAXException Any SAX exception, possibly
     *                      wrapping another exception.
     * @see SAXParseException
     */
    @Override
    public void warning(SAXParseException exception) throws SAXException {

    }

    /**
     * Receive notification of a recoverable error.
     *
     * <p>This corresponds to the definition of "error" in section 1.2
     * of the W3C XML 1.0 Recommendation.  For example, a validating
     * parser would use this callback to report the violation of a
     * validity constraint.  The default behaviour is to take no
     * action.</p>
     *
     * <p>The SAX parser must continue to provide normal parsing
     * events after invoking this method: it should still be possible
     * for the application to process the document through to the end.
     * If the application cannot do so, then the parser should report
     * a fatal error even if the XML recommendation does not require
     * it to do so.</p>
     *
     * <p>Filters may use this method to report other, non-XML errors
     * as well.</p>
     *
     * @param exception The error information encapsulated in a
     *                  SAX parse exception.
     * @throws SAXException Any SAX exception, possibly
     *                      wrapping another exception.
     * @see SAXParseException
     */
    @Override
    public void error(SAXParseException exception) throws SAXException {

    }

    /**
     * Receive notification of a non-recoverable error.
     *
     * <p><strong>There is an apparent contradiction between the
     * documentation for this method and the documentation for {@link
     * ContentHandler#endDocument}.  Until this ambiguity
     * is resolved in a future major release, clients should make no
     * assumptions about whether endDocument() will or will not be
     * invoked when the parser has reported a fatalError() or thrown
     * an exception.</strong></p>
     *
     * <p>This corresponds to the definition of "fatal error" in
     * section 1.2 of the W3C XML 1.0 Recommendation.  For example, a
     * parser would use this callback to report the violation of a
     * well-formedness constraint.</p>
     *
     * <p>The application must assume that the document is unusable
     * after the parser has invoked this method, and should continue
     * (if at all) only for the sake of collecting additional error
     * messages: in fact, SAX parsers are free to stop reporting any
     * other events once this method has been invoked.</p>
     *
     * @param exception The error information encapsulated in a
     *                  SAX parse exception.
     * @throws SAXException Any SAX exception, possibly
     *                      wrapping another exception.
     * @see SAXParseException
     */
    @Override
    public void fatalError(SAXParseException exception) throws SAXException {

    }

    /**
     * Report the start of DTD declarations, if any.
     *
     * <p>This method is intended to report the beginning of the
     * DOCTYPE declaration; if the document has no DOCTYPE declaration,
     * this method will not be invoked.</p>
     *
     * <p>All declarations reported through
     * {@link DTDHandler DTDHandler} or
     * {@link DeclHandler DeclHandler} events must appear
     * between the startDTD and {@link #endDTD endDTD} events.
     * Declarations are assumed to belong to the internal DTD subset
     * unless they appear between {@link #startEntity startEntity}
     * and {@link #endEntity endEntity} events.  Comments and
     * processing instructions from the DTD should also be reported
     * between the startDTD and endDTD events, in their original
     * order of (logical) occurrence; they are not required to
     * appear in their correct locations relative to DTDHandler
     * or DeclHandler events, however.</p>
     *
     * <p>Note that the start/endDTD events will appear within
     * the start/endDocument events from ContentHandler and
     * before the first
     * {@link ContentHandler#startElement startElement}
     * event.</p>
     *
     * @param name     The document type name.
     * @param publicId The declared public identifier for the
     *                 external DTD subset, or null if none was declared.
     * @param systemId The declared system identifier for the
     *                 external DTD subset, or null if none was declared.
     *                 (Note that this is not resolved against the document
     *                 base URI.)
     * @throws SAXException The application may raise an
     *                      exception.
     * @see #endDTD
     * @see #startEntity
     */
    @Override
    public void startDTD(String name, String publicId, String systemId) throws SAXException {

    }

    /**
     * Report the end of DTD declarations.
     *
     * <p>This method is intended to report the end of the
     * DOCTYPE declaration; if the document has no DOCTYPE declaration,
     * this method will not be invoked.</p>
     *
     * @throws SAXException The application may raise an exception.
     * @see #startDTD
     */
    @Override
    public void endDTD() throws SAXException {

    }

    /**
     * Report the beginning of some internal and external XML entities.
     *
     * <p>The reporting of parameter entities (including
     * the external DTD subset) is optional, and SAX2 drivers that
     * report LexicalHandler events may not implement it; you can use the
     * <code
     * >http://xml.org/sax/features/lexical-handler/parameter-entities</code>
     * feature to query or control the reporting of parameter entities.</p>
     *
     * <p>General entities are reported with their regular names,
     * parameter entities have '%' prepended to their names, and
     * the external DTD subset has the pseudo-entity name "[dtd]".</p>
     *
     * <p>When a SAX2 driver is providing these events, all other
     * events must be properly nested within start/end entity
     * events.  There is no additional requirement that events from
     * {@link DeclHandler DeclHandler} or
     * {@link DTDHandler DTDHandler} be properly ordered.</p>
     *
     * <p>Note that skipped entities will be reported through the
     * {@link ContentHandler#skippedEntity skippedEntity}
     * event, which is part of the ContentHandler interface.</p>
     *
     * <p>Because of the streaming event model that SAX uses, some
     * entity boundaries cannot be reported under any
     * circumstances:</p>
     *
     * <ul>
     * <li>general entities within attribute values</li>
     * <li>parameter entities within declarations</li>
     * </ul>
     *
     * <p>These will be silently expanded, with no indication of where
     * the original entity boundaries were.</p>
     *
     * <p>Note also that the boundaries of character references (which
     * are not really entities anyway) are not reported.</p>
     *
     * <p>All start/endEntity events must be properly nested.
     *
     * @param name The name of the entity.  If it is a parameter
     *             entity, the name will begin with '%', and if it is the
     *             external DTD subset, it will be "[dtd]".
     * @throws SAXException The application may raise an exception.
     * @see #endEntity
     * @see DeclHandler#internalEntityDecl
     * @see DeclHandler#externalEntityDecl
     */
    @Override
    public void startEntity(String name) throws SAXException {

    }

    /**
     * Report the end of an entity.
     *
     * @param name The name of the entity that is ending.
     * @throws SAXException The application may raise an exception.
     * @see #startEntity
     */
    @Override
    public void endEntity(String name) throws SAXException {

    }

    /**
     * Report the start of a CDATA section.
     *
     * <p>The contents of the CDATA section will be reported through
     * the regular {@link ContentHandler#characters
     * characters} event; this event is intended only to report
     * the boundary.</p>
     *
     * @throws SAXException The application may raise an exception.
     * @see #endCDATA
     */
    @Override
    public void startCDATA() throws SAXException {

    }

    /**
     * Report the end of a CDATA section.
     *
     * @throws SAXException The application may raise an exception.
     * @see #startCDATA
     */
    @Override
    public void endCDATA() throws SAXException {

    }

    /**
     * Report an XML comment anywhere in the document.
     *
     * <p>This callback will be used for comments inside or outside the
     * document element, including comments in the external DTD
     * subset (if read).  Comments in the DTD must be properly
     * nested inside start/endDTD and start/endEntity events (if
     * used).</p>
     *
     * @param ch     An array holding the characters in the comment.
     * @param start  The starting position in the array.
     * @param length The number of characters to use from the array.
     * @throws SAXException The application may raise an exception.
     */
    @Override
    public void comment(char[] ch, int start, int length) throws SAXException {

    }
}
