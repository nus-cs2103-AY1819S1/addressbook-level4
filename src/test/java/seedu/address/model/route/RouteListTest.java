package seedu.address.model.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalRoutes.ALICE;
import static seedu.address.testutil.TypicalRoutes.getTypicalRouteList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.route.exceptions.DuplicateRouteException;
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
    public void resetData_withDuplicateRoutes_throwsDuplicateRouteException() {
        // Two routes with the same identity fields
        Route editedAlice = new RouteBuilder(ALICE).withDeliveryman(VALID_NAME_BOB).build();
        List<Route> newRoutes = Arrays.asList(ALICE, editedAlice);
        RouteListStub newData = new RouteListStub(newRoutes);

        thrown.expect(DuplicateRouteException.class);
        routeList.resetData(newData);
    }

    @Test
    public void hasRoute_nullRoute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        routeList.hasRoute(null);
    }

    @Test
    public void hasRoute_personNotInRouteList_returnsFalse() {
        assertFalse(routeList.hasRoute(ALICE));
    }

    @Test
    public void hasRoute_personInRouteList_returnsTrue() {
        routeList.addRoute(ALICE);
        assertTrue(routeList.hasRoute(ALICE));
    }

    @Test
    public void hasRoute_personWithSameIdentityFieldsInRouteList_returnsTrue() {
        routeList.addRoute(ALICE);
        Route editedAlice = new RouteBuilder(ALICE).build();
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
