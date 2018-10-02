package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Category;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Person;
import seedu.address.model.expense.UniquePersonList;
import seedu.address.model.user.Username;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    protected Username username;
    private final UniquePersonList persons;
    private final CategoryList categoryList;
    private final Budget maximumBudget;

    /**
     * Creates an empty AddressBook with the given username.
     * @param username the username of the AddressBook
     */
    public AddressBook(Username username) {
        this.username = username;
        this.persons = new UniquePersonList();
        this.categoryList = new CategoryList();
        this.maximumBudget = new Budget(0.0);
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this(toBeCopied.getUsername());
        resetData(toBeCopied);
    }
    //// budget operations

    /**
     * Modifies the maximum budget for the current expense tracker
     * @param budget a valid double
     */
    public void modifyMaximumBudget(double budget) {
        this.maximumBudget.modifyBudget(budget);
    }
    //// list overwrite operaticons

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        persons.forEach(person -> this.maximumBudget.addExpense(person.getCost().getCostValue()));
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        this.maximumBudget.clearSpending();
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return this.persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public boolean addPerson(Person p) {
        this.persons.add(p);
        return this.maximumBudget.addExpense(p.getCost().getCostValue());
    }

    /**
     * Add an expense to the expense tracker.
     * If the category of the expense doesn't exist, the category will be created.
     * */
    public void addExpense(Expense e) { //TODO: Refine this once other attributes are ready
        Category category = e.getCategory();
        if (!this.categoryList.hasCategory(category)) {
            this.categoryList.addCategory(category);
        }
        this.categoryList.addExpense(category, e);
    }

    public CategoryList getCategoryList() {
        return this.categoryList;
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        this.persons.setPerson(target, editedPerson);
        this.maximumBudget.alterSpending(target, editedPerson);

    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        this.maximumBudget.removeExpense(key);
    }

    public Budget getMaximumBudget() {
        return this.maximumBudget;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username newUsername) {
        this.username = newUsername;
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
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
