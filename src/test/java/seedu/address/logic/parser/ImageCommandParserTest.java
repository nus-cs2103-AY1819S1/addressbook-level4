package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ImageCommand;
import seedu.address.model.person.Room;

import java.io.File;

//@@author javenseow
public class ImageCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE);

    private ImageCommandParser parser = new ImageCommandParser();

    @Test
    public void parse_missingPrefix_throwsParserException() {
        //no prefix
        assertParseFailure(parser, "A123, C://path/image.jpg", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_notJpgPath_throwsParseException() {
        // path ends with '.png'
        assertParseFailure(parser, "r/A123 f/C://path/image.png", MESSAGE_INVALID_FORMAT);

        //path ends with no file extension
        assertParseFailure(parser, "r/A123 f/C://path/image", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsImageCommand() {
        // path that ends with '.jpg' and room
        ImageCommand expectedImageCommand =
                new ImageCommand(new Room("A123"),
                        new File("C:\\Users\\javen\\OneDrive\\Pictures\\Saved Pictures\\Default.jpg"));
        assertParseSuccess(parser,
                " r/A123 f/C:\\Users\\javen\\OneDrive\\Pictures\\Saved Pictures\\Default.jpg",
                expectedImageCommand);
    }
}