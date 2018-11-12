package seedu.address.logic.commands.timetable;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleUserDataUtil.SAMPLE_GROUP_NAME;
import static seedu.address.testutil.TypicalDates.DATE_A;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Name;
import seedu.address.model.UserData;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.user.User;
import seedu.address.model.util.SampleUserDataUtil;
import seedu.address.testutil.UserBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FindDatesCommand.
 */
public class FindDatesCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), SampleUserDataUtil.getSampleUserData(),
                new UserBuilder().build());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                SampleUserDataUtil.getSampleUserData(), new UserBuilder().build());
    }

    @Test
    public void execute_findDateValidGroup_showsSameFindDates() {
        UserData userData = model.getUserData();
        User userWithGroup = null;
        List<User> users = userData.getUsers();

        for (User user : users) {
            List<Group> groups = user.getGroups();
            if (groups == null) {
                continue;
            } else if (groups.stream().anyMatch(group -> group.getGroupName().equals(new Name(SAMPLE_GROUP_NAME)))) {
                userWithGroup = user;
                break;
            }
        }

        assertNotNull(userWithGroup);

        assertTrue(!userWithGroup.getGroups().isEmpty());

        Name groupName = userWithGroup.getGroups().get(0).getGroupName();
        model.logoutUser();
        model.loginUser(userWithGroup);

        expectedModel.logoutUser();
        expectedModel.loginUser(userWithGroup);

        assertCommandSuccess(new FindDatesCommand(groupName, DATE_A.getWeek()),
                model, commandHistory, String.format(FindDatesCommand.MESSAGE_SUCCESS,
                        DATE_A.getWeek(), groupName), expectedModel);
    }

    @Test
    public void execute_findDateInvalidGroup_throwsCommandException() {
        Name groupName = new Name("TRASHGROUP");
        assertFalse(model.hasGroup(groupName));
        assertCommandFailure(new FindDatesCommand(groupName, DATE_A.getWeek()), model, commandHistory,
                FindDatesCommand.MESSAGE_NO_SUCH_GROUP);

    }

}
