package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBudgetBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface BudgetBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBudgetBookFilePath();

    /**
     * Returns BudgetBook data as a {@link ReadOnlyBudgetBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBudgetBook> readBudgetBook() throws DataConversionException, IOException;

    /**
     * @see #getBudgetBookFilePath()
     */
    Optional<ReadOnlyBudgetBook> readBudgetBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBudgetBook} to the storage.
     * @param budgetBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBudgetBook(ReadOnlyBudgetBook budgetBook) throws IOException;

    /**
     * @see #saveBudgetBook(ReadOnlyBudgetBook)
     */
    void saveBudgetBook(ReadOnlyBudgetBook budgetBook, Path filePath) throws IOException;

}
