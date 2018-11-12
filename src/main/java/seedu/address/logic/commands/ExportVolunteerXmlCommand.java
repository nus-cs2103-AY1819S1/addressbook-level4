package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
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
import seedu.address.model.event.EventId;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * Exports a person's volunteer information from SocialCare
 */
public class ExportVolunteerXmlCommand extends Command {

    public static final String COMMAND_WORD = "exportvolunteerxml";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports an XML file of the volunteer "
            + "the specified index in the displayed volunteer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_EXPORT_VOLUNTEER_SUCCESS = "Volunteer exported for volunteer at %1$d ";
    private static final String MESSAGE_EXPORT_VOLUNTEER_FAILED = "Volunteer export failed, please try again.";
    private static final String TRANSFORMER_ERROR = "Transformer error.";
    private static final String PARSER_ERROR = "Parser error.";


    private static final String DEFAULT_SAVE_PATH = System.getProperty("user.dir") + "/Volunteer Xml/";
    private static final String ALT_SAVE_PATH = System.getProperty("user.home") + "/Desktop/";
    private static String SAVE_PATH = DEFAULT_SAVE_PATH;

    private static final java.util.logging.Logger logger = LogsCenter.getLogger(ExportVolunteerXmlCommand.class);


    private final Index index;
    private final String exportTypeV = "VOLUNTEER";

    /**
     * @param index of volunteer or event in the filtered list
     */
    public ExportVolunteerXmlCommand(Index index) {
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
        List<Volunteer> list = model.getFilteredVolunteerList();

        // Handle case where the index input exceeds or equals the size of the last displayed list
        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        // Get the Volunteer object whom the index corresponds to
        Volunteer selectedVolunteer = list.get(index.getZeroBased());

        try {
            createVolunteerXml(model, selectedVolunteer);
        } catch (TransformerException e) {
            throw new CommandException(MESSAGE_EXPORT_VOLUNTEER_FAILED + TRANSFORMER_ERROR);
        } catch (ParserConfigurationException e) {
            throw new CommandException(MESSAGE_EXPORT_VOLUNTEER_FAILED + PARSER_ERROR);
        }

        return new CommandResult(MESSAGE_EXPORT_VOLUNTEER_SUCCESS);

    }

    /**
     * Creates and write an XML of the volunteer details & events to user desktop
     * @param volunteer who's data to write
     * @param model to access volunteer event records
     */
    private void createVolunteerXml(Model model, Volunteer volunteer) throws TransformerException,
            ParserConfigurationException {
        //setting up the document builders
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docuBuilder = dbFactory.newDocumentBuilder();
        Document doc = docuBuilder.newDocument();

        //root element - currently tag as volunteer
        Element rootElement = doc.createElement(exportTypeV);
        doc.appendChild(rootElement);

        //root attributes to this particular volunteer listing the details
        Attr nameAttr = doc.createAttribute("Name");
        nameAttr.setValue(volunteer.getName().toString());
        rootElement.setAttributeNode(nameAttr);

        Attr phoneAttr = doc.createAttribute("Phone");
        phoneAttr.setValue(volunteer.getPhone().toString());
        rootElement.setAttributeNode(phoneAttr);

        Attr addressAttr = doc.createAttribute("Address");
        addressAttr.setValue(volunteer.getAddress().toString());
        rootElement.setAttributeNode(addressAttr);

        Attr emailAttr = doc.createAttribute("Email");
        emailAttr.setValue(volunteer.getEmail().toString());
        rootElement.setAttributeNode(emailAttr);


        Attr bdayAttr = doc.createAttribute("Birthday");
        bdayAttr.setValue(volunteer.getBirthday().toString());
        rootElement.setAttributeNode(bdayAttr);

        Attr genderAttr = doc.createAttribute("Gender");
        genderAttr.setValue(volunteer.getGender().toString());
        rootElement.setAttributeNode(genderAttr);

        Attr tagsAttr = doc.createAttribute("Tags");
        tagsAttr.setValue(volunteer.getTags().toString());
        rootElement.setAttributeNode(tagsAttr);

        Attr idAttr = doc.createAttribute("VolunteerID");
        idAttr.setValue(volunteer.getVolunteerId().toString());
        rootElement.setAttributeNode(idAttr);


        //elements 1 level below root - this is used to store events
        // Retrieve the volunteer's events
        List<Record> eventRecords = model.getFilteredRecordList().filtered((x) -> x.getVolunteerId()
                .equals(volunteer.getVolunteerId()));

        for (int i = 1; i <= eventRecords.size(); i++) {
            //take note of 0 & 1 indexing difference
            Element element = doc.createElement("EVENT" + Integer.toString(i));
            Record r = eventRecords.get(i - 1);
            EventId eventId = r.getEventId();
            Event event = model.getFilteredEventList().filtered(e -> e.getEventId().equals(eventId)).get(0);

            //set attr
            //this is used to store event details
            Attr eventNameAttr = doc.createAttribute("Name");
            eventNameAttr.setValue(event.getName().toString());
            element.setAttributeNode(eventNameAttr);

            Attr eventStartDateAttr = doc.createAttribute("Start Date");
            eventStartDateAttr.setValue(event.getStartDate().toString());
            element.setAttributeNode(eventStartDateAttr);

            Attr eventStartTimeAttr = doc.createAttribute("Start Time");
            eventStartTimeAttr.setValue(event.getStartTime().toString());
            element.setAttributeNode(eventStartTimeAttr);

            Attr eventEndDateAttr = doc.createAttribute("End Date");
            eventEndDateAttr.setValue(event.getEndDate().toString());
            element.setAttributeNode(eventEndDateAttr);

            Attr eventEndTimeAttr = doc.createAttribute("End Time");
            eventEndTimeAttr.setValue(event.getEndTime().toString());
            element.setAttributeNode(eventEndTimeAttr);

            Attr eventLocationAttr = doc.createAttribute("Location");
            eventLocationAttr.setValue(event.getLocation().toString());
            element.setAttributeNode(eventLocationAttr);

            Attr eventDescriptionAttr = doc.createAttribute("Description");
            eventDescriptionAttr.setValue(event.getDescription().toString());
            element.setAttributeNode(eventDescriptionAttr);

            Attr eventIdAttr = doc.createAttribute("Event ID");
            eventIdAttr.setValue(eventId.toString());
            element.setAttributeNode(eventIdAttr);

            //append node
            rootElement.appendChild(element);
        }

        // Setting up transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        // Setting up file path
        File output = new File(SAVE_PATH
                + volunteer.getName().toString() + ".xml");

        // writing to file
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }
}
