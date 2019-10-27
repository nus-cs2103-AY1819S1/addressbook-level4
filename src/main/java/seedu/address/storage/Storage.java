package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.EntryBookChangedEvent;
import seedu.address.commons.events.model.ResumeSaveEvent;
import seedu.address.commons.events.model.TemplateLoadRequestedEvent;
import seedu.address.commons.events.storage.TemplateLoadedEvent;
import seedu.address.commons.events.storage.TemplateLoadingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.model.ReadOnlyEntryBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.resume.Resume;
import seedu.address.model.template.Template;
import seedu.address.storage.entry.EntryBookStorage;

/**
 * API of the Storage component
 */
public interface Storage extends EntryBookStorage, UserPrefsStorage, TemplateStorage, ResumeStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    // ENTRYBOOK
    @Override
    Path getEntryBookFilePath();

    @Override
    Optional<ReadOnlyEntryBook> readEntryBook() throws DataConversionException, IOException;

    @Override
    void saveEntryBook(ReadOnlyEntryBook entryBook) throws IOException;

    @Override
    Path getTemplateFilePath();

    @Override
    Template loadTemplate() throws IOException, InvalidTemplateFileException;

    @Override
    Template loadTemplate(Path filePath) throws IOException, InvalidTemplateFileException;

    /**
     * Attempts to load the template from the hard disk.
     * Raises {@link TemplateLoadedEvent} if it is successful, or {@link TemplateLoadingExceptionEvent}
     * if there was an error during saving.
     */
    void handleEntryBookChangedEvent(EntryBookChangedEvent event);

    /**
     * Attempts to load the template from the hard disk.
     * Raises {@link TemplateLoadedEvent} if it is successful, or {@link TemplateLoadingExceptionEvent}
     * if there was an error during saving.
     */
    void handleTemplateLoadRequestedEvent(TemplateLoadRequestedEvent event);

    @Override
    void saveResume(Resume resume) throws IOException;

    @Override
    void saveResume(Resume resume, Path filePath) throws IOException;

    void handleResumeSaveEvent(ResumeSaveEvent sre);
}
