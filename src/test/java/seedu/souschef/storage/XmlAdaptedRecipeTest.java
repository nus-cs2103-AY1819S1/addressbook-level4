package seedu.souschef.storage;

import static org.junit.Assert.assertEquals;
import static seedu.souschef.storage.recipe.XmlAdaptedRecipe.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.souschef.testutil.TypicalRecipes.BANDITO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.storage.recipe.XmlAdaptedInstruction;
import seedu.souschef.storage.recipe.XmlAdaptedRecipe;
import seedu.souschef.storage.recipe.XmlAdaptedTag;
import seedu.souschef.testutil.Assert;

public class XmlAdaptedRecipeTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DIFFICULTY = "+651234";
    private static final String INVALID_COOKTIME = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BANDITO.getName().toString();
    private static final String VALID_DIFFICULTY = BANDITO.getDifficulty().toString();
    private static final String VALID_COOKTIME = BANDITO.getCookTime().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BANDITO.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedInstruction> VALID_INSTRUCTION = BANDITO.getInstructions().stream()
            .map(XmlAdaptedInstruction::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRecipeDetails_returnsRecipe() throws Exception {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(BANDITO);
        assertEquals(BANDITO, recipe.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(INVALID_NAME, VALID_DIFFICULTY, VALID_COOKTIME, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(null, VALID_DIFFICULTY,
                VALID_COOKTIME, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidDifficulty_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(VALID_NAME, INVALID_DIFFICULTY, VALID_COOKTIME, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullDifficulty_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(VALID_NAME, null, VALID_COOKTIME,
                VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidCooktime_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(VALID_NAME, VALID_DIFFICULTY, INVALID_COOKTIME,
                        VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = CookTime.MESSAGE_COOKTIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullCooktime_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(VALID_NAME, VALID_DIFFICULTY,
                null, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CookTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(VALID_NAME, VALID_DIFFICULTY, VALID_COOKTIME,
                        VALID_INSTRUCTION, invalidTags);
        Assert.assertThrows(IllegalValueException.class, recipe::toModelType);
    }

}
