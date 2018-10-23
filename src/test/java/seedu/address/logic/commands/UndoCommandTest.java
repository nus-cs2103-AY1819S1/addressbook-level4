package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertVolunteerCommandFailure;
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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model modelVolunteer = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private final Model expectedModelVolunteer = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);

        deleteFirstVolunteer(modelVolunteer);
        deleteFirstVolunteer(modelVolunteer);

        deleteFirstVolunteer(expectedModelVolunteer);
        deleteFirstVolunteer(expectedModelVolunteer);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);

        // multiple undoable states in model
        expectedModelVolunteer.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelVolunteer, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModelVolunteer);

        // single undoable state in model
        expectedModelVolunteer.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelVolunteer, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModelVolunteer);

        // no undoable states in model
        assertVolunteerCommandFailure(new UndoCommand(), modelVolunteer, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
