package seedu.souschef.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.commons.util.XmlUtil;
import seedu.souschef.model.AppContent;
import seedu.souschef.storage.recipe.XmlSerializableRecipe;
import seedu.souschef.testutil.TypicalRecipes;

public class XmlSerializableRecipeTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableAddressBookTest");
    private static final Path TYPICAL_RECIPES_FILE = TEST_DATA_FOLDER.resolve("typicalRecipesAddressBook.xml");
    private static final Path INVALID_RECIPE_FILE = TEST_DATA_FOLDER.resolve("invalidRecipe.xml");
    private static final Path DUPLICATE_RECIPE_FILE = TEST_DATA_FOLDER.resolve("duplicateRecipeAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalRecipesFile_success() throws Exception {
        XmlSerializableRecipe dataFromFile = XmlUtil.getDataFromFile(TYPICAL_RECIPES_FILE,
                XmlSerializableRecipe.class);
        AppContent addressBookFromFile = dataFromFile.toModelType();
        AppContent typicalRecipesAddressBook = TypicalRecipes.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalRecipesAddressBook);
    }

    @Test
    public void toModelType_invalidRecipeFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRecipe dataFromFile = XmlUtil.getDataFromFile(INVALID_RECIPE_FILE,
                XmlSerializableRecipe.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateRecipes_throwsIllegalValueException() throws Exception {
        XmlSerializableRecipe dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RECIPE_FILE,
                XmlSerializableRecipe.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRecipe.MESSAGE_DUPLICATE_RECIPE);
        dataFromFile.toModelType();
    }

}
