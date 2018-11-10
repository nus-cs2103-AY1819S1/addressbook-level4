package seedu.souschef.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

import seedu.souschef.logic.parser.Context;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.recipe.RecipeBuilder;

/**
 * Stores the history of commands executed.
 */
public class History {
    private LinkedList<String> userInputHistory;
    private Context context;
    private RecipeBuilder recipeBuilder;

    public History() {
        userInputHistory = new LinkedList<>();
        context = Context.RECIPE;
    }

    public History(History history) {
        userInputHistory = new LinkedList<>(history.userInputHistory);
        context = Context.RECIPE;
    }

    /**
     * returns context.
     */
    public Context getContext() {
        return context;
    }

    public String getKeyword() {
        if (context.equals(Context.CROSS)) {
            return "recipe";
        }
        return context.toString().toLowerCase();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Recipe builder to support multi-line command for partial building up.
    public void createRecipeBuilder(RecipeBuilder recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
    }

    public void contributeToRecipe(Instruction instruction) {
        recipeBuilder.addInstruction(instruction);
    }

    /**
     * Create an actual instance of recipe from the builder.
     */
    public Recipe buildRecipe() {
        Recipe recipe = recipeBuilder.build();
        return recipe;
    }

    /**
     * Clear an actual instance of recipe from the builder.
     */
    public void clearRecipe() {
        recipeBuilder = null;
    }

    /**
     * Check if history currently stores a recipe that is being build in mid-way.
     */
    public boolean isBuildingRecipe() {
        return (recipeBuilder != null);
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new LinkedList<>(userInputHistory);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof History)) {
            return false;
        }

        // state check
        History other = (History) obj;
        return userInputHistory.equals(other.userInputHistory);
    }

    @Override
    public int hashCode() {
        return userInputHistory.hashCode();
    }
}
