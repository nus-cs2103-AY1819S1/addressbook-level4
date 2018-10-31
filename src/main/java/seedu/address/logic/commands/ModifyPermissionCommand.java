package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_PERMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_PERMISSION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.permission.Permission;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Username;
import seedu.address.model.project.Project;

/**
 * Modify the permission an employee have.
 */
public class ModifyPermissionCommand extends Command {

    public static final String COMMAND_WORD = "modifypermission";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the permission of the person identified "
            + "by the index number used in the displayed person list.\n "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ADD_PERMISSION + " PERMISSION_TO_ADD]... "
            + "[" + PREFIX_REMOVE_PERMISSION + " PERMISSION_TO_REMOVE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADD_PERMISSION + " ADD_EMPLOYEE "
            + PREFIX_REMOVE_PERMISSION + " VIEW_PROJECT";

    public static final String MESSAGE_MODIFY_PERMISSION_SUCCESS = "Permission modified for %s.\nNew Permissions : %s";
    public static final String MESSAGE_NO_MODIFICATION = "At least 1 permission that can be added or removed "
            + "must be provided.";
    public static final String MESSAGE_ADD_AND_REMOVE_SAME_PERMISSION = "Permission to be added and removed "
            + "cannot be the same.";


    private Index index;
    private Set<Permission> toAdd;
    private Set<Permission> toRemove;

    public ModifyPermissionCommand(Index index, Set<Permission> toAdd, Set<Permission> toRemove) {
        requireAllNonNull(index, toAdd, toRemove);

        this.requiredPermission.addPermissions(Permission.ASSIGN_PERMISSION);
        this.index = index;
        this.toAdd = toAdd;
        this.toRemove = toRemove;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        boolean isEdited;

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = duplicatePerson(personToEdit);
        isEdited = editedPerson.getPermissionSet().addPermissions(toAdd);
        isEdited = editedPerson.getPermissionSet().removePermissions(toRemove) || isEdited;

        if (!isEdited) {
            throw new CommandException(MESSAGE_NO_MODIFICATION);
        }

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_MODIFY_PERMISSION_SUCCESS, editedPerson.getName(),
                editedPerson.getPermissionSet()));
    }

    /**
     * Creates and return a {@code Person} with the details of {@code p}
     */
    private Person duplicatePerson(Person p) {
        Name name = p.getName();
        Phone phone = p.getPhone();
        Email email = p.getEmail();
        Address address = p.getAddress();
        Salary salary = p.getSalary();
        Username username = p.getUsername();
        Password password = p.getPassword();
        Set<Project> projects = p.getProjects();
        PermissionSet pSet = p.getPermissionSet();
        return new Person(name, phone, email, address, salary, username, password, projects, pSet);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModifyPermissionCommand)) {
            return false;
        }

        // state check
        ModifyPermissionCommand mp = (ModifyPermissionCommand) other;
        return index.equals(mp.index)
                && toAdd.equals(mp.toAdd)
                && toRemove.equals(mp.toRemove);
    }
}
