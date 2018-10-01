package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.AppContent;
import seedu.address.storage.XmlAdaptedRecipe;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.testutil.AppContentBuilder;
import seedu.address.testutil.RecipeBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validAddressBook.xml");
    private static final Path MISSING_RECIPE_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingRecipeField.xml");
    private static final Path INVALID_RECIPE_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidRecipeField.xml");
    private static final Path VALID_RECIPE_FILE = TEST_DATA_FOLDER.resolve("validRecipe.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, AppContent.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, AppContent.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, AppContent.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        AppContent dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableAddressBook.class).toModelType();
        assertEquals(9, dataFromFile.getRecipeList().size());
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithMissingRecipeField_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                MISSING_RECIPE_FIELD_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe = new XmlAdaptedRecipe(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithInvalidRecipeField_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                INVALID_RECIPE_FIELD_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe = new XmlAdaptedRecipe(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithValidRecipe_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                VALID_RECIPE_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe = new XmlAdaptedRecipe(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new AppContent());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new AppContent());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableAddressBook dataToWrite = new XmlSerializableAddressBook(new AppContent());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);

        AppContentBuilder builder = new AppContentBuilder(new AppContent());
        dataToWrite = new XmlSerializableAddressBook(
                builder.withRecipe(new RecipeBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedRecipe}
     * objects.
     */
    @XmlRootElement(name = "recipe")
    private static class XmlAdaptedRecipeWithRootElement extends XmlAdaptedRecipe {}
}
