package seedu.modsuni.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.testutil.TypicalCredentials.getTypicalCredentialStore;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_SEB;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.testutil.EditStudentDescriptorBuilder;
import seedu.modsuni.testutil.StudentBuilder;

public class EditStudentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(
        getTypicalModuleList(),
        new UserPrefs(),
        getTypicalCredentialStore());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() {
        model.setCurrentUser(new StudentBuilder().build());
    }

    @Test
    public void constructorNullDescriptorThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditStudentCommand(null);
    }

    @Test
    public void executeAllFieldsSpecifiedSuccess() {
        Student editedStudent = new StudentBuilder()
            .withName("Max Emilian Verstappen")
            .withEnrollmentDate("01/01/2001")
            .withMajor(Arrays.asList("IS"))
            .withMinor(Arrays.asList("EEE"))
            .build();
        EditStudentDescriptor descriptor =
            new EditStudentDescriptorBuilder(editedStudent).build();

        EditStudentCommand editStudentCommand =
            new EditStudentCommand(descriptor);

        String expectedMessage = String.format(
            EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
            editedStudent.toString());

        ModelManager expectedModel = new ModelManager(
            model.getModuleList(),
            new UserPrefs(),
            model.getCredentialStore());
        expectedModel.setCurrentUser(editedStudent);

        assertCommandSuccess(
            editStudentCommand,
            model,
            commandHistory,
            expectedMessage,
            expectedModel);
    }

    @Test
    public void executeSomeFieldsSpecifiedSuccess() {
        Student editedStudent = new StudentBuilder()
            .withName("Max Emilian Verstappen")
            .build();
        EditStudentDescriptor descriptor =
            new EditStudentDescriptorBuilder(editedStudent).build();

        EditStudentCommand editStudentCommand =
            new EditStudentCommand(descriptor);

        String expectedMessage = String.format(
            EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
            editedStudent.toString());

        ModelManager expectedModel = new ModelManager(
            model.getModuleList(),
            new UserPrefs(),
            model.getCredentialStore());
        expectedModel.setCurrentUser(editedStudent);

        assertCommandSuccess(
            editStudentCommand,
            model,
            commandHistory,
            expectedMessage,
            expectedModel);
    }

    @Test
    public void executeNoFieldsSpecifiedSuccess() {
        EditStudentCommand editStudentCommand =
            new EditStudentCommand(new EditStudentDescriptor());

        Student editedStudent = (Student) model.getCurrentUser();

        String expectedMessage = String.format(
            EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
            editedStudent.toString());

        ModelManager expectedModel = new ModelManager(
            model.getModuleList(),
            new UserPrefs(),
            model.getCredentialStore());
        expectedModel.setCurrentUser(editedStudent);

        assertCommandSuccess(
            editStudentCommand,
            model,
            commandHistory,
            expectedMessage,
            expectedModel);
    }

    @Test
    public void executeCurrentUserNullThrowsCommandException() throws CommandException {

        Student editedStudent = new StudentBuilder()
            .withName("Max Emilian Verstappen")
            .withEnrollmentDate("01/01/2001")
            .withMajor(Arrays.asList("IS"))
            .withMinor(Arrays.asList("EEE"))
            .build();

        EditStudentDescriptor descriptor =
            new EditStudentDescriptorBuilder(editedStudent).build();

        EditStudentCommand editStudentCommand =
            new EditStudentCommand(descriptor);

        ModelManager newModel = new ModelManager(
            model.getModuleList(),
            new UserPrefs(),
            model.getCredentialStore());

        thrown.expect(CommandException.class);
        thrown.expectMessage(EditStudentCommand.MESSAGE_NOT_LOGGED_IN);

        editStudentCommand.execute(newModel, commandHistory);
    }

    @Test
    public void equals() {
        Student max = STUDENT_MAX;
        Student seb = STUDENT_SEB;
        EditStudentDescriptor maxDescriptor =
            new EditStudentDescriptorBuilder(max).build();
        EditStudentDescriptor sebDescriptor =
            new EditStudentDescriptorBuilder(seb).build();

        EditStudentCommand editMaxCommand =
            new EditStudentCommand(maxDescriptor);
        EditStudentCommand editSebCommand =
            new EditStudentCommand(sebDescriptor);

        // same object -> returns true
        assertTrue(editMaxCommand.equals(editMaxCommand));

        // same values -> returns true
        EditStudentCommand editMaxCommandCopy =
            new EditStudentCommand(maxDescriptor);
        assertTrue(editMaxCommand.equals(editMaxCommandCopy));

        // different values -> returns false
        assertFalse(editMaxCommand.equals(1));

        // null -> returns false
        assertFalse(editMaxCommand.equals(null));

        // different user -> returns false
        assertFalse(editMaxCommand.equals(editSebCommand));


    }
}
