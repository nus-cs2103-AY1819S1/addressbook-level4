package seedu.restaurant.logic.parser.reservation;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.restaurant.logic.commands.reservation.SelectReservationCommand;

//@@author m4dkip
/**
 * Test scope: similar to {@code DeleteReservationCommandParserTest}.
 * @see DeleteReservationCommandParserTest
 */
public class SelectReservationCommandParserTest {

    private SelectReservationCommandParser parser = new SelectReservationCommandParser();

    @Test
    public void parse_validArgs_returnsSelectReservationCommand() {
        assertParseSuccess(parser, "1", new SelectReservationCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectReservationCommand.MESSAGE_USAGE));
    }
}
