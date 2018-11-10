package seedu.souschef.model;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.souschef.commons.core.ComponentManager;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.model.AppContentChangedEvent;

/**
 * Represents one domain of the in-memory recipeModel of the application content data.
 */
public class ModelManager<T extends UniqueType> extends ComponentManager implements Model<T> {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAppContent versionedAppContent;

    private final FilteredList<T> filteredList;
    private final UniqueList<T> uniqueList;


    /**
     * Initializes a ModelManager with the given appContent and its uniqueType.
     */
    public ModelManager(VersionedAppContent appContent, UniqueList<T> ul) {
        super();
        requireAllNonNull(appContent, ul);
        logger.fine("Initializing with application content: " + appContent + " and unique list " + ul);


        versionedAppContent = appContent;
        filteredList = new FilteredList<>(ul.asUnmodifiableObservableList());
        uniqueList = ul;

    }

    @Override
    public ReadOnlyAppContent getAppContent() {
        return versionedAppContent;
    }

    /** Raises an event to indicate the recipeModel has changed */
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
        updateFilteredList(PREDICATE_SHOW_ALL);
        indicateAppContentChanged();
    }

    @Override
    public void update(T target, T edited) {
        requireAllNonNull(target, edited);
        uniqueList.set(target, edited);
        indicateAppContentChanged();
    }

    @Override
    public void resetList() {
        this.uniqueList.set(new UniqueList<T>());
        indicateAppContentChanged();
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code versionedAppContent}
     */
    @Override
    public ObservableList<T> getFilteredList() {
        return FXCollections.unmodifiableObservableList(filteredList);
    }

    @Override
    public void updateFilteredList(Predicate predicate) {
        requireNonNull(predicate);
        filteredList.setPredicate(predicate);
    }

    @Override
    public void sort(Comparator<T> comparator) {
        uniqueList.sortList(comparator);
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

                && filteredList.equals(other.filteredList);

    }

}
