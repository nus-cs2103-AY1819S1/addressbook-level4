package systemtests;

//import static seedu.souschef.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//import org.junit.Test;

import seedu.souschef.model.AppContent;
//import seedu.souschef.model.recipe.Recipe;
//import seedu.souschef.model.util.SampleDataUtil;
import seedu.souschef.testutil.TestUtil;

public class SampleDataTest extends AddressBookSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected AppContent getInitialData() {
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

    /*@Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Recipe[] expectedList = SampleDataUtil.getRecipes();
        assertListMatching(getRecipeListPanel(), expectedList);
    }*/
}
