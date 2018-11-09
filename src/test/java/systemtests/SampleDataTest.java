package systemtests;

import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.util.SampleDataUtil;
import seedu.jxmusic.testutil.TestUtil;

public class SampleDataTest extends LibrarySystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected Library getInitialData() {
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
    public void library_dataFileDoesNotExist_loadSampleData() {
        Playlist[] expectedList = SampleDataUtil.getSamplePlaylists();
        assertListMatching(getPlaylistListPanel(), expectedList);
    }
}
