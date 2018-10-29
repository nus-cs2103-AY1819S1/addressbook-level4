package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.SetUpDisplayCardInfoEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.TriviaBundle;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.person.Person;
import seedu.address.model.state.State;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.TriviaResult;
import seedu.address.model.test.TriviaTest;
import seedu.address.testutil.CardBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_cardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();
        Card validCard = new CardBuilder().build();

        CommandResult commandResult = new AddCommand(validCard).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCard), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCard), modelStub.cardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() throws Exception {
        Card validCard = new CardBuilder().build();
        AddCommand addCommand = new AddCommand(validCard);
        ModelStub modelStub = new ModelStubWithCard(validCard);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_CARD);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Card qOnEarthRound = new CardBuilder().withQuestion("Why is the earth round?").build();
        Card qOnEarthFlat = new CardBuilder().withQuestion("Why is the earth flat?").build();
        AddCommand addEarthRoundCommand = new AddCommand(qOnEarthRound);
        AddCommand addEarthFlatCommand = new AddCommand(qOnEarthFlat);

        // same object -> returns true
        assertTrue(addEarthRoundCommand.equals(addEarthRoundCommand));

        // same values -> returns true
        AddCommand addEarthRoundCommandCopy = new AddCommand(qOnEarthRound);
        assertTrue(addEarthRoundCommand.equals(addEarthRoundCommandCopy));

        // different types -> returns false
        assertFalse(addEarthRoundCommand.equals(1));

        // null -> returns false
        assertFalse(addEarthRoundCommand.equals(null));

        // different person -> returns false
        assertFalse(addEarthRoundCommand.equals(addEarthFlatCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMultipleCards(UniqueCardList cards) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyTriviaBundle newData) {
            throw new AssertionError(("This method should not be called."));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTriviaBundle getTriviaBundle() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean haveAnyCard(UniqueCardList cards) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCard(Card target) {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void updateCard(Card target, Card editedCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCardList(Predicate<Card> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTriviaBundle() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTriviaBundle() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTriviaBundle() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTriviaBundle() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTriviaBundle() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void startTriviaTest(TriviaTest test) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void stopTriviaTest() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TriviaTest getCurrentRunningTest() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public State getAppState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isInTestingState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean matchQuestionAndAnswer(Index questionIndex, Index answerIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<TriviaResult> getTriviaResultList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Attempt> getAttemptsByCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleSetUpDisplayCardInfoEvent(SetUpDisplayCardInfoEvent event) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single card.
     */
    private class ModelStubWithCard extends ModelStub {
        private final Card card;

        ModelStubWithCard(Card card) {
            requireNonNull(card);
            this.card = card;
        }

        @Override
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return this.card.equals(card);
        }
    }

    /**
     * A Model stub that always accept the card being added.
     */
    private class ModelStubAcceptingCardAdded extends ModelStub {
        final ArrayList<Card> cardsAdded = new ArrayList<>();

        @Override
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return cardsAdded.stream().anyMatch(card::equals);
        }

        @Override
        public void addCard(Card card) {
            requireNonNull(card);
            cardsAdded.add(card);
        }

        @Override
        public void commitTriviaBundle() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyTriviaBundle getTriviaBundle() {
            return new TriviaBundle();
        }
    }

}
