package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandModuleTestUtil.deleteFirstModule;
import static seedu.address.logic.commands.CommandOccasionTestUtil.deleteFirstOccasion;
import static seedu.address.logic.commands.CommandPersonTestUtil.deleteFirstPerson;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.getTypicalModulesAddressBook;
import static seedu.address.testutil.TypicalOccasions.getTypicalOccasionsAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {

    private final Model modelforPerson = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    private final Model expectedModelforPerson = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    private final Model modelforModule = new ModelManager(getTypicalModulesAddressBook(), new UserPrefs());
    private final Model expectedModelforModule = new ModelManager(getTypicalModulesAddressBook(), new UserPrefs());
    private final Model modelforOccasion = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
    private final Model expectedModelforOccasion = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPerson(modelforPerson);
        deleteFirstPerson(modelforPerson);

        deleteFirstPerson(expectedModelforPerson);
        deleteFirstPerson(expectedModelforPerson);

        deleteFirstModule(modelforModule);
        deleteFirstModule(modelforModule);

        deleteFirstModule(expectedModelforModule);
        deleteFirstModule(expectedModelforModule);

        deleteFirstOccasion(modelforOccasion);
        deleteFirstOccasion(modelforOccasion);

        deleteFirstOccasion(expectedModelforOccasion);
        deleteFirstOccasion(expectedModelforOccasion);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModelforPerson.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelforPerson, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModelforPerson);

        // single undoable state in model
        expectedModelforPerson.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelforPerson, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModelforPerson);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), modelforPerson, commandHistory, UndoCommand.MESSAGE_FAILURE);

        // multiple undoable states in model
        expectedModelforModule.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelforModule, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModelforModule);

        // single undoable state in model
        expectedModelforModule.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelforModule, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModelforModule);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), modelforModule, commandHistory, UndoCommand.MESSAGE_FAILURE);

        // multiple undoable states in model
        expectedModelforOccasion.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelforOccasion, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModelforOccasion);

        // single undoable state in model
        expectedModelforOccasion.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelforOccasion, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModelforOccasion);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), modelforOccasion, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
