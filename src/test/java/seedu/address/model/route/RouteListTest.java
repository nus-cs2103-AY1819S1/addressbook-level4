package seedu.address.model.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRoutes.ANGMOKIO;
import static seedu.address.testutil.TypicalRoutes.getTypicalRouteList;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.testutil.RouteBuilder;

public class RouteListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RouteList routeList = new RouteList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), routeList.getRouteList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        routeList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyRouteList_replacesData() {
        RouteList newData = getTypicalRouteList();
        routeList.resetData(newData);
        assertEquals(newData, routeList);
    }

    @Test
    public void hasRoute_nullRoute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        routeList.hasRoute(null);
    }

    @Test
    public void hasRoute_routeNotInRouteList_returnsFalse() {
        assertFalse(routeList.hasRoute(ANGMOKIO));
    }

    @Test
    public void hasRoute_routeInRouteList_returnsTrue() {
        routeList.addRoute(ANGMOKIO);
        assertTrue(routeList.hasRoute(ANGMOKIO));
    }

    @Test
    public void hasRoute_routeWithSameIdentityFieldsInRouteList_returnsTrue() {
        routeList.addRoute(ANGMOKIO);
        Route editedAlice = new RouteBuilder(ANGMOKIO).build();
        assertTrue(routeList.hasRoute(editedAlice));
    }

    @Test
    public void getRouteList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        routeList.getRouteList().remove(0);
    }

    /**
     * A stub ReadOnlyRouteList whose routes list can violate interface constraints.
     */
    private static class RouteListStub implements ReadOnlyRouteList {
        private final ObservableList<Route> routes = FXCollections.observableArrayList();

        RouteListStub(Collection<Route> routes) {
            this.routes.setAll(routes);
        }

        @Override
        public ObservableList<Route> getRouteList() {
            return routes;
        }
    }

}
