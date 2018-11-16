package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.logic.commands.CreateApplyCommand;
import seedu.address.model.UserPrefs;
import seedu.address.model.transformation.Transformation;

public class ImageMagickUtilTest {
    private Path testCommandFolder = Paths.get("src", "test", "data", "JsonConvertArgsStorageTest");
    @Test
    public void assertParsePlatformSuccessfully() {
        assertEquals(3, ImageMagickUtil.getPlatform("mac OS X"));
        assertEquals(2, ImageMagickUtil.getPlatform("windows 10"));
    }

    @Test
    public void assertGetZipUrlSuccessfully() {
        assertFalse(ImageMagickUtil.getImageMagickZipUrl("mac OS X").getFile().equals(""));
    }

    @Test
    public void assertGetCommandFolderSuccessfully() {
        String tmpLocation = "testing/folder";
        ImageMagickUtil.setTemporaryCommandForder(tmpLocation);
        assertEquals(ImageMagickUtil.getCommandSaveFolder(), tmpLocation);
    }

    @Test
    public void assertCopyZipFileOutsideSuccessfully() {
        UserPrefs userPrefs = new UserPrefs();
        try {
            ImageMagickUtil.copyOutside(userPrefs, "windows 10");
            File file1 = new File(userPrefs.getCurrDirectory() + "/ImageMagick-7.0.8-14-portable-Q16-x64");
            if (file1.exists()) {
                file1.delete();
            }
            ImageMagickUtil.copyOutside(userPrefs, "mac OS X");
            File file2 = new File(userPrefs.getCurrDirectory() + "/ImageMagick-7.0.8");
            if (file2.exists()) {
                file2.delete();
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertParseBuildInOperationSuccessfully() {
        Logger logger = LogsCenter.getLogger(ImageMagickUtilTest.class);
        logger.warning(System.getProperty("os.name").toLowerCase());
        UserPrefs userPrefs = new UserPrefs();
        try {
            Path path = Paths.get("src", "test", "data", "sandbox", "test.jpg");
            ImageMagickUtil.copyOutside(userPrefs, System.getProperty("os.name").toLowerCase());
            ImageMagickUtil.processImage(path, new Transformation("blur", "0x8"), false);
        } catch (IllegalOperationException e) {
            return;
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertParseCustomisedOperationSuccessfully() {
        UserPrefs userPrefs = new UserPrefs();
        Path path = Paths.get("src", "test", "data", "sandbox", "test.jpg");
        try {
            ImageMagickUtil.copyOutside(userPrefs, System.getProperty("os.name").toLowerCase());
            ArrayList<Transformation> list = new ArrayList<>();
            list.add(new Transformation("blur", "0x8"));
            list.add(new Transformation("rotate", "90"));
            new CreateApplyCommand("blurR", list);
            ImageMagickUtil.setTemporaryCommandForder(testCommandFolder.toString());
            ImageMagickUtil.processImage(path, new Transformation("@blurR"), false);
        } catch (IllegalOperationException e) {
            return;
        } catch (Exception e) {
            fail();
        }
    }
}
