package seedu.souschef.model;

import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;
/**
 * Represents the in-memory recipeModel of the application content data.
 */
public class ModelSetCoordinator implements ModelSet {
    private static final Logger logger = LogsCenter.getLogger(ModelSetCoordinator.class);

    private final Model<Recipe> recipeModel;
    private final Model<HealthPlan> healthPlanModel;
    private final Model<Day> mealPlannerModel;
    private final Model<Ingredient> ingredientModel;
    private final Model<CrossRecipe> crossRecipeModel;
    private final Model<Favourites> favouriteModel;
    private final VersionedAppContent versionedAppContent;
    /**
     * Initializes all ModelManagers with the given appContent and userPrefs.
     */
    public ModelSetCoordinator(ReadOnlyAppContent appContent, UserPrefs userPrefs) {
        requireAllNonNull(appContent, userPrefs);
        logger.fine("Initializing with application content: " + appContent + " and user prefs " + userPrefs);
        versionedAppContent = new VersionedAppContent(appContent);
        recipeModel = new ModelManager<>(versionedAppContent, versionedAppContent.getRecipes());
        ingredientModel = new ModelManager<>(versionedAppContent, versionedAppContent.getIngredients());
        crossRecipeModel = new ModelManager<>(versionedAppContent, versionedAppContent.getCrossRecipes());
        healthPlanModel = new ModelManager<>(versionedAppContent, versionedAppContent.getHealthPlans());
        mealPlannerModel = new ModelManager<>(versionedAppContent, versionedAppContent.getMealPlanner());
        favouriteModel = new ModelManager<>(versionedAppContent, versionedAppContent.getFavourites());
    }

    public ModelSetCoordinator() {
        this(new AppContent(), new UserPrefs());
    }

    public ReadOnlyAppContent getAppContent() {
        return versionedAppContent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelSetCoordinator)) {
            return false;
        }

        // state check
        ModelSetCoordinator other = (ModelSetCoordinator) obj;
        return versionedAppContent.equals(other.versionedAppContent)
                && recipeModel.equals(other.recipeModel)
                && healthPlanModel.equals(other.healthPlanModel)
                && mealPlannerModel.equals(other.mealPlannerModel)
                && ingredientModel.equals(other.ingredientModel)
                && crossRecipeModel.equals(other.crossRecipeModel)
                && favouriteModel.equals(other.favouriteModel);
    }

    @Override
    public Model<Recipe> getRecipeModel() {
        return recipeModel;
    }

    @Override
    public Model<Day> getMealPlannerModel() {
        return mealPlannerModel;
    }

    @Override
    public Model<Ingredient> getIngredientModel() {
        return ingredientModel;
    }

    @Override
    public Model<CrossRecipe> getCrossRecipeModel() {
        return crossRecipeModel;
    }

    @Override
    public Model<HealthPlan> getHealthPlanModel() {
        return healthPlanModel;
    }

    @Override
    public Model<Favourites> getFavouriteModel() {
        return favouriteModel;
    }

}
