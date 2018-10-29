package seedu.clinicio.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.logic.commands.AddCommand;
import seedu.clinicio.logic.commands.ClearCommand;
import seedu.clinicio.logic.commands.DeleteCommand;
import seedu.clinicio.logic.commands.EditCommand;
import seedu.clinicio.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.clinicio.logic.commands.ExitCommand;
import seedu.clinicio.logic.commands.FindCommand;
import seedu.clinicio.logic.commands.HelpCommand;
import seedu.clinicio.logic.commands.HistoryCommand;
import seedu.clinicio.logic.commands.ListCommand;
import seedu.clinicio.logic.commands.LoginCommand;
import seedu.clinicio.logic.commands.RedoCommand;
import seedu.clinicio.logic.commands.SelectCommand;
import seedu.clinicio.logic.commands.UndoCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.person.NameContainsKeywordsPredicate;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.testutil.EditPersonDescriptorBuilder;
import seedu.clinicio.testutil.PersonBuilder;
import seedu.clinicio.testutil.PersonUtil;

public class ClinicIoParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClinicIoParser parser = new ClinicIoParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
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

    //@@author jjlee050
    @Test
    public void parseCommand_login() throws Exception {
        LoginCommand command = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_WORD + " r/staff n/" + ADAM.getName().fullName + " pass/doctor1");
        assertEquals(new LoginCommand(new Staff(ADAM.getName(), new Password(VALID_PASSWORD_ADAM, false))),
                command);

        // TODO: Add receptionist
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
}
