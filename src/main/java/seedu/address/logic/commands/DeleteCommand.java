package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TypeUtil.MODULE;
import static seedu.address.commons.util.TypeUtil.PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

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
        CommandResult commandResult;
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        List<Module> completeModuleList = model.getFilteredModuleList();
        ListIterator<Module> moduleListIterator = completeModuleList.listIterator();
        while (moduleListIterator.hasNext()) {
            Module m = moduleListIterator.next();
            Optional<Person> possiblyPresentPerson = m.getStudents().asNormalList()
                    .stream().filter(person -> person.isSamePerson(personToDelete)).findFirst();
            ModuleDescriptor updatedModuleDescriptor = new ModuleDescriptor();
            possiblyPresentPerson.ifPresent(person -> {
                List<Person> updatedPersons = m.getStudents().makeShallowDuplicate().asNormalList();
                updatedPersons.remove(person);
                UniquePersonList updatedPersonList = new UniquePersonList(updatedPersons);
                updatedModuleDescriptor.setStudents(updatedPersonList);
                Module updatedModule = Module.createEditedModule(m, updatedModuleDescriptor);
                model.updateModule(m, updatedModule);
            });
        }

        model.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
        List<Occasion> completeOccasionList = model.getFilteredOccasionList();
        ListIterator<Occasion> occasionListIterator = completeOccasionList.listIterator();
        while (occasionListIterator.hasNext()) {
            Occasion o = occasionListIterator.next();
            Optional<Person> possiblyPresentPerson = o.getAttendanceList().asNormalList()
                    .stream().filter(person -> person.isSamePerson(personToDelete)).findFirst();
            OccasionDescriptor updatedOccasionDescriptor = new OccasionDescriptor();
            possiblyPresentPerson.ifPresent(person -> {
                List<Person> updatedPersons = o.getAttendanceList().makeShallowDuplicate().asNormalList();
                updatedPersons.remove(person);
                UniquePersonList updatedPersonList = new UniquePersonList(updatedPersons);
                updatedOccasionDescriptor.setAttendanceList(updatedPersonList);
                Occasion updatedOccasion = Occasion.createEditedOccasion(o, updatedOccasionDescriptor);
                model.updateOccasion(o, updatedOccasion);
            });
        }

        model.deletePerson(personToDelete);
        commandResult = new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        return commandResult;
    }

    /**
     * Deletes the module at the targeted index, and returns the command result.
     */
    private CommandResult deleteModuleAndGetResult(Model model) throws CommandException {
        CommandResult commandResult;
        List<Module> lastShownList = model.getFilteredModuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToDelete = lastShownList.get(targetIndex.getZeroBased());
        
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> completePersonList = model.getFilteredPersonList();
        ListIterator<Person> personListIterator = completePersonList.listIterator();
        while (personListIterator.hasNext()) {
            Person p = personListIterator.next();
            Optional<Module> possiblyPresentModule = p.getModuleList().asNormalList()
                    .stream().filter(module -> module.isSameModule(moduleToDelete)).findFirst();
            PersonDescriptor updatedPersonDescriptor = new PersonDescriptor();
            possiblyPresentModule.ifPresent(module -> {
                List<Module> updatedModules = p.getModuleList().makeShallowDuplicate().asNormalList();
                updatedModules.remove(module);
                UniqueModuleList updatedModuleList = new UniqueModuleList(updatedModules);
                updatedPersonDescriptor.setUniqueModuleList(updatedModuleList);
                Person updatedPerson = Person.createEditedPerson(p, updatedPersonDescriptor);
                model.updatePerson(p, updatedPerson);
            });
        }

        model.deleteModule(moduleToDelete);
        commandResult = new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
        return commandResult;
    }

    /**
     * Deletes the occasion at the targeted index, and returns the command result.
     */
    private CommandResult deleteOccasionAndGetResult(Model model) throws CommandException {
        CommandResult commandResult;
        List<Occasion> lastShownList = model.getFilteredOccasionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
        }

        Occasion occasionToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> completePersonList = model.getFilteredPersonList();
        ListIterator<Person> personListIterator = completePersonList.listIterator();

        while (personListIterator.hasNext()) {
            Person p = personListIterator.next();
            Optional<Occasion> possiblyPresentOccasion = p.getOccasionList().asNormalList()
                    .stream().filter(occasion -> occasion.isSameOccasion(occasionToDelete)).findFirst();
            PersonDescriptor updatedPersonDescriptor = new PersonDescriptor();
            possiblyPresentOccasion.ifPresent(occasion -> {
                List<Occasion> updatedOccasions = p.getOccasionList().makeShallowDuplicate().asNormalList();
                updatedOccasions.remove(occasion);
                UniqueOccasionList updatedOccasionList = new UniqueOccasionList(updatedOccasions);
                updatedPersonDescriptor.setUniqueOccasionList(updatedOccasionList);
                Person updatedPerson = Person.createEditedPerson(p, updatedPersonDescriptor);
                model.updatePerson(p, updatedPerson);
            });
        }
        
        model.deleteOccasion(occasionToDelete);
        commandResult = new CommandResult(String.format(MESSAGE_DELETE_OCCASION_SUCCESS, occasionToDelete));
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
