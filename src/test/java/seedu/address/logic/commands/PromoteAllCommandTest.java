package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.PromoteCommand.MESSAGE_GRADUATED_STUDENTS;
import static seedu.address.logic.commands.PromoteCommand.MESSAGE_SUCCESS;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class PromoteAllCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    // Execute promote all in an empty address book
    @Test
    public void executePromoteAllEmptyAddressBook() {
        Model emptyModel = new ModelManager();
        PromoteAllCommand promoteAllCommand = new PromoteAllCommand();
        String expectedMessage = String.format(MESSAGE_SUCCESS
                + MESSAGE_GRADUATED_STUDENTS, 0, "");

        assertCommandSuccess(promoteAllCommand, emptyModel, commandHistory, expectedMessage, emptyModel);
    }

    // Execute promote all in TutorPal with some final year students and no graduated students
    @Test
    public void executePromoteAllWithoutGraduatedStudents() {
        String expectedMessage = String.format(MESSAGE_SUCCESS
                + MESSAGE_GRADUATED_STUDENTS, 7,
                "Alice Pauline Carl Kurz Daniel Meier Elle Meyer");

        PromoteAllCommand promoteAllCommand = new PromoteAllCommand();
        CommandResult commandResult = promoteAllCommand.execute(model, commandHistory);

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }
}
