package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

/**
 * Test class for ModifyPermissionCommand
 * All test here use the Person in first index of model, which only contains ASSIGN_PERMISSION permission.
 */
public class ModifyPermissionCommandTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
                getTypicalArchiveList(), new UserPrefs());
        model.setLoggedInUser(User.getAdminUser());
    }

    @Test
    public void execute_noModification_failure() {
        Set<Permission> toAdd = new HashSet<>();
        Set<Permission> toRemove = new HashSet<>();
        ModifyPermissionCommand command = new ModifyPermissionCommand(INDEX_FIRST_PERSON, toAdd, toRemove);

        //No modification to permission -> CommandException
        Assert.assertThrows(CommandException.class, () -> command.execute(model, commandHistory));

        toRemove.add(Permission.ADD_EMPLOYEE);
        Assert.assertThrows(CommandException.class, () -> command.execute(model, commandHistory));

        toRemove.clear();
        toAdd.add(Permission.ADD_EMPLOYEE);

        try {
            command.execute(model, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("Execution of command shouldn't fail", e);
        }

        Assert.assertThrows(CommandException.class, () -> command.execute(model, commandHistory));

    }

    @Test
    public void execute_addPermission_success() {
        Set<Permission> toAdd = new HashSet<>();
        toAdd.add(Permission.ADD_EMPLOYEE);
        toAdd.add(Permission.ADD_ASSIGNMENT);
        Set<Permission> toRemove = new HashSet<>();
        ModifyPermissionCommand command = new ModifyPermissionCommand(INDEX_FIRST_PERSON, toAdd, toRemove);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedFirstPerson = new PersonBuilder(firstPerson).build();
        editedFirstPerson.getPermissionSet().addPermissions(toAdd);
        editedFirstPerson.getPermissionSet().removePermissions(toRemove);

        String expectedString = String.format(ModifyPermissionCommand.MESSAGE_MODIFY_PERMISSION_SUCCESS,
                editedFirstPerson.getName(), editedFirstPerson.getPermissionSet());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedFirstPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedString, expectedModel);
    }

    @Test
    public void execute_addPermission_failure() {
        Set<Permission> toAdd = new HashSet<>();
        toAdd.add(Permission.DELETE_EMPLOYEE);
        Set<Permission> toRemove = new HashSet<>();
        ModifyPermissionCommand command = new ModifyPermissionCommand(INDEX_FIRST_PERSON, toAdd, toRemove);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedFirstPerson = new PersonBuilder(firstPerson).build();
        editedFirstPerson.getPermissionSet().addPermissions(toAdd);
        editedFirstPerson.getPermissionSet().removePermissions(toRemove);

        String expectedString = String.format(ModifyPermissionCommand.MESSAGE_NO_MODIFICATION,
                editedFirstPerson.getPermissionSet());

        assertCommandFailure(command, model, commandHistory, expectedString);
    }

    @Test
    public void execute_removePermission_success() {
        Set<Permission> toAdd = new HashSet<>();
        Set<Permission> toRemove = new HashSet<>();
        toRemove.add(Permission.DELETE_EMPLOYEE);
        ModifyPermissionCommand command = new ModifyPermissionCommand(INDEX_FIRST_PERSON, toAdd, toRemove);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedFirstPerson = new PersonBuilder(firstPerson).build();
        editedFirstPerson.getPermissionSet().addPermissions(toAdd);
        editedFirstPerson.getPermissionSet().removePermissions(toRemove);

        String expectedString = String.format(ModifyPermissionCommand.MESSAGE_MODIFY_PERMISSION_SUCCESS,
                editedFirstPerson.getName(), editedFirstPerson.getPermissionSet());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedFirstPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedString, expectedModel);
    }

    @Test
    public void execute_removePermission_failure() {
        Set<Permission> toAdd = new HashSet<>();
        Set<Permission> toRemove = new HashSet<>();
        toRemove.add(Permission.ADD_EMPLOYEE);
        ModifyPermissionCommand command = new ModifyPermissionCommand(INDEX_FIRST_PERSON, toAdd, toRemove);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedFirstPerson = new PersonBuilder(firstPerson).build();
        editedFirstPerson.getPermissionSet().addPermissions(toAdd);
        editedFirstPerson.getPermissionSet().removePermissions(toRemove);

        String expectedString = String.format(ModifyPermissionCommand.MESSAGE_NO_MODIFICATION,
                editedFirstPerson.getPermissionSet());

        assertCommandFailure(command, model, commandHistory, expectedString);
    }

    @Test
    public void execute_addAndRemovePermission_success() {
        Set<Permission> toAdd = new HashSet<>();
        Set<Permission> toRemove = new HashSet<>();
        toAdd.add(Permission.ADD_EMPLOYEE);
        toRemove.add(Permission.DELETE_EMPLOYEE);
        ModifyPermissionCommand command = new ModifyPermissionCommand(INDEX_FIRST_PERSON, toAdd, toRemove);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedFirstPerson = new PersonBuilder(firstPerson).build();
        editedFirstPerson.getPermissionSet().addPermissions(toAdd);
        editedFirstPerson.getPermissionSet().removePermissions(toRemove);

        String expectedString = String.format(ModifyPermissionCommand.MESSAGE_MODIFY_PERMISSION_SUCCESS,
                editedFirstPerson.getName(), editedFirstPerson.getPermissionSet());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedFirstPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedString, expectedModel);
    }

    @Test
    public void execute_addAndRemovePermission_failure() {
        Set<Permission> toAdd = new HashSet<>();
        Set<Permission> toRemove = new HashSet<>();
        toAdd.add(Permission.DELETE_EMPLOYEE);
        toRemove.add(Permission.ADD_EMPLOYEE);
        ModifyPermissionCommand command = new ModifyPermissionCommand(INDEX_FIRST_PERSON, toAdd, toRemove);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedFirstPerson = new PersonBuilder(firstPerson).build();
        editedFirstPerson.getPermissionSet().addPermissions(toAdd);
        editedFirstPerson.getPermissionSet().removePermissions(toRemove);

        String expectedString = String.format(ModifyPermissionCommand.MESSAGE_NO_MODIFICATION,
                editedFirstPerson.getPermissionSet());

        assertCommandFailure(command, model, commandHistory, expectedString);
    }

}
