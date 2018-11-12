package seedu.saveit.logic.parser;

import static seedu.saveit.logic.parser.CliSyntax.PREFIX_DESCRIPTION_STRING;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_TAG_STRING;
import static seedu.saveit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.saveit.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.logic.commands.FindByTagCommand;
import seedu.saveit.model.issue.IssueHasTagsPredicate;

public class FindByTagCommandParserTest {

    private FindByTagCommandParser parser = new FindByTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindByTagCommand.EMPTY_TAGS_ERROR_MESSAGE));
    }

    @Test
    public void parse_noValidArgumentsInIdentifiers_throwsParseException() {
        assertParseFailure(parser, PREFIX_TAG_STRING + " " + PREFIX_TAG_STRING + " ", String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindByTagCommand.EMPTY_TAGS_ERROR_MESSAGE));
    }

    @Test
    public void parse_invalidIdentifiers_throwParseException() {
        assertParseFailure(parser, PREFIX_DESCRIPTION_STRING + " ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindByTagCommand.MESSAGE_USAGE));

        assertParseFailure(parser, PREFIX_DESCRIPTION_STRING + " " + PREFIX_TAG_STRING + " ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArguments_returnsFindByTagCommand() {
        FindByTagCommand expectedFindByTagCommand =
                new FindByTagCommand(new IssueHasTagsPredicate(Arrays.asList("solved", "newBug")));

        assertParseSuccess(parser, " " + PREFIX_TAG_STRING + "solved " + PREFIX_TAG_STRING + "newBug",
                expectedFindByTagCommand);
    }
}
