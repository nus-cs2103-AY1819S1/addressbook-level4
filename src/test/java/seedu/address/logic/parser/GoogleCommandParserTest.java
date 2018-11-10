package seedu.address.logic.parser;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.ENTIRE_GOOGLE_MESSAGE;
import static seedu.address.commons.core.Messages.MESSAGE_GOOGLE_INVALID_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.google.GoogleDlCommand;
import seedu.address.logic.commands.google.GoogleLsCommand;
import seedu.address.logic.commands.google.GoogleRefreshCommand;
import seedu.address.logic.commands.google.GoogleUploadCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author chivent
public class GoogleCommandParserTest {
    private final GoogleCommandParser parser = new GoogleCommandParser();

    @Test
    public void parseCommandLsVariants() throws ParseException {
        assertEqualsGoogleLs("");

        // List all albums
        assertEqualsGoogleLs("/a");

        // List all images in an album
        assertEqualsGoogleLs("<Albums>");

        // Invalid list
        assertEqualsGoogleLs("<A");
    }

    @Test
    public void parseCommandDownload() throws ParseException {
        // Download album
        assertEqualsGoogleDl("/a<Album>");

        // Download image
        assertEqualsGoogleDl("/i<image.png>");

        // Download images from album
        assertEqualsGoogleDl("/a<Album> /i<image.png>");

        //Assert invalid
        try {
            assertEqualsGoogleDl("");
        } catch (Exception ex) {
            assertTrue(ex instanceof ParseException);
            assertEquals(ex.getMessage(), MESSAGE_GOOGLE_INVALID_FORMAT + "\n\n" + GoogleDlCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void parseCommandUpload() throws ParseException {
        // Upload directory
        assertEqualsGoogleUl("all");

        // Download image
        assertEqualsGoogleUl("<image.png>");

        //Assert invalid
        try {
            assertEqualsGoogleUl("");
        } catch (Exception ex) {
            assertTrue(ex instanceof ParseException);
            assertEquals(ex.getMessage(), MESSAGE_GOOGLE_INVALID_FORMAT + "\n\n" + GoogleUploadCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void parseCommandRefresh() throws ParseException {
        assertTrue(parser.parse(GoogleRefreshCommand.TYPE) instanceof GoogleRefreshCommand);
    }

    @Test
    public void parseCommandInvalid() {
        try {
            parser.parse("");
        } catch (Exception ex) {
            assertTrue(ex instanceof ParseException);
            assertEquals(ex.getMessage(), ENTIRE_GOOGLE_MESSAGE);
        }

        try {
            parser.parse("qe");
        } catch (Exception ex) {
            assertTrue(ex instanceof ParseException);
            assertEquals(ex.getMessage(), ENTIRE_GOOGLE_MESSAGE);
        }

        try {
            parser.parse("g");
        } catch (Exception ex) {
            assertTrue(ex instanceof ParseException);
            assertEquals(ex.getMessage(), ENTIRE_GOOGLE_MESSAGE);
        }
    }

    private void assertEqualsGoogleLs(String commandParameter) throws ParseException {
        String commandFormat = GoogleLsCommand.TYPE + " " + commandParameter;
        GoogleLsCommand expected = new GoogleLsCommand(commandParameter);
        assertEquals(expected, parser.parse(commandFormat));
    }

    private void assertEqualsGoogleDl(String commandParameter) throws ParseException {
        String commandFormat = GoogleDlCommand.TYPE + " " + commandParameter;
        GoogleDlCommand expected = new GoogleDlCommand(commandParameter);
        assertEquals(expected, parser.parse(commandFormat));
    }

    private void assertEqualsGoogleUl(String commandParameter) throws ParseException {
        String commandFormat = GoogleUploadCommand.TYPE + " " + commandParameter;
        GoogleUploadCommand expected = new GoogleUploadCommand(commandParameter);
        assertEquals(expected, parser.parse(commandFormat));
    }
}
