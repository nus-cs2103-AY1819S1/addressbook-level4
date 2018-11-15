package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_EXTENSION;
import static seedu.address.commons.core.Messages.MESSAGE_IS_FILE_DIRECTORY;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.PersonBuilder.DEFAULT_STORED_INVALID_TIMETABLE_LOCATION;
import static seedu.address.testutil.PersonBuilder.DEFAULT_STORED_LOCATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.commands.AddTimetableCommand;

public class AddTimetableCommandParserTest {

    private AddTimetableCommandParser parser = new AddTimetableCommandParser();


    @Test
    public void parseCompulsoryFieldMissingFailure() {
        String expectedMessage = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimetableCommand.MESSAGE_USAGE);
        String validFileLocation = BOB.getStoredLocation();
        BOB.getTimetable().downloadTimetableAsCsv();
        boolean doesFileExists = new File(BOB.getStoredLocation()).exists();
        assertTrue(doesFileExists);

        //missing index but with fileLocation
        assertParseFailure(parser, " " + PREFIX_FILE_LOCATION + validFileLocation,
            expectedMessage);

    }


    @Test
    public void parseAddTimetableWithoutFileLocationSuccess() {
        String targetIndex = INDEX_SECOND.getOneBased() + " ";
        BOB.getTimetable().downloadTimetableAsCsv();
        boolean doesFileExists = new File(BOB.getStoredLocation()).exists();
        String userInput = targetIndex;
        assertTrue(doesFileExists);
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_SECOND, null);
        assertParseSuccess(parser, userInput, addTimetableCommand);
    }

    @Test
    public void parseAddTimetableWithValidFileLocationSuccess() {
        String targetIndex = INDEX_SECOND.getOneBased() + " ";
        String fileLocation = BOB.getStoredLocation();
        BOB.getTimetable().downloadTimetableAsCsv();
        boolean doesFileExists = new File(BOB.getStoredLocation()).exists();
        String userInput = targetIndex + PREFIX_FILE_LOCATION + fileLocation;
        assertTrue(doesFileExists);
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_SECOND,
            fileLocation);
        assertParseSuccess(parser, userInput, addTimetableCommand);
    }

    @Test
    public void parseAddTimetableWithInvalidFileExtension() {
        String targetIndex = INDEX_SECOND.getOneBased() + " ";
        String fileLocation = DEFAULT_STORED_INVALID_TIMETABLE_LOCATION + "/1.txt";
        String expectedMessage = MESSAGE_INVALID_FILE_EXTENSION;
        String userInput = targetIndex + PREFIX_FILE_LOCATION + fileLocation;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_addTimetableWithAFileDirectory_failure() {
        String fileDirectory = DEFAULT_STORED_LOCATION + ".csv";
        String targetIndex = INDEX_SECOND.getOneBased() + " ";
        String expectedMessage = MESSAGE_IS_FILE_DIRECTORY;
        File timetable = new File(fileDirectory);
        timetable.mkdirs();
        boolean doesFileExists = timetable.exists();
        assertTrue(doesFileExists);
        assertTrue(timetable.isDirectory());
        String userInput = targetIndex + PREFIX_FILE_LOCATION + fileDirectory;
        assertParseFailure(parser, userInput, expectedMessage);
        timetable.delete();
    }


}
