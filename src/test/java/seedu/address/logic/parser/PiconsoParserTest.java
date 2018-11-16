package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_IMAGE;

import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CdCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.LsCommand;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.PrevCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.canvas.CanvasCommand;
import seedu.address.logic.commands.google.GoogleDlCommand;
import seedu.address.logic.commands.google.GoogleLsCommand;
import seedu.address.logic.commands.google.GoogleRefreshCommand;
import seedu.address.logic.commands.google.GoogleUploadCommand;
import seedu.address.logic.commands.layer.LayerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class PiconsoParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PiconsoParser parser = new PiconsoParser();

    @Test
    public void parseCommandExit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommandHelp() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommandSelect() throws Exception {
        OpenCommand command = (OpenCommand) parser.parseCommand(
                OpenCommand.COMMAND_WORD + " " + INDEX_FIRST_IMAGE.getOneBased());
        assertEquals(new OpenCommand(INDEX_FIRST_IMAGE), command);
    }

    //@@author benedictcss
    @Test
    public void parseCommandCd() throws Exception {
        CdCommand command = (CdCommand) parser.parseCommand(CdCommand.COMMAND_WORD + " Desktop");
        assertEquals(new CdCommand(Paths.get("Desktop")), command);

        // Throws parse exception when field is Empty
        assertThrows(ParseException.class, ()->parser.parseCommand(
                CdCommand.COMMAND_WORD + " "));
    }

    @Test
    public void parseCommandNextCommandWordReturnsNextCommand() throws Exception {
        assertTrue(parser.parseCommand(NextCommand.COMMAND_WORD) instanceof NextCommand);
        assertTrue(parser.parseCommand(NextCommand.COMMAND_WORD + " 4") instanceof NextCommand);
    }

    @Test
    public void parseCommandPrevCommandWordReturnsPrevCommand() throws Exception {
        assertTrue(parser.parseCommand(PrevCommand.COMMAND_WORD) instanceof PrevCommand);
        assertTrue(parser.parseCommand(PrevCommand.COMMAND_WORD + " 4") instanceof PrevCommand);
    }
    //@@author

    @Test
    public void parseCommandLsCommandWordReturnsLsCommand() throws Exception {
        assertTrue(parser.parseCommand(LsCommand.COMMAND_WORD) instanceof LsCommand);
        assertTrue(parser.parseCommand(LsCommand.COMMAND_WORD + " 23") instanceof LsCommand);
    }

    @Test
    public void parseCommandRedoCommandWordReturnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommandUndoCommandWordReturnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommandCanvasCommandWordsReturnCanvasCommands() throws Exception {
        assertTrue(parser.parseCommand("canvas size 3x3") instanceof CanvasCommand);

        assertTrue(parser.parseCommand("canvas auto-resize off") instanceof CanvasCommand);
        assertThrows(ParseException.class, ()->parser.parseCommand("canvas auto-resize"));

        assertTrue(parser.parseCommand("canvas bgcolor rgba(0,0,0,0.2)") instanceof CanvasCommand);
        assertThrows(ParseException.class, ()->parser.parseCommand("canvas bgcolor"));

        assertThrows(ParseException.class, ()->parser.parseCommand("canvas invalid"));
    }

    @Test
    public void parseCommandLayerCommandWordsReturnLayerCommands() throws Exception {
        assertTrue(parser.parseCommand("layer add 1") instanceof LayerCommand);
        assertThrows(ParseException.class, ()-> parser.parseCommand("layer add"));

        assertTrue(parser.parseCommand("layer delete 1") instanceof LayerCommand);
        assertThrows(ParseException.class, ()-> parser.parseCommand("layer delete"));

        assertTrue(parser.parseCommand("layer position 1x1") instanceof LayerCommand);
        assertTrue(parser.parseCommand("layer position") instanceof LayerCommand);

        assertTrue(parser.parseCommand("layer swap 1 2") instanceof LayerCommand);
        assertThrows(ParseException.class, ()-> parser.parseCommand("layer swap"));

        assertTrue(parser.parseCommand("layer select 1") instanceof LayerCommand);
        assertThrows(ParseException.class, ()-> parser.parseCommand("layer select"));
        assertThrows(ParseException.class, ()-> parser.parseCommand("layer invalid"));
    }

    @Test
    public void parseCommandGoogleCommandWordReturnsGoogleCommand() throws Exception {
        assertTrue(parser.parseCommand(GoogleLsCommand.FULL_CMD) instanceof GoogleLsCommand);
        assertTrue(parser.parseCommand(GoogleDlCommand.FULL_CMD + " /a<album>") instanceof GoogleDlCommand);
        assertTrue(parser.parseCommand(GoogleUploadCommand.FULL_CMD + " all") instanceof GoogleUploadCommand);
        assertTrue(parser.parseCommand(GoogleRefreshCommand.FULL_CMD) instanceof GoogleRefreshCommand);
    }

    @Test
    public void parseCommandLogoutCommandWordReturnsLogoutCommand() throws Exception {
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD) instanceof LogoutCommand);
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD + " s") instanceof LogoutCommand);
    }

    @Test
    public void parseCommandLoginCommandWordReturnsLoginCommand() throws Exception {
        assertTrue(parser.parseCommand(LoginCommand.COMMAND_WORD) instanceof LoginCommand);
        assertTrue(parser.parseCommand(LoginCommand.COMMAND_WORD + " s") instanceof LoginCommand);
    }

    @Test
    public void parseCommandUnrecognisedInputThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommandUnknownCommandThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
