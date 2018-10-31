package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.PersonContainsTagPredicate;

//@@author A19Sean
public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsTagCommand() {
        List<String> tags = Arrays.asList("owesMoney", "friends");
        // Find tags command, no leading and trailing whitespaces
        TagCommand expectedFindTagCommand =
                new TagCommand(new PersonContainsTagPredicate(tags), TagCommand.Action.FIND, tags);
        assertParseSuccess(parser, "owesMoney friends", expectedFindTagCommand);

        // Find tags command, multiple whitespaces between keywords
        assertParseSuccess(parser, " \n owesMoney \n \t friends \t", expectedFindTagCommand);

        // Delete tags command, no leading and trailing whitespaces
        List<String> tagsToBeDeleted = Arrays.asList("owesMoney", "friends");
        assertParseSuccess(parser, "owesMoney friends delete", new TagCommand(new
                PersonContainsTagPredicate(tagsToBeDeleted), TagCommand.Action.DELETE, tagsToBeDeleted));

        // Delete tags command, multiple whitespaces between keywords
        assertParseSuccess(parser, "\t\nowesMoney   \t\n  friends \t\t delete\n\n", new TagCommand(new
                PersonContainsTagPredicate(tagsToBeDeleted), TagCommand.Action.DELETE, tagsToBeDeleted));

        // Edit tags command, no leading and trailing whitespaces
        assertParseSuccess(parser, "edit owesMoney unreliable", new TagCommand(
                new PersonContainsTagPredicate(Collections.singletonList("owesMoney")),
                TagCommand.Action.EDIT,
                Arrays.asList("owesMoney", "unreliable")));

        // Edit tags command, multiple whitespaces between keywords
        assertParseSuccess(parser, "\n\tedit     \t\t\n\nowesMoney \n\n\tunreliable\n\t", new TagCommand(
                new PersonContainsTagPredicate(Collections.singletonList("owesMoney")),
                TagCommand.Action.EDIT,
                Arrays.asList("owesMoney", "unreliable")));
    }

}
