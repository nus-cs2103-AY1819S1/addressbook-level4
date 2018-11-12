package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.LEAVEDATES_DESC_BENSON_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.LEAVEDESCIPTION_DESC_BENSON_LEAVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_PERMISSION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ActiveCommand;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.AssignmentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LeaveApplyCommand;
import seedu.address.logic.commands.LeaveApproveCommand;
import seedu.address.logic.commands.LeaveListCommand;
import seedu.address.logic.commands.LeaveRejectCommand;
import seedu.address.logic.commands.ListAssignmentCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ModifyPermissionCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewPermissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.project.Assignment;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.AssignmentUtil;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.LeaveApplicationBuilder;
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
    public void parseCommand_modifyPermissionCommandWord_returnsModifyPermissionCommand() throws Exception {
        assertTrue(parser.parseCommand(ModifyPermissionCommand.COMMAND_WORD + " 1 " + PREFIX_ADD_PERMISSION
                + Permission.ADD_EMPLOYEE.name()) instanceof ModifyPermissionCommand);
    }

    @Test
    public void parseCommand_leaveapply() throws Exception {
        LeaveApplication leaveApplication = new LeaveApplicationBuilder().build();
        LeaveApplyCommand command = (LeaveApplyCommand) parser.parseCommand(LeaveApplyCommand.COMMAND_WORD
                + LEAVEDESCIPTION_DESC_BENSON_LEAVE + LEAVEDATES_DESC_BENSON_LEAVE);
        assertEquals(new LeaveApplyCommand(leaveApplication), command);
    }

    @Test
    public void parseCommand_leavelist() throws Exception {
        assertTrue(parser.parseCommand(LeaveListCommand.COMMAND_WORD) instanceof LeaveListCommand);
        assertTrue(parser.parseCommand(LeaveListCommand.COMMAND_WORD + " 3") instanceof LeaveListCommand);
    }

    @Test
    public void parseCommand_leaveapprove() throws Exception {
        LeaveApproveCommand command = (LeaveApproveCommand) parser.parseCommand(
                LeaveApproveCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LeaveApproveCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_leavereject() throws Exception {
        LeaveRejectCommand command = (LeaveRejectCommand) parser.parseCommand(
                LeaveRejectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LeaveRejectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_viewPermissionCommand() throws Exception {
        ViewPermissionCommand command = (ViewPermissionCommand) parser.parseCommand(
                ViewPermissionCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ViewPermissionCommand(INDEX_FIRST_PERSON), command);
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

    @Test
    public void parseCommand_addAssignment() throws Exception {
        Assignment assignment = new AssignmentBuilder().build();
        AddAssignmentCommand command =
                (AddAssignmentCommand) parser.parseCommand(AssignmentUtil.getAddAssignmentCommand(assignment));
        assertEquals(new AddAssignmentCommand(assignment), command);
    }

    @Test
    public void parseCommand_listAssignment() throws Exception {
        assertTrue(parser.parseCommand(ListAssignmentCommand.COMMAND_WORD) instanceof ListAssignmentCommand);
        assertTrue(parser.parseCommand(ListAssignmentCommand.COMMAND_WORD + " 3") instanceof ListAssignmentCommand);
    }

    @Test
    public void parseCommand_deleteAssignment() throws Exception {
        DeleteAssignmentCommand command = (DeleteAssignmentCommand) parser.parseCommand(
                DeleteAssignmentCommand.COMMAND_WORD + " " + INDEX_FIRST_ASSIGNMENT.getOneBased());
        assertEquals(new DeleteAssignmentCommand(INDEX_FIRST_ASSIGNMENT), command);
    }

    @Test
    public void parseCommand_editAssignment() throws Exception {
        Assignment assignment = new AssignmentBuilder().build();
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(assignment).build();
        EditAssignmentCommand command =
                (EditAssignmentCommand) parser.parseCommand(EditAssignmentCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ASSIGNMENT.getOneBased() + " "
                        + AssignmentUtil.getEditAssignmentDescriptorDetails(descriptor));
        assertEquals(new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, descriptor), command);
    }

    @Test
    public void parseCommand_assignment() throws Exception {
        assertTrue(parser.parseCommand(AssignmentCommand.COMMAND_WORD) instanceof AssignmentCommand);
        assertTrue(parser.parseCommand(AssignmentCommand.COMMAND_WORD + " 3") instanceof AssignmentCommand);
    }

    @Test
    public void parseCommand_active() throws Exception {
        assertTrue(parser.parseCommand(ActiveCommand.COMMAND_WORD) instanceof ActiveCommand);
        assertTrue(parser.parseCommand(ActiveCommand.COMMAND_WORD + " 3") instanceof ActiveCommand);
    }

    @Test
    public void parseCommand_archive() throws Exception {
        assertTrue(parser.parseCommand(ArchiveCommand.COMMAND_WORD) instanceof ArchiveCommand);
        assertTrue(parser.parseCommand(ArchiveCommand.COMMAND_WORD + " 3") instanceof ArchiveCommand);
    }

}
