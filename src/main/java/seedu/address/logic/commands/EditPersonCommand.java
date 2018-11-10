package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.Person.createEditedPerson;

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
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.UniquePersonList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditPersonCommand extends Command {

    public static final String COMMAND_WORD = "editperson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final PersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditPersonCommand(Index index, PersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new PersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        editPersonFromAssociateModules(model, personToEdit, editedPerson);
        editPersonFromAssociateOccasions(model, personToEdit, editedPerson);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    //Reused from teammate @waytan with minor modifications

    /**
     * Edits person from his associate modules.
     * @param model
     * @param personToEdit The person to edit.
     * @param editedPerson The person after editing.
     */
    private void editPersonFromAssociateModules(Model model, Person personToEdit, Person editedPerson) {
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        List<Module> completeModuleList = model.getFilteredModuleList();
        ListIterator<Module> moduleListIterator = completeModuleList.listIterator();
        while (moduleListIterator.hasNext()) {
            Module module = moduleListIterator.next();
            module.getStudents()
                    .asNormalList()
                    .stream()
                    .filter(person -> person.isSamePerson(personToEdit))
                    .findFirst()
                    .ifPresent(editPersonFromModule(model, module, editedPerson));
        }
    }

    //Reused from teammate @waytan with minor modifications

    /**
     * Edits person from his associate occasion.
     * @param model
     * @param personToEdit The person to edit.
     * @param editedPerson The person after editing.
     */
    private void editPersonFromAssociateOccasions(Model model, Person personToEdit, Person editedPerson) {
        model.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
        List<Occasion> completeOccasionList = model.getFilteredOccasionList();
        ListIterator<Occasion> occasionListIterator = completeOccasionList.listIterator();
        while (occasionListIterator.hasNext()) {
            Occasion occasion = occasionListIterator.next();
            occasion.getAttendanceList()
                    .asNormalList()
                    .stream()
                    .filter(person -> person.isSamePerson(personToEdit))
                    .findFirst()
                    .ifPresent(editPersonFromOccasion(model, occasion, editedPerson));
        }
    }

    //Reused from teammate @waytan with minor modifications
    /**
     * Returns a consumer that takes in a Person and edits it in the specified Module in the model.
     */
    private Consumer<Person> editPersonFromModule(Model model, Module module, Person editedPerson) {
        return person -> {
            ModuleDescriptor updatedModuleDescriptor = new ModuleDescriptor();
            List<Person> updatedPersons = module.getStudents().makeShallowDuplicate().asNormalList();
            int indexOfPersonToEdit = updatedPersons.indexOf(person);
            if (indexOfPersonToEdit != -1) {
                updatedPersons.remove(person);
                updatedPersons.add(indexOfPersonToEdit, editedPerson);
                UniquePersonList updatedPersonList = new UniquePersonList(updatedPersons);
                updatedModuleDescriptor.setStudents(updatedPersonList);
                Module updatedModule = Module.createEditedModule(module, updatedModuleDescriptor);
                model.updateModule(module, updatedModule);
            }
        };
    }

    //Reused from teammate @waytan with minor modifications
    /**
     * Returns a consumer that takes in a Person and edits it in the specified Occasion in the model.
     */
    private Consumer<Person> editPersonFromOccasion(Model model, Occasion occasion, Person editedPerson) {
        return person -> {
            OccasionDescriptor updatedOccasionDescriptor = new OccasionDescriptor();
            List<Person> updatedPersons = occasion.getAttendanceList().makeShallowDuplicate().asNormalList();
            int indexOfPersonToEdit = updatedPersons.indexOf(person);
            if (indexOfPersonToEdit != -1) {
                updatedPersons.remove(person);
                updatedPersons.add(indexOfPersonToEdit, editedPerson);
                UniquePersonList updatedPersonList = new UniquePersonList(updatedPersons);
                updatedOccasionDescriptor.setAttendanceList(updatedPersonList);
                Occasion updatedOccasion = Occasion.createEditedOccasion(occasion, updatedOccasionDescriptor);
                model.updateOccasion(occasion, updatedOccasion);
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
        if (!(other instanceof EditPersonCommand)) {
            return false;
        }

        // state check
        EditPersonCommand e = (EditPersonCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

}
