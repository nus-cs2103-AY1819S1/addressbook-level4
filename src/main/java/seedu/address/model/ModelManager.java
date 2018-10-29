package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TriviaBundleChangedEvent;
import seedu.address.commons.events.model.TriviaResultsChangedEvent;
import seedu.address.commons.events.ui.CloseTriviaTestViewEvent;
import seedu.address.commons.events.ui.DisplayCardInfoEvent;
import seedu.address.commons.events.ui.SetUpDisplayCardInfoEvent;
import seedu.address.commons.events.ui.ShowTriviaTestViewEvent;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.person.Person;
import seedu.address.model.state.AppState;
import seedu.address.model.state.State;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.ReadOnlyTriviaResults;
import seedu.address.model.test.TriviaResult;
import seedu.address.model.test.TriviaResults;
import seedu.address.model.test.TriviaTest;
import seedu.address.model.test.matchtest.MatchTest;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;

    private final VersionedTriviaBundle versionedTriviaBundle;
    private final FilteredList<Card> filteredCards;
    private final TriviaResults triviaResults;
    private final AppState appState;

    private TriviaTest currentRunningTest;

    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyTriviaBundle triviaBundle,
                        ReadOnlyTriviaResults triviaResults, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, triviaBundle, userPrefs);

        logger.fine("Initializing with addressbook and trivia bundle: " + triviaBundle
                + " and user prefs " + userPrefs);

        versionedTriviaBundle = new VersionedTriviaBundle(triviaBundle);
        filteredCards = new FilteredList<>(versionedTriviaBundle.getCardList());
        this.triviaResults = new TriviaResults(triviaResults);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());

        currentRunningTest = null;
        appState = new AppState();
    }

    public ModelManager() {
        this(new AddressBook(), new TriviaBundle(), new TriviaResults(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public void resetData(ReadOnlyTriviaBundle newData) {
        versionedTriviaBundle.resetData(newData);
        indicateTriviaBundleChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyTriviaBundle getTriviaBundle() {
        return versionedTriviaBundle;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTriviaBundleChanged() {
        raise(new TriviaBundleChangedEvent(versionedTriviaBundle));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return versionedTriviaBundle.hasCard(card);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteCard(Card target) {
        versionedTriviaBundle.removeCard(target);
        indicateTriviaBundleChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addCard(Card card) {
        versionedTriviaBundle.addCard(card);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        indicateTriviaBundleChanged();
    }


    @Override
    public boolean canClearCardList() {
        return versionedTriviaBundle.canClearCardList();
    }

    //@Override
    //public void clearCardList() {
    //    versionedTriviaBundle.resetData();
    //}

    @Override
    public void updateCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);

        versionedTriviaBundle.updateCard(target, editedCard);
        indicateTriviaBundleChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedTriviaBundle}
     */
    @Override
    public ObservableList<Card> getFilteredCardList() {
        return FXCollections.unmodifiableObservableList(filteredCards);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredCardList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        filteredCards.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTriviaBundle() {
        return versionedTriviaBundle.canUndo();
    }

    @Override
    public boolean canRedoTriviaBundle() {
        return versionedTriviaBundle.canRedo();
    }

    @Override
    public void undoTriviaBundle() {
        versionedTriviaBundle.undo();
        indicateTriviaBundleChanged();
    }

    @Override
    public void redoTriviaBundle() {
        versionedTriviaBundle.redo();
        indicateTriviaBundleChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public void commitTriviaBundle() {
        versionedTriviaBundle.commit();
    }

    //=========== App State =================================================================================

    @Override
    public State getAppState() {
        return appState.getState();
    }

    @Override
    public boolean isInTestingState() {
        return appState.getState() == State.TEST || appState.getState() == State.MATCH_TEST
                || appState.getState() == State.MATCH_TEST_RESULT;
    }

    //=========== Trivia Tests ==============================================================================

    @Override
    public void startTriviaTest(TriviaTest test) {
        currentRunningTest = test;
        appState.setAppState(State.MATCH_TEST);
        test.startTest();
        raise(new ShowTriviaTestViewEvent(test.getTestingPage()));
    }

    @Override
    public void stopTriviaTest() {
        currentRunningTest.stopTest();
        appState.setAppState(State.NORMAL);
        raise(new CloseTriviaTestViewEvent());
        currentRunningTest = null;
    }

    @Override
    public TriviaTest getCurrentRunningTest() {
        return currentRunningTest;
    }

    //=========== Matching Tests ============================================================================

    @Override
    public boolean matchQuestionAndAnswer(Index questionIndex, Index answerIndex) throws IndexOutOfBoundsException {
        assert currentRunningTest instanceof MatchTest;

        MatchTest matchTest = (MatchTest) currentRunningTest;
        boolean isCorrectMatch = matchTest.match(questionIndex, answerIndex);
        if (matchTest.isCompleted()) {
            triviaResults.addTriviaResult(new TriviaResult(currentRunningTest));
            raise(new TriviaResultsChangedEvent(triviaResults));
            appState.setAppState(State.MATCH_TEST_RESULT);
        }

        return isCorrectMatch;
    }

    //=========== Trivia Test Results ==========================================================================

    @Override
    public List<TriviaResult> getTriviaResultList() {
        return triviaResults.getResultList();
    }

    @Override
    public List<Attempt> getAttemptsByCard(Card card) {
        return triviaResults.getAttemptsByCard(card);
    }

    @Override
    @Subscribe
    public void handleSetUpDisplayCardInfoEvent(SetUpDisplayCardInfoEvent event) {
        Card selectedCard = event.selectedCard;
        raise(new DisplayCardInfoEvent(selectedCard, triviaResults.getAttemptsByCard(selectedCard)));
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return (versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons))
                && (versionedTriviaBundle == null // short circuit for regression compatibility with addressbook
                    || (versionedTriviaBundle.equals(other.versionedTriviaBundle)
                    && filteredCards.equals(other.filteredCards)))
                && ((currentRunningTest == null && other.currentRunningTest == null)
                    || (currentRunningTest != null && currentRunningTest.equals(other.currentRunningTest)));
    }

    //=========== Import =====================================================================

    @Override
    public boolean haveAnyCard(UniqueCardList cards) {
        requireNonNull(cards);
        return versionedTriviaBundle.haveAnyCard(cards);
    }

    @Override
    public void addMultipleCards(UniqueCardList cards) {
        versionedTriviaBundle.resetRecentlyImportStatus();
        versionedTriviaBundle.addMultipleCards(cards);
        updateFilteredCardList(PREDICATE_SHOW_RECENTLY_IMPORTED_CARDS);
        indicateTriviaBundleChanged();
    }
}
