package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.resume.Resume;
import seedu.address.model.template.Template;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ResumeEntry> PREDICATE_SHOW_ALL_ENTRIES = entry -> true;

    /**
     * returns the entryBook
     */
    ReadOnlyEntryBook getEntryBook();

    /**
     *
     * @param entry to be searched
     * @return true if an entry with the same identity as {@code entry} exists in the ResuMaker.
     */
    boolean hasEntry(ResumeEntry entry);

    /**
     * Deletes the given entry.
     * The entry must exist in the entry book.
     */
    void deleteEntry(ResumeEntry target);

    /**
     * Adds the given entry.
     * {@code entry} must not already exist in the address book.
     */
    void addEntry(ResumeEntry entry);

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the entry book.
     * The Entry identity of {@code editedEntry} must not be the same as another existing entry in the address book.
     */
    void updateEntry(ResumeEntry target, ResumeEntry editedEntry);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ResumeEntry> getFilteredEntryList();

    /** If args given, filtered from full list */
    ObservableList<ResumeEntry> getFilteredEntryList(Predicate<ResumeEntry> predicate);
    ObservableList<ResumeEntry> getFilteredEntryList(String category, List<String> tags);

    /** Returns predicate based on category and tags given */
    Predicate<ResumeEntry> mkPredicate(String category, List<String> tags);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEntryList(Predicate<ResumeEntry> predicate);

    /**
     * Returns the user particulars.
     */
    UserParticulars getUserParticulars();

    /**
     * Loads a template from the specified filePath.
     */
    void loadTemplate(Path filePath);

    /**
     * Returns the currently loaded template.
     */
    Optional<Template> getLoadedTemplate();

    /**
     * Saves the current entry book state for undo/redo.
     */
    void commitEntryBook();

    /**
     * Generates a resume using the current EntryBook and Template.
     */
    void generateResume();

    /**
     * Fetches the last Resume generated (if any).
     */
    Optional<Resume> getLastResume();

    /**
     * Saves the last Resume generated to the specified filePath.
     */
    void saveLastResume(Path filePath);

    /**
     * Returns the name of an Event that possibly matches a given expression.
     * This may not be part of the Model API in V1.4.
     * I have added it here to ensure a user testable feature for V1.2 submission.
     */
    String getPossibleEventName(String expression);

    /**
     * Returns a pre-filled ResumeEntry (in an Optional) for the given possibleEventName, or an empty Optional
     * if no matching ResumeEntry is found.
     */
    Optional<ResumeEntry> getContextualResumeEntry(String possibleEventName);
}
