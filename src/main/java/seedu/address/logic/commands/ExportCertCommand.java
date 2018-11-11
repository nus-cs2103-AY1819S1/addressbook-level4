package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.util.CertGenerator;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventContainsEventIdPredicate;
import seedu.address.model.event.EventId;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordContainsNonZeroHourPredicate;
import seedu.address.model.record.RecordContainsVolunteerIdPredicate;
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

    public static final String MESSAGE_EXPORT_CERT_SUCCESS = "Certificate exported for volunteer at INDEX %1$d to ";
    public static final String MESSAGE_EXPORT_FAILED = "Certificate export failed, please try again";
    public static final String MESSAGE_VOLUNTEER_NO_RECORD = "Selected volunteer has no valid event records.\n"
            + "Try adding some records or updating current records to set a positive non-zero hour value.";

    private static final String PDF_SAVE_PATH = System.getProperty("user.dir") + File.separator + "Certs"
            + File.separator;
    private static final String PDF_ALT_SAVE_PATH = System.getProperty("user.home") + File.separator + "Desktop"
            + File.separator;
    private static final java.util.logging.Logger logger = LogsCenter.getLogger(ExportCertCommand.class);

    private final Index index;

    private String currentSavePath = PDF_SAVE_PATH;

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
                logger.info("Creating a new folder 'Certs' in user's current working directory.");
            } catch (SecurityException se) {
                logger.warning("Couldn't create a relative export path next to jar file. "
                        + "Defaulting to user's Desktop.");
                currentSavePath = PDF_ALT_SAVE_PATH;
            }
        }
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
        if (!hasNonZeroEventRecords(model, selectedVolunteer)) {
            logger.info("Volunteer has no records.");
            throw new CommandException(MESSAGE_VOLUNTEER_NO_RECORD);
        }

        // Retrieve records and events pertaining to this volunteer
        List<Pair<Record, Event>> recordEventPairs = getRecordEventPairs(model, selectedVolunteer);

        // Instantiate a new CertGenerator and supply required data
        CertGenerator certGenerator = new CertGenerator(currentSavePath, selectedVolunteer, recordEventPairs);

        // Try generating and exporting the PDF for the selected volunteer
        try {
            certGenerator.generatePdf();
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_EXPORT_FAILED);
        }

        // Return a success result
        return new CommandResult(String.format(MESSAGE_EXPORT_CERT_SUCCESS + currentSavePath,
                index.getOneBased()));
    }

    public String getCurrentSavePath() {
        return currentSavePath;
    }

    /**
     * Retrieves volunteer service data as a list of Record, Event pairs.
     * @param model to retrieve record and event data from
     * @param selectedVolunteer who's record and event data is to be retrieved
     * @return a List of Record, Event pairs for the supplied volunteer
     */
    private List<Pair<Record, Event>> getRecordEventPairs(Model model, Volunteer selectedVolunteer) {
        List<Pair<Record, Event>> recordEventPairs = new ArrayList<Pair<Record, Event>>();

        List<Record> records = model.getFilteredRecordList()
                .filtered(new RecordContainsVolunteerIdPredicate(selectedVolunteer.getVolunteerId()));

        for (Record r: records) {
            EventId eventId = r.getEventId();

            List<Event> filteredEventList = model.getFilteredEventList()
                    .filtered(new EventContainsEventIdPredicate(eventId));

            assert(filteredEventList.size() == 1); // Make sure no duplicate events

            Event event = filteredEventList.get(0);

            recordEventPairs.add(new Pair<Record, Event>(r, event));
        }

        // Defensively return a read-only list
        return Collections.unmodifiableList(recordEventPairs);
    }

    /**
     * Checks if a {@code volunteer} has any event {@code records}.
     * @param model from which the {@code volunteer}'s {@code records} will be retrieved, if present
     * @param volunteer who's presence of event {@code records} is to be checked
     * @return true if {@code volunteer} has {@code records}, and false otherwise
     */
    private boolean hasNonZeroEventRecords(Model model, Volunteer volunteer) {
        VolunteerId volunteerId = volunteer.getVolunteerId();

        // Attempt to retrieve a list of the volunteer's records with non-zero hour value
        List<Record> eventRecords = model.getFilteredRecordList()
                .filtered(new RecordContainsVolunteerIdPredicate(volunteerId))
                .filtered(new RecordContainsNonZeroHourPredicate());

        return !eventRecords.isEmpty();
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
