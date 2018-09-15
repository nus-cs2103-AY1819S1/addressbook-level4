package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.TriviaBundle;
import seedu.address.model.card.Card;
import seedu.address.model.person.Person;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_cardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();
        Card validCard = new CardBuilder().build();

        CommandResult commandResult = new AddCCommand(validCard).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCCommand.MESSAGE_SUCCESS, validCard), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCard), modelStub.cardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() throws Exception {
        Card validCard = new CardBuilder().build();
        AddCCommand addCommand = new AddCCommand(validCard);
        ModelStub modelStub = new ModelStubWithCard(validCard);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCCommand.MESSAGE_DUPLICATE_CARD);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void equalsForTrivia() {
        Card qOnEarthRound = new CardBuilder().withQuestion("Why is the earth round?").build();
        Card qOnEarthFlat = new CardBuilder().withQuestion("Why is the earth flat?").build();
        AddCCommand addEarthRoundCommand = new AddCCommand(qOnEarthRound);
        AddCCommand addEarthFlatCommand = new AddCCommand(qOnEarthFlat);

        // same object -> returns true
        assertTrue(addEarthRoundCommand.equals(addEarthRoundCommand));

        // same values -> returns true
        AddCCommand addEarthRoundCommandCopy = new AddCCommand(qOnEarthRound);
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
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
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
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
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
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
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
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        public ReadOnlyTriviaBundle getTriviaBundle() {
            return new TriviaBundle();
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
