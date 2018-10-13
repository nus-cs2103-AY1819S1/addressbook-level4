package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyArticleList;

/**
 * Represents a storage for {@link seedu.address.model.ArticleList}.
 */
public interface ArticleListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getArticleListFilePath();

    /**
     * Returns ArticleList data as a {@link ReadOnlyArticleList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyArticleList> readArticleList() throws DataConversionException, IOException;

    /**
     * @see #getArticleListFilePath()
     */
    Optional<ReadOnlyArticleList> readArticleList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyArticleList} to the storage.
     * @param articleList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveArticleList(ReadOnlyArticleList articleList) throws IOException;

    /**
     * @see #saveArticleList(ReadOnlyArticleList)
     */
    void saveArticleList(ReadOnlyArticleList articleList, Path filePath) throws IOException;

}
