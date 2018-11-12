package seedu.address.logic.util;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javafx.util.Pair;

import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerId;

/**
 * Populates and exports a PDF file containing supplied volunteer information to the given file path.
 */
public class CertGenerator {
    private static final int MAX_EVENT_NAME_LENGTH = 43;
    private static final int MAX_NUM_RECORDS_DISPLAYABLE = 20;
    private static final int TITLE_HEIGHT_OFFSET = 740;
    private static final int BODY_INDENT_WIDTH = 20;
    private static final Color BORDER_COLOR = Color.DARK_GRAY;
    private static final float LEADING_TITLE = 20f;
    private static final float LEADING_BODY = 18f;
    private static final int FONT_SIZE_TITLE = 24;
    private static final int FONT_SIZE_BODY = 14;
    private static final PDFont TITLE_FONT = PDType1Font.TIMES_BOLD_ITALIC;
    private static final String TITLE_TEXT = "Certificate of Recognition";
    private static final String BULLET = "\u2022  ";

    private String savePath;
    private Volunteer volunteer;
    private List<Pair<Record, Event>> volunteerRecordEventPairs;
    private PDDocument doc;

    /**
     * Instantiates a new CertGenerator with the given arguments.
     * @param savePath to export the certificate to
     * @param selectedVolunteer whose certificate is being generated
     * @param recordEventPairs pertaining to the volunteer's contribution
     */
    public CertGenerator(String savePath, Volunteer selectedVolunteer, List<Pair<Record, Event>> recordEventPairs) {
        this.savePath = savePath;
        this.volunteer = selectedVolunteer;
        this.volunteerRecordEventPairs = recordEventPairs;
        this.doc = new PDDocument();
    }

    /**
     * Populates and exports a PDF with volunteer information from the fields to the save path.
     * @throws IOException if there are issues writing to or exporting PDF file
     */
    public void generatePdf() throws IOException {
        populatePdf();
        exportPdf();
    }

    /**
     * Populates PDF page(s) with volunteer information from the fields.
     * @throws IOException if there are issues writing to the file
     */
    private void populatePdf() throws IOException {
        // Create a new page and add it to the document
        PDPage page = new PDPage();
        doc.addPage(page);

        // Setup a new content stream to write to a page
        PDPageContentStream contStream = new PDPageContentStream(doc, page);

        // Draw a border around the page
        drawBorder(page, contStream);

        contStream.beginText();

        // Add title text to the center of the page, at the top
        addTitle(page, contStream);
        contStream.newLine();
        contStream.newLine();

        // Add the volunteer name and NRIC
        addVolunteerDetails(page, contStream);
        contStream.newLine();

        // Input today's date
        contStream.showText("Date: " + String.valueOf(LocalDate.now().format(DateTimeFormatter
                .ofPattern("dd-MM-yyyy"))));
        contStream.newLine();
        contStream.newLine();

        // Reduce the leading for the main body of certificate
        contStream.setLeading(LEADING_BODY);

        // Standardised formality text
        String formalityTextLine1 = "To whomever it may concern,";
        contStream.showText(formalityTextLine1);
        contStream.newLine();
        contStream.newLine();

        String formalityTextLine2 = "This is to certify " + volunteer.getName()
                + "'s contributions to our organisation via the following event(s):";
        contStream.showText(formalityTextLine2);
        contStream.newLine();
        contStream.newLine();

        // Input contribution history of the volunteer
        addContributionRecords(contStream);
        contStream.newLine();
        contStream.newLine();

        // Sign off
        String appreciationLine = "We greatly appreciate " + volunteer.getName()
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
    }

    /**
     * Adds the volunteer's contribution records to the content stream.
     * @param contStream to write to
     * @throws IOException if issues writing to the content stream
     */
    private void addContributionRecords(PDPageContentStream contStream) throws IOException {
        int totalHours = 0;
        int numRecordsDisplayed = 0;

        for (Pair<Record, Event> pair: volunteerRecordEventPairs) {
            // Truncate if not all records can be displayed
            if (numRecordsDisplayed > MAX_NUM_RECORDS_DISPLAYABLE) {
                contStream.showText("... and " + (volunteerRecordEventPairs.size() - numRecordsDisplayed)
                        + " more event(s)");
                contStream.newLine();
                break;
            }

            // Input information from event record
            Record thisRecord = pair.getKey();
            Event thisEvent = pair.getValue();

            Hour eventHours = thisRecord.getHour();
            String eventName = thisEvent.getName().fullName;
            EventId eventId = thisEvent.getEventId();
            Date endDate = thisEvent.getEndDate();
            Date startDate = thisEvent.getStartDate();

            // Trim event name to prevent exceeding page width
            if (eventName.length() > MAX_EVENT_NAME_LENGTH) {
                eventName = eventName.substring(0, MAX_EVENT_NAME_LENGTH - 1) + "...";
            }

            String eventEntryLine = eventName + " [" + eventId + "]" + " - " + eventHours + " hour(s) from "
                    + startDate + " to " + endDate;

            contStream.showText(BULLET);
            contStream.showText(eventEntryLine);
            contStream.newLine();

            totalHours += Integer.parseInt(eventHours.toString());
            numRecordsDisplayed++;
        }
        contStream.newLine();

        // Input the total hours from across all records
        String totalHoursLine = "Total hours contributed: " + totalHours;
        contStream.showText(totalHoursLine);
    }

    /**
     * Adds volunteer's name and NRIC to the content stream.
     * @param page to retrieve dimensions for setting new line offset
     * @param contStream to write to
     * @throws IOException if issues writing to content stream
     */
    private void addVolunteerDetails(PDPage page, PDPageContentStream contStream) throws IOException {
        // Retrieve the selected volunteer's attributes
        VolunteerId volunteerId = volunteer.getVolunteerId();
        Name volunteerName = volunteer.getName();

        contStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, FONT_SIZE_BODY);
        float titleWidth = TITLE_FONT.getStringWidth(TITLE_TEXT) * FONT_SIZE_TITLE / 1000f;
        contStream.newLineAtOffset(-(page.getMediaBox().getWidth() / 2 - titleWidth / 2) + BODY_INDENT_WIDTH, 0);

        String volunteerNameLine = "Volunteer Name: " + volunteerName;
        contStream.showText(volunteerNameLine);
        contStream.newLine();

        String volunteerIdLine = "Volunteer NRIC: " + volunteerId;
        contStream.showText(volunteerIdLine);
    }

    /**
     * Adds the title to the content stream.
     * @param page to retrieve dimensions to set new line offset
     * @param contStream to write to
     * @throws IOException if issues writing to content stream
     */
    private void addTitle(PDPage page, PDPageContentStream contStream) throws IOException {
        // Set TITLE_TEXT font and leading
        contStream.setFont(TITLE_FONT, FONT_SIZE_TITLE);
        contStream.setLeading(LEADING_TITLE);

        // Input TITLE_TEXT to the center of the page
        float titleWidth = TITLE_FONT.getStringWidth(TITLE_TEXT) * FONT_SIZE_TITLE / 1000f;
        contStream.newLineAtOffset(page.getMediaBox().getWidth() / 2 - titleWidth / 2, TITLE_HEIGHT_OFFSET);
        contStream.showText(TITLE_TEXT);
    }

    /**
     * Draws a rectangular border around the page.
     * @param page to retrieve dimensions for the rectangle
     * @param contStream to write to
     * @throws IOException if issues writing to content stream
     */
    private void drawBorder(PDPage page, PDPageContentStream contStream) throws IOException {
        // Draw a dark gray border around the page
        contStream.setStrokingColor(BORDER_COLOR);
        contStream.addRect(
                BODY_INDENT_WIDTH / 2,
                BODY_INDENT_WIDTH / 2,
                page.getMediaBox().getWidth() - BODY_INDENT_WIDTH,
                page.getMediaBox().getHeight() - BODY_INDENT_WIDTH
        );
        contStream.stroke();
    }

    /**
     * Exports the document to the save path, then closes it.
     * @throws IOException if there are issues saving the document to the filepath or closing the document
     */
    private void exportPdf() throws IOException {
        // Save document as <volunteerName>_<volunteerId>.pdf to the save path
        doc.save(savePath + volunteer.getName() + "_" + volunteer.getVolunteerId() + ".pdf");

        // Close the document
        doc.close();
    }
}
