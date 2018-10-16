package seedu.address.model.category;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;

import seedu.address.model.entry.ResumeEntry;

/**
 * Manages category of the entries.
 */
public class CategoryManager extends ComponentManager {
    private static final Logger logger = LogsCenter.getLogger(CategoryManager.class);
    private final FilteredList<ResumeEntry> filteredEntries;

    /**
     * Initializes CategoryManager with the given list of entries.
     */
    public CategoryManager(ObservableList<ResumeEntry> entries) {
        super();
        requireAllNonNull(entries);

        logger.fine("Initializing category manager of length: " + entries.size());
        filteredEntries = new FilteredList<ResumeEntry>(entries);
    }

    public CategoryManager(List<ResumeEntry> entries) {
        this(FXCollections.observableList(entries));
    }

    public CategoryManager() {
        this(new ArrayList<ResumeEntry>());
    }

    /** Returns an filtered unmodifiable view of the list of {@code ResumeEntry}. */
    public ObservableList<ResumeEntry> getList() {
        return FXCollections.unmodifiableObservableList(filteredEntries);
    }

    /** Sets the filter for category view. */
    public void setFilter(Predicate<ResumeEntry> predicate) {
        requireNonNull(predicate);
        filteredEntries.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CategoryManager)) {
            return false;
        }

        // state check
        CategoryManager other = (CategoryManager) obj;
        return filteredEntries.equals(other.filteredEntries);
    }
}
