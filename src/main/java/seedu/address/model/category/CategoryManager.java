package seedu.address.model.category;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;

import seedu.address.model.category.Category;

/**
 * Represents the in-memory model of the address book data.
 */
public class CategoryManager extends ComponentManager {
    private static final Logger logger = LogsCenter.getLogger(CategoryManager.class);
    private final FilteredList<Entry> filteredEntries;

    /**
     * Initializes CategoryManager with the given list of entries.
     */
    public CategoryManager(List<Entry> entries) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing category manager of length: " + entries.size());
        filteredEntries = new FilteredList<>(entries);
    }

    public CategoryManager() {
        this(new List<Entry>());
    }

    /** Sets the list of entries to filter from. */
    public void setList(List<Entry> entries) {
        filteredEntries = new FilteredList<>(entries);
    }

    /** Returns an filtered unmodifiable view of the list of {@code Entry}. */
    public ObservableList<Entry> getList() {
        return FXCollections.unmodifiableObservableList(filteredEntries);
    }

    /** Sets the filter for category view. */
    public void setFilter(Predicate<Entry> predicate) {
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
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return filteredEntries.equals(other.filteredEntries);
    }
}
