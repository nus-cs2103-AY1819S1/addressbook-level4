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

public class RedoCommandTest {

    private final Model modelforPerson = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    private final Model expectedModelforPerson = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    private final Model modelforModule = new ModelManager(getTypicalModulesAddressBook(), new UserPrefs());
    private final Model expectedModelforModule = new ModelManager(getTypicalModulesAddressBook(), new UserPrefs());
    private final Model modelforOccasion = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
    private final Model expectedModelforOccasion = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(modelforPerson);
        deleteFirstPerson(modelforPerson);
        modelforPerson.undoAddressBook();
        modelforPerson.undoAddressBook();

        deleteFirstPerson(expectedModelforPerson);
        deleteFirstPerson(expectedModelforPerson);
        expectedModelforPerson.undoAddressBook();
        expectedModelforPerson.undoAddressBook();

        deleteFirstModule(modelforModule);
        deleteFirstModule(modelforModule);
        modelforModule.undoAddressBook();
        modelforModule.undoAddressBook();

        deleteFirstModule(expectedModelforModule);
        deleteFirstModule(expectedModelforModule);
        expectedModelforModule.undoAddressBook();
        expectedModelforModule.undoAddressBook();

        deleteFirstOccasion(modelforOccasion);
        deleteFirstOccasion(modelforOccasion);
        modelforOccasion.undoAddressBook();
        modelforOccasion.undoAddressBook();

        deleteFirstOccasion(expectedModelforOccasion);
        deleteFirstOccasion(expectedModelforOccasion);
        expectedModelforOccasion.undoAddressBook();
        expectedModelforOccasion.undoAddressBook();
    }

    @Test
    public void execute() {
        // Person level
        // multiple redoable states in model
        expectedModelforPerson.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelforPerson, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelforPerson);

        // single redoable state in model
        expectedModelforPerson.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelforPerson, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelforPerson);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), modelforPerson, commandHistory, RedoCommand.MESSAGE_FAILURE);

        // Module Level
        // multiple redoable states in model
        expectedModelforModule.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelforModule, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelforModule);

        // single redoable state in model
        expectedModelforModule.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelforModule, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelforModule);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), modelforModule, commandHistory, RedoCommand.MESSAGE_FAILURE);

        // Occasion level
        // multiple redoable states in model
        expectedModelforOccasion.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelforOccasion, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelforOccasion);

        // single redoable state in model
        expectedModelforOccasion.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelforOccasion, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelforOccasion);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), modelforOccasion, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
