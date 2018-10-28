package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPersonCommand}.
 */
public class FindPersonCommandTest {
    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        //test NameContainsKeywordsPredicate
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPersonCommand findFirstNameCommand = new FindPersonCommand(firstNamePredicate);
        FindPersonCommand findSecondNameCommand = new FindPersonCommand(secondNamePredicate);

        // same object -> returns true
        assertTrue(findFirstNameCommand.equals(findFirstNameCommand));

        // same values -> returns true
        FindPersonCommand findFirstNameCommandCopy = new FindPersonCommand(firstNamePredicate);
        assertTrue(findFirstNameCommand.equals(findFirstNameCommandCopy));

        // different types -> returns false
        assertFalse(findFirstNameCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstNameCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstNameCommand.equals(findSecondNameCommand));

        //test PhoneContainsKeywordsPredicate
        PhoneContainsKeywordsPredicate firstPhonePredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("123"));
        PhoneContainsKeywordsPredicate secondPhonePredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("456"));

        FindPersonCommand findFirstPhoneCommand = new FindPersonCommand(firstPhonePredicate);
        FindPersonCommand findSecondPhoneCommand = new FindPersonCommand(secondPhonePredicate);

        // same object -> returns true
        assertTrue(findFirstPhoneCommand.equals(findFirstPhoneCommand));

        // same values -> returns true
        FindPersonCommand findFirstPhoneCommandCopy = new FindPersonCommand(firstPhonePredicate);
        assertTrue(findFirstPhoneCommand.equals(findFirstPhoneCommandCopy));

        // different types -> returns false
        assertFalse(findFirstPhoneCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstPhoneCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstPhoneCommand.equals(findSecondPhoneCommand));

        //test EmailContainsKeywordsPredicate
        EmailContainsKeywordsPredicate firstEmailPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("abc@emial.com"));
        EmailContainsKeywordsPredicate secondEmailPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("def@email.com"));

        FindPersonCommand findFirstEmailCommand = new FindPersonCommand(firstEmailPredicate);
        FindPersonCommand findSecondEmailCommand = new FindPersonCommand(secondEmailPredicate);

        // same object -> returns true
        assertTrue(findFirstEmailCommand.equals(findFirstEmailCommand));

        // same values -> returns true
        FindPersonCommand findFirstEmailCommandCopy = new FindPersonCommand(firstEmailPredicate);
        assertTrue(findFirstEmailCommand.equals(findFirstEmailCommandCopy));

        // different types -> returns false
        assertFalse(findFirstEmailCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstEmailCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstEmailCommand.equals(findSecondEmailCommand));

        //test AddressContainsKeywordsPredicate
        AddressContainsKeywordsPredicate firstAddressPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("soc 123"));
        AddressContainsKeywordsPredicate secondAddressPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("fos 456"));

        FindPersonCommand findFirstAddressCommand = new FindPersonCommand(firstAddressPredicate);
        FindPersonCommand findSecondAddressCommand = new FindPersonCommand(secondAddressPredicate);

        // same object -> returns true
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommand));

        // same values -> returns true
        FindPersonCommand findFirstAddressCommandCopy = new FindPersonCommand(firstAddressPredicate);
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommandCopy));

        // different types -> returns false
        assertFalse(findFirstAddressCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstAddressCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstAddressCommand.equals(findSecondAddressCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        //test findPerson using name
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        FindPersonCommand nameCommand = new FindPersonCommand(namePredicate);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(nameCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        //test findPerson using Phone
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(" ");
        FindPersonCommand phoneCommand = new FindPersonCommand(phonePredicate);
        expectedModel.updateFilteredPersonList(phonePredicate);
        assertCommandSuccess(phoneCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        //test findPerson using Email
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(" ");
        FindPersonCommand emailCommand = new FindPersonCommand(emailPredicate);
        expectedModel.updateFilteredPersonList(emailPredicate);
        assertCommandSuccess(emailCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        //test findPerson using Address
        AddressContainsKeywordsPredicate addressPredicate = prepareAddressPredicate(" ");
        FindPersonCommand addressCommand = new FindPersonCommand(addressPredicate);
        expectedModel.updateFilteredPersonList(addressPredicate);
        assertCommandSuccess(addressCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);

        //test findPerson using name
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        FindPersonCommand nameCommand = new FindPersonCommand(namePredicate);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(nameCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        //test findPerson using phone
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate("95352563 9482224 9482427");
        FindPersonCommand phoneCommand = new FindPersonCommand(phonePredicate);
        expectedModel.updateFilteredPersonList(phonePredicate);
        assertCommandSuccess(phoneCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        //test findPerson using email
        EmailContainsKeywordsPredicate emailPredicate =
                prepareEmailPredicate("heinz@example.com werner@example.com lydia@example.com");
        FindPersonCommand emailCommand = new FindPersonCommand(emailPredicate);
        expectedModel.updateFilteredPersonList(emailPredicate);
        assertCommandSuccess(emailCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        //test findPerson using address
        AddressContainsKeywordsPredicate addressPredicate = prepareAddressPredicate("wall michegan tokyo");
        FindPersonCommand addressCommand = new FindPersonCommand(addressPredicate);
        expectedModel.updateFilteredPersonList(addressPredicate);
        assertCommandSuccess(addressCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsPredicate}.
     */
    private PhoneContainsKeywordsPredicate preparePhonePredicate(String userInput) {
        return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate prepareEmailPredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
