package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.state.State.LEARN;
import static seedu.address.model.state.State.REVIEW;
import static seedu.address.model.state.State.TEST;

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
import seedu.address.commons.events.model.TabChangeEvent;
import seedu.address.commons.events.model.TriviaBundleChangedEvent;
import seedu.address.commons.events.model.TriviaResultsChangedEvent;
import seedu.address.commons.events.ui.CloseTriviaTestViewEvent;
import seedu.address.commons.events.ui.DisplayCardInfoEvent;
import seedu.address.commons.events.ui.OpenEndedTestShowAnswerEvent;
import seedu.address.commons.events.ui.OpenEndedTestShowNextQuestionEvent;
import seedu.address.commons.events.ui.SetUpDisplayCardInfoEvent;
import seedu.address.commons.events.ui.ShowTriviaTestResultEvent;
import seedu.address.commons.events.ui.ShowTriviaTestViewEvent;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.person.Person;
import seedu.address.model.state.AppState;
import seedu.address.model.state.State;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.ReadOnlyTriviaResults;
import seedu.address.model.test.TestType;
import seedu.address.model.test.TriviaResult;
import seedu.address.model.test.TriviaResults;
import seedu.address.model.test.TriviaTest;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.test.openendedtest.OpenEndedTest;

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
        return getAppState() == State.MATCH_TEST || getAppState() == State.MATCH_TEST_RESULT
                || getAppState() == State.OPEN_ENDED_TEST_QUESTION || getAppState() == State.OPEN_ENDED_TEST_ANSWER
                || getAppState() == State.OPEN_ENDED_TEST_RESULT;
    }

    @Override
    public void setAppState(State state) {
        appState.setAppState(state);
    }

    //=========== Trivia Tests ==============================================================================

    @Override
    public void startTriviaTest(TriviaTest test) {
        currentRunningTest = test;
        if (test.getTestType() == TestType.MATCH_TEST) {
            appState.setAppState(State.MATCH_TEST);
        } else {
            appState.setAppState(State.OPEN_ENDED_TEST_QUESTION);
        }
        test.startTest();
        raise(new ShowTriviaTestViewEvent(test.getTestingPage()));
    }

    @Override
    public void stopTriviaTest() {
        currentRunningTest.stopTest();
        appState.setAppState(State.TEST);
        raise(new CloseTriviaTestViewEvent());
        currentRunningTest = null;
    }

    @Override
    public TriviaTest getCurrentRunningTest() {
        return currentRunningTest;
    }

    //=========== Match Tests ============================================================================

    @Override
    public boolean matchQuestionAndAnswer(Index questionIndex, Index answerIndex) throws IndexOutOfBoundsException {
        assert currentRunningTest instanceof MatchTest;
        requireNonNull(answerIndex);

        MatchTest matchTest = (MatchTest) currentRunningTest;
        boolean isCorrectMatch = matchTest.match(questionIndex, answerIndex);
        if (matchTest.isCompleted()) {
            triviaResults.addTriviaResult(new TriviaResult(currentRunningTest));
            raise(new TriviaResultsChangedEvent(triviaResults));
            appState.setAppState(State.MATCH_TEST_RESULT);
        }

        return isCorrectMatch;
    }

    //=========== Open Ended Tests ==========================================================================

    @Override
    public boolean isOpenEndedTestAnswerCorrect(char in) {
        assert currentRunningTest instanceof OpenEndedTest;
        OpenEndedTest openEndedTest = (OpenEndedTest) currentRunningTest;
        boolean isAnswerCorrect = openEndedTest.addAttempt(in);
        if (!openEndedTest.isCompleted()) {
            openEndedTest.advanceCard();
            Card nextCard = openEndedTest.getCurrCard();
            raise(new OpenEndedTestShowNextQuestionEvent(nextCard));
            appState.setAppState(State.OPEN_ENDED_TEST_QUESTION);
        } else {
            triviaResults.addTriviaResult(new TriviaResult(currentRunningTest));
            raise(new TriviaResultsChangedEvent(triviaResults));
            raise(new ShowTriviaTestResultEvent(openEndedTest.getResultPage()));
            appState.setAppState(State.OPEN_ENDED_TEST_RESULT);
        }
        return isAnswerCorrect;
    }

    @Override
    public void recordAnswerToOpenEndedTest(String userInput) {
        assert currentRunningTest instanceof OpenEndedTest;
        OpenEndedTest openEndedTest = (OpenEndedTest) currentRunningTest;
        openEndedTest.recordAnswer(userInput);
        raise(new OpenEndedTestShowAnswerEvent(userInput));
        appState.setAppState(State.OPEN_ENDED_TEST_ANSWER);
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
    @Subscribe
    public void handleChangeTab(TabChangeEvent event) {
        State toGo = LEARN;
        if ("Learn".equals(event.getUpdatedTab())) {
            toGo = LEARN;

        } else if ("Test".equals(event.getUpdatedTab())) {
            toGo = TEST;

        } else if ("Review".equals(event.getUpdatedTab())) {
            toGo = REVIEW;

        }
        appState.setAppState(toGo);

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
