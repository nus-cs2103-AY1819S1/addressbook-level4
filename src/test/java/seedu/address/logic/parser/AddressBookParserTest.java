package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_TRACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.BudgetCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CreateCcaCommand;
import seedu.address.logic.commands.DeleteCcaCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTransactionCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.EditCcaDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Entry;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.CcaUtil;
import seedu.address.testutil.EditCcaDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.EntryUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " all") instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
            SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    //@@author ericyjw
    @Test
    public void parseCommand_createCca() throws Exception {
        Cca cca = new CcaBuilder()
            .withHead("-")
            .withViceHead("-")
            .build();
        CreateCcaCommand command = (CreateCcaCommand) parser.parseCommand(CcaUtil.getCreateCcaCommand(cca));
        assertEquals(new CreateCcaCommand(cca), command);
    }

    @Test
    public void parseCommand_deleteCca() throws Exception {
        DeleteCcaCommand command = (DeleteCcaCommand) parser.parseCommand(
            DeleteCcaCommand.COMMAND_WORD + " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL);
        assertEquals(new DeleteCcaCommand(new CcaName(VALID_CCA_NAME_BASKETBALL)), command);
    }

    @Test
    public void parseCommand_addTransaction() throws Exception {
        Entry toAdd = new EntryBuilder().build();
        CcaName cca = new CcaName(VALID_CCA_NAME_BASKETBALL);
        AddTransactionCommand command =
            (AddTransactionCommand) parser.parseCommand(EntryUtil.getAddTransactionCommand(VALID_CCA_NAME_BASKETBALL,
                toAdd));
        assertEquals(new AddTransactionCommand(cca, toAdd.getDate(), toAdd.getAmount(), toAdd.getRemarks()), command);
    }

    @Test
    public void parseCommand_deleteTransaction() throws Exception {
        DeleteTransactionCommand command = (DeleteTransactionCommand) parser.parseCommand(
            DeleteTransactionCommand.COMMAND_WORD + " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL
                + " " + PREFIX_TRANSACTION + "1");
        assertEquals(new DeleteTransactionCommand(new CcaName(VALID_CCA_NAME_BASKETBALL), 1), command);
    }

    @Test
    public void parseCommand_update() throws Exception {
        Cca cca = new CcaBuilder()
            .withCcaName(VALID_CCA_NAME_BASKETBALL)
            .withTransaction(null)
            .build();
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(cca).build();

        String userInput = UpdateCommand.COMMAND_WORD + " "
            + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + " " + CcaUtil.getEditCcaDescriptorDetails(descriptor);
        UpdateCommand command = (UpdateCommand) parser.parseCommand(userInput);
        assertEquals(new UpdateCommand(new CcaName(VALID_CCA_NAME_BASKETBALL), descriptor), command);
    }

    @Test
    public void parseCommand_budget() throws Exception {
        BudgetCommand command = (BudgetCommand) parser.parseCommand(
            BudgetCommand.COMMAND_WORD + " " + PREFIX_TAG + VALID_CCA_NAME_TRACK);
        assertEquals(new BudgetCommand(new CcaName(VALID_CCA_NAME_TRACK)), command);
    }

}
