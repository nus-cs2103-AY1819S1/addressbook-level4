package seedu.planner.logic.commands;

import static seedu.planner.commons.util.CollectionUtil.formatMessage;
import static seedu.planner.commons.util.CollectionUtil.getAnyOne;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_MODULE_CS0000;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MODULE_CS1010;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MODULE_CS1231;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MODULE_CS2040;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.DeleteCommand.MESSAGE_DELETE_MODULES_SUCCESS;
import static seedu.planner.logic.commands.DeleteCommand.MESSAGE_NON_EXISTENT_MODULES;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.ModulePlanner;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.module.Module;
import seedu.planner.testutil.ModulePlannerBuilder;

//@@author GabrielYik

public class DeleteCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private Model expectedModel;

    @Before
    public void setUp() {
        ModulePlanner mp1 = new ModulePlannerBuilder()
                .withModule(VALID_MODULE_CS1010)
                .withModule(VALID_MODULE_CS1231)
                .withModule(VALID_MODULE_CS2030)
                .build();
        model = new ModelManager(mp1, new UserPrefs());

        ModulePlanner mp2 = new ModulePlannerBuilder()
                .withModule(VALID_MODULE_CS1010)
                .withModule(VALID_MODULE_CS1231)
                .withModule(VALID_MODULE_CS2030)
                .build();
        expectedModel = new ModelManager(mp2, new UserPrefs());
    }

    @Test
    public void execute_validModule_success() {
        Set<Module> moduleToDelete = Set.of(VALID_MODULE_CS1010);
        DeleteCommand deleteCommand = new DeleteCommand(moduleToDelete);

        String expectedMessage = String.format(
                MESSAGE_DELETE_MODULES_SUCCESS, getAnyOne(moduleToDelete).get());
        expectedModel.deleteModules(moduleToDelete);
        expectedModel.commitModulePlanner();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validModules_success() {
        Set<Module> modulesToDelete = Set.of(VALID_MODULE_CS1010, VALID_MODULE_CS1231);
        DeleteCommand deleteCommand = new DeleteCommand(modulesToDelete);

        String expectedMessage = formatMessage(MESSAGE_DELETE_MODULES_SUCCESS, modulesToDelete);
        expectedModel.deleteModules(modulesToDelete);
        expectedModel.commitModulePlanner();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentModule_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(Set.of(VALID_MODULE_CS2040));

        String expectedMessage = String.format(MESSAGE_NON_EXISTENT_MODULES, VALID_MODULE_CS2040);

        assertCommandFailure(deleteCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validAndNonExistentModule() {
        Set<Module> modulesToDelete = Set.of(VALID_MODULE_CS1010, INVALID_MODULE_CS0000, VALID_MODULE_CS2040);
        DeleteCommand deleteCommand = new DeleteCommand(modulesToDelete);

        List<Module> expectedNonExistentModules = List.of(INVALID_MODULE_CS0000, VALID_MODULE_CS2040);
        String expectedMessage = String.format(MESSAGE_DELETE_MODULES_SUCCESS, VALID_MODULE_CS1010)
                + "\n" + formatMessage(MESSAGE_NON_EXISTENT_MODULES, expectedNonExistentModules);
        expectedModel.deleteModules(Set.of(VALID_MODULE_CS1010));
        expectedModel.commitModulePlanner();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
