package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagListCommand;
import seedu.address.logic.commands.TagRetagCommand;
import seedu.address.logic.commands.TagRmCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_help_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommandParser.MESSAGE_USAGE));
    }

    @Test
    public void parse_rightCommmand_returnsRightCommand() throws ParseException {
        assertTrue(parser.parse("list ~work #java") instanceof TagListCommand);
        assertTrue(parser.parse("ls ~work #java") instanceof TagListCommand);

        assertTrue(parser.parse("retag 1 ~work #java") instanceof TagRetagCommand);
        assertTrue(parser.parse("rt 1 ~work #java") instanceof TagRetagCommand);

        assertTrue(parser.parse("add 1 ~work #java") instanceof TagAddCommand);

        assertTrue(parser.parse("remove 1 #java") instanceof TagRmCommand);
        assertTrue(parser.parse("rm 1 #java") instanceof TagRmCommand);
    }

    @Test
    public void parse_tagList_returnsTagListCommand() {
        // no leading and trailing whitespaces
        TagListCommand expectedTagListCommand =
                new TagListCommand("work", Arrays.asList("java"));
        assertParseSuccess(parser, "list ~work #java", expectedTagListCommand);
        assertParseSuccess(parser, "ls ~work #java", expectedTagListCommand);
    }

}
