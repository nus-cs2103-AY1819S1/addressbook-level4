package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventContainsEventIdPredicate;
import seedu.address.model.event.EventId;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordContainsVolunteerIdPredicate;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerId;

/**
 * Exports a PDF document with data on a volunteer's involvement with the organisation.
 */
public class ExportCertCommand extends Command {

    public static final String COMMAND_WORD = "exportcert";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports a PDF certificate for the volunteer at "
            + "the specified index in the displayed volunteer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";
    public static final String MESSAGE_EXPORT_CERT_SUCCESS = "Certificate exported for volunteer at %1$d to ";
    public static final String MESSAGE_EXPORT_FAILED = "Certificate export failed, please try again";
    public static final String PDF_SAVE_PATH = System.getProperty("user.dir") + "/Volunteer Certs/";
    public static final String PDF_ALT_SAVE_PATH = System.getProperty("user.home") + "/Desktop/";
    public static final String MESSAGE_VOLUNTEER_NO_RECORD = "Selected volunteer has no stored event records";

    private static final java.util.logging.Logger logger = LogsCenter.getLogger(ExportCertCommand.class);

    private static String currentSavePath = PDF_SAVE_PATH;

    private final Index index;

    /**
     * @param index of the volunteer in the filtered volunteer list whose certificate is to be generated and exported
     */
    public ExportCertCommand(Index index) {
        requireNonNull(index);
        this.index = index;

        // Create a folder in user's working directory to export certificates to, if possible
        File exportDir = new File(currentSavePath);
        if (!exportDir.exists()) {
            try {
                exportDir.mkdir();
                logger.info("Creating a new folder 'Volunteer Certs' in user's current working directory.");
            } catch (SecurityException se) {
                logger.warning("Couldn't create a relative export path next to jar file. "
                        + "Defaulting to user's Desktop.");
                currentSavePath = PDF_ALT_SAVE_PATH;
            }
        }
    }

    public static String getCurrentSavePath() {
        return currentSavePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Volunteer> lastShownList = model.getFilteredVolunteerList();

        // Handle case where the index input exceeds or equals the size of the last displayed list
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        // Get the Volunteer object whom the index corresponds to
        Volunteer selectedVolunteer = lastShownList.get(index.getZeroBased());

        // Return CommandException if volunteer has no records
        if (!hasEventRecords(model, selectedVolunteer)) {
            logger.info("Volunteer has no records.");
            throw new CommandException(MESSAGE_VOLUNTEER_NO_RECORD);
        }

        // Try creating and exporting the PDF for the selected volunteer
        try {
            createPdf(model, selectedVolunteer);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_EXPORT_FAILED);
        }

        // Return a success result
        return new CommandResult(String.format(MESSAGE_EXPORT_CERT_SUCCESS + currentSavePath, index.getOneBased()));
    }

    /**
     * Checks if a {@code volunteer} has any event {@code record}s.
     * @param model from which the {@code volunteer}'s {@code record}s will be retrieved, if present
     * @param volunteer who's presence of event {@code record}s is to be checked
     * @return true if {@code volunteer} has {@code record}s, and false otherwise
     */
    private boolean hasEventRecords(Model model, Volunteer volunteer) {
        VolunteerId volunteerId = volunteer.getVolunteerId();

        // Attempt to retrieve a list of the volunteer's records
        List<Record> eventRecords = model.getFilteredRecordList()
                .filtered(new RecordContainsVolunteerIdPredicate(volunteerId));

        return !eventRecords.isEmpty();
    }

    /**
     * Creates and exports a PDF document containing a {@code volunteer}'s data
     * @param model from which the volunteer's event records will be accessed
     * @param volunteer who's data is to be input into the PDF document
     */
    private void createPdf(Model model, Volunteer volunteer) throws IOException {
        // Retrieve the selected volunteer's attributes
        VolunteerId volunteerId = volunteer.getVolunteerId();
        Name volunteerName = volunteer.getName();

        // Retrieve the volunteer's event records
        List<Record> eventRecords = model.getFilteredRecordList()
                .filtered(new RecordContainsVolunteerIdPredicate(volunteerId));

        // Create the new document
        PDDocument doc = new PDDocument();

        // Create a new page and add it to the document
        PDPage page = new PDPage();
        doc.addPage(page);

        // Setup a new content stream to write to a page
        PDPageContentStream contStream = new PDPageContentStream(doc, page);

        // Populate the PDF with necessary details
        contStream.beginText();

        contStream.setLeading(20f);

        // Set title font
        PDFont titleFont = PDType1Font.TIMES_BOLD_ITALIC;
        float titleFontSize = 24;
        contStream.setFont(titleFont, titleFontSize);

        // Input title to the center of the page
        String title = "Certificate of Recognition";
        float titleWidth = titleFont.getStringWidth(title) * titleFontSize / 1000f;
        contStream.newLineAtOffset(page.getMediaBox().getWidth() / 2 - titleWidth / 2, 740);
        contStream.showText(title);
        contStream.newLine();
        contStream.newLine();

        // Volunteer Name, ID and current date section
        contStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 14);

        contStream.newLineAtOffset(-(page.getMediaBox().getWidth() / 2 - titleWidth / 2) + 20, 0);

        String volunteerNameLine = "Volunteer Name: " + volunteerName;
        contStream.showText(volunteerNameLine);
        contStream.newLine();

        String volunteerIdLine = "Volunteer ID: " + volunteerId;
        contStream.showText(volunteerIdLine);
        contStream.newLine();

        contStream.showText("Date: " + String.valueOf(LocalDate.now().format(DateTimeFormatter
                .ofPattern("dd-MM-yyyy"))));
        contStream.newLine();
        contStream.newLine();

        // Reduce the leading for main body of certificate
        contStream.setLeading(17f);

        // Standardised formality text
        String formalityTextLine1 = "To whomever it may concern,";
        contStream.showText(formalityTextLine1);
        contStream.newLine();
        contStream.newLine();

        String formalityTextLine2 = "This is to certify " + volunteerName
                + "'s contributions to our organisation via the following events:";
        contStream.showText(formalityTextLine2);
        contStream.newLine();

        // Event contribution information
        contStream.newLine();
        for (Record r: eventRecords) {
            // Information from event record
            Hour eventHours = r.getHour();
            EventId eventId = r.getEventId();

            // Get the exact corresponding event object and extract information from it
            List<Event> filteredEventList = model.getFilteredEventList()
                    .filtered(new EventContainsEventIdPredicate(eventId));
            assert(filteredEventList.size() == 1); // Make sure no duplicate events
            Event event = filteredEventList.get(0);
            seedu.address.model.event.Name eventName = event.getName();
            Date startDate = event.getStartDate();
            Date endDate = event.getEndDate();

            String eventEntryLine = eventName + " - " + eventHours + " hours from " + startDate + " to " + endDate;

            contStream.showText("\u2022  "); // bullet
            contStream.showText(eventEntryLine);
            contStream.newLine();
        }

        contStream.newLine();

        String appreciationLine = "We greatly appreciate " + volunteerName
                + "'s services rendered to our organisation.";
        contStream.showText(appreciationLine);
        contStream.newLine();

        contStream.newLine();

        String regardsLine = "Regards,";
        contStream.showText(regardsLine);
        contStream.newLine();
        contStream.newLine();
        contStream.newLine();

        // Line for user to manually sign off on the certificate
        contStream.showText("___________________");

        // Close the content stream
        contStream.endText();
        contStream.close();

        // Save document as <volunteerName>.pdf to the save path
        doc.save(PDF_SAVE_PATH + volunteerName + "_" + volunteerId + ".pdf");

        // Close the document
        doc.close();
    }

    @Override
    public boolean equals(Object other) {
        // Case: Both same object
        if (other == this) {
            return true;
        }

        // Case: Handle null, not instance of
        if (!(other instanceof ExportCertCommand)) {
            return false;
        }

        // Compare internal fields
        ExportCertCommand e = (ExportCertCommand) other;
        return index.equals(e.index);
    }
}
