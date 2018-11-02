
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_A_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_A;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ChangeDeckCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.logic.commands.EditDeckCommand;
import seedu.address.logic.commands.EditDeckCommand.EditDeckDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewDeckCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;
import seedu.address.testutil.AddressbookDeckUtil;
import seedu.address.testutil.DeckBuilder;

public class ParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Parser parser = new Parser();


    @Test
    public void parseCommand_newDeck() throws Exception {
        Deck deck = new DeckBuilder().withName(VALID_NAME_DECK_A).build();
        NewDeckCommand command = (NewDeckCommand) parser.parseCommand(VALID_DECK_NAME_A_ARGS);
        assertEquals(new NewDeckCommand(deck), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteDeck() throws Exception {
        DeleteDeckCommand command = (DeleteDeckCommand) parser.parseCommand(
            DeleteDeckCommand.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased());
        assertEquals(new DeleteDeckCommand(INDEX_FIRST_DECK), command);
    }

    @Test
    public void parseCommand_editDeck() throws Exception {
        Deck deck = new DeckBuilder().build();
        EditDeckDescriptor descriptor = new EditDeckDescriptor(deck).build();
        EditDeckCommand command = (EditDeckCommand) parser.parseCommand(EditDeckCommand.COMMAND_WORD + " "
            + INDEX_FIRST_DECK.getOneBased() + " " + AddressbookDeckUtil
            .getEditDeckDescriptorDetails(descriptor));
        assertEquals(new EditDeckCommand(INDEX_FIRST_DECK, descriptor), command);
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
        assertEquals(new FindCommand(new DeckNameContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_changeDeck() throws Exception {
        ChangeDeckCommand command = (ChangeDeckCommand) parser.parseCommand(
            ChangeDeckCommand.COMMAND_WORD + " " + INDEX_FIRST_DECK.getOneBased());
        assertEquals(new ChangeDeckCommand(INDEX_FIRST_DECK), command);
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
