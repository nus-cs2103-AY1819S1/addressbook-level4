package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.Room;

//@@author javenseow
public class ImageCommandTest {

    private static final String IMAGE_COMMAND_TEST_DATA_FOLDER = "./src/test/data/ImageCommandTest/";
    private static final String INVALID_IMAGE = "Invalid.png";
    private static final String INVALID_ROOM = "B313";
    private static final String VALID_IMAGE = "Valid.jpg";
    private static final String VALID_ROOM = "B314";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nullResident_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImageCommand(null, new File(IMAGE_COMMAND_TEST_DATA_FOLDER + VALID_IMAGE));
    }

    @Test
    public void execute_invalidFile_throwsCommandException() {
        ImageCommand imageCommand = new ImageCommand(new Room(VALID_ROOM),
                new File(IMAGE_COMMAND_TEST_DATA_FOLDER + INVALID_IMAGE));

        String expectedMessage = ImageCommand.FILE_PATH_ERROR;

        assertCommandFailure(imageCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidResident_throwsCommandException() {
        ImageCommand imageCommand = new ImageCommand(new Room(INVALID_ROOM),
                new File(IMAGE_COMMAND_TEST_DATA_FOLDER + VALID_IMAGE));

        String expectedMessage = ImageCommand.MESSAGE_NO_SUCH_PERSON;

        assertCommandFailure(imageCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noSuchFile_throwsCommandException() {
        ImageCommand imageCommand = new ImageCommand(new Room(VALID_ROOM),
                new File(IMAGE_COMMAND_TEST_DATA_FOLDER + "NoSuchFile.jpg"));

        String expectedMessage = ImageCommand.INVALID_IMAGE_ERROR;

        assertCommandFailure(imageCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validFile_success() {
        ImageCommand imageCommand = new ImageCommand(new Room(VALID_ROOM),
                new File(IMAGE_COMMAND_TEST_DATA_FOLDER + VALID_IMAGE));

        String expectedMessage = String.format(ImageCommand.MESSAGE_SUCCESS, ALICE);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new BudgetBook(model.getBudgetBook()), new UserPrefs(), model.getExistingEmails());
        Person editedPerson = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getRoom(), ALICE.getSchool(),
                new ProfilePicture(Paths.get(VALID_ROOM.toLowerCase() + ".jpg")), ALICE.getTags());

        expectedModel.updatePerson(ALICE, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(imageCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Room aliceRoom = ALICE.getRoom();
        Room bobRoom = BOB.getRoom();
        ImageCommand imageAliceCommand = new ImageCommand(aliceRoom,
                new File(IMAGE_COMMAND_TEST_DATA_FOLDER + VALID_IMAGE));
        ImageCommand imageBobCommand = new ImageCommand(bobRoom,
                new File(IMAGE_COMMAND_TEST_DATA_FOLDER + VALID_IMAGE));

        // same object -> returns true
        assertTrue(imageAliceCommand.equals(imageAliceCommand));

        // same values -> returns true
        ImageCommand imageAliceCommandCopy = new ImageCommand(aliceRoom,
                new File(IMAGE_COMMAND_TEST_DATA_FOLDER + VALID_IMAGE));
        assertTrue(imageAliceCommand.equals(imageAliceCommandCopy));

        // different types -> returns false
        assertFalse(imageAliceCommand.equals(1));

        // null -> returns false
        assertFalse(imageAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(imageAliceCommand.equals(imageBobCommand));
    }
}
