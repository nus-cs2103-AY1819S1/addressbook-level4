package systemtests;

import static seedu.learnvocabulary.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.util.SampleDataUtil;

import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.TestUtil;

public class SampleDataTest extends LearnVocabularySystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected LearnVocabulary getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
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
    public void learnVocabulary_dataFileDoesNotExist_loadSampleData() {
        Word[] expectedList = SampleDataUtil.getSampleWords();
        assertListMatching(getWordListPanel(), expectedList);
    }
}
