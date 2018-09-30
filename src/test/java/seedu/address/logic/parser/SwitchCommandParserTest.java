package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTEXT_INVALID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.CONTEXT_VALID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SwitchCommand;
import seedu.address.model.Context;

public class SwitchCommandParserTest {
    private SwitchCommandParser parser = new SwitchCommandParser();

    @Test
    public void parse_invalidValue_failure() {
        // invalid contextId
        assertParseFailure(parser, CONTEXT_INVALID_DESC, Context.MESSAGE_CONTEXT_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CONTEXT_VALID_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
    }
}
