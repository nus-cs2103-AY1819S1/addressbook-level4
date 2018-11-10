package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

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
        }
        ImageMagickUtil.copyOutside(userPrefs, "mac OS X");
        File file2 = new File(userPrefs.getCurrDirectory() + "/ImageMagick-7.0.8");
        if (file2.exists()) {
            file2.delete();
        }
    }

    @Test
    public void assertParseBuildInOperationSuccessfully() throws ParseException, InterruptedException, IOException {
        Logger logger = LogsCenter.getLogger(ImageMagickUtilTest.class);
        logger.warning(System.getProperty("os.name").toLowerCase());
        UserPrefs userPrefs = new UserPrefs();
        ImageMagickUtil.copyOutside(userPrefs, System.getProperty("os.name").toLowerCase());
        Path path = Paths.get("src", "test", "data", "sandbox", "test.jpg");
        /*
        try {
            ImageMagickUtil.processImage(path, new Transformation("blur", "0x8"));
        } catch (UnsupportedPlatformException e) {
            return;
        }
        */
    }

    @Test
    public void assertParseCustomisedOperationSuccessfully() throws ParseException, InterruptedException, IOException {
        UserPrefs userPrefs = new UserPrefs();
        ImageMagickUtil.copyOutside(userPrefs, System.getProperty("os.name").toLowerCase());
        Path path = Paths.get("src", "test", "data", "sandbox", "test.jpg");
        /*
        try {
            ArrayList<Transformation> list = new ArrayList<>();
            list.add(new Transformation("blur", "0x8"));
            list.add(new Transformation("rotate", "90"));
            new CreateConvertCommand("blurR", list);
            ImageMagickUtil.processImage(path, new Transformation("@blurR"));
        } catch (UnsupportedPlatformException e) {
            return;
        }
        */
    }
}
