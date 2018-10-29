package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SWITCH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VOLUNTEER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCertCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Context;
import seedu.address.model.volunteer.NameContainsKeywordsPredicate;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;
import seedu.address.testutil.VolunteerBuilder;
import seedu.address.testutil.VolunteerUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_switch() throws Exception {
        // TODO: Fix the parse for switch
        String cmd = SwitchCommand.COMMAND_WORD + " " + PREFIX_SWITCH + Context.EVENT_CONTEXT_ID;
        assertTrue(parser.parseCommand(cmd,
                Context.VOLUNTEER_CONTEXT_ID) instanceof SwitchCommand);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Volunteer volunteer = new VolunteerBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(
                VolunteerUtil.getAddCommand(volunteer),
                Context.VOLUNTEER_CONTEXT_ID);
        assertEquals(new AddCommand(volunteer), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD,
                Context.VOLUNTEER_CONTEXT_ID) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3",
                Context.VOLUNTEER_CONTEXT_ID) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_VOLUNTEER.getOneBased(),
                Context.VOLUNTEER_CONTEXT_ID);
        assertEquals(new DeleteCommand(INDEX_FIRST_VOLUNTEER), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Volunteer volunteer = new VolunteerBuilder().build();
        EditCommand.EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(volunteer).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_VOLUNTEER.getOneBased() + " "
                        + VolunteerUtil.getEditVolunteerDescriptorDetails(descriptor),
                Context.VOLUNTEER_CONTEXT_ID);
        assertEquals(new EditCommand(INDEX_FIRST_VOLUNTEER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD,
                Context.VOLUNTEER_CONTEXT_ID) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3",
                Context.VOLUNTEER_CONTEXT_ID) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                Context.VOLUNTEER_CONTEXT_ID);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD,
                Context.VOLUNTEER_CONTEXT_ID) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3",
                Context.VOLUNTEER_CONTEXT_ID) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD,
                Context.VOLUNTEER_CONTEXT_ID) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3",
                Context.VOLUNTEER_CONTEXT_ID) instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", Context.VOLUNTEER_CONTEXT_ID);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD,
                Context.VOLUNTEER_CONTEXT_ID) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3",
                Context.VOLUNTEER_CONTEXT_ID) instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased(),
                Context.VOLUNTEER_CONTEXT_ID);
        assertEquals(new SelectCommand(INDEX_FIRST_VOLUNTEER), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD,
                Context.VOLUNTEER_CONTEXT_ID) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1",
                Context.VOLUNTEER_CONTEXT_ID) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD,
                Context.VOLUNTEER_CONTEXT_ID) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3",
                Context.VOLUNTEER_CONTEXT_ID) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("", Context.VOLUNTEER_CONTEXT_ID);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", Context.VOLUNTEER_CONTEXT_ID);
    }

    @Test
    public void parseCommand_exportcert() throws Exception {
        ExportCertCommand command = (ExportCertCommand) parser.parseCommand(ExportCertCommand.COMMAND_WORD
                + " " + INDEX_FIRST_VOLUNTEER.getOneBased(), Context.VOLUNTEER_CONTEXT_ID);
        assertEquals(new ExportCertCommand(INDEX_FIRST_VOLUNTEER), command);
    }
}
