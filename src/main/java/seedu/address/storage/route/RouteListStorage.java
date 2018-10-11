package seedu.address.storage.route;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.route.ReadOnlyRouteList;

/**
 * Represents a storage for {@link seedu.address.model.route.RouteList}.
 */
public interface RouteListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRouteListFilePath();

    /**
     * Returns a list of routes data as a {@link ReadOnlyRouteList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRouteList> readRouteList() throws DataConversionException, IOException;

    /**
     * @see #getRouteListFilePath()
     */
    Optional<ReadOnlyRouteList> readRouteList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRouteList} to the storage.
     * @param routeList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRouteList(ReadOnlyRouteList routeList) throws IOException;

    /**
     * @see #saveRouteList(ReadOnlyRouteList)
     */
    void saveRouteList(ReadOnlyRouteList routeList, Path filePath) throws IOException;

}
