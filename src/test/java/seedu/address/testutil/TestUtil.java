package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelToDo;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the calendarevent in the {@code model}'s calendarevent list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredAndSortedCalendarEventList().size() / 2);
    }

    /**
     * Returns the last index of the calendarevent in the {@code model}'s calendarevent list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredAndSortedCalendarEventList().size());
    }

    /**
     * Returns the calendarevent in the {@code model}'s calendarevent list at {@code index}.
     */
    public static CalendarEvent getPerson(Model model, Index index) {
        return model.getFilteredAndSortedCalendarEventList().get(index.getZeroBased());
    }

    /**
     * Returns the todolistevent in the {@code modelToDo}'s todolistevent list at {@code index}.
     */
    public static ToDoListEvent getTask(ModelToDo modelToDo, Index index) {
        return modelToDo.getFilteredToDoListEventList().get(index.getZeroBased());
    }
}
