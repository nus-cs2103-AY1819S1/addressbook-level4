package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.model.category.Category;
import seedu.address.model.tag.Tag;

public class TagAddCommandParserTest {
    private TagAddCommandParser parser = new TagAddCommandParser();

    @Test
    public void parse_help_throwsParseException() {
        assertParseFailure(parser, "help", TagAddCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsTagAddCommand() {
        Category category = new Category("work");
        HashSet<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("java"));

        // no leading and trailing whitespaces
        TagAddCommand expectedTagAddCommand =
                new TagAddCommand(Index.fromZeroBased(0), category, tags);
        assertParseSuccess(parser, "1 ~work #java", expectedTagAddCommand);

        // only tags
        expectedTagAddCommand = new TagAddCommand(Index.fromZeroBased(1), null, tags);
        assertParseSuccess(parser, "2 #java", expectedTagAddCommand);

        // only category
        expectedTagAddCommand = new TagAddCommand(Index.fromZeroBased(2), category, new HashSet<Tag>());
        assertParseSuccess(parser, "3 ~work", expectedTagAddCommand);
    }

}
