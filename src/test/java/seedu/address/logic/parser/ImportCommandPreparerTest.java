//@@author chantca95
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ImportCommandPreparerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvTest");
    private static final Path CORRECT_CSV = TEST_DATA_FOLDER.resolve("AddressbookCorrect.csv");
    private static final Path CONTACT_ONLY_NAME_CSV = TEST_DATA_FOLDER.resolve("AddressbookContactOnlyName.csv");
    private static final Path DUPLICATE_CLASH_CSV = TEST_DATA_FOLDER.resolve("AddressbookDuplicateClash.csv");
    private static final Path DUPLICATE_CLASH_NEGATIVE_CSV =
            TEST_DATA_FOLDER.resolve("AddressbookDuplicateClashNegative.csv");
    private static final Path CONTACT_NO_NAME_CSV = TEST_DATA_FOLDER.resolve("AddressbookIncompleteContacts.csv");
    private static final Path INVALID_CONTACT_FIELD_CSV =
            TEST_DATA_FOLDER.resolve("AddressbookInvalidContactField.csv");
    private static final Path TEXT_DOC_CSV = TEST_DATA_FOLDER.resolve("AddressbookText.txt");

    private static final ArrayList<String> noTags = new ArrayList<>();

    @Test
    public void perfectly_formatted_csv() throws FileNotFoundException, ParseException {
        ImportCommandPreparer preparer = new ImportCommandPreparer();
        File file = CORRECT_CSV.toFile();
        ImportCommand command = preparer.parseFile(file);

        //Initialize the expected Import command
        ArrayList<Person> expectedPersons = new ArrayList<>();

        expectedPersons.add(new Person(
                new Name("Alex Chan"),
                Optional.of(new Phone("97412033")),
                Optional.of(new Email("chantca95@gmail.com")),
                Optional.of(new Address("Bedok North Street 2 Block 120")),
                Collections.singleton(new Tag("Loanshark")))
        );

        expectedPersons.add(new Person(
                new Name("Louiz"),
                Optional.of(new Phone("98573747")),
                Optional.of(new Email("louizkc@gmail.com")),
                Optional.of(new Address("Cinammon College Level 19")),
                ParserUtil.parseTags(noTags))
        );

        expectedPersons.add(new Person(
                new Name("Auyok Sean"),
                Optional.of(new Phone("85737463")),
                Optional.of(new Email("seanA@gmail.com")),
                Optional.of(new Address("IDKWhere he stays Road")),
                ParserUtil.parseTags(Arrays.asList("Transferee", "Student")))
        );

        boolean expectedHasContactWithInvalidField = false;
        boolean expectedHasContactWithoutName = false;
        ImportCommand expectedImportCommand = new ImportCommand(expectedPersons,
                expectedHasContactWithInvalidField, expectedHasContactWithoutName);

        assertEquals(command, expectedImportCommand);
    }

    @Test
    public void contactsWithOnlyName() throws FileNotFoundException, ParseException {
        ImportCommandPreparer preparer = new ImportCommandPreparer();
        File file = CONTACT_ONLY_NAME_CSV.toFile();
        ImportCommand command = preparer.parseFile(file);

        //Initialize the expected Import command
        ArrayList<Person> expectedPersons = new ArrayList<>();

        expectedPersons.add(new Person(
                new Name("Ernie"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                ParserUtil.parseTags(noTags))
        );

        expectedPersons.add(new Person(
                new Name("Arjun"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                ParserUtil.parseTags(noTags))
        );

        boolean expectedHasContactWithInvalidField = false;
        boolean expectedHasContactWithoutName = false;
        ImportCommand expectedImportCommand = new ImportCommand(expectedPersons,
                expectedHasContactWithInvalidField, expectedHasContactWithoutName);

        assertEquals(command, expectedImportCommand);
    }

    @Test
    public void contactsWithNoName() throws FileNotFoundException, ParseException {
        ImportCommandPreparer preparer = new ImportCommandPreparer();
        File file = CONTACT_NO_NAME_CSV.toFile();
        ImportCommand command = preparer.parseFile(file);

        //Initialize the expected Import command
        ArrayList<Person> expectedPersons = new ArrayList<>();

        expectedPersons.add(new Person(
                new Name("Winnie"),
                Optional.of(new Phone("91847372")),
                Optional.of(new Email("honeypot@disney.com")),
                Optional.of(new Address("Walt Disney Studios")),
                ParserUtil.parseTags(Arrays.asList("Pooh", "Bear")))
        );
        expectedPersons.add(new Person(
                new Name("Martin Henz"),
                Optional.of(new Phone("84736316")),
                Optional.of(new Email("henz@comp.nus.edu.sg")),
                Optional.of(new Address("COM 1")),
                Collections.singleton(new Tag("1101S")))
        );

        boolean expectedHasContactWithInvalidField = false;
        boolean expectedHasContactWithoutName = true;
        ImportCommand expectedImportCommand = new ImportCommand(expectedPersons,
                expectedHasContactWithInvalidField, expectedHasContactWithoutName);

        assertEquals(command, expectedImportCommand);
    }

    @Test
    public void contactsWithInvalidField() throws FileNotFoundException, ParseException {
        ImportCommandPreparer preparer = new ImportCommandPreparer();
        File file = INVALID_CONTACT_FIELD_CSV.toFile();
        ImportCommand command = preparer.parseFile(file);

        //Initialize the expected Import command
        ArrayList<Person> expectedPersons = new ArrayList<>();
        expectedPersons.add(new Person(
                new Name("Bert"),
                Optional.of(new Phone("94837243")),
                Optional.of(new Email("valid@gmail.com")),
                Optional.of(new Address("Sesame Street")),
                ParserUtil.parseTags(Arrays.asList("puppet", "grouchy")))
        );

        boolean expectedHasContactWithInvalidField = true;
        boolean expectedHasContactWithoutName = false;
        ImportCommand expectedImportCommand = new ImportCommand(expectedPersons,
                expectedHasContactWithInvalidField, expectedHasContactWithoutName);

        assertEquals(command, expectedImportCommand);
    }

    @Test
    public void textDocCsv() throws FileNotFoundException, ParseException {
        ImportCommandPreparer preparer = new ImportCommandPreparer();
        File file = TEXT_DOC_CSV.toFile();
        ImportCommand command = preparer.parseFile(file);

        //Initialize the expected Import command
        ArrayList<Person> expectedPersons = new ArrayList<>();
        expectedPersons.add(new Person(
                new Name("Chee Cheong Fun"),
                Optional.of(new Phone("98987423")),
                Optional.of(new Email("horjiak@gmail.com")),
                Optional.of(new Address("Bedok 88")),
                ParserUtil.parseTags(Arrays.asList("hawker", "businessman")))
        );
        expectedPersons.add(new Person(
                new Name("Amos Yee"),
                Optional.of(new Phone("98743423")),
                Optional.of(new Email("amosyee@gmail.com")),
                Optional.of(new Address("America")),
                ParserUtil.parseTags(Arrays.asList("refugee", "activist")))
        );

        boolean expectedHasContactWithInvalidField = false;
        boolean expectedHasContactWithoutName = false;
        ImportCommand expectedImportCommand = new ImportCommand(expectedPersons,
                expectedHasContactWithInvalidField, expectedHasContactWithoutName);

        assertEquals(command, expectedImportCommand);
    }
}
