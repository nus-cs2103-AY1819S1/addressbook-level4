package seedu.address.model.util;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

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
 * Contains utility to insert and remove links between {@code Person} and {@code Module}
 * or {@code Occasion} through modification of their internal attendance lists.
 * @author alistair
 */
public class AttendanceListUtil {
    //@@author waytan
    /**
     * Removes a person from the attendanceList of all associated Occasions.
     */
    public static void removePersonFromAssociatedOccasions(Model model, Person personToDelete) {
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

    //@@author waytan
    /**
     * Removes a person from the studentList of all associated Modules.
     */
    public static void removePersonFromAssociatedModules(Model model, Person personToDelete) {
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

    //@@author waytan
    /**
     * Removes a module from the moduleList of all associated Persons.
     */
    public static void removeModuleFromAssociatedPersons(Model model, Module moduleToDelete) {
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

    //@@author waytan
    /**
     * Removes an Occasion from the occasionList of all associated Persons.
     */
    public static void removeOccasionFromAssociatedPersons(Model model, Occasion occasionToDelete) {
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

    //@@author waytan
    /**
     * Returns a consumer that takes in a Person and removes it from the specified Occasion in the Model.
     */
    public static Consumer<Person> removePersonFromOccasion(Model model, Occasion occasion) {
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

    //@@author waytan
    /**
     * Returns a consumer that takes in a Person and removes it from the specified module in the model.
     */
    public static Consumer<Person> removePersonFromModule(Model model, Module module) {
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

    //@@author waytan
    /**
     * Returns a consumer that takes in a Module and removes it from the specified Person in the Model.
     */
    public static Consumer<Module> removeModuleFromPerson(Model model, Person person) {
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

    //@@author waytan
    /**
     * Returns a consumer that takes in an Occasion and removes it from the specified Person in the Model.
     */
    public static Consumer<Occasion> removeOccasionFromPerson(Model model, Person person) {
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

    /**
     * Removes said {@code person} from {@code module} attendance list in {@code model} and vice versa.
     * @param model The model to change.
     * @param person The person to remove from attendance list of {@code module}.
     * @param module The module to remove from attendance list of {@code person}.
     */
    public static void delinkPersonModule(Model model, Person person, Module module) {
        removePersonFromModule(model, module).accept(person);
        removeModuleFromPerson(model, person).accept(module);
    }

    /**
     * Removes said {@code person} from {@code occasion} attendance list in {@code model} and vice versa.
     * @param model The model to change.
     * @param person The person to remove from attendance list of {@code occasion}.
     * @param occasion The occasion to remove from attendance list of {@code person}.
     */
    public static void delinkPersonOccasion(Model model, Person person, Occasion occasion) {
        removePersonFromOccasion(model, occasion).accept(person);
        removeOccasionFromPerson(model, person).accept(occasion);
    }
}
