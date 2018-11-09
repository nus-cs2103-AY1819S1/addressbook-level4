package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TypeUtil.MODULE;
import static seedu.address.commons.util.TypeUtil.PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.model.occasion.UniqueOccasionList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Deletes an entry identified using its displayed index in the active list.
 * @author waytan
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the entry identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_DELETE_OCCASION_SUCCESS = "Deleted Occasion: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        CommandResult commandResult;
        if (model.getActiveType().equals(PERSON)) {
            commandResult = deletePersonAndGetResult(model);
        } else if (model.getActiveType().equals(MODULE)) {
            commandResult = deleteModuleAndGetResult(model);
        } else {
            commandResult = deleteOccasionAndGetResult(model);
        }
        model.commitAddressBook();
        return commandResult;
    }


    /**
     * Deletes the person at the targeted index, and returns the command result.
     */
    private CommandResult deletePersonAndGetResult(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        removePersonFromAssociatedModules(model, personToDelete);
        removePersonFromAssociatedOccasions(model, personToDelete);
        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * Deletes the module at the targeted index, and returns the command result.
     */
    private CommandResult deleteModuleAndGetResult(Model model) throws CommandException {
        List<Module> lastShownList = model.getFilteredModuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToDelete = lastShownList.get(targetIndex.getZeroBased());

        removeModuleFromAssociatedPersons(model, moduleToDelete);
        model.deleteModule(moduleToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    /**
     * Deletes the occasion at the targeted index, and returns the command result.
     */
    private CommandResult deleteOccasionAndGetResult(Model model) throws CommandException {
        List<Occasion> lastShownList = model.getFilteredOccasionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
        }

        Occasion occasionToDelete = lastShownList.get(targetIndex.getZeroBased());

        removeOccasionFromAssociatedPersons(model, occasionToDelete);

        model.deleteOccasion(occasionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_OCCASION_SUCCESS, occasionToDelete));
    }

    /**
     * Removes a person from the attendanceList of all associated Occasions.
     */
    private void removePersonFromAssociatedOccasions(Model model, Person personToDelete) {
        model.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
        List<Occasion> completeOccasionList = model.getFilteredOccasionList();
        ListIterator<Occasion> occasionListIterator = completeOccasionList.listIterator();

        while (occasionListIterator.hasNext()) {
            Occasion occasion = occasionListIterator.next();
            occasion.getAttendanceList()
                    .asNormalList()
                    .stream()
                    .filter(person -> person.isSamePerson(personToDelete))
                    .findFirst()
                    .ifPresent(removePersonFromOccasion(model, occasion));
        }
    }

    /**
     * Removes a person from the studentList of all associated Modules.
     */
    private void removePersonFromAssociatedModules(Model model, Person personToDelete) {
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        List<Module> completeModuleList = model.getFilteredModuleList();
        ListIterator<Module> moduleListIterator = completeModuleList.listIterator();
        while (moduleListIterator.hasNext()) {
            Module module = moduleListIterator.next();
            module.getStudents()
                    .asNormalList()
                    .stream()
                    .filter(person -> person.isSamePerson(personToDelete))
                    .findFirst()
                    .ifPresent(removePersonFromModule(model, module));
        }
    }

    /**
     * Removes a module from the moduleList of all associated Persons.
     */
    private void removeModuleFromAssociatedPersons(Model model, Module moduleToDelete) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> completePersonList = model.getFilteredPersonList();
        ListIterator<Person> personListIterator = completePersonList.listIterator();
        while (personListIterator.hasNext()) {
            Person person = personListIterator.next();
            person.getModuleList()
                    .asNormalList()
                    .stream()
                    .filter(module -> module.isSameModule(moduleToDelete))
                    .findFirst()
                    .ifPresent(removeModuleFromPerson(model, person));
        }
    }

    /**
     * Removes an Occasion from the occasionList of all associated Persons.
     */
    private void removeOccasionFromAssociatedPersons(Model model, Occasion occasionToDelete) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> completePersonList = model.getFilteredPersonList();
        ListIterator<Person> personListIterator = completePersonList.listIterator();

        while (personListIterator.hasNext()) {
            Person person = personListIterator.next();
            person.getOccasionList()
                    .asNormalList()
                    .stream()
                    .filter(occasion -> occasion.isSameOccasion(occasionToDelete))
                    .findFirst()
                    .ifPresent(removeOccasionFromPerson(model, person));
        }
    }

    /**
     * Returns a consumer that takes in a Person and removes it from the specified Occasion in the Model.
     */
    private Consumer<Person> removePersonFromOccasion(Model model, Occasion occasion) {
        return person -> {
            OccasionDescriptor updatedOccasionDescriptor = new OccasionDescriptor();
            List<Person> updatedPersons = occasion.getAttendanceList().makeShallowDuplicate().asNormalList();
            updatedPersons.remove(person);
            UniquePersonList updatedPersonList = new UniquePersonList(updatedPersons);
            updatedOccasionDescriptor.setAttendanceList(updatedPersonList);
            Occasion updatedOccasion = Occasion.createEditedOccasion(occasion, updatedOccasionDescriptor);
            model.updateOccasion(occasion, updatedOccasion);
        };
    }

    /**
     * Returns a consumer that takes in a Person and removes it from the specified module in the model.
     */
    private Consumer<Person> removePersonFromModule(Model model, Module module) {
        return person -> {
            ModuleDescriptor updatedModuleDescriptor = new ModuleDescriptor();
            List<Person> updatedPersons = module.getStudents().makeShallowDuplicate().asNormalList();
            updatedPersons.remove(person);
            UniquePersonList updatedPersonList = new UniquePersonList(updatedPersons);
            updatedModuleDescriptor.setStudents(updatedPersonList);
            Module updatedModule = Module.createEditedModule(module, updatedModuleDescriptor);
            model.updateModule(module, updatedModule);
        };
    }

    /**
     * Returns a consumer that takes in a Module and removes it from the specified Person in the Model.
     */
    private Consumer<Module> removeModuleFromPerson(Model model, Person person) {
        return module -> {
            PersonDescriptor updatedPersonDescriptor = new PersonDescriptor();
            List<Module> updatedModules = person.getModuleList().makeShallowDuplicate().asNormalList();
            updatedModules.remove(module);
            UniqueModuleList updatedModuleList = new UniqueModuleList(updatedModules);
            updatedPersonDescriptor.setUniqueModuleList(updatedModuleList);
            Person updatedPerson = Person.createEditedPerson(person, updatedPersonDescriptor);
            model.updatePerson(person, updatedPerson);
        };
    }

    /**
     * Returns a consumer that takes in an Occasion and removes it from the specified Person in the Model.
     */
    private Consumer<Occasion> removeOccasionFromPerson(Model model, Person person) {
        return occasion -> {
            PersonDescriptor updatedPersonDescriptor = new PersonDescriptor();
            List<Occasion> updatedOccasions = person.getOccasionList().makeShallowDuplicate().asNormalList();
            updatedOccasions.remove(occasion);
            UniqueOccasionList updatedOccasionList = new UniqueOccasionList(updatedOccasions);
            updatedPersonDescriptor.setUniqueOccasionList(updatedOccasionList);
            Person updatedPerson = Person.createEditedPerson(person, updatedPersonDescriptor);
            model.updatePerson(person, updatedPerson);
        };
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
