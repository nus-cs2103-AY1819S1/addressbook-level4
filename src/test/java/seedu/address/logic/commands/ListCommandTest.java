package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private Model modelVolunteer;
    private Model expectedModelVolunteer;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        modelVolunteer = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
        expectedModelVolunteer = new ModelManager(modelVolunteer.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listVolunteerIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListVolunteerCommand(), modelVolunteer, commandHistory,
                ListVolunteerCommand.MESSAGE_SUCCESS, expectedModelVolunteer);
    }

    @Test
    public void execute_listVolunteerIsFiltered_showsEverything() {
        showVolunteerAtIndex(modelVolunteer, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListVolunteerCommand(), modelVolunteer, commandHistory,
                ListVolunteerCommand.MESSAGE_SUCCESS, expectedModelVolunteer);
    }
}
