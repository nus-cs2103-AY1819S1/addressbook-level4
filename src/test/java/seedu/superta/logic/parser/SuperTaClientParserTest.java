package seedu.superta.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NEW_MAX_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_FEEDBACK;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_NAME;
import static seedu.superta.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.logic.commands.AddCommand;
import seedu.superta.logic.commands.AddStudentToTutorialGroupCommand;
import seedu.superta.logic.commands.ClearCommand;
import seedu.superta.logic.commands.CreateAssignmentCommand;
import seedu.superta.logic.commands.CreateAttendanceCommand;
import seedu.superta.logic.commands.CreateTutorialGroupCommand;
import seedu.superta.logic.commands.DeleteCommand;
import seedu.superta.logic.commands.EditCommand;
import seedu.superta.logic.commands.ExitCommand;
import seedu.superta.logic.commands.FeedbackCommand;
import seedu.superta.logic.commands.FindCommand;
import seedu.superta.logic.commands.HelpCommand;
import seedu.superta.logic.commands.HistoryCommand;
import seedu.superta.logic.commands.ListCommand;
import seedu.superta.logic.commands.MarkAttendanceCommand;
import seedu.superta.logic.commands.RedoCommand;
import seedu.superta.logic.commands.RemoveStudentFromTutorialGroupCommand;
import seedu.superta.logic.commands.SelectCommand;
import seedu.superta.logic.commands.UndoCommand;
import seedu.superta.logic.commands.UpdateAssignmentCommand;
import seedu.superta.logic.commands.UpdateAssignmentCommand.UpdateAssignmentDescriptor;
import seedu.superta.logic.commands.ViewStudentFeedbackCommand;
import seedu.superta.logic.commands.ViewTutorialGroupCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.AssignmentBuilder;
import seedu.superta.testutil.EditStudentDescriptorBuilder;
import seedu.superta.testutil.StudentBuilder;
import seedu.superta.testutil.StudentUtil;
import seedu.superta.testutil.TutorialGroupBuilder;
import seedu.superta.testutil.UpdateAssignmentDescriptorBuilder;

public class SuperTaClientParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final SuperTaClientParser parser = new SuperTaClientParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student), command);
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
        Student student = new StudentBuilder().build();
        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " "
                        + PREFIX_NAME + "alice fooh ida");
        assertTrue(command instanceof FindCommand);
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

    @Test
    public void parseCommand_feedback() throws Exception {
        final Feedback feedback = new Feedback("Some feedback.");
        final StudentId studentId = new StudentId(VALID_STUDENT_ID_AMY);
        FeedbackCommand command = (FeedbackCommand) parser.parseCommand(FeedbackCommand.COMMAND_WORD + " "
                + PREFIX_STUDENT_ID + studentId + " " + PREFIX_FEEDBACK + feedback.value);
        assertEquals(new FeedbackCommand(studentId, feedback), command);
    }

    @Test
    public void parseCommand_createAssignment() throws Exception {
        TutorialGroup tutorialGroup = new TutorialGroupBuilder().build();
        Assignment assignment = new AssignmentBuilder().build();
        CreateAssignmentCommand command = (CreateAssignmentCommand) parser.parseCommand(
                CreateAssignmentCommand.COMMAND_WORD + " "
                        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tutorialGroup.getId() + " "
                        + PREFIX_ASSIGNMENT_TITLE + assignment.getTitle() + " "
                        + PREFIX_ASSIGNMENT_MAX_MARKS + assignment.getMaxMarks());

        assertEquals(new CreateAssignmentCommand(tutorialGroup.getId(), assignment), command);
    }

    @Test
    public void parseCommand_updateAssignment() throws Exception {
        TutorialGroup tutorialGroup = new TutorialGroupBuilder().build();
        Assignment assignmentToChange = new AssignmentBuilder().build();
        Assignment assignmentChanged = new Assignment(new Title("Lab Worksheet"), 90.0,
                assignmentToChange.getGradebook());
        UpdateAssignmentDescriptor descriptor = new UpdateAssignmentDescriptorBuilder(assignmentChanged).build();
        UpdateAssignmentCommand command = (UpdateAssignmentCommand) parser.parseCommand(
                UpdateAssignmentCommand.COMMAND_WORD + " "
                        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tutorialGroup.getId() + " "
                        + PREFIX_GENERAL_ASSIGNMENT_TITLE + assignmentToChange.getTitle() + " "
                        + PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE + assignmentChanged.getTitle() + " "
                        + PREFIX_ASSIGNMENT_NEW_MAX_MARKS + assignmentChanged.getMaxMarks());

        assertEquals(new UpdateAssignmentCommand(
                tutorialGroup.getId(), assignmentToChange.getTitle(), descriptor), command);
    }

    @Test
    public void parseCommand_createAttendance() throws Exception {
        final Session session = new Session("Week 4 tutorial");
        final String tgId = "01A";
        CreateAttendanceCommand command = (CreateAttendanceCommand) parser.parseCommand(
                CreateAttendanceCommand.COMMAND_WORD + " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tgId + " "
                + PREFIX_SESSION_NAME + session.getSessionName());
        assertEquals(new CreateAttendanceCommand(tgId, session), command);
    }

    @Test
    public void parseCommand_markAttendance() throws Exception {
        final String tgId = "01A";
        final Session session = new Session("Week 4 tutorial");
        final StudentId studentId = new StudentId(VALID_STUDENT_ID_AMY);
        final Set<StudentId> stIdSet = new HashSet<>();
        stIdSet.add(studentId);

        MarkAttendanceCommand command = (MarkAttendanceCommand) parser.parseCommand(
                MarkAttendanceCommand.COMMAND_WORD + " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tgId + " "
                        + PREFIX_SESSION_NAME + session.getSessionName() + " " + PREFIX_GENERAL_STUDENT_ID + studentId);
        assertEquals(new MarkAttendanceCommand(tgId, session, stIdSet), command);
    }

    @Test
    public void parseCommand_viewFeedback() throws Exception {
        final StudentId studentId = new StudentId(VALID_STUDENT_ID_AMY);

        ViewStudentFeedbackCommand command = (ViewStudentFeedbackCommand) parser.parseCommand(
                ViewStudentFeedbackCommand.COMMAND_WORD + " " + PREFIX_STUDENT_ID + studentId);
        assertEquals(new ViewStudentFeedbackCommand(studentId), command);
    }

    @Test
    public void parseCommand_createTutorialGroup() throws Exception {
        TutorialGroup tutorialGroup = new TutorialGroupBuilder().build();
        CreateTutorialGroupCommand command = (CreateTutorialGroupCommand) parser.parseCommand(
                CreateTutorialGroupCommand.COMMAND_WORD + " "
                        + PREFIX_TUTORIAL_GROUP_NAME + tutorialGroup.getName() + " "
                        + PREFIX_TUTORIAL_GROUP_ID + tutorialGroup.getId());

        assertEquals(new CreateTutorialGroupCommand(tutorialGroup), command);
    }

    @Test
    public void parseCommand_viewTutorialGroup() throws Exception {
        TutorialGroup tutorialGroup = new TutorialGroupBuilder().build();
        ViewTutorialGroupCommand command = (ViewTutorialGroupCommand) parser.parseCommand(
                ViewTutorialGroupCommand.COMMAND_WORD + " "
                        + PREFIX_TUTORIAL_GROUP_ID + tutorialGroup.getId());

        assertEquals(new ViewTutorialGroupCommand(tutorialGroup.getId()), command);
    }

    @Test
    public void parseCommand_addStudentToTutorialGroup() throws Exception {
        TutorialGroup tutorialGroup = new TutorialGroupBuilder().build();
        Student student = new StudentBuilder().build();
        AddStudentToTutorialGroupCommand command = (AddStudentToTutorialGroupCommand) parser.parseCommand(
                AddStudentToTutorialGroupCommand.COMMAND_WORD + " "
                        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tutorialGroup.getId() + " "
                        + PREFIX_GENERAL_STUDENT_ID + student.getStudentId());

        assertEquals(new AddStudentToTutorialGroupCommand(tutorialGroup.getId(), student.getStudentId()), command);
    }

    @Test
    public void parseCommand_removeStudentFromTutorialGroup() throws Exception {
        TutorialGroup tutorialGroup = new TutorialGroupBuilder().build();
        Student student = new StudentBuilder().build();
        tutorialGroup.addStudent(student);

        RemoveStudentFromTutorialGroupCommand command =
                (RemoveStudentFromTutorialGroupCommand) parser.parseCommand(
                RemoveStudentFromTutorialGroupCommand.COMMAND_WORD + " "
                        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tutorialGroup.getId() + " "
                        + PREFIX_GENERAL_STUDENT_ID + student.getStudentId());

        assertEquals(new RemoveStudentFromTutorialGroupCommand(tutorialGroup.getId(), student.getStudentId()), command);
    }

}
