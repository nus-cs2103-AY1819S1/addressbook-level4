package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertVolunteerCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstVolunteer;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model modelVolunteer = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private final Model expectedModelVolunteer = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);
        model.undoAddressBook();
        model.undoAddressBook();

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        expectedModel.undoAddressBook();
        expectedModel.undoAddressBook();

        deleteFirstVolunteer(modelVolunteer);
        deleteFirstVolunteer(modelVolunteer);
        modelVolunteer.undoAddressBook();
        modelVolunteer.undoAddressBook();

        deleteFirstVolunteer(expectedModelVolunteer);
        deleteFirstVolunteer(expectedModelVolunteer);
        expectedModelVolunteer.undoAddressBook();
        expectedModelVolunteer.undoAddressBook();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);

        // multiple redoable states in model
        expectedModelVolunteer.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelVolunteer, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelVolunteer);

        // single redoable state in model
        expectedModelVolunteer.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelVolunteer, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelVolunteer);

        // no redoable state in model
        assertVolunteerCommandFailure(new RedoCommand(), modelVolunteer, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
