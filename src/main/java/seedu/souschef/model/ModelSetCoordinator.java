package seedu.souschef.model;

import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.model.MealPlanDeletedEvent;
import seedu.souschef.commons.events.model.MealPlannerClearedEvent;
import seedu.souschef.commons.events.model.RecipeDeletedEvent;
import seedu.souschef.commons.events.model.RecipeEditedEvent;
import seedu.souschef.commons.events.storage.SwitchFeatureStorageEvent;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Meal;
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
        registerAsAnEventHandler(this);
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

    private void setFeatureStorage(Context context) {
        EventsCenter.getInstance().post(new SwitchFeatureStorageEvent(context));
    }

    @Subscribe
    protected void handleMealPlanDeletedEvent(MealPlanDeletedEvent event) {
        Day toDelete = event.day;
        healthPlanModel.updateFilteredList(Model.PREDICATE_SHOW_ALL);
        List<HealthPlan> healthPlanList = healthPlanModel.getFilteredList();

        for (HealthPlan h : healthPlanList) {
            if (h.getMealPlans().contains(toDelete)) {
                h.getMealPlans().remove(toDelete);
            }
        }

        setFeatureStorage(Context.HEALTH_PLAN);
        healthPlanModel.indicateAppContentChanged();
        setFeatureStorage(event.context);
    }

    @Subscribe
    protected void handleRecipeDeletedEvent(RecipeDeletedEvent event) {
        Recipe toDelete = event.recipe;
        mealPlannerModel.updateFilteredList(Model.PREDICATE_SHOW_ALL);
        List<Day> mealPlanList = mealPlannerModel.getFilteredList();

        for (Day d : mealPlanList) {
            for (Meal m : d.getMeals()) {
                if (!m.isEmpty() && m.getRecipe().isSame(toDelete)) {
                    m.setRecipe(null);
                    if (d.isEmpty()) {
                        mealPlannerModel.delete(d);
                        EventsCenter.getInstance().post(new MealPlanDeletedEvent(d, Context.RECIPE));
                    }
                }
            }
        }

        setFeatureStorage(Context.MEAL_PLAN);
        mealPlannerModel.indicateAppContentChanged();
        setFeatureStorage(Context.HEALTH_PLAN);
        healthPlanModel.indicateAppContentChanged();
        setFeatureStorage(Context.RECIPE);
    }

    @Subscribe
    protected void handleMealPlannerClearedEvent(MealPlannerClearedEvent event) {
        healthPlanModel.updateFilteredList(Model.PREDICATE_SHOW_ALL);
        List<HealthPlan> hpList = healthPlanModel.getFilteredList();

        for (HealthPlan hp : hpList) {
            hp.getMealPlans().clear();
        }

        setFeatureStorage(Context.HEALTH_PLAN);
        healthPlanModel.indicateAppContentChanged();
        setFeatureStorage(Context.MEAL_PLAN);
    }

    @Subscribe
    protected void handleRecipeEditedEvent(RecipeEditedEvent event) {
        mealPlannerModel.updateFilteredList(Model.PREDICATE_SHOW_ALL);
        List<Day> mealPlanList = mealPlannerModel.getFilteredList();
        Recipe oldRecipe = event.oldRecipe;
        Recipe newRecipe = event.newRecipe;

        for (Day d : mealPlanList) {
            for (Meal m : d.getMeals()) {
                if (!m.isEmpty() && m.getRecipe().isSame(oldRecipe)) {
                    m.setRecipe(newRecipe);
                }
            }
        }

        setFeatureStorage(Context.MEAL_PLAN);
        mealPlannerModel.indicateAppContentChanged();
        setFeatureStorage(Context.HEALTH_PLAN);
        healthPlanModel.indicateAppContentChanged();
        setFeatureStorage(Context.RECIPE);
    }
}
