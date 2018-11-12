package systemtests;

import org.junit.Test;

import seedu.superta.logic.commands.CreateTutorialGroupCommand;
import seedu.superta.model.Model;
import seedu.superta.model.student.Student;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.TutorialGroupBuilder;
import seedu.superta.testutil.TypicalSuperTaClient;

public class CreateTutorialGroupSystemTest extends SuperTaClientSystemTest {

    @Test
    public void createTutorialGroup() {
        Model model = getModel();

        Student firstStudent = TypicalSuperTaClient.ALICE;
        Student secondStudent = TypicalSuperTaClient.BOB;

        /* Case: Adding a tutorial group with leading spaces and trailing spaces should add the tutorial group. */
        TutorialGroupBuilder tgBuilder = new TutorialGroupBuilder();
        tgBuilder.withId("04a");
        tgBuilder.withName("CS1101S Studio 04A");
        TutorialGroup typicalTg = tgBuilder.build();
        String command = "  " + CreateTutorialGroupCommand.COMMAND_WORD + " n/CS1101S Studio 04A " + "id/04a    ";

        assertCommandSuccess(command, typicalTg, String.format(CreateTutorialGroupCommand.MESSAGE_SUCCESS, typicalTg));

        /* Case: undo creation -> TutorialGroup deleted. */
        /**
        command = UndoCommand.COMMAND_WORD;
        executeCommand(command);
        Model expected = getModel();

        assertEquals(model, expected);
         **/

        /* Case: redo -> TutorialGroup added again. */
        /**
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.addTutorialGroup(typicalTg);
        assertCommandSuccess(command, model, expectedResultMessage);
         **/

        /* Case: Add a tutorial group with the same name, but different ID. -> Added. */
        /**
        command = CreateTutorialGroupCommand.COMMAND_WORD + " n/CS1101S Studio 04A " + "id/cs1101s04a";
        TutorialGroup tester = tgBuilder.withId("cs1101s04a").build();
        model.addTutorialGroup(tester);
        expectedResultMessage = String.format(CreateTutorialGroupCommand.MESSAGE_SUCCESS, tester);
        assertCommandSuccess(command, tester, expectedResultMessage);
         **/

    }

    /**
     * Checks whether the executed command will produced the expected model with the expected message.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Checks whether the assertion was correct.
     */
    private void assertCommandSuccess(String command, TutorialGroup expected, String expectedMessage) {
        Model expectedModel = getModel();
        expectedModel.addTutorialGroup(expected);
        assertCommandSuccess(command, expectedModel, expectedMessage);
    }

}
