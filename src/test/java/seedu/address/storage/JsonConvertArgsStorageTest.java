package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.model.transformation.Transformation;


public class JsonConvertArgsStorageTest {

    public static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonConvertArgsStorageTest");

    @Test
    public void assertStorageSuccessfully() {
        try {
            File sampleFile = new File(TEST_DATA_FOLDER.toString() + "/blurR.json");
            if (sampleFile.exists()) {
                sampleFile.delete();
            }
            List<Transformation> cmds = new ArrayList<>();
            cmds.add(new Transformation("blur", "0x8"));
            cmds.add(new Transformation("rotate", "90"));
            JsonConvertArgsStorage.storeArgument("blurR", cmds, TEST_DATA_FOLDER.toString());
            if (!sampleFile.exists()) {
                fail();
            }
        } catch (IOException e) {
            throw new AssertionError("There should not be an error storing the operation", e);
        }
    }

    @Test
    public void assertStorageUnsuccessfully() {
        try {
            List<Transformation> cmds = new ArrayList<>();
            cmds.add(new Transformation("blur", "0x8, rotate, 90"));
            JsonConvertArgsStorage.storeArgument("blurR", cmds, TEST_DATA_FOLDER.toString() + "/something");
            fail();
        } catch (IOException e) {
            if (!e.getMessage().contains("No such file or directory")) {
                fail();
            }
        }
    }

    @Test
    public void assertRetrieveArgumentSuccessfully() {
        try {
            File file = TEST_DATA_FOLDER.resolve("blurR.json").toFile();
            List<String> sampleArgs = new ArrayList<>(Arrays.asList("blur", "0x8", "-rotate", "90"));
            List<String> args = JsonConvertArgsStorage.retrieveCommandArguments(file);
            assertEquals(args, sampleArgs);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void assertRetrieveArgumentUnSuccessfully() {
        try {
            File file = TEST_DATA_FOLDER.resolve("fake.json").toFile();
            JsonConvertArgsStorage.retrieveCommandArguments(file);
            fail();
        } catch (IOException e) {
            if (!e.getMessage().contains("No such file or directory")) {
                fail();
            }
        }
    }

    @Test
    public void assertRetrieveOperationTemplateSuccessfully() throws IOException {
        URL fileUrl = ImageMagickUtil.class.getResource("/imageMagic/commandTemplates/blur.json");
        List<String> sampleTemplate = Arrays.asList("radius(0-99) x sigma(0-99)");
        List<String> template = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, "blur", "arg");
        assertEquals(template, sampleTemplate);
    }

    @Test
    public void assertRetrieveOperationTemplateUnSuccessfully() {
        try {
            URL fileUrl = ImageMagickUtil.class.getResource("/imageMagic/commandTemplates/fake.json");
            JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, "blur", "arg");
            fail();
        } catch (IOException e) {
            if (!e.getMessage().contains("the url is invalid")) {
                fail();
            }
        }
    }
}
