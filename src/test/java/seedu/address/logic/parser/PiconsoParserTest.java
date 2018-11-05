package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_IMAGE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.canvas.CanvasCommand;
import seedu.address.logic.commands.layer.LayerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class PiconsoParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PiconsoParser parser = new PiconsoParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_IMAGE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_IMAGE), command);
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
    public void parseCommand_canvasCommandWords_returnCanvasCommands() throws Exception {
        assertTrue(parser.parseCommand("canvas size 3x3") instanceof CanvasCommand);
        assertTrue(parser.parseCommand("canvas auto-resize off") instanceof CanvasCommand);
        assertTrue(parser.parseCommand("canvas bgcolour rgba(0,0,0,0.2)") instanceof CanvasCommand);
    }

    @Test
    public void parseCommand_layerCommandWords_returnLayerCommands() throws Exception {
        assertTrue(parser.parseCommand("layer add 1") instanceof LayerCommand);
        assertTrue(parser.parseCommand("layer delete 1") instanceof LayerCommand);
        assertTrue(parser.parseCommand("layer position 1x1") instanceof LayerCommand);
        assertTrue(parser.parseCommand("layer swap 1 2") instanceof LayerCommand);
        assertTrue(parser.parseCommand("layer select 1") instanceof LayerCommand);
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
