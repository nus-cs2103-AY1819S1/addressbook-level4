package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ImportCommandPreparer;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ImportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvTest");
    private static final Path CORRECT_CSV = TEST_DATA_FOLDER.resolve("AddressbookCorrect.csv");
    private static final Path DUPLICATE_CLASH_CSV = TEST_DATA_FOLDER.resolve("AddressbookDuplicateClash.csv");
    private static final Path DUPLICATE_CLASH_NEGATIVE_CSV =
            TEST_DATA_FOLDER.resolve("AddressbookDuplicateClashNegative.csv");

    private Model model;
    private CommandHistory commandHistory;
    ImportCommandPreparer preparer = new ImportCommandPreparer();
    
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_newImport_success() throws ParseException {
        setUp();
        File file = CORRECT_CSV.toFile();
        ImportCommand command = preparer.parseFile(file);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        //Expected persons being added to expectedModel
        Person Alex = new PersonBuilder().withName("Alex Chan")
                .withAddress("Bedok North Street 2 Block 120").withEmail("chantca95@gmail.com")
                .withPhone("97412033")
                .withTags("Loanshark").build();
        Person Louiz = new PersonBuilder().withName("Louiz")
                .withAddress("Cinammon College Level 19").withEmail("louizkc@gmail.com")
                .withPhone("98573747").build();
        Person Sean = new PersonBuilder().withName("Auyok Sean")
                .withAddress("IDKWhere he stays Road").withEmail("seanA@gmail.com")
                .withPhone("85737463")
                .withTags("Transferee", "Student").build();

        expectedModel.addPerson(Alex);
        expectedModel.addPerson(Louiz);
        expectedModel.addPerson(Sean);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory,
                String.format(ImportCommand.MESSAGE_SUCCESS), expectedModel);
    }

    @Test
    public void execute_newImport_duplicates_ignored() throws ParseException {
        setUp();
        File file = DUPLICATE_CLASH_CSV.toFile();
        ImportCommand command = preparer.parseFile(file);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        
        //Populate the test model with an Alex entry, a duplicate of which is in the CSV under test
        Person Alex = new PersonBuilder().withName("Alex Chan")
                .withAddress("Bedok North Street 2 Block 120").withEmail("chantca95@gmail.com")
                .withPhone("97412033")
                .withTags("Loanshark").build();
        
        model.addPerson(Alex);
        expectedModel.addPerson(Alex);

        //Expected persons being added to expectedModel
        Person Alistair = new PersonBuilder().withName("Alistair")
                .withAddress("Cinammon College Also").withEmail("princeali@gmail.com")
                .withPhone("95812341")
                .withTags("Transferee", "Floorball").build();

        expectedModel.addPerson(Alistair);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory,
                String.format(ImportCommand.MESSAGE_SUCCESS + ImportCommand.MESSAGE_DUPLICATE), expectedModel);
    }

    @Test
    public void execute_newImport_halfDuplicates_allowed() throws ParseException {
        setUp();
        File file = DUPLICATE_CLASH_NEGATIVE_CSV.toFile();
        ImportCommand command = preparer.parseFile(file);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        //Populate the test model with an Alex entry, an (apparent) duplicate of which is in the CSV under test.
        Person Alex = new PersonBuilder().withName("Alex Chan")
                .withAddress("Bedok North Street 2 Block 120").withEmail("chantca95@gmail.com")
                .withPhone("97412033")
                .withTags("Loanshark").build();

        model.addPerson(Alex);
        expectedModel.addPerson(Alex);

        //Expected persons being added to expectedModel
        //The Alex Chan emtry in the CSV being tested does not count as a duplicate and will be added to the list.
        Person AlexCopy = new PersonBuilder().withName("Alex Chan")
                .withAddress("Bedok North Street 2 Block 120").withEmail("javalover@gmail.com")
                .withPhone("43678243")
                .withTags("Loanshark").build();

        expectedModel.addPerson(AlexCopy);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory,
                String.format(ImportCommand.MESSAGE_SUCCESS), expectedModel);
    }
}