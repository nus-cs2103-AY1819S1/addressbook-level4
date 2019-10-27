package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.TagListCommand;

public class TagListCommandParserTest {

    private TagListCommandParser parser = new TagListCommandParser();

    @Test
    public void parse_help_throwsParseException() {
        assertParseFailure(parser, "help", TagListCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsTagListCommand() {
        // no leading and trailing whitespaces
        TagListCommand expectedTagListCommand =
                new TagListCommand("work", Arrays.asList("java"));
        assertParseSuccess(parser, " ~work #java", expectedTagListCommand);

        // only tags
        expectedTagListCommand = new TagListCommand("", Arrays.asList("java"));
        assertParseSuccess(parser, " #java", expectedTagListCommand);

        // only category
        expectedTagListCommand = new TagListCommand("work", new ArrayList<String>());
        assertParseSuccess(parser, " ~work", expectedTagListCommand);
    }

}
