package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PromoteIndividualCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executePromoteFirstPersonSuccess() {
        Person promotedStudent = new PersonBuilder(model.getFilteredPersonList().get(0)).build();
        promotedStudent.addTag(new Tag("Graduated"));
        PromoteIndividualCommand promoteIndividualCommand = new PromoteIndividualCommand("1");

        String expectedMessage = String.format(PromoteIndividualCommand.MESSAGE_SUCCESS
                + PromoteIndividualCommand.MESSAGE_GRADUATED_STUDENTS, 1, promotedStudent.getName().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), promotedStudent);
        expectedModel.commitAddressBook();


        assertCommandSuccess(promoteIndividualCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executePromoteOutOfBoundIndexFailure() throws CommandException {
        thrown.expect(CommandException.class);
        new PromoteIndividualCommand("1 2 8").execute(model, commandHistory);
    }

    @Test
    public void executePromoteNegativeIndexFailure() throws IndexOutOfBoundsException {
        thrown.expect(IndexOutOfBoundsException.class);
        new PromoteIndividualCommand("-1 2 8");
    }
}
