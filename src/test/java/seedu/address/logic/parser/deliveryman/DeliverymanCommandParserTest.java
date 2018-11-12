package seedu.address.logic.parser.deliveryman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanAddCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanDeleteCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanFindCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymanNameContainsKeywordsPredicate;
import seedu.address.testutil.DeliverymanBuilder;
import seedu.address.testutil.DeliverymanUtil;


public class DeliverymanCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DeliverymanCommandParser parser = new DeliverymanCommandParser();

    @Test
    public void parse_add() throws Exception {
        Deliveryman deliveryman = new DeliverymanBuilder().build();
        DeliverymanAddCommand command = (DeliverymanAddCommand)
            parser.parse(DeliverymanUtil.getDeliverymanAddCommand(deliveryman));
        assertEquals(new DeliverymanAddCommand(deliveryman), command);
    }

    @Test
    public void parse_delete() throws Exception {
        DeliverymanDeleteCommand command = (DeliverymanDeleteCommand) parser.parse(
                DeliverymanDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeliverymanDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parse_list() throws Exception {
        assertTrue(parser.parse(DeliverymanListCommand.COMMAND_WORD) instanceof DeliverymanListCommand);
        assertTrue(parser.parse(DeliverymanListCommand.COMMAND_WORD + " 3") instanceof DeliverymanListCommand);
    }

    @Test
    public void parse_find() throws Exception {
        String keyword = "foo";
        DeliverymanFindCommand command = (DeliverymanFindCommand) parser.parse(
                DeliverymanFindCommand.COMMAND_WORD + " n/" + keyword);
        assertEquals(new DeliverymanFindCommand(
                new DeliverymanNameContainsKeywordsPredicate(keyword)), command);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_DELIVERYMAN_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parse("");
        parser.parse("3");
    }

    @Test
    public void parse_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parse("unknownCommand");
    }
}
