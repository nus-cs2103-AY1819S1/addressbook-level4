package seedu.meeting.logic.commands;

import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.meeting.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.util.GroupContainsPersonPredicate;
import seedu.meeting.model.group.util.GroupTitleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getMeetingBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(ListCommand.ListCommandType.PERSON), model, commandHistory,
            ListCommand.MESSAGE_SUCCESS_PERSON, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(ListCommand.ListCommandType.PERSON), model, commandHistory,
            ListCommand.MESSAGE_SUCCESS_PERSON, expectedModel);
    }

    @Test
    public void execute_groupListIsNotFiltered_showsSameGroupList() {
        assertCommandSuccess(new ListCommand(ListCommand.ListCommandType.GROUP), model, commandHistory,
            ListCommand.MESSAGE_SUCCESS_GROUP, expectedModel);
    }

    @Test
    public void execute_groupListIsFiltered_showsEverything() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);

        // Because when a group is selected, the person list will change, hence this needs to be called to filter
        // expectedModel's person list.
        Group group = expectedModel.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());

        final String[] groupTitle = group.getTitle().fullTitle.split("\\s+");
        expectedModel.updateFilteredGroupList(new GroupTitleContainsKeywordsPredicate(Collections.emptyList(),
            Arrays.asList(groupTitle[0]), Collections.emptyList()));
        expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(Collections.singletonList(group)));

        assertCommandSuccess(new ListCommand(ListCommand.ListCommandType.GROUP), model, commandHistory,
            ListCommand.MESSAGE_SUCCESS_GROUP, expectedModel);
    }

    @Test
    public void execute_meetingListIsNotFiltered_showsSameMeetingList() {
        assertCommandSuccess(new ListCommand(ListCommand.ListCommandType.MEETING), model, commandHistory,
            ListCommand.MESSAGE_SUCCESS_MEETING, expectedModel);
    }

    @Test
    public void execute_meetingListIsFiltered_showsEverything() {
        // One way to filter the meeting list is to select the group
        showGroupAtIndex(model, INDEX_FIRST_GROUP);

        Group group = expectedModel.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        final String[] groupTitle = group.getTitle().fullTitle.split("\\s+");
        expectedModel.updateFilteredGroupList(new GroupTitleContainsKeywordsPredicate(Collections.emptyList(),
            Arrays.asList(groupTitle), Collections.emptyList()));
        expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
    }
}
