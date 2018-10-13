package seedu.souschef.model;

import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents the in-memory recipeModel of the application content data.
 */
public class ModelSetCoordinator implements ModelSet {
    private static final Logger logger = LogsCenter.getLogger(ModelSetCoordinator.class);

    private final Model<Recipe> recipeModel;
    private final Model<Ingredient> ingredientModel;
    private final Model<HealthPlan> healthPlanModel;
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
        healthPlanModel = new ModelManager<>(versionedAppContent, versionedAppContent.getHealthPlans());
        // More to be added
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
                && ingredientModel.equals(other.ingredientModel);
    }

    public Model<Recipe> getRecipeModel() {
        return recipeModel;
    }

    public Model<Ingredient> getIngredientModel() {
        return ingredientModel;
    }

    public Model<HealthPlan> getHealthPlanModel() {
        return healthPlanModel;
    }
    // More to be added
}
