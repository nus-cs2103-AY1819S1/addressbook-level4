package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.module.Module.createEditedModule;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowModuleRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;

/**
 * Edits the details of an existing module in the address book.
 *
 * @author alistair
 * @author kongzijin
 */
public class EditModuleCommand extends Command {

    public static final String COMMAND_WORD = "editmodule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
            + "by the module code used in the displayed module list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MODULECODE + "MODULE CODE] "
            + "[" + PREFIX_MODULETITLE + "MODULE TITLE] "
            + "[" + PREFIX_ACADEMICYEAR + "ACADEMIC YEAR] "
            + "[" + PREFIX_SEMESTER + "SEMESTER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULECODE + "CS1101S ";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the address book.";

    private final Index index;
    private final ModuleDescriptor editModuleDescriptor;

    /**
     * @param index of the module in the filtered module list to edit
     * @param editModuleDescriptor details to edit the module with
     */
    public EditModuleCommand(Index index, ModuleDescriptor editModuleDescriptor) {
        requireNonNull(index);
        requireNonNull(editModuleDescriptor);

        this.index = index;
        this.editModuleDescriptor = new ModuleDescriptor(editModuleDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToEdit = lastShownList.get(index.getZeroBased());
        Module editedModule = createEditedModule(moduleToEdit, editModuleDescriptor);
        removeModuleFromAssociatedPersons(model, moduleToEdit, editedModule);

        if (!moduleToEdit.isSameModule(editedModule) && model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.updateModule(moduleToEdit, editedModule);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        model.commitAddressBook();
        EventsCenter.getInstance().post(new ShowModuleRequestEvent());
        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }

    //Reused from teammate @waytan with minor modifications
    /**
     * Removes a module from the moduleList of all associated Persons.
     */
    private void removeModuleFromAssociatedPersons(Model model, Module moduleToEdit, Module editedModule) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> completePersonList = model.getFilteredPersonList();
        ListIterator<Person> personListIterator = completePersonList.listIterator();
        while (personListIterator.hasNext()) {
            Person person = personListIterator.next();
            person.getModuleList()
                    .asNormalList()
                    .stream()
                    .filter(module -> module.isSameModule(moduleToEdit))
                    .findFirst()
                    .ifPresent(editModuleFromPerson(model, person, editedModule));
        }
    }

    //Reused from teammate @waytan with minor modifications
    /**
     * Returns a consumer that takes in a Module and removes it from the specified Person in the Model.
     */
    private Consumer<Module> editModuleFromPerson(Model model, Person person, Module editModule) {
        return module -> {
            PersonDescriptor updatedPersonDescriptor = new PersonDescriptor();
            List<Module> updatedModules = person.getModuleList().makeShallowDuplicate().asNormalList();
            int indexOfModuleToEdit = updatedModules.indexOf(editModule);
            if (indexOfModuleToEdit != -1) {
                updatedModules.remove(module);
                updatedModules.add(indexOfModuleToEdit, editModule);
                UniqueModuleList updatedModuleList = new UniqueModuleList(updatedModules);
                updatedPersonDescriptor.setUniqueModuleList(updatedModuleList);
                Person updatedPerson = Person.createEditedPerson(person, updatedPersonDescriptor);
                model.updatePerson(person, updatedPerson);
            }
        };
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditModuleCommand)) {
            return false;
        }

        // state check
        EditModuleCommand e = (EditModuleCommand) other;
        return index.equals(e.index)
            && editModuleDescriptor.equals(e.editModuleDescriptor);
    }

}

