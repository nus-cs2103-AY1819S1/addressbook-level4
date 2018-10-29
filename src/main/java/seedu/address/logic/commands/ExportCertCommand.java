package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
    public static final String MESSAGE_EXPORT_CERT_SUCCESS = "Certificate exported for volunteer at %1$d to your Desktop.";
    public static final String MESSAGE_EXPORT_FAILED = "Certificate export failed, please try again.";
    public static final String PDF_SAVE_PATH = System.getProperty("user.home") + "/Desktop/";

    private final Index index;

    /**
     * @param index of the volunteer in the filtered volunteer list whose certificate is to be generated and exported
     */
    public ExportCertCommand(Index index) {
        requireNonNull(index);
        this.index = index;
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

        // Try creating and exporting the PDF for the selected volunteer
        try {
            createPDF(selectedVolunteer);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_EXPORT_FAILED);
        }

        // Return a success result
        return new CommandResult(String.format(MESSAGE_EXPORT_CERT_SUCCESS, index.getOneBased()));
    }

    /**
     * Creates and exports a PDF document containing a Volunteer's data
     * @param volunteer who's data is to be input into the PDF document
     */
    private void createPDF(Volunteer volunteer) throws IOException {
        // Retrieve the selected volunteer's attributes
        VolunteerId volunteerId = volunteer.getVolunteerId();
        Name volunteerName = volunteer.getName();

        // Create the new document
        PDDocument doc = new PDDocument();

        // Create a new page and add it to the document
        PDPage page = new PDPage();
        doc.addPage(page);

        // Setup a new content stream to write to a page
        PDPageContentStream contStream = new PDPageContentStream(doc, page);

        // Populate the PDF with necessary details
        contStream.beginText();

        contStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 12);
        contStream.setLeading(14.5f);

        contStream.newLineAtOffset(250, 700);
        String line1 = "Certificate of Recognition";
        contStream.showText(line1);

        contStream.newLine();

        String line2 = "Name: " + volunteerName;
        contStream.showText(line2);

        contStream.newLine();

        String line3 = "Volunteer ID: " + volunteerId;
        contStream.showText(line3);

        // Close the content stream
        contStream.endText();
        contStream.close();

        // Save document as <volunteerName>.pdf to the save path
        doc.save(PDF_SAVE_PATH + volunteerName + ".pdf");

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
