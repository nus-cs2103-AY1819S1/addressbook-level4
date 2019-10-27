package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagRmCommand;
import seedu.address.model.tag.Tag;

public class TagRmCommandParserTest {
    private TagRmCommandParser parser = new TagRmCommandParser();

    @Test
    public void parse_help_throwsParseException() {
        assertParseFailure(parser, "help", TagRmCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsTagRmCommand() {
        HashSet<Tag> tags = new HashSet<Tag>();

        // no tags
        TagRmCommand expectedTagRmCommand =
                new TagRmCommand(Index.fromZeroBased(0), tags);
        assertParseSuccess(parser, "1", expectedTagRmCommand);

        // single tag
        tags.add(new Tag("java"));
        expectedTagRmCommand = new TagRmCommand(Index.fromZeroBased(1), tags);
        assertParseSuccess(parser, "2 #java", expectedTagRmCommand);

        // multi tags
        tags.add(new Tag("python"));
        expectedTagRmCommand = new TagRmCommand(Index.fromZeroBased(2), tags);
        assertParseSuccess(parser, "3 #java #python", expectedTagRmCommand);
    }

}
