package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.PictureCommand;

//@@author denzelchung
public class PictureCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PictureCommand.MESSAGE_USAGE);

    private PictureCommandParser parser = new PictureCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name specified
        assertParseFailure(parser, "l//images/johndoe.jpg", MESSAGE_INVALID_FORMAT);

        // no file location specified
        assertParseFailure(parser, NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no name and no file location specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }
}
