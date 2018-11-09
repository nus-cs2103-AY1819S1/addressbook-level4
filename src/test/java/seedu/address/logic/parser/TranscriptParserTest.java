package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalModules.ASKING_QUESTIONS;
import static seedu.address.testutil.TypicalModules.DATA_STRUCTURES;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;

import java.util.EnumMap;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.AdjustCommand;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.commands.GoalCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoModuleCommand;
import seedu.address.logic.commands.UndoModuleCommand;
import seedu.address.logic.parser.arguments.EditArgument;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ModuleUtil;
import seedu.address.testutil.TypicalModules;

//@@author alexkmj
/**
 * Unit testing for {@code TranscriptParser}.
 * <p>
 * Tests for the following:
 * <ul>
 *     <li>null command should fail</li>
 *     <li>empty command should fail</li>
 *     <li>invalid command should fail</li>
 *     <li>Add module command</li>
 *     <li>Delete module command</li>
 *     <li>Edit module command</li>
 * </ul>
 */
public class TranscriptParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * {@code TranscriptParser} used for all tests in {@TranscriptParserTest}.
     */
    private final TranscriptParser parser = new TranscriptParser();

    /**
     * {@code null}, empty, or invalid command should fail.
     */
    @Test
    public void parseCommandInvalidModuleFail() {
        Assert.assertThrows(NullPointerException.class, () -> {
            parser.parseCommand(null);
        });

        Assert.assertThrows(ParseException.class, () -> {
            parser.parseCommand("");
        });

        Assert.assertThrows(ParseException.class, () -> {
            parser.parseCommand("random");
        });
    }

    /**
     * Tests for add module command.
     *
     * @throws ParseException parse exception that should not be thrown
     */
    @Test
    public void parseCommandAddModuleSuccess() throws ParseException {
        AddModuleCommand addModuleCommand = new AddModuleCommand(DISCRETE_MATH);
        String addModuleString = ModuleUtil.getAddModuleCommand(DISCRETE_MATH);
        AddModuleCommand command = (AddModuleCommand) parser
                .parseCommand(addModuleString);
        assertEquals(addModuleCommand, command);
    }

    /**
     * Test for delete module command.
     *
     * @throws ParseException parse exception that should not be thrown
     */
    @Test
    public void parseCommandDeleteModuleSuccess() throws ParseException {
        DeleteModuleCommand deleteModuleCommand =
                new DeleteModuleCommand(DISCRETE_MATH.getCode());
        String deleteModuleString = ModuleUtil
                .getDeleteModuleCommand(DISCRETE_MATH);
        DeleteModuleCommand command = (DeleteModuleCommand) parser
                .parseCommand(deleteModuleString);
        assertEquals(deleteModuleCommand, command);
    }

    /**
     * Test for edit module command.
     *
     * @throws ParseException
     */
    @Test
    public void parseCommandEditModuleSuccess() throws ParseException {
        EnumMap<EditArgument, Object> argMap;
        argMap = new EnumMap<>(EditArgument.class);
        argMap.put(EditArgument.TARGET_CODE, DATA_STRUCTURES.getCode());
        argMap.put(EditArgument.TARGET_YEAR, DATA_STRUCTURES.getYear());
        argMap.put(EditArgument.TARGET_SEMESTER, DATA_STRUCTURES.getSemester());
        argMap.put(EditArgument.NEW_CODE, ASKING_QUESTIONS.getCode());

        EditModuleCommand editModuleCommand = new EditModuleCommand(argMap);
        String editModuleString = ModuleUtil
                .getEditModuleCommand(DATA_STRUCTURES,
                        ASKING_QUESTIONS.getCode(),
                        null,
                        null,
                        null,
                        null);

        EditModuleCommand command = (EditModuleCommand) parser
                .parseCommand(editModuleString);
        assertEquals(editModuleCommand, command);
    }

    //@@author jeremiah-ang
    @Test
    public void parseCommandAdjustModuleSuccess() throws ParseException {
        Module target = TypicalModules.DATA_STRUCTURES;
        AdjustCommand adjustCommand = new AdjustCommand(
                target.getCode(), target.getYear(), target.getSemester(), target.getGrade());
        AdjustCommand command = (AdjustCommand) parser.parseCommand(
                ModuleUtil.getAdjustModuleCommand(
                        target.getCode(), target.getYear(), target.getSemester(), target.getGrade()));
        assertTrue(command instanceof  AdjustCommand);
        assertEquals(adjustCommand, command);
    }

    @Test
    public void parseCommandGoalModuleSuccess() throws ParseException {
        assertTrue(parser.parseCommand(GoalCommand.COMMAND_WORD + " 3") instanceof GoalCommand);
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
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoModuleCommand.COMMAND_WORD) instanceof RedoModuleCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoModuleCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoModuleCommand.COMMAND_WORD) instanceof UndoModuleCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoModuleCommand);
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
