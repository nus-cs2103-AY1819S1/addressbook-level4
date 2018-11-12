package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CCA_NAME_BASKETBALL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCcaCommand;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
public class DeleteCcaCommandParserTest {

    private DeleteCcaCommandParser parser = new DeleteCcaCommandParser();
    private CcaName targetCca = new CcaName("Basketball");

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL, new DeleteCcaCommand(targetCca));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // wrong prefix
        assertParseFailure(parser, " " + PREFIX_NAME + VALID_CCA_NAME_BASKETBALL,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE));

        // no prefix
        assertParseFailure(parser, " " + VALID_CCA_NAME_BASKETBALL,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE));

        // not valid cca name
        assertParseFailure(parser, " " + INVALID_CCA_NAME_BASKETBALL_DESC,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE));
    }
}
