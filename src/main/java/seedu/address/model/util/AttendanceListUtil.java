package seedu.address.model.util;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ModuleBrowserChangeEvent;
import seedu.address.commons.events.ui.OccasionBrowserChangeEvent;
import seedu.address.commons.events.ui.PersonBrowserChangeEvent;
import seedu.address.commons.events.ui.RefreshModuleBrowserEvent;
import seedu.address.commons.events.ui.RefreshOccasionBrowserEvent;
import seedu.address.commons.events.ui.RefreshPersonBrowserEvent;
import seedu.address.commons.util.TypeUtil;
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
    /**
     * Posts correct event for refreshing current browser.
     */
    public static void postRefreshEvent(Model model) {
        TypeUtil currType = model.getActiveType();
        if (currType == TypeUtil.PERSON) {
            EventsCenter.getInstance().post(new RefreshPersonBrowserEvent());
        } else if (currType == TypeUtil.MODULE) {
            EventsCenter.getInstance().post(new RefreshModuleBrowserEvent());
        } else {
            EventsCenter.getInstance().post(new RefreshOccasionBrowserEvent());
        }
    }

    /**
     * Posts correct event for clearing current browser.
     */
    public static void postClearEvent(Model model) {
        TypeUtil currType = model.getActiveType();
        if (currType == TypeUtil.PERSON) {
            EventsCenter.getInstance().post(new PersonBrowserChangeEvent(new Person(new PersonDescriptor())));
        } else if (currType == TypeUtil.MODULE) {
            EventsCenter.getInstance().post(new ModuleBrowserChangeEvent(new Module(new ModuleDescriptor())));
        } else {
            EventsCenter.getInstance().post(new OccasionBrowserChangeEvent(new Occasion(new OccasionDescriptor())));
        }
    }

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

    //@@author KongZijin
    //Reused from teammate @waytan with minor modifications
    /**
     * Removes a module from the moduleList of all associated Persons.
     */
    public static void editModuleFromAssociatedPersons(Model model, Module moduleToEdit, Module editedModule) {
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

    //@@author KongZijin
    //Reused from teammate @waytan with minor modifications
    /**
     * Removes a occasion from the occasionList of all associated Persons.
     */
    public static void editOccasionFromAssociatedPersons(Model model, Occasion occasionToEdit,
                                                           Occasion editedOccasion) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> completePersonList = model.getFilteredPersonList();
        ListIterator<Person> personListIterator = completePersonList.listIterator();
        while (personListIterator.hasNext()) {
            Person person = personListIterator.next();
            person.getOccasionList()
                    .asNormalList()
                    .stream()
                    .filter(occasion -> occasion.isSameOccasion(occasionToEdit))
                    .findFirst()
                    .ifPresent(editOccasionFromPerson(model, person, editedOccasion));
        }
    }

    //@@author KongZijin
    //Reused from teammate @waytan with minor modifications
    /**
     * Edits person from his associate modules.
     * @param model
     * @param personToEdit The person to edit.
     * @param editedPerson The person after editing.
     */
    public static void editPersonFromAssociateModules(Model model, Person personToEdit, Person editedPerson) {
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

    //@@author KongZijin
    //Reused from teammate @waytan with minor modifications
    /**
     * Edits person from his associate occasion.
     * @param model
     * @param personToEdit The person to edit.
     * @param editedPerson The person after editing.
     */
    public static void editPersonFromAssociateOccasions(Model model, Person personToEdit, Person editedPerson) {
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

    //@@author alistairong
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

    //@@author alistairong
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

    //@@author waytan
    /**
     * Returns a consumer that takes in a Person and removes it from the specified Occasion in the Model.
     */
    private static Consumer<Person> removePersonFromOccasion(Model model, Occasion occasion) {
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
    private static Consumer<Person> removePersonFromModule(Model model, Module module) {
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
    private static Consumer<Module> removeModuleFromPerson(Model model, Person person) {
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
    private static Consumer<Occasion> removeOccasionFromPerson(Model model, Person person) {
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

    //@@author KongZijin
    //Reused from teammate @waytan with minor modifications
    /**
     * Returns a consumer that takes in a Person and edits it in the specified Module in the model.
     */
    private static Consumer<Person> editPersonFromModule(Model model, Module module, Person editedPerson) {
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

    //@@author KongZijin
    //Reused from teammate @waytan with minor modifications
    /**
     * Returns a consumer that takes in a Person and edits it in the specified Occasion in the model.
     */
    private static Consumer<Person> editPersonFromOccasion(Model model, Occasion occasion, Person editedPerson) {
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

    //@@author KongZijin
    //Reused from teammate @waytan with minor modifications
    /**
     * Returns a consumer that takes in a Module and removes it from the specified Person in the Model.
     */
    private static Consumer<Module> editModuleFromPerson(Model model, Person person, Module editModule) {
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

    //@@author KongZijin
    //Reused from teammate @waytan with minor modifications
    /**
     * Returns a consumer that takes in a Occasion and removes it from the specified Person in the Model.
     */
    private static Consumer<Occasion> editOccasionFromPerson(Model model, Person person, Occasion editOccasion) {
        return occasion -> {
            PersonDescriptor updatedPersonDescriptor = new PersonDescriptor();
            List<Occasion> updatedOccasions = person.getOccasionList().makeShallowDuplicate().asNormalList();
            int indexOfOccasionToEdit = updatedOccasions.indexOf(editOccasion);
            if (indexOfOccasionToEdit != -1) {
                updatedOccasions.remove(occasion);
                updatedOccasions.add(indexOfOccasionToEdit, editOccasion);
                UniqueOccasionList updatedOccasionList = new UniqueOccasionList(updatedOccasions);
                updatedPersonDescriptor.setUniqueOccasionList(updatedOccasionList);
                Person updatedPerson = Person.createEditedPerson(person, updatedPersonDescriptor);
                model.updatePerson(person, updatedPerson);
            }
        };
    }
}
