package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PreviewImageGenerator;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);


    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void clearCacheTest() {
        File cache = new File("cache");
        cache.mkdir();
        String cachePath = cache.getPath();
        BufferedImage image = PreviewImageGenerator.getABufferedImage();
        try {
            File out = new File(cachePath + "/test.png");
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            logger.warning("Error in clear cache test :" + e.getMessage());
        }

        storageManager.clearCache();

        Assert.assertEquals(0, cache.list().length);

    }

    @Test
    public void getUserPrefsFilePathTest() {
        Path path = storageManager.getUserPrefsFilePath();
        assertNotNull(path);
    }

}
