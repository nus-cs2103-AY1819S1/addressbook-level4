package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entity.Entity;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.NotAPersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent entity as the given argument.
     */
    public boolean contains(Entity toCheck) {
        requireNonNull(toCheck);
        if (!(toCheck instanceof Person)) {
            return false;
        }
        Person personToCheck = (Person) toCheck;
        return internalList.stream().anyMatch(personToCheck::isSameEntity);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Entity toAdd) {
        requireNonNull(toAdd);
        if (!(toAdd instanceof Person)) {
            throw new NotAPersonException();
        } else if (contains(toAdd)) {
            throw new DuplicatePersonException();
        } else {
            Person personToAdd = (Person) toAdd;
            internalList.add(personToAdd);
        }
    }

    /**
     * Replaces the entity {@code target} in the list with {@code editedEntity}.
     * {@code target} must exist in the list.
     * The identity of {@code editedPerson} must not be the same as another entity in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameEntity(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    public void setEntity(Entity target, Entity editedEntity) {
        requireAllNonNull(target, editedEntity);
        if (!(target instanceof Person && editedEntity instanceof Person)) {
            return;
        }
        setPerson((Person) target, (Person) editedEntity);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Entity toRemove) {
        requireNonNull(toRemove);
        if (!(toRemove instanceof Person)) {
            throw new NotAPersonException();
        }

        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSameEntity(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
