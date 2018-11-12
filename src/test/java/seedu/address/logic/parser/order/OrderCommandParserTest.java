package seedu.address.logic.parser.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.order.AddCommand;
import seedu.address.logic.commands.order.ClearCommand;
import seedu.address.logic.commands.order.DeleteCommand;
import seedu.address.logic.commands.order.EditCommand;
import seedu.address.logic.commands.order.EditCommand.EditOrderDescriptor;
import seedu.address.logic.commands.order.FindCommand;
import seedu.address.logic.commands.order.ListCommand;
import seedu.address.logic.commands.order.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.OrderUtil;


public class OrderCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final OrderCommandParser parser = new OrderCommandParser();

    @Test
    public void parse_add() throws Exception {
        Order order = new OrderBuilder().build();
        AddCommand command = (AddCommand) parser.parse(OrderUtil.getAddCommand(order));
        assertEquals(new AddCommand(order), command);
    }

    @Test
    public void parse_clear() throws Exception {
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parse_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parse(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parse_list() throws Exception {
        assertTrue(parser.parse(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parse(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parse_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parse(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST), command);
    }

    @Test
    public void parse_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String searchKeyword = keywords.stream().collect(Collectors.joining(" "));

        FindCommand command = (FindCommand) parser.parse(
                FindCommand.COMMAND_WORD + " n/" + searchKeyword);
        assertEquals(new FindCommand(new OrderNameContainsKeywordPredicate(searchKeyword)), command);
    }

    @Test
    public void parse_edit() throws Exception {
        Order order = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(order).build();
        EditCommand command = (EditCommand) parser.parse(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + OrderUtil.getEditOrderDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_ORDER_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
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
