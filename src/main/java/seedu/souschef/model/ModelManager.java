package seedu.souschef.model;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.souschef.commons.core.ComponentManager;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.model.AppContentChangedEvent;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents the in-memory model of the application content data.
 */
public class ModelManager<T extends UniqueType> extends ComponentManager implements Model<T> {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAppContent versionedAppContent;
    private final FilteredList<Recipe> filteredRecipes;
    private final UniqueList<T> uniqueList;

    /**
     * Initializes a ModelManager with the given appContent and userPrefs.
     */
    public ModelManager(ReadOnlyAppContent appContent, UserPrefs userPrefs) {
        super();
        requireAllNonNull(appContent, userPrefs);

        logger.fine("Initializing with application content: " + appContent + " and user prefs " + userPrefs);

        versionedAppContent = new VersionedAppContent(appContent);
        filteredRecipes = new FilteredList<>(versionedAppContent.getRecipeList());
        uniqueList = (UniqueList<T>) versionedAppContent.getRecipes();
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
    public boolean has(T element) {
        requireNonNull(element);
        return uniqueList.contains(element);
    }

    @Override
    public void delete(T target) {
        uniqueList.remove(target);
        indicateAppContentChanged();
    }

    @Override
    public void add(T target) {
        uniqueList.add(target);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        indicateAppContentChanged();
    }

    @Override
    public void update(T target, T edited) {
        requireAllNonNull(target, edited);
        uniqueList.set(target, edited);
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
                && filteredRecipes.equals(other.filteredRecipes);
    }

}
