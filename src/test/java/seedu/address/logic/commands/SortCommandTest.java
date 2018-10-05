package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_SORTED_OVERVIEW;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPropertyComparator;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalPersons;

//TODO
class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private PersonPropertyComparator nameComparator = PersonPropertyComparator.NAME;
    private PersonPropertyComparator phoneComparator = PersonPropertyComparator.PHONE;
    private PersonPropertyComparator emailComparator = PersonPropertyComparator.EMAIL;
    private PersonPropertyComparator addressComparator = PersonPropertyComparator.ADDRESS;

    private SortCommand sortByNameCommand = new SortCommand(nameComparator);
    private SortCommand sortByPhoneCommand = new SortCommand(phoneComparator);
    private SortCommand sortByEmailCommand = new SortCommand(emailComparator);
    private SortCommand sortByAddressCommand = new SortCommand(addressComparator);

    @Test
    public void equals() {
        assertEquals(sortByNameCommand, new SortCommand(PersonPropertyComparator.NAME));
        assertEquals(sortByPhoneCommand, new SortCommand(PersonPropertyComparator.PHONE));
        assertEquals(sortByEmailCommand, new SortCommand(PersonPropertyComparator.EMAIL));
        assertEquals(sortByAddressCommand, new SortCommand(PersonPropertyComparator.ADDRESS));

        assertNotEquals(sortByNameCommand, sortByPhoneCommand);
        assertNotEquals(sortByNameCommand, sortByEmailCommand);
        assertNotEquals(sortByNameCommand, sortByAddressCommand);
        assertNotEquals(sortByPhoneCommand, sortByEmailCommand);
        assertNotEquals(sortByPhoneCommand, sortByAddressCommand);
        assertNotEquals(sortByEmailCommand, sortByAddressCommand);
        assertNotEquals(sortByNameCommand, null);
        assertNotEquals(sortByPhoneCommand, null);
        assertNotEquals(sortByEmailCommand, null);
        assertNotEquals(sortByAddressCommand, null);
    }

    @Test
    public void execute_sortByName() {
        Model testModel = new ModelManager(new AddressBookBuilder().withPerson(CARL).withPerson(BENSON)
                .withPerson(ALICE).build(), new UserPrefs());
        assertEquals(Arrays.asList(CARL, BENSON, ALICE), testModel.getSortedPersonList());
        String expectedMessage = String.format(MESSAGE_PERSONS_SORTED_OVERVIEW, nameComparator.toString());
        Model expectedModel = new ModelManager(new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(CARL).build(), new UserPrefs());
        testModel.updateSortedPersonList(nameComparator);
        assertCommandSuccess(sortByNameCommand, testModel, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), testModel.getSortedPersonList());
    }
}
