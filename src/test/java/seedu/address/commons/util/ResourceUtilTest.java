package seedu.address.commons.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class ResourceUtilTest {
    private final Path testFolder = Paths.get("src", "test", "data", "sandbox");

    @Test
    public void unzipFolderTest() throws IOException {
        File zippedFile = testFolder.resolve("testFolder.zip").toFile();
        ResourceUtil.unzipFolder(zippedFile);
        File unzippedFile = testFolder.resolve("testFolder").toFile();
        assertTrue(unzippedFile.exists() && unzippedFile.isDirectory());
        assertTrue(unzippedFile.listFiles() != null && unzippedFile.listFiles()[0].isDirectory()
                && unzippedFile.listFiles()[0].getName().equals("test"));
        unzippedFile.delete();
    }
}
