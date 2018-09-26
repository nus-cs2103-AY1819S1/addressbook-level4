package seedu.address.model.recipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalRecipes.ALICE;
import static seedu.address.testutil.TypicalRecipes.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.recipe.exceptions.DuplicateRecipeException;
import seedu.address.model.recipe.exceptions.RecipeNotFoundException;
import seedu.address.testutil.RecipeBuilder;

public class UniqueRecipeListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRecipeList uniqueRecipeList = new UniqueRecipeList();

    @Test
    public void contains_nullRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.contains(null);
    }

    @Test
    public void contains_RecipeNotInList_returnsFalse() {
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
        thrown.expect(DuplicateRecipeException.class);
        uniqueRecipeList.add(ALICE);
    }

    @Test
    public void setRecipe_nullTargetRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.setRecipe(null, ALICE);
    }

    @Test
    public void setRecipe_nullEditedRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.setRecipe(ALICE, null);
    }

    @Test
    public void setRecipe_targetRecipeNotInList_throwsRecipeNotFoundException() {
        thrown.expect(RecipeNotFoundException.class);
        uniqueRecipeList.setRecipe(ALICE, ALICE);
    }

    @Test
    public void setRecipe_editedRecipeIsSameRecipe_success() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.setRecipe(ALICE, ALICE);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(ALICE);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasSameIdentity_success() {
        uniqueRecipeList.add(ALICE);
        Recipe editedAlice = new RecipeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueRecipeList.setRecipe(ALICE, editedAlice);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(editedAlice);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasDifferentIdentity_success() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.setRecipe(ALICE, BOB);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BOB);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasNonUniqueIdentity_throwsDuplicateRecipeException() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.add(BOB);
        thrown.expect(DuplicateRecipeException.class);
        uniqueRecipeList.setRecipe(ALICE, BOB);
    }

    @Test
    public void remove_nullRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.remove(null);
    }

    @Test
    public void remove_recipeDoesNotExist_throwsRecipeNotFoundException() {
        thrown.expect(RecipeNotFoundException.class);
        uniqueRecipeList.remove(ALICE);
    }

    @Test
    public void remove_existingRecipe_removesRecipe() {
        uniqueRecipeList.add(ALICE);
        uniqueRecipeList.remove(ALICE);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setrecipes_nullUniqueRecipeList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.setRecipes((UniqueRecipeList) null);
    }

    @Test
    public void setRecipes_uniqueRecipeList_replacesOwnListWithProvidedUniqueRecipeList() {
        uniqueRecipeList.add(ALICE);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BOB);
        uniqueRecipeList.setRecipes(expectedUniqueRecipeList);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecipeList.setRecipes((List<Recipe>) null);
    }

    @Test
    public void setRecipes_list_replacesOwnListWithProvidedList() {
        uniqueRecipeList.add(ALICE);
        List<Recipe> recipeList = Collections.singletonList(BOB);
        uniqueRecipeList.setRecipes(recipeList);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BOB);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_listWithDuplicateRecipes_throwsDuplicateRecipeException() {
        List<Recipe> listWithDuplicateRecipes = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateRecipeException.class);
        uniqueRecipeList.setRecipes(listWithDuplicateRecipes);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecipeList.asUnmodifiableObservableList().remove(0);
    }
}
