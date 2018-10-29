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
import seedu.address.model.portation.ImportFile;
import seedu.address.model.state.State;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.TriviaResult;
import seedu.address.model.test.TriviaTest;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.ImportFileUtil;

public class ImportCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_cardAcceptedByModel_importSuccessful() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();

        ImportFile file = ImportFileUtil.getValidNoTopicImportFile();
        Card validCard = new CardBuilder().withQuestion("question").withAnswer("answer").withTopics("NoTopic").build();
        List<Card> cardList = Arrays.asList(validCard);

        CommandResult commandResult = new ImportCommand(file).execute(modelStub, commandHistory);

        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, cardList.size(), file.getFileName()),
                commandResult.feedbackToUser);
        assertEquals(cardList, modelStub.cardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_typicalCardsAcceptedByModel_importSuccessful() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();

        ImportFile file = ImportFileUtil.getTypicalImportFile();
        Card validCard1 = new CardBuilder().withQuestion("question1").withTopics("topic1").build();
        Card validCard2 = new CardBuilder().withQuestion("question2").withTopics("topic1").build();
        Card validCard3 = new CardBuilder().withQuestion("question3").withTopics("topic2", "topic3").build();
        List<Card> cardList = Arrays.asList(validCard1, validCard2, validCard3);


        CommandResult commandResult = new ImportCommand(file).execute(modelStub, commandHistory);

        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, cardList.size(), file.getFileName()),
                commandResult.feedbackToUser);
        assertEquals(cardList, modelStub.cardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCardInTriviaBundle_throwsCommandException() throws Exception {
        ImportFile validImportFile = ImportFileUtil.getValidNoTopicImportFile();
        ImportCommand importCommand = new ImportCommand(validImportFile);
        Card validCard = new CardBuilder().withQuestion("question").build();

        ModelStub modelStub = new ModelStubWithCard(validCard);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ImportCommand.MESSAGE_DUPLICATE_CARD);
        importCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        ImportFile typicalImportFile = ImportFileUtil.getTypicalImportFile();
        ImportFile dummyImportFile = ImportFileUtil.getDummyImportFile();

        ImportCommand importTypicalImportFile = new ImportCommand(typicalImportFile);
        ImportCommand importDummyImportFile = new ImportCommand(dummyImportFile);


        // same object -> returns true
        assertTrue(importTypicalImportFile.equals(importTypicalImportFile));

        // same values -> returns true
        ImportCommand importTypicalImportFileCopy = new ImportCommand(typicalImportFile);
        assertTrue(importTypicalImportFile.equals(importTypicalImportFileCopy));

        // different types -> returns false
        assertFalse(importTypicalImportFile.equals(1));

        // null -> returns false
        assertFalse(importTypicalImportFile.equals(null));

        // different import file -> returns false
        assertFalse(importTypicalImportFile.equals(importDummyImportFile));
    }

    @Test
    public void constructor_nullFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
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
            throw new AssertionError("This method should not be called.");
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
        public List<TriviaResult> getTriviaResultList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Attempt> getAttemptsByCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean matchQuestionAndAnswer(Index questionIndex, Index answerIndex) {
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
        private final UniqueCardList cards;

        ModelStubWithCard(Card card) {
            requireNonNull(card);
            cards = new UniqueCardList();
            cards.add(card);
        }

        @Override
        public boolean haveAnyCard(UniqueCardList cardList) {
            requireNonNull(cardList);
            return cardList.toStream().anyMatch(card -> cards.contains(card));
        }
    }

    /**
     * A Model stub that always accept the card being added.
     */
    private class ModelStubAcceptingCardAdded extends ModelStub {
        final ArrayList<Card> cardsAdded = new ArrayList<>();

        @Override
        public boolean haveAnyCard(UniqueCardList cardList) {
            requireNonNull(cardList);
            return cardList.toStream().anyMatch(card -> cardsAdded.contains(card));
        }

        @Override
        public void addMultipleCards(UniqueCardList cardList) {
            requireNonNull(cardList);
            for (Card c : cardList) {
                cardsAdded.add(c);
            }
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
