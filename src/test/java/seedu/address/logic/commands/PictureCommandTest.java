package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author denzelchung
/**
 * Contains integration tests (interaction with the Model) and unit tests for PictureCommand.
 */
public class PictureCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_throwsCommandException() throws Exception {
        Path fileLocation = Paths.get("image/alice.jpg");
        PictureCommand pictureCommand = new PictureCommand(INDEX_FIRST_PERSON, fileLocation);
        pictureCommand.execute(model, commandHistory);

        String expectedMessage = String.format(PictureCommand.MESSAGE_SUCCESS, INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(pictureCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
