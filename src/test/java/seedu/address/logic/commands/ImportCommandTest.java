package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCcas.getTypicalBudgetBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author kengwoon

/**
 * Contains unit tests for ImportCommand.
 */
public class ImportCommandTest {

    private static final String VALID_PERSON_NAME = "John";
    private static final String VALID_PERSON_PHONE = "12345678";
    private static final String VALID_PERSON_EMAIL = "john@gmail.com";
    private static final String VALID_PERSON_ROOM = "C234";
    private static final String VALID_PERSON_SCHOOL = "Computing";

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalBudgetBook(), new UserPrefs(),
        new HashSet<>());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nullFile_throwsFileNotFoundException() {
        ImportCommand importCommand = new ImportCommand(new File("./imports/notAFile.xml").toPath());

        String expectedMessage = ImportCommand.MESSAGE_FILE_NOT_FOUND;

        assertCommandFailure(importCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidFileFormat_failure() {
        ImportCommand importCommand = new ImportCommand(
            new File("./src/test/data/ImportCommandTest/invalidFormat.xml").toPath());

        String expectedMessage = ImportCommand.MESSAGE_PARSE_ERR;

        assertCommandFailure(importCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validContactsFile_success() {
        String fileName = "contactImports.xml";
        File file = new File("./src/test/data/ImportCommandTest/" + fileName);
        ImportCommand importCommand = new ImportCommand(file.toPath());

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new BudgetBook(model.getBudgetBook()), new UserPrefs(), model.getExistingEmails());
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("basketball"));
        Person newPerson = new Person(new Name(VALID_PERSON_NAME), new Phone(VALID_PERSON_PHONE),
                new Email(VALID_PERSON_EMAIL),
                new Room(VALID_PERSON_ROOM), new School(VALID_PERSON_SCHOOL), tags);
        expectedModel.addPerson(newPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCcaFile_success() {
        String fileName = "ccaImports.xml";
        File file = new File("./src/test/data/ImportCommandTest/" + fileName);
        ImportCommand importCommand = new ImportCommand(file.toPath());

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new BudgetBook(model.getBudgetBook()), new UserPrefs(), model.getExistingEmails());
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person original = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        PersonBuilder personInFile = new PersonBuilder(original);
        Person edited = personInFile.withTags("Golf").build();
        expectedModel.updatePerson(original, edited);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.commitAddressBook();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validBudgetBookFile_success() {
        String fileName = "budgetBookImports.xml";
        File file = new File("./src/test/data/ImportCommandTest/" + fileName);
        ImportCommand importCommand = new ImportCommand(file.toPath());

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new BudgetBook(model.getBudgetBook()), new UserPrefs(), model.getExistingEmails());
        Set<Entry> entries = new HashSet<>();
        entries.add(new Entry(2, new Date("20.06.2014"), new Amount(-400), new Remarks("Hockey Camp")));
        Cca newCca = new Cca(new CcaName("Hockey"), new Name("MrYanDao"), new Name("XiaoMing"), new Budget(500),
            new Spent(300), new Outstanding(200), entries);
        expectedModel.addCca(newCca);
        expectedModel.updateFilteredCcaList(Model.PREDICATE_SHOW_ALL_CCAS);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTransactionsFile_success() {
        String fileName = "transactionImports.xml";
        File file = new File("./src/test/data/ImportCommandTest/" + fileName);
        ImportCommand importCommand = new ImportCommand(file.toPath());

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new BudgetBook(model.getBudgetBook()), new UserPrefs(), model.getExistingEmails());
        Index indexLastCca = Index.fromOneBased(model.getFilteredCcaList().size());
        Cca originalCca = model.getFilteredCcaList().get(indexLastCca.getZeroBased());
        Set<Entry> entries = new HashSet<>(originalCca.getEntries());
        int entryNum = originalCca.getEntrySize() + 1;
        entries.add(new Entry(entryNum, new Date("20.06.2014"), new Amount(-400), new Remarks("Floorball Camp")));
        CcaBuilder ccaInFile = new CcaBuilder(originalCca);
        Cca editedCca = ccaInFile.withTransaction(entries).build();
        expectedModel.updateCca(originalCca, editedCca);
        expectedModel.updateFilteredCcaList(Model.PREDICATE_SHOW_ALL_CCAS);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
