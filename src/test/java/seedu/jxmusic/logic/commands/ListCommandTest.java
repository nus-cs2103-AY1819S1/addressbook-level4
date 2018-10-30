//package seedu.jxmusic.logic.commands;
//
//import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalAddressBook;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import seedu.jxmusic.model.Model;
//import seedu.jxmusic.model.ModelManager;
//import seedu.jxmusic.model.UserPrefs;
//
///**
// * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
// */
//public class ListCommandTest {
//
//    private Model model;
//    private Model expectedModel;
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Before
//    public void setUp() {
//        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_listIsNotFiltered_showsSameList() {
//        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_listIsFiltered_showsEverything() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//}
