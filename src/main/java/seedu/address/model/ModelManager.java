package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.EntryBookChangedEvent;
import seedu.address.commons.events.model.ResumeSaveEvent;
import seedu.address.commons.events.model.TemplateLoadRequestedEvent;
import seedu.address.commons.events.storage.TemplateLoadedEvent;
import seedu.address.commons.events.storage.TemplateLoadingExceptionEvent;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.category.CategoryManager;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.resume.Resume;
import seedu.address.model.tag.TagManager;
import seedu.address.model.template.Template;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Awareness awareness;
    private final UserParticulars userParticulars;
    private final VersionedEntryBook versionedEntryBook;
    private final FilteredList<ResumeEntry> filteredEntries;

    // Tag and Category managers
    private final TagManager tagManager;
    private final CategoryManager categoryManager;

    private Template loadedTemplate;
    private Resume lastGeneratedResume;

    /**
     * Initializes a ModelManager with the given entrybook, userPrefs and awareness object
     */
    public ModelManager(ReadOnlyEntryBook entryBook, UserPrefs userPrefs,
                        Awareness newAwareness) {
        super();
        requireAllNonNull(entryBook, userPrefs);

        logger.fine("Initializing with entry book: " + entryBook + " and user prefs " + userPrefs);

        loadedTemplate = null;
        awareness = newAwareness;
        userParticulars = userPrefs.getUserParticulars();

        versionedEntryBook = new VersionedEntryBook(entryBook);
        filteredEntries = new FilteredList<>(versionedEntryBook.getEntryList());

        tagManager = new TagManager();
        categoryManager = new CategoryManager();
    }

    public ModelManager() {
        this(new EntryBook(), new UserPrefs(), new Awareness());
    }

    @Override
    public ReadOnlyEntryBook getEntryBook() {
        return versionedEntryBook;
    }

    //=========== Entry Book Changes ===============================================================================

    /** Raises an event to indicate the model has changed */
    private void indicateEntryBookChanged() {
        raise (new EntryBookChangedEvent(versionedEntryBook));
    }

    @Override
    public boolean hasEntry(ResumeEntry entry) {
        requireNonNull(entry);
        return versionedEntryBook.hasEntry(entry);
    }

    @Override
    public void addEntry(ResumeEntry entry) {
        versionedEntryBook.addEntry(entry);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        indicateEntryBookChanged();
    }

    @Override
    public void deleteEntry(ResumeEntry target) {
        versionedEntryBook.removeEntry(target);
        indicateEntryBookChanged();
    }

    @Override
    public void updateEntry(ResumeEntry target, ResumeEntry editedEntry) {
        requireAllNonNull(target, editedEntry);

        versionedEntryBook.updateEntry(target, editedEntry);
        indicateEntryBookChanged();
    }

    @Override
    public void commitEntryBook() {
        versionedEntryBook.commit();
    }

    //=========== Filtered Entry List ===============================================================================

    /**
     * Returns an unmodifiable view of the list of {@code Entry} backed by the internal list of
     * {@code versionedEntryBook}, if arguments given, will filter full list instead.
     */
    @Override
    public ObservableList<ResumeEntry> getFilteredEntryList() {
        return FXCollections.unmodifiableObservableList(filteredEntries);
    }

    @Override
    public ObservableList<ResumeEntry> getFilteredEntryList(Predicate<ResumeEntry> predicate) {
        FilteredList<ResumeEntry> entries = getFullEntryList();
        entries.setPredicate(predicate);

        return FXCollections.unmodifiableObservableList(entries);
    }

    @Override
    public ObservableList<ResumeEntry> getFilteredEntryList(String category, List<String> tags) {
        return getFilteredEntryList(mkPredicate(category, tags));
    }

    @Override
    public Predicate<ResumeEntry> mkPredicate(String category, List<String> tags) {
        Predicate<ResumeEntry> predicate = category.length() > 0
            ? categoryManager.mkPredicate(category)
            : PREDICATE_SHOW_ALL_ENTRIES;

        return tags.size() > 0
            ? tagManager.mkPredicate(predicate, tags)
            : predicate;
    }

    private FilteredList<ResumeEntry> getFullEntryList() {
        return new FilteredList<ResumeEntry>(versionedEntryBook.getEntryList());
    }

    @Override
    public void updateFilteredEntryList(Predicate<ResumeEntry> predicate) {
        requireNonNull(predicate);
        filteredEntries.setPredicate(predicate);
    }

    //=========== Particulars ===============================================================================

    @Override
    public UserParticulars getUserParticulars() {
        return userParticulars;
    }

    //=========== Template ==================================================================================

    @Override
    public void loadTemplate(Path filePath) {
        raise(new TemplateLoadRequestedEvent(filePath));
    }

    @Override
    public Optional<Template> getLoadedTemplate() {
        // up to the Generation part to raise
        // NewResultAvailableEvent to say no template loaded

        return Optional.ofNullable(loadedTemplate);
    }

    //=========== Awareness accessors =======================================================================
    @Override
    public String getPossibleEventName(String expression) {
        return awareness.getPossibleEventName(expression);
    }

    @Override
    public Optional<ResumeEntry> getContextualResumeEntry(String possibleEventName) {
        return awareness.getContextualResumeEntry(possibleEventName);
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

        return versionedEntryBook.equals(other.versionedEntryBook)
                && filteredEntries.equals(other.filteredEntries)
                && categoryManager.equals(other.categoryManager)
                && tagManager.equals(other.tagManager)
                && awareness.equals(other.awareness)
                && nullCheck(loadedTemplate, other.loadedTemplate)
                && nullCheck(lastGeneratedResume, other.lastGeneratedResume);
    }

    private boolean nullCheck(Object a, Object b) {
        return (a == b || a.equals(b));
    }

    //=========== Resume generation =======================================================================

    public void generateResume() {
        lastGeneratedResume = new Resume(this);
    }

    public Optional<Resume> getLastResume() {
        return Optional.ofNullable(lastGeneratedResume);
    }

    public void saveLastResume(Path filePath) {
        raise(new ResumeSaveEvent(lastGeneratedResume, filePath));
    }

    //=========== Listener for template loading =============================================================
    @Subscribe
    public void handleTemplateLoadedEvent(TemplateLoadedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Template loaded from " + event.filePath.toString() + " to Model"));
        loadedTemplate = event.getTemplate();
    }

    @Subscribe
    public void handleTemplateLoadingExceptionEvent(TemplateLoadingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Exception when attempting to load template from "
                + event.filePath.toString()));
        // if there was a previous template, it remains as the active one
    }
}
