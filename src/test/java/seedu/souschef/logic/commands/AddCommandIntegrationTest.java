package seedu.souschef.logic.commands;

import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model<Recipe> model;
    private History history = new History();

    @Before
    public void setUp() {
        model = new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
    }

    @Test
    public void execute_newPerson_success() {
        Recipe validRecipe = new RecipeBuilder().build();

        Model<Recipe> expectedModel = new ModelSetCoordinator(model.getAppContent(), new UserPrefs()).getRecipeModel();
        expectedModel.add(validRecipe);
        expectedModel.commitAppContent();

        assertCommandSuccess(new AddCommand<Recipe>(model, validRecipe), model, history,
                String.format(AddCommand.MESSAGE_ADD_SUCCESS, "recipe", validRecipe), expectedModel);
    }

    /*@Test
    public void execute_duplicatePerson_throwsCommandException() {
        Recipe recipeInList = model.getAppContent().getObservableRecipeList().get(0);
        assertCommandFailure(new AddCommand(model, recipeInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_RECIPE);
    }*/

}
