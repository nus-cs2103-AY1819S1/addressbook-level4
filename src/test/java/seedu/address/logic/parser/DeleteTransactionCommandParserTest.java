package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CCA_NAME_BASKETBALL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_TRACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTransactionCommand;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
public class DeleteTransactionCommandParserTest {

    private DeleteTransactionCommandParser parser = new DeleteTransactionCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        String userInput = " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + " " + PREFIX_TRANSACTION + "1";
        assertParseSuccess(parser, userInput, new DeleteTransactionCommand(new CcaName(VALID_CCA_NAME_BASKETBALL), 1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + " " + PREFIX_TRANSACTION + "a";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteTransactionCommand.MESSAGE_USAGE));

        userInput = " " + PREFIX_TAG + "Basketball - M" + " " + PREFIX_TRANSACTION + "1";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        String userInput = " " + PREFIX_TRANSACTION + "a";
        assertParseFailure(parser, userInput, DeleteTransactionCommand.MESSAGE_USAGE);

        userInput = " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL;
        assertParseFailure(parser, userInput, DeleteTransactionCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_extraValidArgs_returnsDeleteCommand() {
        String userInput = " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + " " + PREFIX_TAG + VALID_CCA_NAME_TRACK +
            " " + PREFIX_TRANSACTION + "1";
        assertParseFailure(parser, userInput, DeleteTransactionCommand.MESSAGE_USAGE);

        userInput = " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + " " + PREFIX_TRANSACTION + "2" +
            " " + PREFIX_TRANSACTION + "1";
        assertParseFailure(parser, userInput, DeleteTransactionCommand.MESSAGE_USAGE);
    }
}
