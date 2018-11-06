package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Export an Event information as an XML
 */
public class ExportEventXmlCommand extends Command {

    public static final String COMMAND_WORD = "exporteventxml";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports an XML file of the event "
            + "given the file path to the xml file\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_EXPORT_EVENT_SUCCESS = "Event at %1$d exported"
    private static final String MESSAGE_EXPORT_EVENT_FAILED = "Event export failed, please try again.";
    private static final String TRANSFORMER_ERROR = "Transformer error.";
    private static final String PARSER_ERROR = "Parser error.";

    private static final String DEFAULT_SAVE_PATH = System.getProperty("user.dir") + "/Event Xml/";
    private static final String ALT_SAVE_PATH = System.getProperty("user.home") + "/Desktop/";
    private static String SAVE_PATH = DEFAULT_SAVE_PATH;

    private static final java.util.logging.Logger logger = LogsCenter.getLogger(ExportEventXmlCommand.class);


    private final Index index;
    private final String exportTypeE = "EVENT";

    /**
     * @param index of volunteer or event in the filtered list
     */
    public ExportEventXmlCommand(Index index) {
        requireNonNull(index);
        this.index = index;

        //Create folder for output
        File exportDir = new File(DEFAULT_SAVE_PATH);
        if (!exportDir.exists()) {
            try {
                exportDir.mkdir();
            } catch (SecurityException se) {
                logger.warning("Couldn't create a relative export path next to jar file. "
                        + "Defaulting to user's Desktop.");
                SAVE_PATH = ALT_SAVE_PATH;
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> list = model.getFilteredEventList();

        // Handle case where the index input exceeds or equals the size of the last displayed list
        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        // Get the Event object whom the index corresponds to
        Event selectedEvent = list.get(index.getZeroBased());

        try {
            createEventXml(model, selectedEvent);
        } catch (TransformerException e) {
            throw new CommandException(MESSAGE_EXPORT_EVENT_FAILED + TRANSFORMER_ERROR);
        } catch (ParserConfigurationException e) {
            throw new CommandException(MESSAGE_EXPORT_EVENT_FAILED + PARSER_ERROR);
        }

        return new CommandResult(MESSAGE_EXPORT_EVENT_SUCCESS);
    }

    /**
     * Helper method to do the file creation and data writing
     * @param model model to take the entire list
     * @param event the specified event given on index
     * @throws ParserConfigurationException - document builder errors
     * @throws TransformerException - transformer writing error
     */
    private void createEventXml(Model model, Event event) throws TransformerException,
            ParserConfigurationException {
        //setting up the document builders
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docuBuilder = dbFactory.newDocumentBuilder();
        Document doc = docuBuilder.newDocument();

        //root element - currently tag as event
        Element rootElement = doc.createElement(exportTypeE);
        doc.appendChild(rootElement);

        //root attributes to this particular event listing the details
        Attr eventNameAttr = doc.createAttribute("Name");
        eventNameAttr.setValue(event.getName().toString());
        rootElement.setAttributeNode(eventNameAttr);

        Attr eventStartDateAttr = doc.createAttribute("Start Date");
        eventStartDateAttr.setValue(event.getStartDate().toString());
        rootElement.setAttributeNode(eventStartDateAttr);

        Attr eventStartTimeAttr = doc.createAttribute("Start Time");
        eventStartTimeAttr.setValue(event.getStartTime().toString());
        rootElement.setAttributeNode(eventStartTimeAttr);

        Attr eventEndDateAttr = doc.createAttribute("End Date");
        eventEndDateAttr.setValue(event.getEndDate().toString());
        rootElement.setAttributeNode(eventEndDateAttr);

        Attr eventEndTimeAttr = doc.createAttribute("End Time");
        eventEndTimeAttr.setValue(event.getEndTime().toString());
        rootElement.setAttributeNode(eventEndTimeAttr);

        Attr eventLocationAttr = doc.createAttribute("Location");
        eventLocationAttr.setValue(event.getLocation().toString());
        rootElement.setAttributeNode(eventLocationAttr);

        Attr eventDescriptionAttr = doc.createAttribute("Description");
        eventDescriptionAttr.setValue(event.getDescription().toString());
        rootElement.setAttributeNode(eventDescriptionAttr);

        Attr eventIdAttr = doc.createAttribute("Event ID");
        eventIdAttr.setValue(event.getEventId().toString());
        rootElement.setAttributeNode(eventIdAttr);

        //elements 1 level below root - this is used to store volunteers for the event

        // Setting up transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        // Setting up file path
        File output = new File(SAVE_PATH
                + event.getName().toString() + event.getEventId().toString()
                + ".xml");

        // writing to file
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }
}
