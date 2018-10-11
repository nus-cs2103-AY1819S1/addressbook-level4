package seedu.address.model.route;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.TypicalRoutes.ANGMOKIO;
import static seedu.address.testutil.TypicalRoutes.BEDOK;

import org.junit.Test;

import seedu.address.testutil.RouteBuilder;

public class RouteTest {

    @Test
    public void isSameRoute() {
        // same object -> returns true
        assertTrue(ANGMOKIO.isSameRoute(ANGMOKIO));

        // null -> returns false
        assertFalse(ANGMOKIO.isSameRoute(null));

        // different destination -> returns false
        Route editedAngmokio = new RouteBuilder(ANGMOKIO).withDestination(VALID_ADDRESS_BOB).build();
        assertFalse(ANGMOKIO.isSameRoute(editedAngmokio));

        // different source -> returns false
        editedAngmokio = new RouteBuilder(ANGMOKIO).withSource(VALID_ADDRESS_BOB).build();
        assertFalse(ANGMOKIO.isSameRoute(editedAngmokio));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Route aliceCopy = new RouteBuilder(ANGMOKIO).build();
        assertTrue(ANGMOKIO.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ANGMOKIO.equals(ANGMOKIO));

        // null -> returns false
        assertFalse(ANGMOKIO.equals(null));

        // different type -> returns false
        assertFalse(ANGMOKIO.equals(5));

        // different route -> returns false
        assertFalse(ANGMOKIO.equals(BEDOK));

        // different source -> returns false
        Route editedAngmokio = new RouteBuilder(ANGMOKIO).withSource(VALID_ADDRESS_BOB).build();
        assertFalse(ANGMOKIO.equals(editedAngmokio));

        // different destination -> returns false
        editedAngmokio = new RouteBuilder(ANGMOKIO).withDestination(VALID_ADDRESS_BOB).build();
        assertFalse(ANGMOKIO.equals(editedAngmokio));

    }
}
