package seedu.souschef.commons.util;

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

import seedu.souschef.model.AppContent;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.storage.recipe.XmlAdaptedInstruction;
import seedu.souschef.storage.recipe.XmlAdaptedRecipe;
import seedu.souschef.storage.recipe.XmlAdaptedTag;
import seedu.souschef.storage.recipe.XmlSerializableRecipe;
import seedu.souschef.testutil.AppContentBuilder;
import seedu.souschef.testutil.RecipeBuilder;
import seedu.souschef.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validAddressBook.xml");
    private static final Path MISSING_RECIPE_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingRecipeField.xml");
    private static final Path INVALID_RECIPE_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidRecipeField.xml");
    private static final Path VALID_RECIPE_FILE = TEST_DATA_FOLDER.resolve("validRecipe.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml");

    private static final String INVALID_DIFFICULTY = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_DIFFICULTY = "5";
    private static final String VALID_COOKTIME = "23M";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("asian"));
    private static final List<XmlAdaptedInstruction> VALID_INSTRUCTION = Collections
            .singletonList(new XmlAdaptedInstruction(new Instruction("Preheat oven.")));

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
        AppContent dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableRecipe.class).toModelType();
        assertEquals(9, dataFromFile.getObservableRecipeList().size());
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithMissingRecipeField_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                MISSING_RECIPE_FIELD_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe = new XmlAdaptedRecipe(
                null, VALID_DIFFICULTY, VALID_COOKTIME, VALID_INSTRUCTION, VALID_TAGS);
        assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithInvalidRecipeField_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                INVALID_RECIPE_FIELD_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe = new XmlAdaptedRecipe(
                VALID_NAME, INVALID_DIFFICULTY, VALID_COOKTIME, VALID_INSTRUCTION, VALID_TAGS);
        assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithValidRecipe_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                VALID_RECIPE_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe = new XmlAdaptedRecipe(
                VALID_NAME, VALID_DIFFICULTY, VALID_COOKTIME, VALID_INSTRUCTION, VALID_TAGS);
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
        XmlSerializableRecipe dataToWrite = new XmlSerializableRecipe(new AppContent());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableRecipe dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableRecipe.class);
        assertEquals(dataToWrite, dataFromFile);

        AppContentBuilder builder = new AppContentBuilder(new AppContent());
        dataToWrite = new XmlSerializableRecipe(
                builder.withRecipe(new RecipeBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableRecipe.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedRecipe}
     * objects.
     */
    @XmlRootElement(name = "recipe")
    private static class XmlAdaptedRecipeWithRootElement extends XmlAdaptedRecipe {}
}
