package seedu.thanepark.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_RIDE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.thanepark.logic.commands.AddCommand;
import seedu.thanepark.logic.commands.AllCommandWords;
import seedu.thanepark.logic.commands.ClearCommand;
import seedu.thanepark.logic.commands.DeleteCommand;
import seedu.thanepark.logic.commands.ExitCommand;
import seedu.thanepark.logic.commands.FindCommand;
import seedu.thanepark.logic.commands.HelpCommand;
import seedu.thanepark.logic.commands.HistoryCommand;
import seedu.thanepark.logic.commands.RedoCommand;
import seedu.thanepark.logic.commands.UndoCommand;
import seedu.thanepark.logic.commands.UpdateCommand;
import seedu.thanepark.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.thanepark.logic.commands.ViewAllCommand;
import seedu.thanepark.logic.commands.ViewCommand;
import seedu.thanepark.logic.commands.ViewStatusCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.RideContainsKeywordsPredicate;
import seedu.thanepark.model.ride.RideStatusPredicate;
import seedu.thanepark.model.ride.Status;
import seedu.thanepark.testutil.RideBuilder;
import seedu.thanepark.testutil.RideUtil;
import seedu.thanepark.testutil.UpdateRideDescriptorBuilder;

public class ThaneParkParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ThaneParkParser parser = new ThaneParkParser();

    @Test
    public void parseCommand_add() throws Exception {
        Ride ride = new RideBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(RideUtil.getAddCommand(ride));
        assertEquals(new AddCommand(ride), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RIDE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RIDE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Ride ride = new RideBuilder().build();
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder(ride).build();
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RIDE.getOneBased() + " " + RideUtil.getEditRideDescriptorDetails(descriptor));
        assertEquals(new UpdateCommand(INDEX_FIRST_RIDE, descriptor), command);
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
        assertEquals(new FindCommand(new RideContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);

        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " more") instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " more 3") instanceof HelpCommand);

        final String commandWord = "add";
        assert (AllCommandWords.isCommandWord(commandWord));
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " " + commandWord) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " " + commandWord
                + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " more") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ViewAllCommand.COMMAND_WORD) instanceof ViewAllCommand);
        assertTrue(parser.parseCommand(ViewAllCommand.COMMAND_WORD + " 3") instanceof ViewAllCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_RIDE.getOneBased());
        assertEquals(new ViewCommand(INDEX_FIRST_RIDE), command);
    }

    @Test
    public void parseCommand_viewstatus() throws Exception {
        RideStatusPredicate predicate = new RideStatusPredicate(Status.OPEN);
        ViewStatusCommand command = (ViewStatusCommand) parser.parseCommand(
                ViewStatusCommand.COMMAND_WORD + " open");
        assertEquals(new ViewStatusCommand(predicate), command);
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
