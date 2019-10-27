package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagRetagCommand;
import seedu.address.model.category.Category;
import seedu.address.model.tag.Tag;

public class TagRetagCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRetagCommand.MESSAGE_USAGE);

    private TagRetagCommandParser parser = new TagRetagCommandParser();

    @Test
    public void parse_help_throwsParseException() {
        assertParseFailure(parser, "help", TagRetagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsTagRetagCommand() {
        Category category = new Category("work");
        HashSet<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("java"));

        // no leading and trailing whitespaces
        TagRetagCommand expectedTagRetagCommand =
                new TagRetagCommand(Index.fromZeroBased(0), category, tags);
        assertParseSuccess(parser, "1 ~work #java", expectedTagRetagCommand);

        // only category
        expectedTagRetagCommand = new TagRetagCommand(Index.fromZeroBased(2), category, new HashSet<Tag>());
        assertParseSuccess(parser, "3 ~work", expectedTagRetagCommand);
    }

    @Test
    public void parse_onlyTags_fails() {
        // only tags, failure
        assertParseFailure(parser, "2 #java", MESSAGE_INVALID_FORMAT);

        // no index, failure
        assertParseFailure(parser, "#java", MESSAGE_INVALID_FORMAT);

        // nothing, failure
        assertParseFailure(parser, "2", MESSAGE_INVALID_FORMAT);
    }

}
