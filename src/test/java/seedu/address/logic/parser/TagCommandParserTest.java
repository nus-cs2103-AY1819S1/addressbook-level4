package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

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
        // no leading and trailing whitespaces
        TagCommand expectedTagCommand =
                new TagCommand(new PersonContainsTagPredicate(Arrays.asList("friends", "owesMoney")));
        assertParseSuccess(parser, "friends owesMoney", expectedTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t owesMoney  \t", expectedTagCommand);
    }

}
