package seedu.superta.logic.commands;

import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Before;
import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.student.Student;
import seedu.superta.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Student validStudent = new StudentBuilder().withStudentId("A0192832X").build();

        Model expectedModel = new ModelManager(model.getSuperTaClient(), new UserPrefs());
        expectedModel.addStudent(validStudent);
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(new AddCommand(validStudent), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validStudent), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Student studentInList = model.getSuperTaClient().getStudentList().get(0);
        assertCommandFailure(new AddCommand(studentInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_STUDENT);
    }

}
