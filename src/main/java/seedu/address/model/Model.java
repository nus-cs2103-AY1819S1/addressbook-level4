package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTriviaBundle newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */

    void commitAddressBook();

    /** Returns the TriviaBundle */
    ReadOnlyTriviaBundle getTriviaBundle();

    /**
     * Returns true if a card with the same identity as {@code card} exists in the list of trivia bundle.
     */
    boolean hasCard(Card card);

    /**
     * Adds the given card.
     * {@code card} must not already exist in the list of trivia bundle.
     */
    void addCard(Card card);

    /** Returns an unmodifiable view of the filtered card's list */
    ObservableList<Card> getFilteredCardList();

    /**
     * Updates the filter of the filtered card list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCardList(Predicate<Card> predicate);

    /**
     * Replaces the given card {@code target} with {@code editedCard}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updateCard(Card target, Card editedCard);

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitTriviaBundle();

    boolean canUndoTriviaBundle();

    /**
     * Returns true if the model has undone trivial bundle states to restore.
     */
    boolean canRedoTriviaBundle();

    /**
     * Restores the model's trivial bundle to its previous state.
     */
    void undoTriviaBundle();

    /**
     * Restores the model's trivial bundle to its previously undone state.
     */
    void redoTriviaBundle();

    /**
     * Saves the current trivial bundle state for undo/redo.
     */

}
