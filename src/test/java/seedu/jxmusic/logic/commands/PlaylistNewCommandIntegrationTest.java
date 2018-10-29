//package seedu.jxmusic.logic.commands;
//
//import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalAddressBook;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import seedu.jxmusic.model.Model;
//import seedu.jxmusic.model.ModelManager;
//import seedu.jxmusic.model.UserPrefs;
//import seedu.jxmusic.testutil.PlaylistBuilder;
//
///**
// * Contains integration tests (interaction with the Model) for {@code PlaylistNewCommand}.
// */
//public class AddCommandIntegrationTest {
//
//    private Model model;
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Before
//    public void setUp() {
//        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_newPerson_success() {
//        Person validPerson = new PlaylistBuilder().build();
//
//        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        expectedModel.addPerson(validPerson);
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(new PlaylistNewCommand(validPerson), model, commandHistory,
//                String.format(PlaylistNewCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
//    }
//
//    @Test
//    public void execute_duplicatePerson_throwsCommandException() {
//        Person personInList = model.getAddressBook().getPlaylistList().get(0);
//        assertCommandFailure(new PlaylistNewCommand(personInList), model, commandHistory,
//                PlaylistNewCommand.MESSAGE_DUPLICATE_PERSON);
//    }
//
//}
