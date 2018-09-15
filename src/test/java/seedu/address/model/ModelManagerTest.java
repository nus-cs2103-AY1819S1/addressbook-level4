package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.Q_GIT_CLONE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.card.QuestionContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TriviaBundleBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasCard_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasCard(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasCard_cardNotInTriviaBundle_returnsFalse() {
        assertFalse(modelManager.hasCard(Q_EARTH_ROUND));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasCard_cardInTriviaBundle_returnsTrue() {
        modelManager.addCard(Q_EARTH_ROUND);
        assertTrue(modelManager.hasCard(Q_EARTH_ROUND));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredCardList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        TriviaBundle triviaBundle = new TriviaBundleBuilder().withCard(Q_EARTH_ROUND).withCard(Q_GIT_CLONE).build();
        AddressBook differentAddressBook = new AddressBook();
        TriviaBundle differentTriviaBundle = new TriviaBundle();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, triviaBundle, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, triviaBundle, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, differentTriviaBundle, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = Q_EARTH_ROUND.getQuestion().value.split("\\s+");
        modelManager.updateFilteredCardList(new QuestionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, triviaBundle, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);

        // different filteredList -> returns false
        keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, triviaBundle, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, triviaBundle, differentUserPrefs)));
    }
}
