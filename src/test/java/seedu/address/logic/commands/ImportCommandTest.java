package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

//@@author kengwoon

/**
 * Contains unit tests for ImportCommand.
 */
public class ImportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private final String PERSON_NAME = "John";
    private final String PERSON_PHONE = "12345678";
    private final String PERSON_EMAIL = "john@gmail.com";
    private final String PERSON_ROOM = "C234";
    private final String PERSON_SCHOOL = "Computing";

    @Test
    public void execute_nullFile_throwsFileNotFoundException() throws Exception {
        ImportCommand importCommand = new ImportCommand(new File("./imports/notAFile.xml"));

        String expectedMessage = ImportCommand.MESSAGE_FILE_NOT_FOUND;

        assertEquals(expectedMessage, importCommand.execute(model, commandHistory));
    }

    @Test
    public void execute_validContactsFile_success() {
        String fileName = "contactImports.xml";
        File file = new File("./imports/" + fileName);
        ImportCommand importCommand = new ImportCommand(file);

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new BudgetBook(model.getBudgetBook()), new UserPrefs());
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("basketball"));
        Person newPerson = new Person(new Name(PERSON_NAME), new Phone(PERSON_PHONE), new Email(PERSON_EMAIL),
                new Room(PERSON_ROOM), new School(PERSON_SCHOOL), tags);
        expectedModel.addPerson(newPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCcaFile_success() {
        String fileName = "ccaImports.xml";
        File file = new File("./imports/" + fileName);
        ImportCommand importCommand = new ImportCommand(file);

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new BudgetBook(model.getBudgetBook()), new UserPrefs());
        Set<Tag> tags = new HashSet<>();
        Person original = model.getAddressBook().getPersonList().get(0);
        tags.add((Tag)original.getTags().toArray()[0]);
        tags.add(new Tag("golf"));
        Person edited = new Person(original.getName(), original.getPhone(), original.getEmail(),
                original.getRoom(), original.getSchool(), tags);
        expectedModel.updatePerson(original, edited);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.commitAddressBook();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
