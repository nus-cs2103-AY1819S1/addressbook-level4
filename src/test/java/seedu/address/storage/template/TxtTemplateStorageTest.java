package seedu.address.storage.template;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.model.template.Template;
import seedu.address.storage.TxtTemplateStorage;

public class TxtTemplateStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "TxtTemplateStorageTest");
    private static final Path VALID_TEMPLATE = Paths.get("validTemplate.txt");
    private static final Path RANDOM_FORMAT_FILE = Paths.get("randomFormatTemplate.txt");
    private static final Path INVALID_ROWS_TEMPLATE = Paths.get("invalidRowsTemplate.txt");
    private static final Path VALID_AND_INVALID_ROWS_TEMPLATE = Paths.get("validAndInvalidRowsTemplate.txt");
    private static final Path INVALID_FILEPATH = Paths.get("nonExistentTemplate.txt");
    private static final Path BLANK_FILE = Paths.get("blankFile.txt");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Test
    public void loadTemplate_validFilePath_success() throws Exception {
        Template loaded = new TxtTemplateStorage().loadTemplate(TEST_DATA_FOLDER.resolve(VALID_TEMPLATE));
        assertEquals(Template.getDefaultTemplate().getSectionsString(), loaded.getSectionsString());
    }

    @Test
    public void loadTemplate_blankTemplateFile_success() throws Exception {
        Template loaded = new TxtTemplateStorage().loadTemplate(TEST_DATA_FOLDER.resolve(BLANK_FILE));
        assertEquals(new ArrayList<>(), loaded.getSections()); // no sections
    }

    @Test
    public void loadTemplate_invalidFilePath_throwsIoException() throws Exception {
        thrown.expect(IOException.class);
        new TxtTemplateStorage().loadTemplate(TEST_DATA_FOLDER.resolve(INVALID_FILEPATH));
    }

    @Test
    public void loadTemplate_randomFile_throwsInvalidTemplateFileException() throws Exception {
        thrown.expect(InvalidTemplateFileException.class);
        Template loaded = new TxtTemplateStorage().loadTemplate(TEST_DATA_FOLDER.resolve(RANDOM_FORMAT_FILE));
    }

    @Test
    public void loadTemplate_invalidRows_throwsInvalidTemplateFileException() throws Exception {
        thrown.expect(InvalidTemplateFileException.class);
        new TxtTemplateStorage().loadTemplate(TEST_DATA_FOLDER.resolve(INVALID_ROWS_TEMPLATE));
    }

    @Test
    public void loadTemplate_validAndInvalidRows_throwsInvalidTemplateFileException() throws Exception {
        thrown.expect(InvalidTemplateFileException.class);
        new TxtTemplateStorage().loadTemplate(TEST_DATA_FOLDER.resolve(VALID_AND_INVALID_ROWS_TEMPLATE));
    }

    @Test
    public void loadTemplate_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        new TxtTemplateStorage().loadTemplate(null);
    }
}
