package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.TriviaBundle;
import seedu.address.model.card.Card;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends AppSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getAddressBookFile()}.
     */
    @Override
    protected AddressBook getAddressBookData() {
        return null;
    }

    /**
     * Returns null to force test app to load data of the file in {@code getTriviaBundleFile()}.
     */
    @Override
    protected TriviaBundle getTriviaBundleData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getAddressBookFile() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getTriviaBundleFile() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Person[] expectedList = SampleDataUtil.getSamplePersons();
        assertListMatching(getPersonListPanel(), expectedList);
    }

    @Test
    public void triviaBundle_dataFileDoesNotExist_loadSampleData() {
        Card[] expectedList = SampleDataUtil.getSampleCards();
        assertListMatching(getCardListPanel(), expectedList);
    }
}
