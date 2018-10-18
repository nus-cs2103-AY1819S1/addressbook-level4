package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.SaveItChangedEvent;
import seedu.address.commons.util.CollectionUtil;

//import javafx.collections.transformation.SortedList;
//import seedu.address.model.issue.IssueSort;

/**
 * Represents the in-memory model of the saveIt data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static final Index ROOT_DIRECTORY = Index.fromZeroBased(0);
    private final VersionedSaveIt versionedSaveIt;
    private FilteredList<Issue> filteredIssues;

    /**
     * Initializes a ModelManager with the given saveIt and userPrefs.
     */
    public ModelManager(ReadOnlySaveIt saveIt, UserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(saveIt, userPrefs);

        logger.fine("Initializing with SaveIt: " + saveIt + " and user prefs " + userPrefs);

        versionedSaveIt = new VersionedSaveIt(saveIt);
        filteredIssues = new FilteredList<>(versionedSaveIt.getIssueList());
    }

    public ModelManager() {
        this(new SaveIt(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlySaveIt newData) {
        versionedSaveIt.resetData(newData);
        indicateSaveItChanged();
    }

    @Override
    public void resetDirectory(Index targetIndex, boolean rootDirectory) {
        if (rootDirectory) {
            versionedSaveIt.setCurrentDirectory(targetIndex.getZeroBased());
        } else {
            versionedSaveIt.setCurrentDirectory(targetIndex.getOneBased());
        }
        indicateSaveItChanged();
    }

    @Override
    public int getCurrentDirectory() {
        return versionedSaveIt.getCurrentDirectory();
    }

    @Override
    public ReadOnlySaveIt getSaveIt() {
        return versionedSaveIt;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateSaveItChanged() {
        raise(new SaveItChangedEvent(versionedSaveIt));
    }

    @Override
    public boolean hasIssue(Issue issue) {
        requireNonNull(issue);
        return versionedSaveIt.hasIssue(issue);
    }

    @Override
    public void deleteIssue(Issue target) {
        versionedSaveIt.removeIssue(target);
        indicateSaveItChanged();
    }

    @Override
    public void addIssue(Issue issue) {
        versionedSaveIt.addIssue(issue);
        updateFilteredIssueList(PREDICATE_SHOW_ALL_ISSUES);
        indicateSaveItChanged();
    }

    @Override
    public void updateIssue(Issue target, Issue editedIssue) {
        CollectionUtil.requireAllNonNull(target, editedIssue);

        versionedSaveIt.updateIssue(target, editedIssue);
        indicateSaveItChanged();
    }

    @Override
    public void filterIssues(Predicate<Issue> predicate) {
        updateFilteredIssueList(predicate);
        // Update the search frequencies after filtering
        for (Issue issue : filteredIssues) {
            issue.updateFrequency();
        }

        // Sorts properly but the UI is not listing properly
        // SortedList sortedFilteredList = new SortedList<>(filteredIssues, new IssueSort());
    }

    //=========== Filtered Issue List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Issue} backed by the internal list of
     * {@code versionedSaveIt}
     */
    @Override
    public ObservableList<Issue> getFilteredIssueList() {
        return FXCollections.unmodifiableObservableList(filteredIssues);
    }

    @Override
    public void updateFilteredIssueList(Predicate<Issue> predicate) {
        requireNonNull(predicate);
        filteredIssues.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoSaveIt() {
        return versionedSaveIt.canUndo();
    }

    @Override
    public boolean canRedoSaveIt() {
        return versionedSaveIt.canRedo();
    }

    @Override
    public void undoSaveIt() {
        versionedSaveIt.undo();
        indicateSaveItChanged();
    }

    @Override
    public void redoSaveIt() {
        versionedSaveIt.redo();
        indicateSaveItChanged();
    }

    @Override
    public void commitSaveIt() {
        versionedSaveIt.commit();
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
        return versionedSaveIt.equals(other.versionedSaveIt)
                && filteredIssues.equals(other.filteredIssues);
    }

}
