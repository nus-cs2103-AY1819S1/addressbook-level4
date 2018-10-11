package seedu.address.model.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.TypicalRoutes.ANGMOKIO;
import static seedu.address.testutil.TypicalRoutes.BEDOK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.route.exceptions.DuplicateRouteException;
import seedu.address.model.route.exceptions.RouteNotFoundException;
import seedu.address.testutil.RouteBuilder;

public class UniqueRouteListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRouteList uniqueRouteList = new UniqueRouteList();

    @Test
    public void contains_nullRoute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRouteList.contains(null);
    }

    @Test
    public void contains_routeNotInList_returnsFalse() {
        assertFalse(uniqueRouteList.contains(ANGMOKIO));
    }

    @Test
    public void contains_routeInList_returnsTrue() {
        uniqueRouteList.add(ANGMOKIO);
        assertTrue(uniqueRouteList.contains(ANGMOKIO));
    }

    @Test
    public void add_nullRoute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRouteList.add(null);
    }

    @Test
    public void add_duplicateRoute_throwsDuplicateRouteException() {
        uniqueRouteList.add(ANGMOKIO);
        thrown.expect(DuplicateRouteException.class);
        uniqueRouteList.add(ANGMOKIO);
    }

    @Test
    public void setRoute_nullTargetRoute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRouteList.setRoute(null, ANGMOKIO);
    }

    @Test
    public void setRoute_nullEditedRoute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRouteList.setRoute(ANGMOKIO, null);
    }

    @Test
    public void setRoute_targetRouteNotInList_throwsRouteNotFoundException() {
        thrown.expect(RouteNotFoundException.class);
        uniqueRouteList.setRoute(ANGMOKIO, ANGMOKIO);
    }

    @Test
    public void setRoute_editedRouteIsSameRoute_success() {
        uniqueRouteList.add(ANGMOKIO);
        uniqueRouteList.setRoute(ANGMOKIO, ANGMOKIO);
        UniqueRouteList expectedUniqueRouteList = new UniqueRouteList();
        expectedUniqueRouteList.add(ANGMOKIO);
        assertEquals(expectedUniqueRouteList, uniqueRouteList);
    }

    @Test
    public void setRoute_editedRouteHasSameIdentity_success() {
        uniqueRouteList.add(ANGMOKIO);
        Route editedAngmokio = new RouteBuilder(ANGMOKIO)
                .withDestination(VALID_ADDRESS_BOB).build();
        uniqueRouteList.setRoute(ANGMOKIO, editedAngmokio);
        UniqueRouteList expectedUniqueRouteList = new UniqueRouteList();
        expectedUniqueRouteList.add(editedAngmokio);
        assertEquals(expectedUniqueRouteList, uniqueRouteList);
    }

    @Test
    public void setRoute_editedRouteHasDifferentIdentity_success() {
        uniqueRouteList.add(ANGMOKIO);
        uniqueRouteList.setRoute(ANGMOKIO, BEDOK);
        UniqueRouteList expectedUniqueRouteList = new UniqueRouteList();
        expectedUniqueRouteList.add(BEDOK);
        assertEquals(expectedUniqueRouteList, uniqueRouteList);
    }

    @Test
    public void setRoute_editedRouteHasNonUniqueIdentity_throwsDuplicateRouteException() {
        uniqueRouteList.add(ANGMOKIO);
        uniqueRouteList.add(BEDOK);
        thrown.expect(DuplicateRouteException.class);
        uniqueRouteList.setRoute(ANGMOKIO, BEDOK);
    }

    @Test
    public void remove_nullRoute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRouteList.remove(null);
    }

    @Test
    public void remove_routeDoesNotExist_throwsRouteNotFoundException() {
        thrown.expect(RouteNotFoundException.class);
        uniqueRouteList.remove(ANGMOKIO);
    }

    @Test
    public void remove_existingRoute_removesRoute() {
        uniqueRouteList.add(ANGMOKIO);
        uniqueRouteList.remove(ANGMOKIO);
        UniqueRouteList expectedUniqueRouteList = new UniqueRouteList();
        assertEquals(expectedUniqueRouteList, uniqueRouteList);
    }

    @Test
    public void setRoutes_nullUniqueRouteList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRouteList.setRoutes((UniqueRouteList) null);
    }

    @Test
    public void setRoutes_uniqueRouteList_replacesOwnListWithProvidedUniqueRouteList() {
        uniqueRouteList.add(ANGMOKIO);
        UniqueRouteList expectedUniqueRouteList = new UniqueRouteList();
        expectedUniqueRouteList.add(BEDOK);
        uniqueRouteList.setRoutes(expectedUniqueRouteList);
        assertEquals(expectedUniqueRouteList, uniqueRouteList);
    }

    @Test
    public void setRoutes_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRouteList.setRoutes((List<Route>) null);
    }

    @Test
    public void setRoutes_list_replacesOwnListWithProvidedList() {
        uniqueRouteList.add(ANGMOKIO);
        List<Route> routeList = Collections.singletonList(BEDOK);
        uniqueRouteList.setRoutes(routeList);
        UniqueRouteList expectedUniqueRouteList = new UniqueRouteList();
        expectedUniqueRouteList.add(BEDOK);
        assertEquals(expectedUniqueRouteList, uniqueRouteList);
    }

    @Test
    public void setRoutes_listWithDuplicateRoutes_throwsDuplicateRouteException() {
        List<Route> listWithDuplicateRoutes = Arrays.asList(ANGMOKIO, ANGMOKIO);
        thrown.expect(DuplicateRouteException.class);
        uniqueRouteList.setRoutes(listWithDuplicateRoutes);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRouteList.asUnmodifiableObservableList().remove(0);
    }
}
