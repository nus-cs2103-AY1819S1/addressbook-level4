package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;


import org.junit.Test;

import seedu.address.logic.commands.ImageCommand;
import seedu.address.model.person.Room;

import java.io.File;

public class ImageCommandParserTest {

    public static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE);

    private ImageCommandParser parser = new ImageCommandParser();

    @Test
    public void parse_missingPrefix_throwsParseException() {

        //no prefix
        assertParseFailure(parser, "A123 C:\\Users\\javen\\OneDrive\\Pictures\\Saved Pictures\\a123.jpg",
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsImageCommand() {
        //valid input
        ImageCommand expectedImageCommand = new ImageCommand(new Room("A123"),
                new File("C:/Users/javen/OneDrive/Pictures/Saved Pictures/a123.jpg"));
        System.out.println(expectedImageCommand);
        assertParseSuccess(parser, "r/A123 f/C:/Users/javen/OneDrive/Pictures/Saved Pictures/a123.jpg",
                expectedImageCommand);
    }
}