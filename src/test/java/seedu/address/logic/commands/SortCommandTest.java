package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPropertyComparator;

//TODO
class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        PersonPropertyComparator nameComparator = PersonPropertyComparator.NAME;
        PersonPropertyComparator phoneComparator = PersonPropertyComparator.PHONE;
        PersonPropertyComparator emailComparator = PersonPropertyComparator.EMAIL;
        PersonPropertyComparator addressComparator = PersonPropertyComparator.ADDRESS;

        SortCommand sortByNameCommand = new SortCommand(nameComparator);
        SortCommand sortByPhoneCommand = new SortCommand(phoneComparator);
        SortCommand sortByEmailCommand = new SortCommand(emailComparator);
        SortCommand sortByAddressCommand = new SortCommand(addressComparator);

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
    void execute() {
    }

    @Test
    void execute1() {
    }
}
