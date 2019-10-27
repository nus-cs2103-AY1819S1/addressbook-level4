package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_FACEBOOK;
import static seedu.address.logic.commands.CommandTestUtil.SUBTITLE_DESC_FACEBOOK;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_FACEBOOK;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddBulletCommand;
import seedu.address.logic.commands.ContextCommand;
import seedu.address.logic.commands.EditEntryInfoCommand.EditEntryInfoDescriptor;
import seedu.address.logic.commands.EditEntryInfoCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LoadTemplateCommand;
import seedu.address.logic.commands.MakeCommand;
import seedu.address.logic.commands.SelectEntryCommand;
import seedu.address.logic.commands.exceptions.DeleteEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CommandParser parser = new CommandParser();

    @Test
    public void parseCommand_deleteEntry() throws Exception {
        DeleteEntryCommand command = (DeleteEntryCommand) parser.parseCommand(
                DeleteEntryCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased());
        assertEquals(new DeleteEntryCommand(INDEX_FIRST_ENTRY), command);
    }

    @Test
    public void parseCommand_editEntryInfo() throws Exception {
        EditEntryInfoDescriptor descriptor = new EditEntryInfoDescriptor(WORK_FACEBOOK);
        String userInput = INDEX_FIRST_ENTRY.getOneBased()
                + TITLE_DESC_FACEBOOK + SUBTITLE_DESC_FACEBOOK + DURATION_FACEBOOK;
        EditEntryInfoCommand command = (EditEntryInfoCommand) parser
                .parseCommand(EditEntryInfoCommand.COMMAND_WORD + " " + userInput);
        assertEquals(new EditEntryInfoCommand(INDEX_FIRST_ENTRY, descriptor), command);
    }

    @Test
    public void parseCommand_addBullet() throws Exception {
        AddBulletCommand command = (AddBulletCommand) parser.parseCommand(AddBulletCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ENTRY.getOneBased() + " " + "test bullet");
        assertEquals(new AddBulletCommand(INDEX_FIRST_ENTRY, "test bullet"), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_loadtemplate() throws Exception {
        assertTrue(parser.parseCommand(LoadTemplateCommand.COMMAND_WORD + " template.txt")
                instanceof LoadTemplateCommand);
    }

    @Test
    public void parseCommand_context() throws Exception {
        String expression = "   nussu exco member    ";
        ContextCommand command = (ContextCommand) parser.parseCommand(
            ContextCommand.COMMAND_WORD + expression);
        assertEquals(new ContextCommand(expression.trim()), command);
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
    public void parseCommand_make() throws Exception {
        assertTrue(parser.parseCommand(MakeCommand.COMMAND_WORD + " sep.txt") instanceof MakeCommand);
    }

    @Test
    public void parseCommand_selectEntry() throws Exception {
        SelectEntryCommand command = (SelectEntryCommand) parser.parseCommand(
                SelectEntryCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased());
        assertEquals(new SelectEntryCommand(INDEX_FIRST_ENTRY), command);
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
