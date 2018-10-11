package seedu.address.model.route;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.route.exceptions.DuplicateRouteException;
import seedu.address.model.route.exceptions.RouteNotFoundException;

/**
 * A list of routes that enforces uniqueness between its elements and does not allow nulls.
 * A route is considered unique by comparing using {@code Route#isSameRoute(Route)}. As such, adding and updating of
 * routes uses Route#isSameRoute(Route) for equality so as to ensure that the route being added or updated is
 * unique in terms of identity in the UniqueRouteList. However, the removal of a route uses Route#equals(Object) so
 * as to ensure that the route with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Route#isSameRoute(Route)
 */
public class UniqueRouteList implements Iterable<Route> {

    private final ObservableList<Route> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent route as the given argument.
     */
    public boolean contains(Route toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRoute);
    }

    /**
     * Adds a route to the list.
     * The route must not already exist in the list.
     */
    public void add(Route toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRouteException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the route {@code target} in the list with {@code editedRoute}.
     * {@code target} must exist in the list.
     * The route identity of {@code editedRoute} must not be the same as another existing route in the list.
     */
    public void setRoute(Route target, Route editedRoute) {
        requireAllNonNull(target, editedRoute);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RouteNotFoundException();
        }

        if (!target.isSameRoute(editedRoute) && contains(editedRoute)) {
            throw new DuplicateRouteException();
        }

        internalList.set(index, editedRoute);
    }

    /**
     * Removes the equivalent route from the list.
     * The route must exist in the list.
     */
    public void remove(Route toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RouteNotFoundException();
        }
    }

    public void setRoutes(UniqueRouteList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code routes}.
     * {@code routes} must not contain duplicate routes.
     */
    public void setRoutes(List<Route> routes) {
        requireAllNonNull(routes);
        if (!routesAreUnique(routes)) {
            throw new DuplicateRouteException();
        }

        internalList.setAll(routes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Route> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Route> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRouteList // instanceof handles nulls
                && internalList.equals(((UniqueRouteList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code routes} contains only unique routes.
     */
    private boolean routesAreUnique(List<Route> routes) {
        for (int i = 0; i < routes.size() - 1; i++) {
            for (int j = i + 1; j < routes.size(); j++) {
                if (routes.get(i).isSameRoute(routes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
