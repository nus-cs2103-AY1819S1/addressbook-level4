package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Ssenisub implements ReadOnlySsenisub {

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

    public Ssenisub() {}

    /**
     * Creates an SSENISUB using the Persons in the {@code toBeCopied}
     */
    public Ssenisub(ReadOnlySsenisub toBeCopied) {
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
     * Resets the existing data of this {@code Ssenisub} with {@code newData}.
     */
    public void resetData(ReadOnlySsenisub newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in Ssenisub.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the same Name as {@code person} exists in Ssenisub.
     */
    public boolean hasName(Person person) {
        requireNonNull(person);
        return persons.containsName(person);
    }
    /**
     * Returns true if a person with the same Phone Number as {@code person} exists in Ssenisub.
     */
    public boolean hasPhoneNumber(Person person) {
        requireNonNull(person);
        return persons.containsPhoneNumber(person);
    }

    /**
     * Returns true if a person with the same Email as {@code person} exists in Ssenisub.
     */
    public boolean hasEmail(Person person) {
        requireNonNull(person);
        return persons.containsEmail(person);
    }

    /**
     * Adds a person to SSENISUB.
     * The person must not already exist in SSENISUB.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in SSENISUB.
     * The person identity of {@code editedPerson} must not be the same as another existing person in SSENISUB.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code favouritedReadOnlyPerson}.
     * Sorts the list to show favourite contacts first.
     * {@code Ssenisub}'s tag list will be updated with the tags of {@code favouritedReadOnlyPerson}.
     */
    public void favouritePerson(Person target, Person favouritedPerson) {
        requireNonNull(favouritedPerson);

        // Person favouritedPerson = new Person(favouritedPerson);

        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.

        persons.setPerson(target, favouritedPerson);

        UniquePersonList notFavouriteList = new UniquePersonList();
        UniquePersonList favouriteList = new UniquePersonList();

        for (Person person : persons) {
            if (person.getFavourite()) {
                favouriteList.add(person);
            } else {
                notFavouriteList.add(person);
            }
        }

        persons.setPersons(favouriteList);
        for (Person person : notFavouriteList) {
            persons.add(person);
        }

    }

    /**
     * Sorts the person list by name
     */
    public void sortByName() {
        persons.sortByName();
    }

    /**
     * Sorts the person list by department
     */
    public void sortByDept() {
        persons.sortByDept();
    }

    /**
     * Sorts the person list by rating from lowest to highest
     */
    public void sortByRatingUp() {
        persons.sortByRatingUp();
    }

    /**
     * Sorts the person list by rating from highest to lowest
     */
    public void sortByRatingDown() {
        persons.sortByRatingDown();
    }

    /**
     * Removes {@code key} from this {@code Ssenisub}.
     * {@code key} must exist in SSENISUB.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Ssenisub // instanceof handles nulls
                && persons.equals(((Ssenisub) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
