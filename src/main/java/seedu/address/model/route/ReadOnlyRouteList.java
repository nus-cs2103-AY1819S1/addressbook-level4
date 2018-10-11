package seedu.address.model.route;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a route list
 */
public interface ReadOnlyRouteList {

    /**
     * Returns an unmodifiable view of the routes list.
     * This list will not contain any duplicate routes.
     */
    ObservableList<Route> getRouteList();

}
