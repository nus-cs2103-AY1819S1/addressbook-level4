package seedu.address.model.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalRoutes.ANGMOKIO;
import static seedu.address.testutil.TypicalRoutes.BEDOK;
import static seedu.address.testutil.TypicalRoutes.CHINATOWN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.RouteListBuilder;

public class VersionedRouteListTest {

    private final ReadOnlyRouteList routeListWithAlice = new RouteListBuilder().withRoute(ANGMOKIO).build();
    private final ReadOnlyRouteList routeListWithBenny = new RouteListBuilder().withRoute(BEDOK).build();
    private final ReadOnlyRouteList routeListWithCharlie = new RouteListBuilder().withRoute(CHINATOWN).build();
    private final ReadOnlyRouteList emptyRouteList = new RouteListBuilder().build();

    @Test
    public void commit_singleRouteList_noStatesRemovedCurrentStateSaved() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        versionedRouteList.commit();
        assertRouteListListStatus(versionedRouteList,
                Collections.singletonList(emptyRouteList),
                emptyRouteList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleRouteListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);

        versionedRouteList.commit();
        assertRouteListListStatus(versionedRouteList,
                Arrays.asList(emptyRouteList, routeListWithAlice, routeListWithBenny),
                routeListWithBenny,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleRouteListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        versionedRouteList.commit();
        assertRouteListListStatus(versionedRouteList,
                Collections.singletonList(emptyRouteList),
                emptyRouteList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleRouteListPointerAtEndOfStateList_returnsTrue() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);

        assertTrue(versionedRouteList.canUndo());
    }

    @Test
    public void canUndo_multipleRouteListPointerAtStartOfStateList_returnsTrue() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);

        assertTrue(versionedRouteList.canUndo());
    }

    @Test
    public void canUndo_singleRouteList_returnsFalse() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        assertFalse(versionedRouteList.canUndo());
    }

    @Test
    public void canUndo_multipleRouteListPointerAtStartOfStateList_returnsFalse() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        assertFalse(versionedRouteList.canUndo());
    }

    @Test
    public void canRedo_multipleRouteListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);

        assertTrue(versionedRouteList.canRedo());
    }

    @Test
    public void canRedo_multipleRouteListPointerAtStartOfStateList_returnsTrue() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        assertTrue(versionedRouteList.canRedo());
    }

    @Test
    public void canRedo_singleRouteList_returnsFalse() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        assertFalse(versionedRouteList.canRedo());
    }

    @Test
    public void canRedo_multipleRouteListPointerAtEndOfStateList_returnsFalse() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);

        assertFalse(versionedRouteList.canRedo());
    }

    @Test
    public void undo_multipleRouteListPointerAtEndOfStateList_success() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);

        versionedRouteList.undo();
        assertRouteListListStatus(versionedRouteList,
                Collections.singletonList(emptyRouteList),
                routeListWithAlice,
                Collections.singletonList(routeListWithBenny));
    }

    @Test
    public void undo_multipleRouteListPointerNotAtStartOfStateList_success() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);

        versionedRouteList.undo();
        assertRouteListListStatus(versionedRouteList,
                Collections.emptyList(),
                emptyRouteList,
                Arrays.asList(routeListWithAlice, routeListWithBenny));
    }

    @Test
    public void undo_singleRouteList_throwsNoUndoableStateException() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        assertThrows(VersionedRouteList.NoUndoableStateException.class, versionedRouteList::undo);
    }

    @Test
    public void undo_multipleRouteListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        assertThrows(VersionedRouteList.NoUndoableStateException.class, versionedRouteList::undo);
    }

    @Test
    public void redo_multipleRouteListPointerNotAtEndOfStateList_success() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);

        versionedRouteList.redo();
        assertRouteListListStatus(versionedRouteList,
                Arrays.asList(emptyRouteList, routeListWithAlice),
                routeListWithBenny,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleRouteListPointerAtStartOfStateList_success() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        versionedRouteList.redo();
        assertRouteListListStatus(versionedRouteList,
                Collections.singletonList(emptyRouteList),
                routeListWithAlice,
                Collections.singletonList(routeListWithBenny));
    }

    @Test
    public void redo_singleRouteList_throwsNoRedoableStateException() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        assertThrows(VersionedRouteList.NoRedoableStateException.class, versionedRouteList::redo);
    }

    @Test
    public void redo_multipleRouteListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAlice, routeListWithBenny);

        assertThrows(VersionedRouteList.NoRedoableStateException.class, versionedRouteList::redo);
    }

    @Test
    public void equals() {
        VersionedRouteList versionedRouteList = prepareRouteListList(routeListWithAlice, routeListWithBenny);

        // same values -> returns true
        VersionedRouteList copy = prepareRouteListList(routeListWithAlice, routeListWithBenny);
        assertTrue(versionedRouteList.equals(copy));

        // same object -> returns true
        assertTrue(versionedRouteList.equals(versionedRouteList));

        // null -> returns false
        assertFalse(versionedRouteList.equals(null));

        // different types -> returns false
        assertFalse(versionedRouteList.equals(1));

        // different state list -> returns false
        VersionedRouteList differentRouteListList = prepareRouteListList(routeListWithBenny, routeListWithCharlie);
        assertFalse(versionedRouteList.equals(differentRouteListList));

        // different current pointer index -> returns false
        VersionedRouteList differentCurrentStatePointer = prepareRouteListList(
                routeListWithAlice, routeListWithBenny);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);
        assertFalse(versionedRouteList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedRouteList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedRouteList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedRouteList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertRouteListListStatus(VersionedRouteList versionedRouteList,
                                             List<ReadOnlyRouteList> expectedStatesBeforePointer,
                                             ReadOnlyRouteList expectedCurrentState,
                                             List<ReadOnlyRouteList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new RouteList(versionedRouteList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedRouteList.canUndo()) {
            versionedRouteList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyRouteList expectedRouteList : expectedStatesBeforePointer) {
            assertEquals(expectedRouteList, new RouteList(versionedRouteList));
            versionedRouteList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyRouteList expectedRouteList : expectedStatesAfterPointer) {
            versionedRouteList.redo();
            assertEquals(expectedRouteList, new RouteList(versionedRouteList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedRouteList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedRouteList.undo());
    }

    /**
     * Creates and returns a {@code VersionedRouteList} with the {@code routeListStates} added into it, and the
     * {@code VersionedRouteList#currentStatePointer} at the end of list.
     */
    private VersionedRouteList prepareRouteListList(ReadOnlyRouteList... routeListStates) {
        assertFalse(routeListStates.length == 0);

        VersionedRouteList versionedRouteList = new VersionedRouteList(routeListStates[0]);
        for (int i = 1; i < routeListStates.length; i++) {
            versionedRouteList.resetData(routeListStates[i]);
            versionedRouteList.commit();
        }

        return versionedRouteList;
    }

    /**
     * Shifts the {@code versionedRouteList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedRouteList versionedRouteList, int count) {
        for (int i = 0; i < count; i++) {
            versionedRouteList.undo();
        }
    }
}
