package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
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
public class TagManager extends ComponentManager {
    private static final Logger logger = LogsCenter.getLogger(TagManager.class);
    private FilteredList<ResumeEntry> filteredEntries;

    /**
     * Initializes CategoryManager with the given list of entries.
     */
    public TagManager(ObservableList<ResumeEntry> entries) {
        super();
        requireAllNonNull(entries);

        logger.fine("Initializing tag manager of length: " + entries.size());
        filteredEntries = new FilteredList<ResumeEntry>(entries);
    }

    public TagManager(List<ResumeEntry> entries) {
        this(FXCollections.observableList(entries));
    }

    public TagManager() {
        this(Arrays.asList());
    }

    /** Returns an filtered unmodifiable view of the list of {@code ResumeEntry}. */
    public ObservableList<ResumeEntry> getList() {
        return FXCollections.unmodifiableObservableList(filteredEntries);
    }

    /** Sets the source for filtered list. */
    public void setList(List<ResumeEntry> entries) {
        filteredEntries = new FilteredList<ResumeEntry>(FXCollections.observableList(entries));
    }

    /** Sets the filter for tag view. */
    public void setPredicate(Predicate<ResumeEntry> predicate) {
        requireNonNull(predicate);
        filteredEntries.setPredicate(predicate);
    }

    /** Returns a {@code Predicate<ResumeEntry>} that does the filtering */
    public Predicate<ResumeEntry> mkPredicate (List<String> tags) {
        requireNonNull(tags);
        return new ContainsTagsPredicate(tags);
    }

    /** Returns a {@code Predicate<ResumeEntry>} that does the filtering with single tag */
    public Predicate<ResumeEntry> mkPredicate (String tag) {
        return mkPredicate(Arrays.asList(tag));
    }

    /** Returns a {@code Predicate<ResumeEntry>} that adds on to the predicate with tag filtering */
    public Predicate<ResumeEntry> mkPredicate (Predicate<ResumeEntry> predicate, List<String> tags) {
        Predicate<ResumeEntry> tagPredicate = mkPredicate(tags);

        return new Predicate<ResumeEntry>() {
            @Override
            public boolean test(ResumeEntry entry) {
                return predicate.test(entry) && tagPredicate.test(entry);
            }
        };
    }

    /** Returns a {@code Predicate<ResumeEntry>} that does the filtering */
    public Predicate<ResumeEntry> mkPredicate (Predicate<ResumeEntry> predicate, String tag) {
        return mkPredicate(predicate, Arrays.asList(tag));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof TagManager)) {
            return false;
        }

        // state check
        TagManager other = (TagManager) obj;
        return filteredEntries.equals(other.filteredEntries);
    }
}
