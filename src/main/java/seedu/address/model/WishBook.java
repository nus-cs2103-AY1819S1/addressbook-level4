package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;


/**
 * Wraps all data at the wish book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class WishBook implements ReadOnlyWishBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public WishBook() {}

    /**
     * Creates an WishBook using the Persons in the {@code toBeCopied}
     */
    public WishBook(ReadOnlyWishBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code WishBook} with {@code newData}.
     */
    public void resetData(ReadOnlyWishBook newData) {
        requireNonNull(newData);

        setPersons(newData.getWishList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code WishBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code tag} from all {@code person}s in this {@code WishBook}.
     * @throws DuplicatePersonException if there's a duplicate {@code Person} in this {@code WishBook}.
     */
    public void removeTagFromAll(Tag tag) throws DuplicatePersonException {
        ArrayList<Person> modifiedPersons = new ArrayList<>();

        for (Person person: persons.asUnmodifiableObservableList()) {
            Set<Tag> modifiedTags = person.getTags();
            modifiedTags.removeIf(t -> t == tag);

            Person modifiedPerson = new Person(person.getName(),
                    person.getPhone(),
                    person.getEmail(),
                    person.getAddress(),
                    person.getRemark(),
                    modifiedTags);

            modifiedPersons.add(modifiedPerson);
        }
        persons.setPersons(modifiedPersons);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getWishList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WishBook // instanceof handles nulls
                && persons.equals(((WishBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
