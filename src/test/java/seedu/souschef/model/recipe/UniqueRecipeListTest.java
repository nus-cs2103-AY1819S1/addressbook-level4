package seedu.souschef.model.recipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.souschef.testutil.TypicalRecipes.ALICE;
import static seedu.souschef.testutil.TypicalRecipes.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.souschef.model.UniqueList;
import seedu.souschef.model.exceptions.DuplicateException;
import seedu.souschef.model.exceptions.NotFoundException;
import seedu.souschef.model.recipe.exceptions.DuplicateRecipeException;
import seedu.souschef.model.recipe.exceptions.RecipeNotFoundException;
import seedu.souschef.testutil.RecipeBuilder;

public class UniqueRecipeListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private final UniqueList<Recipe> uniqueRecipeList = new UniqueList<>();

    @Test
    public void contains_nullRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.contains(null);
    }

    @Test
    public void contains_recipeNotInList_returnsFalse() {
        assertFalse(uniqueRecipeList.contains(ALICE));
    }

    @Test
    public void contains_recipeInList_returnsTrue() {
        uniqueRecipeList.add(ALICE);
        assertTrue(uniqueRecipeList.contains(ALICE));
    }

    @Test
    public void contains_recipeWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRecipeList.add(ALICE);
        Recipe editedAlice = new RecipeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueRecipeList.contains(editedAlice));
    }

    @Test
    public void add_nullRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.add(null);
    }

    @Test
    public void add_duplicateRecipe_throwsDuplicateRecipeException() {
        uniqueRecipeList.add(ALICE);
        thrown.expect(DuplicateException.class);
        uniqueRecipeList.add(ALICE);
    }

    @Test
    public void setRecipe_nullTargetRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.set(null, ALICE);
    }

    @Test
    public void setRecipe_nullEditedRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.set(ALICE, null);
    }

    @Test
    public void setRecipe_targetRecipeNotInList_throwsRecipeNotFoundException() {
        thrown.expect(NotFoundException.class);
        uniqueRecipeList.set(ALICE, ALICE);
    }

    @Test
    public void setRecipe_editedRecipeIsSameRecipe_success() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.set(ALICE, ALICE);
        UniqueList<Recipe> expectedUniqueRecipeList = new UniqueList<>();
        expectedUniqueRecipeList.add(ALICE);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasSameIdentity_success() {
        uniqueRecipeList.add(ALICE);
        Recipe editedAlice = new RecipeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueRecipeList.set(ALICE, editedAlice);
        UniqueList<Recipe> expectedUniqueRecipeList = new UniqueList<>();
        expectedUniqueRecipeList.add(editedAlice);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasDifferentIdentity_success() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.set(ALICE, BOB);
        UniqueList<Recipe> expectedUniqueRecipeList = new UniqueList<>();
        expectedUniqueRecipeList.add(BOB);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasNonUniqueIdentity_throwsDuplicateRecipeException() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.add(BOB);
        thrown.expect(DuplicateException.class);
        uniqueRecipeList.set(ALICE, BOB);
    }

    @Test
    public void remove_nullRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.remove(null);
    }

    @Test
    public void remove_recipeDoesNotExist_throwsRecipeNotFoundException() {
        thrown.expect(NotFoundException.class);
        uniqueRecipeList.remove(ALICE);
    }

    @Test
    public void remove_existingRecipe_removesRecipe() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.remove(ALICE);
        UniqueList<Recipe> expectedUniqueRecipeList = new UniqueList<>();
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setrecipes_nullUniqueRecipeList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.set((UniqueList<Recipe>) null);
    }

    @Test
    public void setRecipes_uniqueRecipeList_replacesOwnListWithProvidedUniqueRecipeList() {
        uniqueRecipeList.add(ALICE);
        UniqueList<Recipe> expectedUniqueRecipeList = new UniqueList<>();
        expectedUniqueRecipeList.add(BOB);
        uniqueRecipeList.set(expectedUniqueRecipeList);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.set((List<Recipe>) null);
    }

    @Test
    public void setRecipes_list_replacesOwnListWithProvidedList() {
        uniqueRecipeList.add(ALICE);
        List<Recipe> recipeList = Collections.singletonList(BOB);
        uniqueRecipeList.set(recipeList);
        UniqueList<Recipe> expectedUniqueRecipeList = new UniqueList<>();
        expectedUniqueRecipeList.add(BOB);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_listWithDuplicateRecipes_throwsDuplicateRecipeException() {
        List<Recipe> listWithDuplicateRecipes = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateException.class);
        uniqueRecipeList.set(listWithDuplicateRecipes);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecipeList.asUnmodifiableObservableList().remove(0);
    }
}
