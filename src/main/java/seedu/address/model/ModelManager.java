package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AppContentChangedEvent;
import seedu.address.model.healthplan.HealthPlan;
import seedu.address.model.recipe.Recipe;

/**
 * Represents the in-memory model of the application content data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAppContent versionedAppContent;
    private final FilteredList<Recipe> filteredRecipes;
    private final FilteredList<HealthPlan>filteredPlans;

    /**
     * Initializes a ModelManager with the given appContent and userPrefs.
     */
    public ModelManager(ReadOnlyAppContent appContent, UserPrefs userPrefs) {
        super();
        requireAllNonNull(appContent, userPrefs);

        logger.fine("Initializing with application content: " + appContent + " and user prefs " + userPrefs);

        versionedAppContent = new VersionedAppContent(appContent);
        filteredRecipes = new FilteredList<>(versionedAppContent.getRecipeList());
        filteredPlans = new FilteredList<>(versionedAppContent.getHealthPlanList());
    }

    public ModelManager() {
        this(new AppContent(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAppContent newData) {
        versionedAppContent.resetData(newData);
        indicateAppContentChanged();
    }

    @Override
    public ReadOnlyAppContent getAppContent() {
        return versionedAppContent;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAppContentChanged() {
        raise(new AppContentChangedEvent(versionedAppContent));
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return versionedAppContent.hasRecipe(recipe);
    }

    @Override
    public void deleteRecipe(Recipe target) {
        versionedAppContent.removeRecipe(target);
        indicateAppContentChanged();
    }

    @Override
    public void addRecipe(Recipe recipe) {
        versionedAppContent.addRecipe(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        indicateAppContentChanged();
    }

    @Override
    public void updateRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);

        versionedAppContent.updateRecipe(target, editedRecipe);
        indicateAppContentChanged();
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code versionedAppContent}
     */
    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return FXCollections.unmodifiableObservableList(filteredRecipes);
    }



    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredRecipes.setPredicate(predicate);
    }


    //health plans

    @Override
    public ObservableList<HealthPlan> getFilteredPlans() {
        return FXCollections.unmodifiableObservableList(filteredPlans);
    }

    @Override
    public void updateFilteredPlans(Predicate<HealthPlan> predicate) {
        requireNonNull(predicate);
        filteredPlans.setPredicate(predicate);
    }

    @Override
    public boolean hasPlan(HealthPlan hp) {
        requireNonNull(hp);
        return versionedAppContent.hasPlan(hp);
    }

    @Override
    public void deletePlan(HealthPlan hp) {
        versionedAppContent.removePlan(hp);
        indicateAppContentChanged();
    }

    @Override
    public void addPlan(HealthPlan hp) {
        versionedAppContent.addPlan(hp);

        updateFilteredPlans(PREDICATE_SHOW_ALL_PLANS);
        indicateAppContentChanged();
    }

    @Override
    public void updatePlan(HealthPlan target, HealthPlan editedPlan) {
        requireAllNonNull(target, editedPlan);

        versionedAppContent.updatePlan(target, editedPlan);
        indicateAppContentChanged();
    }







    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAppContent() {
        return versionedAppContent.canUndo();
    }

    @Override
    public boolean canRedoAppContent() {
        return versionedAppContent.canRedo();
    }

    @Override
    public void undoAppContent() {
        versionedAppContent.undo();
        indicateAppContentChanged();
    }

    @Override
    public void redoAppContent() {
        versionedAppContent.redo();
        indicateAppContentChanged();
    }

    @Override
    public void commitAppContent() {
        versionedAppContent.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAppContent.equals(other.versionedAppContent)
                && filteredRecipes.equals(other.filteredRecipes)
         && filteredPlans.equals(other.filteredPlans);
    }

}
