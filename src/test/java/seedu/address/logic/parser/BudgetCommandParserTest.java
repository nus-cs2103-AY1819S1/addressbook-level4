package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CCA_NAME_BASKETBALL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.BudgetCommand;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
public class BudgetCommandParserTest {

    private BudgetCommandParser parser = new BudgetCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        String userInput = " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL;
        CcaName targetCca = new CcaName(VALID_CCA_NAME_BASKETBALL);
        assertParseSuccess(parser, userInput, new BudgetCommand(targetCca));

        userInput = "";
        assertParseSuccess(parser, userInput, new BudgetCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));

        // invalid cca name
        assertParseFailure(parser, " " + INVALID_CCA_NAME_BASKETBALL_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            BudgetCommand.MESSAGE_USAGE));
    }
}
