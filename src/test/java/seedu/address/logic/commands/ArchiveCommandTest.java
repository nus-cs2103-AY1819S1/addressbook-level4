package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLeaveApplicationForPerson;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LeaveListCommand.
 */
public class ArchiveCommandTest {

  private Model model;
  private Model expectedModel;
  private CommandHistory commandHistory = new CommandHistory();

  @Before
  public void setUp() {
    model = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
            getTypicalArchiveList(), new UserPrefs());
    expectedModel = new ModelManager(model.getAddressBook(), getTypicalAssignmentList(),
            getTypicalArchiveList(), new UserPrefs());
  }

  @Test
  public void execute_listByAdmin_showsEverything() {
    // The admin user is able to see all leave applications from all persons
    model.setLoggedInUser(User.getAdminUser());
    expectedModel.setLoggedInUser(User.getAdminUser());

    assertCommandSuccess(new ArchiveCommand(), model, commandHistory, ArchiveCommand.MESSAGE_SUCCESS,
            expectedModel);
  }
}
