package seedu.address.commons.util;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
import seedu.address.model.transformation.Transformation;

public class ImageMagickUtilTest {
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
        ImageMagickUtil.setTemperatyCommandForder(tmpLocation);
        assertEquals(ImageMagickUtil.getCommandSaveFolder(), tmpLocation);
    }

    @Test
    public void assertCopyZipFileOutsideSuccessfully() throws IOException, InterruptedException {
        UserPrefs userPrefs = new UserPrefs();
        ImageMagickUtil.copyOutside(userPrefs, "windows 10");
        File file1 = new File(userPrefs.getCurrDirectory() + "/ImageMagick-7.0.8-14-portable-Q16-x64");
        if (file1.exists()) {
            file1.delete();
        } else {
            fail();
        }
        ImageMagickUtil.copyOutside(userPrefs, "mac OS X");
        File file2 = new File(userPrefs.getCurrDirectory() + "/ImageMagick-7.0.8");
        if (file2.exists()) {
            file2.delete();
        } else {
            fail();
        }
    }

    @Test
    public void assertParseBuildInOperationSuccessfully() throws ParseException, InterruptedException, IOException {
        UserPrefs userPrefs = new UserPrefs();
        ImageMagickUtil.copyOutside(userPrefs, System.getProperty("os.name").toLowerCase());
        Path path = Paths.get("src", "test", "data", "sandbox", "test.jpg");
        ImageMagickUtil.processImage(path, new Transformation("blur", "0x8"));
    }

    @Test
    public void assertParseCustomisedOperationSuccessfully() throws ParseException, InterruptedException, IOException {
        UserPrefs userPrefs = new UserPrefs();
        ImageMagickUtil.copyOutside(userPrefs, System.getProperty("os.name").toLowerCase());
        Path path = Paths.get("src", "test", "data", "sandbox", "test.jpg");
        ImageMagickUtil.processImage(path, new Transformation("@blurR"));
    }
}
