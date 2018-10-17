package systemtests;

import static seedu.inventory.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.util.SampleDataUtil;
import seedu.inventory.testutil.TestUtil;

public class SampleDataTest extends InventorySystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected Inventory getInitialData() {
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
    public void inventory_dataFileDoesNotExist_loadSampleData() {
        Item[] expectedList = SampleDataUtil.getSampleItems();
        assertListMatching(getItemListPanel(), expectedList);
    }
}
