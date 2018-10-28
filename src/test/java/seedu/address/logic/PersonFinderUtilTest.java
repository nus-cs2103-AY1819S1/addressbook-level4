package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.PersonFinderUtil.GENERAL_MESSAGE_USAGE;
import static seedu.address.logic.PersonFinderUtil.findPerson;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalPersons;

//@@author zioul123
public class PersonFinderUtilTest {
    private static final String COMMON_SURNAME = "Meier";
    private static final String BENSON_FIRSTNAME = "Benson";
    private static final String UNUSED_NAME = "ABCDE HIJ";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void noIdentifierProvided() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GENERAL_MESSAGE_USAGE);
        Assert.assertThrows(ParseException.class, expectedMessage, () -> PersonFinderUtil
                .findPerson(model, ""));
        Assert.assertThrows(ParseException.class, expectedMessage, () -> PersonFinderUtil
                .findPerson(model, "  "));
    }

    @Test
    public void noPeopleMatching() {
        String expectedMessage = Messages.MESSAGE_PERSON_NOT_FOUND;
        Assert.assertThrows(CommandException.class, expectedMessage, () -> PersonFinderUtil
                .findPerson(model, UNUSED_NAME));
    }

    @Test
    public void multiplePeopleMatching() {
        String expectedMessage = Messages.MESSAGE_MULTIPLE_PERSONS_FOUND;
        Assert.assertThrows(CommandException.class, expectedMessage, () -> PersonFinderUtil
                .findPerson(model, COMMON_SURNAME));
    }

    @Test
    public void oneMatch() {
        Person expectedPerson = TypicalPersons.ALICE;
        Person foundPerson;
        try {
            foundPerson = findPerson(model, ALICE.getName().toString());
        } catch (Exception e) {
            foundPerson = null;
        }
        assertEquals(expectedPerson, foundPerson);

        expectedPerson = TypicalPersons.BENSON;
        try {
            foundPerson = findPerson(model, BENSON_FIRSTNAME);
        } catch (Exception e) {
            foundPerson = null;
        }
        assertEquals(expectedPerson, foundPerson);
    }
}
