package seedu.souschef.model;

import static org.junit.Assert.assertEquals;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_STAPLE;
import static seedu.souschef.testutil.TypicalRecipes.APPLE;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.souschef.model.exceptions.DuplicateException;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.testutil.RecipeBuilder;

public class AppContentTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AppContent appContent = new AppContent();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), appContent.getObservableRecipeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        appContent.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AppContent newData = getTypicalAddressBook();
        appContent.resetData(newData);
        assertEquals(newData, appContent);
    }

    @Test
    public void resetData_withDuplicateRecipes_throwsDuplicateRecipeException() {
        // Two recipes with the same identity fields
        Recipe editedAlice = new RecipeBuilder(APPLE).withTags(VALID_TAG_STAPLE)
                .build();
        List<Recipe> newRecipes = Arrays.asList(APPLE, editedAlice);
        AppContentStub newData = new AppContentStub(newRecipes);

        thrown.expect(DuplicateException.class);
        appContent.resetData(newData);
    }

    @Test
    public void getRecipeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        appContent.getObservableRecipeList().remove(0);
    }

    /**
     * A stub ReadOnlyAppContent whose lists can violate interface constraints.
     */
    private static class AppContentStub implements ReadOnlyAppContent {
        private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        private final ObservableList<CrossRecipe> crossRecipes = FXCollections.observableArrayList();
        private final ObservableList<HealthPlan> plans = FXCollections.observableArrayList();
        private final ObservableList<Day> mealPlanner = FXCollections.observableArrayList();
        private final ObservableList<Recipe> favourites = FXCollections.observableArrayList();

        AppContentStub(Collection<Recipe> recipes) {
            this.recipes.setAll(recipes);
        }

        @Override
        public ObservableList<Recipe> getObservableRecipeList() {
            return recipes;
        }

        @Override
        public ObservableList<Ingredient> getObservableIngredientList() {
            return ingredients;
        }

        @Override
        public ObservableList<CrossRecipe> getObservableCrossRecipeList() {
            return crossRecipes;
        }

        @Override
        public ObservableList<HealthPlan> getObservableHealthPlanList () {
            return plans;
        }

        @Override
        public ObservableList<Day> getObservableMealPlanner() {
            return mealPlanner;
        }

        @Override
        public ObservableList<Recipe> getObservableFavouritesList() {
            return favourites;
        }
    }
}
