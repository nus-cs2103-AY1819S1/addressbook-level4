package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SAVED_AMOUNT_AMY;
import static seedu.address.logic.commands.MoveCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.MoveCommandParser.MESSAGE_INVALID_SAME_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WISH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WISH;

import org.junit.Test;

import seedu.address.commons.core.amount.Amount;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MoveCommand;

public class MoveCommandParserTest {
    private MoveCommandParser moveCommandParser = new MoveCommandParser();
    private Index fromIndex = INDEX_FIRST_WISH;
    private Index toIndex = INDEX_SECOND_WISH;
    private int unusedFundsIndex = MoveCommandParser.UNUSED_FUNDS_INDEX;
    private Amount amountToMove = new Amount(VALID_SAVED_AMOUNT_AMY);

    @Test
    public void parse_indexSpecified_success() {
        final String userInputWishToWish = fromIndex.getOneBased() + " " + toIndex.getOneBased()
                + " " + amountToMove.toString();
        final MoveCommand expectedCommandWishToWish = new MoveCommand(fromIndex, toIndex, amountToMove,
                MoveCommand.MoveType.WISH_TO_WISH);
        assertParseSuccess(moveCommandParser, userInputWishToWish, expectedCommandWishToWish);

        final String userInputUnusedFundsToWish = unusedFundsIndex + " " + toIndex.getOneBased()
                + " " + amountToMove.toString();
        final MoveCommand expectedCommandUnusedFundsToWish = new MoveCommand(null, toIndex, amountToMove,
                MoveCommand.MoveType.WISH_FROM_UNUSED_FUNDS);
        assertParseSuccess(moveCommandParser, userInputUnusedFundsToWish, expectedCommandUnusedFundsToWish);

        final String userInputWishToUnusedFunds = fromIndex.getOneBased() + " " + unusedFundsIndex + " "
                + amountToMove.toString();
        final MoveCommand expectedCommandWishToUnusedFunds = new MoveCommand(fromIndex, null, amountToMove,
                MoveCommand.MoveType.WISH_TO_UNUSED_FUNDS);
        assertParseSuccess(moveCommandParser, userInputWishToUnusedFunds, expectedCommandWishToUnusedFunds);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        final String userInputMissingIndex = fromIndex.getZeroBased() + " " + amountToMove.toString();
        assertParseFailure(moveCommandParser, userInputMissingIndex, expectedMessage);

        final String userInputMissingAmount = fromIndex.getZeroBased() + " " + toIndex.getZeroBased() + " ";
        assertParseFailure(moveCommandParser, userInputMissingAmount, expectedMessage);
    }

    @Test
    public void parse_sameIndex_failure() {
        //FROM and TO Index cannot be the same
        final String expectedMessage = MESSAGE_INVALID_SAME_INDEX;

        final String userInputSameIndex = fromIndex.getZeroBased() + " " + fromIndex.getZeroBased() + " "
                + amountToMove.toString();
        assertParseFailure(moveCommandParser, userInputSameIndex, expectedMessage);
    }
}
