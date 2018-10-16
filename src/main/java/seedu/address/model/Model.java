package seedu.address.model;

import java.util.function.Predicate;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddMatchTestResultEvent;
import seedu.address.model.card.Card;
import seedu.address.model.person.Person;
import seedu.address.model.state.State;
import seedu.address.model.test.TriviaTest;

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

    /**
     * Deletes the given card.
     * The card must exist in the trivia bundle.
     */
    void deleteCard(Card target);

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
     * Saves the current trivia bundle state for undo/redo.
     */
    void commitTriviaBundle();

    /**
     * Returns true if the model has previous trivia bundle states to restore.
     */
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
     * Set a test to the trivia application model.
     */
    void startTriviaTest(TriviaTest test);

    /**
     * Stop a test in the trivia application model.
     */
    void stopTriviaTest();

    /**
     * Match a question to another answer. To be used in a matching test.
     */
    boolean matchQuestionAndAnswer(Index questionIndex, Index answerIndex);

    /**
     * Obtain the trivia test that is running.
     */
    TriviaTest getCurrentRunningTest();

    /**
     * Get the app state that the application is currently in.
     */
    State getAppState();

    /**
     * Return boolean on whether the model in in a testing state.
     */
    boolean isInTestingState();

    /**
     * Add a new completed matching test to the list of results.
     */
    @Subscribe
    void handleAddMatchTestResultEvent(AddMatchTestResultEvent event);
}
